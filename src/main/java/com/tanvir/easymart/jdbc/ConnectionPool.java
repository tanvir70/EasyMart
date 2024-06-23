package com.tanvir.easymart.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPool {

    @Produces
    public DataSource getDataSource() {
        var dbProperties = ResourceBundle.getBundle("db");

        var config = new HikariConfig();
        config.setJdbcUrl(dbProperties.getString("db.url"));
        config.setUsername(dbProperties.getString("db.user"));
        config.setPassword(dbProperties.getString("db.password"));
        config.setDriverClassName(dbProperties.getString("db.driver"));
        var maxPoolSize = dbProperties.getString("db.max.connections");
        config.setMaximumPoolSize(Integer.parseInt(maxPoolSize));

        return new HikariDataSource(config);
    }
}
