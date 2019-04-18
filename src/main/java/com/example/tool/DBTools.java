package com.example.tool;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBTools {
    public static SqlSession sqlSession;
    static {
        try {
// 使用MyBatis提供的Resources类加载mybatis的配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
// 构建sqlSession的工厂
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = sessionFactory.openSession(true);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 创建能执行映射文件中sql的sqlSession
    public static SqlSession getSession() {
        return sqlSession;
    }

}
