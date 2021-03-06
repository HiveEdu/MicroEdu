import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";
// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/order/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(id) {
  return request({
    url: '/order/order/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/order/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/order/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(id) {
  return request({
    url: '/order/order/' + id,
    method: 'delete'
  })
}
//订单退款
export function rebund(id) {
  return request({
    url: '/order/order/rebund/' + id,
    method: 'get'
  })
}
// 导出订单
export function exportOrder(query) {
  return request({
    url: '/order/order/export',
    method: 'get',
    params: query
  })
}

// 订单支付
export function orderPay(url, data) {
  return request({
    url:url,
    data: data,
    method: 'post'
  })
}
