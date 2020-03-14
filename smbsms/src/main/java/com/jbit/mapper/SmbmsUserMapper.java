package com.jbit.mapper;

import com.jbit.pojo.SmbmsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface SmbmsUserMapper {
    /**
     * 增加用户信息
     * @param user
     * @return
     */
    public int add( SmbmsUser user);

    /**
     * 通过userCode获取User
     * @param userCode
     * @return
     * @throws Exception
     */
    public SmbmsUser getLoginUser(@Param("userCode") String userCode);



    /**
     * 通过userId删除user
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteUserById( Integer delId);


    /**
     * 通过userId获取user
     * @param id
     * @return
     * @throws Exception
     */
    public SmbmsUser getUserById(@Param("id") Integer id);

    /**
     * 修改用户信息
     * @param user
     * @return
     * @throws Exception
     */
    public int modify( SmbmsUser user);



    /**
     * 查寻所有用户
     * @return
     */
    public List<SmbmsUser> alUser(Map map);

}
