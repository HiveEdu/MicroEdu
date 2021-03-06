package com.myedu.project.parents.controller;

import com.myedu.common.utils.DateUtils;
import com.myedu.common.utils.SecurityUtils;
import com.myedu.common.utils.poi.ExcelUtil;
import com.myedu.framework.aspectj.lang.annotation.Log;
import com.myedu.framework.aspectj.lang.enums.BusinessType;
import com.myedu.framework.web.controller.BaseController;
import com.myedu.framework.web.domain.AjaxResult;
import com.myedu.framework.web.page.TableDataInfo;
import com.myedu.project.parents.domain.YunComplaint;
import com.myedu.project.parents.service.IYunComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 * 投诉Controller
 * 
 * @author myedu
 * @date 2020-01-18
 */
@RestController
@RequestMapping("/parents/complaint")
public class YunComplaintController extends BaseController
{
    @Autowired
    private IYunComplaintService yunComplaintService;

    /**
     * 查询投诉列表
     */
    @PreAuthorize("@ss.hasPermi('parents:complaint:list')")
    @GetMapping("/list")
    public TableDataInfo list(YunComplaint yunComplaint)
    {
        startPage();
        List<YunComplaint> list = yunComplaintService.selectYunComplaintList(yunComplaint);
        return getDataTable(list);
    }

    /**
     * 导出投诉列表
     */
    @PreAuthorize("@ss.hasPermi('parents:complaint:export')")
    @Log(title = "投诉", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(YunComplaint yunComplaint)
    {
        List<YunComplaint> list = yunComplaintService.selectYunComplaintList(yunComplaint);
        ExcelUtil<YunComplaint> util = new ExcelUtil<YunComplaint>(YunComplaint.class);
        return util.exportExcel(list, "complaint");
    }

    /**
     * 获取投诉详细信息
     */
    @PreAuthorize("@ss.hasPermi('parents:complaint:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(yunComplaintService.selectYunComplaintById(id));
    }

    /**
     * 新增投诉
     */
    @PreAuthorize("@ss.hasPermi('parents:complaint:add')")
    @Log(title = "投诉", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody YunComplaint yunComplaint)
    {
        yunComplaint.setCreateById(SecurityUtils.getUserId());
        yunComplaint.setCreateBy(SecurityUtils.getUsername());
        return toAjax(yunComplaintService.insertYunComplaint(yunComplaint));
    }

    /**
     * 修改投诉
     */
    @PreAuthorize("@ss.hasPermi('parents:complaint:edit')")
    @Log(title = "投诉", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YunComplaint yunComplaint)
    {
        return toAjax(yunComplaintService.updateYunComplaint(yunComplaint));
    }

    /**
     * 删除投诉
     */
    @PreAuthorize("@ss.hasPermi('parents:complaint:remove')")
    @Log(title = "投诉", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(yunComplaintService.deleteYunComplaintByIds(ids));
    }

//    /**
//     * @Description :答复投诉
//     * @Author : 梁龙飞
//     * @Date : 2020/3/8 16:30
//     */
//    @PreAuthorize("@ss.hasPermi('parents:complaint:changeStatusOn')")
//    @Log(title = "答复", businessType = BusinessType.UPDATE)
//    @GetMapping("/changeStatusOn/{ids}")
//    public AjaxResult changeStatusOn(@PathVariable Long[] ids,String replyContent)
//    {
//        int rows=0;
//        for (Long id:ids) {
//            YunComplaint yunComplaint= yunComplaintService.selectYunComplaintById(id);
//            if(yunComplaint!=null){
//                yunComplaint.setStatus("1");//已回复
//                yunComplaint.setReplyContent(replyContent);
//                yunComplaint.setReplyBy(SecurityUtils.getUsername());//回复人
//                yunComplaint.setReplyTime(DateUtils.getNowDate());//回复时间
//                rows=yunComplaintService.updateYunComplaint(yunComplaint);
//            }
//        }
//        return toAjax(rows);
//    }

    /**
     * 投诉回复
     */
    @PreAuthorize("@ss.hasPermi('parents:complaint:reply')")
    @Log(title = "投诉", businessType = BusinessType.UPDATE)
    @PutMapping("/reply")
    public AjaxResult reply(@RequestBody YunComplaint yunComplaint)
    {
        yunComplaint.setStatus("1");//已回复
        yunComplaint.setReplyBy(SecurityUtils.getUsername());//回复人
        yunComplaint.setReplyTime(DateUtils.getNowDate());//回复时间
        return toAjax(yunComplaintService.updateYunComplaint(yunComplaint));
    }
}
