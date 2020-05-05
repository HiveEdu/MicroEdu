package com.myedu.app.order.controller;

import com.myedu.common.utils.DateUtils;
import com.myedu.common.utils.SecurityUtils;
import com.myedu.common.utils.ServletUtils;
import com.myedu.common.utils.StringUtils;
import com.myedu.framework.aspectj.lang.annotation.AutoIdempotent;
import com.myedu.framework.aspectj.lang.annotation.Log;
import com.myedu.framework.aspectj.lang.enums.BusinessType;
import com.myedu.framework.security.LoginUser;
import com.myedu.framework.security.service.TokenService;
import com.myedu.framework.web.controller.BaseController;
import com.myedu.framework.web.domain.AjaxResult;
import com.myedu.framework.web.page.TableDataInfo;
import com.myedu.project.dataBasic.domain.SysGrade;
import com.myedu.project.dataBasic.service.ISysGradeService;
import com.myedu.project.order.domain.YunOrder;
import com.myedu.project.order.domain.vo.YunOrderVo;
import com.myedu.project.order.enums.StoreStuStatus;
import com.myedu.project.order.service.IYunOrderService;
import com.myedu.project.parents.domain.vo.YunStudentVo;
import com.myedu.project.parents.service.IYunStudentService;
import com.myedu.project.store.domain.YunStoreStu;
import com.myedu.project.store.service.IYunStoreStuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Controller
 * 
 * @author 梁少鹏
 * @date 2020-01-22
 */
@Api("订单")
@RestController
@RequestMapping("/app/order/order")
public class APPYunOrderController extends BaseController
{
    @Autowired
    private IYunOrderService yunOrderService;
    @Autowired
    private IYunStudentService yunStudentService;
    @Autowired
    private ISysGradeService sysGradeService;
    @Autowired
    private IYunStoreStuService yunStoreStuService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询订单列表
     */
    @AutoIdempotent
    @ApiOperation("查询订单列表")
    @ApiImplicitParam(name = "yunOrder", value = "查询订单列表",
            dataType = "YunOrderVo")
    @GetMapping("/list")
    public TableDataInfo list(YunOrderVo yunOrder)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        startPage();
        yunOrder.setCreateById(loginUser.getUser().getUserId());
        List<YunOrderVo> list = yunOrderService.selectYunOrderList(yunOrder);
        return getDataTable(list);
    }


    /**
     * 获取订单详细信息
     */
    @AutoIdempotent
    @ApiOperation("获取订单详细信息")
    @ApiImplicitParam(name = "id", value = "获取订单详细信息",
            dataType = "Long")
    @GetMapping(value = { "/", "/{id}" })
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id)
    {
        AjaxResult ajax = AjaxResult.success();
        YunStudentVo yunStudent=new YunStudentVo();
        yunStudent.setCreateById(SecurityUtils.getUserId());
        SysGrade sysGrade=new SysGrade();
        ajax.put("students", yunStudentService.selectYunStudentList(yunStudent));
        ajax.put("sysGrades", sysGradeService.selectSysGradeList(sysGrade));
        if (StringUtils.isNotNull(id))
        {
            ajax.put(AjaxResult.DATA_TAG,yunOrderService.selectYunOrderById(id));
        }
        return ajax;
    }

    /**
     * 新增订单
     */
    @AutoIdempotent
    @ApiOperation("新增订单")
    @ApiImplicitParam(name = "yunOrder", value = "新增订单",
            dataType = "YunOrderVo")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YunOrder yunOrder)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        yunOrder.setCreateById(loginUser.getUser().getUserId());
        yunOrder.setCreateBy(loginUser.getUser().getNickName());
        yunOrder.setCreateTime(DateUtils.getNowDate());
        if(yunOrder.getStoreId()!=null){
            //报名给这个门店添加学生
            YunStoreStu yunStoreStu=new YunStoreStu();
            yunStoreStu.setStoreId(yunOrder.getStoreId());
            yunStoreStu.setStuId(yunOrder.getStudentId());
            yunStoreStu.setCreateById(SecurityUtils.getUserId());
            yunStoreStu.setCreateBy(SecurityUtils.getUsername());
            yunStoreStu.setStatus(StoreStuStatus.SIGNUP.getCode());
            yunStoreStuService.insertYunStoreStu(yunStoreStu);
        }
        return toAjax(yunOrderService.insertYunOrder(yunOrder));
    }

    /**
     * 修改订单
     */
    @AutoIdempotent
    @ApiOperation("修改订单")
    @ApiImplicitParam(name = "yunOrder", value = "修改订单",
            dataType = "YunOrderVo")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YunOrder yunOrder)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        yunOrder.setUpdateTime(DateUtils.getNowDate());
        yunOrder.setUpdateBy(loginUser.getUser().getNickName());
        return toAjax(yunOrderService.updateYunOrder(yunOrder));
    }

    /**
     * 删除订单
     */
    @AutoIdempotent
    @ApiOperation("删除订单")
    @ApiImplicitParam(name = "ids", value = "删除订单",
            dataType = "Long[]")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(yunOrderService.deleteYunOrderByIds(ids));
    }

    @AutoIdempotent
    @ApiOperation("支付宝PC网页支付")
    @ApiImplicitParam(name = "yunOrder", value = "支付宝PC网页支付",
            dataType = "YunOrderVo")
    @Log(title = "支付宝PC网页支付")
    @PostMapping(value = "/toPayAsPC")
    public AjaxResult toPayAsPc(@RequestBody YunOrderVo yunOrder) throws Exception{
        AjaxResult ajax = AjaxResult.success();
        String payUrl = yunOrderService.toPayAsPc(yunOrder);
        ajax.put("url",payUrl);
        return ajax;
    }

    @AutoIdempotent
    @ApiOperation("支付宝手机支付")
    @ApiImplicitParam(name = "yunOrder", value = "支付宝手机支付",
            dataType = "YunOrderVo")
    @Log(title = "支付宝手机支付")
    @PostMapping(value = "/toPayAsWeb")
    public AjaxResult toPayAsWeb(@RequestBody YunOrderVo yunOrder) throws Exception{
        AjaxResult ajax = AjaxResult.success();
        String payUrl = yunOrderService.toPayAsWeb(yunOrder);
        ajax.put("url",payUrl);
        return ajax;
    }

    /**
     * @Description :同步通知
     * @Author : 梁少鹏
     * @Date : 2020/2/12 11:35
     */
    @AutoIdempotent
    @ApiOperation("同步通知")
    @GetMapping(value = "/getReturnUrlInfo")
    public String alipayReturnUrlInfo(HttpServletRequest request) {
        String result=yunOrderService.synchronous(request);
        if(result.equals("success")){
            return "<html>\n" +
                    "<head>\n" +
                    "<script type=\"text/javascript\"> function load() { window.close(); } </script>\n" +
                    "</head>\n" +
                    "<body onload=\"" +
                    "load()\"> </body>\n" +
                    "</html>";
        }else{
            result="支付失败";
        }
        return result;
    }
    /**
     * @Description :异步通知
     * @Author : 梁少鹏
     * @Date : 2020/2/12 11:35
     */
    @AutoIdempotent
    @ApiOperation("异步通知")
    @PostMapping(value = "/getNotifyUrlInfo")
    public void alipayNotifyUrlInfo(HttpServletRequest request, HttpServletResponse response){
        yunOrderService.notify(request,response);
    }
}
