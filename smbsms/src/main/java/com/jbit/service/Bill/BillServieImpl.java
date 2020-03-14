package com.jbit.service.Bill;

import com.jbit.mapper.SmbmsBillMapper;
import com.jbit.pojo.SmbmsBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BillServieImpl implements BillService {
    @Autowired
    private SmbmsBillMapper smbmsBillMapper;

    @Override
    public List<SmbmsBill> allBill() {
        return smbmsBillMapper.allBill();
    }

    @Override
    public List<SmbmsBill> smbmsBillByProId(String proId) {
        return smbmsBillMapper.smbmsBillByProId(proId);
    }

    @Override
    public int add(SmbmsBill bill) {
        return smbmsBillMapper.add(bill);
    }

    @Override
    public List<SmbmsBill> getBillList(Map map) {
        return smbmsBillMapper.getBillList(map);
    }

    @Override
    public int deleteBillById(String delId) {
        return smbmsBillMapper.deleteBillById(delId);
    }

    @Override
    public SmbmsBill getBillById(String id) {
        return smbmsBillMapper.getBillById(id);
    }

    @Override
    public int modify(SmbmsBill bill) {
        return smbmsBillMapper.modify(bill);
    }
}
