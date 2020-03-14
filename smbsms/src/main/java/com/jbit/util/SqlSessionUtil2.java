package com.jbit.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil2 {
    private static SqlSessionFactory factory;
    static {

        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession createSqlSession(){
        return  factory.openSession();
    }

    public static Object getMapper(Class c) throws NoSuchMethodException {
        SqlSession sqlSession =   createSqlSession();
        Object mapper = sqlSession.getMapper(c);
        closeSqlsession(sqlSession);
        return mapper;
    }
    public static void closeSqlsession(SqlSession sqlSession){
        if (sqlSession != null){
            sqlSession.close();
        }
    }
}
