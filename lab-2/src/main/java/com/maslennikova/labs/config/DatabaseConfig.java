package com.maslennikova.labs.config;

import com.maslennikova.labs.exception.DatabaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class DatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    private final HikariDataSource dataSource;

    public DatabaseConfig() {
        String url = getEnvOrThrow("DB_URL");
        String user = getEnvOrThrow("DB_USER");
        String password = getEnvOrThrow("DB_PASSWORD");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(5);

        this.dataSource = new HikariDataSource(config);
        log.info("Пул соединений HikariCP успешно инициализирован");
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void runMigrations() {
        log.info("Запуск миграций Liquibase...");
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );
            liquibase.update();
            log.info("Миграции Liquibase успешно применены");
        } catch (Exception e) {
            log.error("Ошибка при выполнении миграций Liquibase", e);
            throw new DatabaseException("Не удалось применить миграции БД", e);
        }
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("Пул соединений HikariCP закрыт");
        }
    }

    private String getEnvOrThrow(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Не задана переменная окружения: " + key);
        }
        return value;
    }
}