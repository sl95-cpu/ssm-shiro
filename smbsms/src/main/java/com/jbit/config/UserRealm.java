package com.jbit.config;

import com.jbit.pojo.SmbmsUser;
import com.jbit.service.User.SmbmsUserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 自定义UserRealm
 */
public class UserRealm  extends AuthorizingRealm {

    @Autowired
    @Qualifier("SmbmsUserServiceImpl")
    private SmbmsUserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权>>>doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        //拿到User 对象
        SmbmsUser currentUser = (SmbmsUser)subject.getPrincipal();
        //设置当前用户的权限
        info.addStringPermission(currentUser.getUserRole().toString());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证>>>>doGetAuthenticationInfo ");
        //用户名验证
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //查询 用户
        SmbmsUser user = userService.getLoginUser(userToken.getUsername());
        if (user == null) {
            return null;
        }
        System.out.println(user.getUserName());
        //密码可以加密 : MD5  MD5盐值加密
        //密码认证 , shiro做 防止密码泄露
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserPassword(), "");

        //验证完成之后,获取当前用户 把用户存入 session
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user",user);
        return info;
    }
}
