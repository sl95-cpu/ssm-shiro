package com.jbit.service.Bill;

import com.jbit.pojo.SmbmsBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BillService {
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
    List<SmbmsBill> smbmsBillByProId(String proId);


    /**
     * 增加订单
     * @param bill
     * @return
     */
    public int add(SmbmsBill bill);


    /**
     * 通过查询条件获取供应商列表-模糊查询-getBillList
     * @param map
     * @return
     */
    public List<SmbmsBill> getBillList(Map map);

    /**
     * 通过delId删除Bill
     * @param delId
     * @return
     */
    public int deleteBillById(String delId);


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
