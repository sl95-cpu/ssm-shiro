package com.jbit.mapper;

import com.jbit.pojo.SmbmsProvider;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface SmbmsProviderMapper {
    /**
     * 增加供应商
     * @param provider
     * @return
     */
    public int add(SmbmsProvider provider);


    /**
     * 通过供应商名称,id、编码获取供应商列表--providerList
     * @param map 可以包含多种查询 (名字,id,编码,)
     * @return
     */
    public List<SmbmsProvider> getProviderList(Map map);

    /**
     * 通过供应商名称 编码 模糊查询
     * @param map
     * @return
     */
    public List<SmbmsProvider> getProvideLike(Map map);

    /**
     * 通过proId删除Provider
     * @param id
     * @return
     */
    public int deleteProviderById(String id);

    /**
     * 修改供应商信息
     * @return
     */
    public int modify(SmbmsProvider provider);

}
