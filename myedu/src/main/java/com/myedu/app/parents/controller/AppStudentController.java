package com.myedu.app.parents.controller;

import com.myedu.common.utils.ServletUtils;
import com.myedu.framework.aspectj.lang.annotation.AutoIdempotent;
import com.myedu.framework.aspectj.lang.annotation.Log;
import com.myedu.framework.aspectj.lang.enums.BusinessType;
import com.myedu.framework.redis.RedisCache;
import com.myedu.framework.security.LoginUser;
import com.myedu.framework.security.service.TokenService;
import com.myedu.framework.web.controller.BaseController;
import com.myedu.framework.web.domain.AjaxResult;
import com.myedu.framework.web.page.TableDataInfo;
import com.myedu.project.parents.domain.YunStudent;
import com.myedu.project.parents.domain.vo.YunStudentVo;
import com.myedu.project.parents.service.IYunStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ${梁少鹏}
 * Date: 2019/12/28
 * Time: 18:45
 * Description:App家长端学生管理
 */
@Api("APP用户信息管理")
@RestController
@RequestMapping("/app/parents/student")
public class AppStudentController extends BaseController {
    @Autowired
    private IYunStudentService yunStudentService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;
    /**
     * @Description :查询当前用户下的学生
     * @Author : 梁少鹏
     * @Date : 2019/12/28 20:28
     */
    @AutoIdempotent
    @ApiOperation("查询当前用户下的学生")
    @ApiImplicitParam(name = "yunStudentVo", value = "查询当前用户下的学生",
            dataType = "YunStudentVo")
    @GetMapping("/getMyStudent")
    public TableDataInfo  getMyStudent(YunStudentVo yunStudentVo)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (loginUser!=null){
            startPage();
            yunStudentVo.setCreateById(loginUser.getUser().getUserId());
            List<YunStudentVo> list = yunStudentService.selectYunStudentList(yunStudentVo);
            return getDataTable(list);
        }else{
            return getDataTableLose(null);
        }
    }
    /**
     * @Description :获取学生详情
     * @Author : 梁少鹏
     * @Date : 2019/12/28 20:32
     */
    @AutoIdempotent
    @ApiOperation("获取学生详情")
    @ApiImplicitParam(name = "id", value = "获取学生详情", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/getStudnetById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult getStudnetById(@PathVariable Long id)
    {
        return AjaxResult.success(yunStudentService.selectYunStudentById(id));
    }
    /**
     * @Description :家长端增加学生
     * @Author : 梁少鹏
     * @Date : 2019/12/28 18:49
     */
    @AutoIdempotent
    @ApiOperation("增加学生")
    @ApiImplicitParam(name = "yunStudent", value = "增加学生", dataType = "YunStudent")
    @Log(title = "学生数据", businessType = BusinessType.INSERT)
    @PostMapping("/addStudent")
    public AjaxResult addStudent(@Valid @RequestBody YunStudent yunStudent) throws IOException {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        yunStudent.setCreateById(loginUser.getUser().getUserId());
        yunStudent.setCreateBy(loginUser.getUser().getNickName());
        return toAjax(yunStudentService.insertYunStudent(yunStudent));
    }
    /**
     * @Description :修改学生数据
     * @Author : 梁少鹏
     * @Date : 2019/12/28 20:35
     */
    @AutoIdempotent
    @ApiOperation("修改学生")
    @ApiImplicitParam(name = "yunStudent", value = "修改学生", dataType = "YunStudent")
    @Log(title = "学生数据", businessType = BusinessType.UPDATE)
    @PostMapping("/editStudent")
    public AjaxResult editStudent(@RequestBody YunStudent yunStudent)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        yunStudent.setUpdateBy(loginUser.getUser().getNickName());
        return toAjax(yunStudentService.updateYunStudent(yunStudent));
    }
    /**
     * @Description :删除学生
     * @Author : 梁少鹏
     * @Date : 2019/12/28 20:39
     */
    @AutoIdempotent
    @ApiOperation("删除学生")
    @ApiImplicitParam(name = "ids", value = "删除学生", dataType = "Long[]")
    @Log(title = "学生数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletStudentByIds/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
       return toAjax(yunStudentService.deleteYunStudentByIds(ids));
    }

}
