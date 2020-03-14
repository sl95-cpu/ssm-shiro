package com.jbit.config;


//import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

//声明这是一个配置
@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean 3.
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加 shiro的 内置过滤器
        /*
         anon:  无需认证就能访问
         authc: 必须认证才能访问
         user:  必须拥有 记住我 功能才能实现
         perms: 拥有某个资源的权限才能访问
         role:  拥有某个角色权限才能访问
         */
        //1.拦截
        Map<String, String> filterMap = new LinkedHashMap<String,String>();

        //授权   访问页面 用户必须拥有 perms 权限 才能有访问的权限
   /*     filterMap.put("/jsp/billadd","perms[1]");
        filterMap.put("/jsp/useradd","perms[1]");
        filterMap.put("/jsp/provideradd","perms[1]");
        filterMap.put("/user/update","perms[user:update]");
        //支持通配符
        filterMap.put("/user/*","authc");
        bean.setFilterChainDefinitionMap(filterMap);*/



       //设置登录请求
        bean.setLoginUrl("/toLogin");
        //设置未授权请求
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }
    //DefaultWebSecurityManager  2.
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象 ,需要自定义类1.
    @Bean
    public UserRealm  userRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect: 用来整合 shiro thymeleaf
  /*  @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }*/
}
