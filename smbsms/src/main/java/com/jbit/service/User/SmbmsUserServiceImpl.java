package com.jbit.service.User;

import com.jbit.mapper.SmbmsUserMapper;
import com.jbit.pojo.SmbmsUser;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SmbmsUserServiceImpl implements SmbmsUserService {


    private SmbmsUserMapper smbmsUserMapper;

    public void setSmbmsUserMapper(SmbmsUserMapper smbmsUserMapper) {
        this.smbmsUserMapper = smbmsUserMapper;
    }

    public int add(SmbmsUser user) { return smbmsUserMapper.add(user); }

    public SmbmsUser getLoginUser(String userCode) {
        return smbmsUserMapper.getLoginUser(userCode);
    }

    public int deleteUserById(Integer delId) { return smbmsUserMapper.deleteUserById(delId); }

    public SmbmsUser getUserById(Integer id) {
        return smbmsUserMapper.getUserById(id);
    }

    public int modify(SmbmsUser user) { return smbmsUserMapper.modify(user); }


    public List<SmbmsUser> alUser(Map map) { return smbmsUserMapper.alUser(map); }
}
