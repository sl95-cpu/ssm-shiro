package com.jbit.mapper;

import com.jbit.pojo.SmbmsBill;
import com.sun.javafx.collections.MappingChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;


public interface SmbmsBillMapper {

    /**
     * 查询 所有 订单
     * @return
     */
    List<SmbmsBill> allBill();

    /**
     *  根据 供应商id 查询订单
     * @param proId
     * @return
     */
    List<SmbmsBill> smbmsBillByProId(@Param("id") String proId);

    /**
     * 增加订单
     * @param bill
     * @return
     */
    public int add(SmbmsBill bill);


    /**
     * 通过查询条件获取供应商列表-模糊查询-getBillList
     * @return
     */
    public List<SmbmsBill> getBillList(Map map);

    /**
     * id
     * @param id
     * @return
     */
    public int deleteBillById(@Param("id") String id);


    /**
     * 通过billId获取Bill
     * @param id
     * @return
     */
    public SmbmsBill getBillById( String id);

    /**
     * 修改订单信息
     * @param bill
     * @return
     */
    public int modify(SmbmsBill bill);

}
