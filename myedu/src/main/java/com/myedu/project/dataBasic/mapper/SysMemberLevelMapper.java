package com.myedu.project.dataBasic.mapper;

import com.myedu.project.dataBasic.domain.SysMemberLevel;
import java.util.List;

/**
 * 会员等级Mapper接口
 * 
 * @author 梁少鹏
 * @date 2020-03-14
 */
public interface SysMemberLevelMapper 
{
    /**
     * 查询会员等级
     * 
     * @param id 会员等级ID
     * @return 会员等级
     */
    public SysMemberLevel selectSysMemberLevelById(Long id);

    /**
     * 查询会员等级列表
     * 
     * @param sysMemberLevel 会员等级
     * @return 会员等级集合
     */
    public List<SysMemberLevel> selectSysMemberLevelList(SysMemberLevel sysMemberLevel);


    /**
     * 新增会员等级
     * 
     * @param sysMemberLevel 会员等级
     * @return 结果
     */
    public int insertSysMemberLevel(SysMemberLevel sysMemberLevel);

    /**
     * 修改会员等级
     * 
     * @param sysMemberLevel 会员等级
     * @return 结果
     */
    public int updateSysMemberLevel(SysMemberLevel sysMemberLevel);

    /**
     * 删除会员等级
     * 
     * @param id 会员等级ID
     * @return 结果
     */
    public int deleteSysMemberLevelById(Long id);

    /**
     * 批量删除会员等级
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMemberLevelByIds(Long[] ids);
}
