package com.example.notify;

import java.sql.DriverManager;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.jdbc.PgConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.jdbc.channel.PostgresChannelMessageTableSubscriber;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.PostgresChannelMessageStoreQueryProvider;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Bean
    public DataSource dataSource( //
            @Value("${spring.datasource.url}") String url, //
            @Value("${spring.datasource.username}") String username, //
            @Value("${spring.datasource.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcChannelMessageStore messageStore(DataSource dataSource) {
        JdbcChannelMessageStore messageStore = new JdbcChannelMessageStore(dataSource);
        messageStore.setChannelMessageStoreQueryProvider(new PostgresChannelMessageStoreQueryProvider());
        return messageStore;
    }

    @Bean
    public PostgresChannelMessageTableSubscriber subscriber( //
            @Value("${spring.datasource.url}") String url, //
            @Value("${spring.datasource.username}") String username, //
            @Value("${spring.datasource.password}") String password) {
        return new PostgresChannelMessageTableSubscriber(
                () -> DriverManager.getConnection(url, username, password).unwrap(PgConnection.class));
    }
}
