package com.myedu.project.store.service;

import com.myedu.project.store.domain.YunStore;
import com.myedu.project.store.domain.vo.YunStoreVo;

import java.util.List;

/**
 * 门店Service接口
 * 
 * @author 梁少鹏
 * @date 2020-01-04
 */
public interface IYunStoreService 
{
    /**
     * 查询门店
     * 
     * @param id 门店ID
     * @return 门店
     */
    public YunStoreVo selectYunStoreById(Long id);

    /**
     * 查询门店列表
     * 
     * @param yunStore 门店
     * @return 门店集合
     */
    public List<YunStoreVo> selectYunStoreList(YunStoreVo yunStore);

    /**
     * 新增门店
     * 
     * @param yunStore 门店
     * @return 结果
     */
    public int insertYunStore(YunStore yunStore);

    /**
     * 修改门店
     * 
     * @param yunStore 门店
     * @return 结果
     */
    public int updateYunStore(YunStore yunStore);

    /**
     * 批量删除门店
     * 
     * @param ids 需要删除的门店ID
     * @return 结果
     */
    public int deleteYunStoreByIds(Long[] ids);

    /**
     * 删除门店信息
     * 
     * @param id 门店ID
     * @return 结果
     */
    public int deleteYunStoreById(Long id);
}
