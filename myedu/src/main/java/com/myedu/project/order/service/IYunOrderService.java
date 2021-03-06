package com.myedu.project.order.service;

import com.myedu.framework.web.domain.AjaxResult;
import com.myedu.project.order.domain.YunOrder;
import com.myedu.project.order.domain.vo.YunOrderVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Service接口
 * 
 * @author 梁少鹏
 * @date 2020-01-22
 */
public interface IYunOrderService 
{
    /**
     * 查询订单
     * 
     * @param id 订单ID
     * @return 订单
     */
    public YunOrderVo selectYunOrderById(Long id);

    /**
     * 查询订单列表
     * 
     * @param yunOrder 订单
     * @return 订单集合
     */
    public List<YunOrderVo> selectYunOrderList(YunOrderVo yunOrder);

    /**
     * 新增订单
     * 
     * @param yunOrder 订单
     * @return 结果
     */
    public int insertYunOrder(YunOrder yunOrder);

    /**
     * 修改订单
     * 
     * @param yunOrder 订单
     * @return 结果
     */
    public int updateYunOrder(YunOrder yunOrder);

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单ID
     * @return 结果
     */
    public int deleteYunOrderByIds(Long[] ids);

    /**
     * 删除订单信息
     * 
     * @param id 订单ID
     * @return 结果
     */
    public int deleteYunOrderById(Long id);

    /**
     * 订单支付
     *
     * @param yunOrder 订单
     * @return 结果
     */
    public String toPayAsPc(YunOrderVo yunOrder) throws Exception;

    public String toPayAsWeb(YunOrderVo yunOrder) throws Exception;

    String synchronous(HttpServletRequest request);
    /**
     * 付款异步通知调用地址
     * @param request 新增参数
     * @return 新增返回值
     */
    void notify(HttpServletRequest request, HttpServletResponse response);

    AjaxResult rebund(Long id);
}
