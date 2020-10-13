package com.seungmoo.springboot.sb_jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

//@Component
public class H2Runner //implements ApplicationRunner
{

    //@Autowired
    DataSource dataSource;

    //@Autowired
    JdbcTemplate jdbcTemplate;

    private Logger logger = LoggerFactory.getLogger(H2Runner.class);

    //@Override
    public void run(ApplicationArguments args) throws Exception {
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            // 아래 셋팅 정보는 spring-boot-auto config 디펜던시의 jdbc DataSourceProperties에서 셋팅해놓음
            logger.info(connection.getMetaData().getURL());
            logger.info(connection.getMetaData().getUserName());

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE USER(ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY(ID))";
            statement.executeUpdate(sql);

            connection.commit();
        }

        // jdbcTemplate을 사용할 때의 장점
        // 코드를 간결하게 사용가능, 안전함, try-catch-finally 반납처리가 되어있음
        // 가독성이 높음 에러메시지를 확인가능함 (기본 JDBC API를 쓰는 것보다 좋다.)
        jdbcTemplate.execute("INSERT INTO USER VALUES (1, 'seungmoo')");
    }
}
