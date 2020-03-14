package com.jbit.service.Role;

import com.jbit.mapper.SmbmsRoleMapper;
import com.jbit.pojo.SmbmsRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmbmsRoleServiceImpl implements SmbmsRoleServie {

    @Autowired
    private SmbmsRoleMapper smbmsRoleMapper;

    public List<SmbmsRole> allRole() { return smbmsRoleMapper.allRole(); }
}
