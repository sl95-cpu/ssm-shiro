package com.jbit.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {
    private static SqlSessionFactory factory;
    static {
        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
             factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public   static SqlSession createSqlSession(){
        return  factory.openSession();
    }
    public static void closeSqlSession(SqlSession sqlSession){
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
