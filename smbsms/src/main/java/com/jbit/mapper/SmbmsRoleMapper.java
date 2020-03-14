package com.jbit.mapper;

import com.jbit.pojo.SmbmsRole;

import java.util.List;
import java.util.Map;

public interface SmbmsRoleMapper {
    List<SmbmsRole> allRole();
    int updateAole(SmbmsRole role);
    List<SmbmsRole> selectRole(Map map);
}
