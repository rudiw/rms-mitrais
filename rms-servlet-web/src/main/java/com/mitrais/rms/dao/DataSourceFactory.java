package com.mitrais.rms.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import javax.swing.event.InternalFrameEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class provides MySQL datasource to be used to connect to database.
 * It implements singleton pattern See <a href="http://www.oodesign.com/singleton-pattern.html">Singleton Pattern</a>
 */
public class DataSourceFactory {
    private final DataSource dataSource;

    DataSourceFactory() {
        final MysqlDataSource dataSource = new MysqlDataSource();
        // TODO: make these database setting configurable by moving to properties file

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final InputStream input = classLoader.getResourceAsStream("database.properties");
        try {
            final Properties prop = new Properties();
            prop.load(input);

            dataSource.setDatabaseName(prop.getProperty("jdbc.db"));
            dataSource.setServerName(prop.getProperty("jdbc.host"));
            dataSource.setPort(Integer.valueOf(prop.getProperty("jdbc.port")));
            dataSource.setUser(prop.getProperty("jdbc.user"));
            dataSource.setPassword(prop.getProperty("jdbc.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dataSource = dataSource;
    }

    /**
     * Get a data source to database
     *
     * @return DataSource object
     */
    public static Connection getConnection() throws SQLException {
        return SingletonHelper.INSTANCE.dataSource.getConnection();
    }

    private static class SingletonHelper {
        private static final DataSourceFactory INSTANCE = new DataSourceFactory();
    }
}
