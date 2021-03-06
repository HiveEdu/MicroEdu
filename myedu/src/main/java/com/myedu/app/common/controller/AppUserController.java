package com.myedu.app.common.controller;

import com.myedu.common.constant.Constants;
import com.myedu.common.constant.UserConstants;
import com.myedu.common.utils.*;
import com.myedu.common.utils.sign.Base64;
import com.myedu.framework.aspectj.lang.annotation.AutoIdempotent;
import com.myedu.framework.redis.RedisCache;
import com.myedu.framework.security.LoginUser;
import com.myedu.framework.security.service.SysLoginService;
import com.myedu.framework.security.service.SysPermissionService;
import com.myedu.framework.security.service.TokenService;
import com.myedu.framework.web.controller.BaseController;
import com.myedu.framework.web.domain.AjaxResult;
import com.myedu.project.system.domain.SysRole;
import com.myedu.project.system.domain.SysUser;
import com.myedu.project.system.domain.vo.UserLoginVO;
import com.myedu.project.system.service.ISysRoleService;
import com.myedu.project.system.service.ISysUserService;
import com.myedu.project.system.service.impl.SysUserServiceImpl;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
/**
 * Created with IntelliJ IDEA.
 * User: 梁少鹏
 * Date: 2019/12/20
 * Time: 22:30
 * Description:
 */
@Api("APP用户信息管理")
@RestController
@RequestMapping("/app/user")
public class AppUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private SysUserServiceImpl sysUserService;
    /**
   * @Description :
   * @Author : 梁少鹏
   * @Date : 2019/12/21 7:48
   */
    @ApiOperation("用户注册")
    @PostMapping("/userRegister")
    public AjaxResult saveUser(@RequestBody  @ApiParam(name="userLogin",value="用户登录信息",required=true) UserLoginVO userLogin)
    {if(UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(userLogin.getUserName())))
        {
            return AjaxResult.error("新增用户'" + userLogin.getUserName() + "'失败，手机号码已存在");
        }
        SysUser user=new SysUser();
        user.setUserName(userLogin.getUserName());
        user.setPhonenumber(userLogin.getUserName());
        user.setPassword(SecurityUtils.encryptPassword(userLogin.getPassword()));
        user.setRoleIds(userLogin.getRoleIds());
        return toAjax(userService.insertUser(user));
    }

    /**
     * @Description :修改用户
     * @Author : 梁少鹏
     * @Date : 2019/12/21 7:48
     */
    @AutoIdempotent
    @ApiOperation("修改用户")
    @ApiImplicitParam(name = "SysUser", value = "修改用户信息", dataType = "SysUser")
    @PostMapping("/editUser")
    public AjaxResult editUser(SysUser user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        user.setUserName(user.getUserName());
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

   /**
    * @Description :App登录方法
    * @Author : 梁少鹏
    * @Date : 2019/12/28 18:42
    */
    @ApiOperation("APP用户登录")
    @PostMapping(value = "/appLogin")
    public AjaxResult appLogin(@RequestBody  @ApiParam(name="userLogin",value="用户登录信息",required=true) UserLoginVO userLogin)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(userLogin.getUserName(), userLogin.getPassword(), userLogin.getCode(), userLogin.getUuid());
        ajax.put(Constants.TOKEN, token);
        SysUser user=userService.selectUserByUserName(userLogin.getUserName());
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }
    /**
     * @Description :获取用户详情
     * @Author : 梁少鹏
     * @Date : 2020/2/1 20:07
     */
    @AutoIdempotent
    @ApiOperation("获取用户详情")
    @PostMapping("/getUserInfo")
    public AjaxResult getUserInfo()
    {
        // 获取请求携带的令牌
        String token = tokenService.getToken(ServletUtils.getRequest());
        LoginUser loginUser =null;
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims = tokenService.parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = tokenService.getTokenKey(uuid);
            loginUser = redisCache.getCacheObject(userKey);
        }
        AjaxResult ajax = AjaxResult.success();
        if(loginUser!=null){
            SysUser user = loginUser.getUser();
            // 角色集合
            Set<String> roles = permissionService.getRolePermission(user);
            // 权限集合
            Set<String> permissions = permissionService.getMenuPermission(user);

            ajax.put("user", user);
            ajax.put("roles", roles);
            ajax.put("permissions", permissions);
        }else{
            ajax.put("user", null);
        }
        return ajax;
    }
    /**
     * @Description :获取角色列表
     * @Author : 梁少鹏
     * @Date : 2020/2/1 20:15
     */
    @ApiOperation("获取角色列表")
    @ApiImplicitParam(name = "HttpServletResponse", value = "获取角色列表")
    @GetMapping("/getRoleList")
    public AjaxResult getRoleList() throws IOException {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> list = roleService.selectRoleAll().stream().filter(r->!r.getRoleName().equals("管理员")).collect(Collectors.toList());
        ajax.put("roleList", list);
        return ajax;
    }
    /**
     * @Description 生成APP登录验证码
     * @Author : 梁少鹏
     * @Date : 2019/12/28 19:32
     */
    @ApiOperation("APP用户登录验证码生成")
    @ApiImplicitParam(name = "HttpServletResponse", value = "APP用户登录验证码生成")
    @GetMapping("/getAppCodeImage")
    public AjaxResult getAppCode(HttpServletResponse response) throws IOException
    {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try
        {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("uuid", uuid);
            ajax.put("img", Base64.encode(stream.toByteArray()));
            return ajax;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        finally
        {
            stream.close();
        }
    }


    /**
     * @Description :token校验
     * @Author : 梁少鹏
     * @Date : 2020/2/1 19:35
     */
    @ApiOperation("token校验")
    @GetMapping("/CheckToken")
    public AjaxResult CheckToken() throws IOException
    {
        // 获取请求携带的令牌
        String token = tokenService.getToken(ServletUtils.getRequest());
        LoginUser loginUser =null;
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims = tokenService.parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = tokenService.getTokenKey(uuid);
            loginUser = redisCache.getCacheObject(userKey);
        }
        AjaxResult ajax = AjaxResult.success();
        if(loginUser==null){
            ajax.error("token无效");
        }else{
            ajax.put(Constants.TOKEN, token);
        }
        return  ajax;
    }

    public Boolean CheckTokenBen() throws IOException
    {
        Boolean result=true;
        // 获取请求携带的令牌
        String token = tokenService.getToken(ServletUtils.getRequest());
        LoginUser loginUser =null;
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims = tokenService.parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = tokenService.getTokenKey(uuid);
            loginUser = redisCache.getCacheObject(userKey);
        }
        AjaxResult ajax = AjaxResult.success();
        if(loginUser==null){
            ajax.error("token无效");
            result=false;
        }else{
            result=true;
            ajax.put(Constants.TOKEN, token);
        }
        return  result;
    }
    /**
     * @Description :token刷新
     * @Author : 梁少鹏
     * @Date : 2020/2/1 19:35
     */
    @ApiOperation("token刷新")
    @GetMapping("/verifyToken")
    public AjaxResult verifyToken() throws IOException
    {
        // 获取请求携带的令牌
        String token = tokenService.getToken(ServletUtils.getRequest());
        LoginUser loginUser =null;
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims = tokenService.parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = tokenService.getTokenKey(uuid);
            loginUser = redisCache.getCacheObject(userKey);
        }
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            tokenService.verifyToken(loginUser);
        }
        AjaxResult ajax = AjaxResult.success();
        return  ajax;
    }

}
