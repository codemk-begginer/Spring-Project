package tech.steve.farmer_app.config;


import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseInitializer {

    @Value("${spring.datasource.initialization-url}")
    private String initializationUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.oracleucp.database-name}")
    private String databaseName;

    @PostConstruct
    @Profile({"local"})
    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(initializationUrl, datasourceUsername, datasourcePassword)) {
            try (Statement statement = connection.createStatement()) {
                String checkDatabaseSql = "SELECT 1 FROM pg_database WHERE datname = '" + databaseName + "'";
                try (var resultSet = statement.executeQuery(checkDatabaseSql)) {
                    if (!resultSet.next()) {
                        String createDatabaseSql = "CREATE DATABASE " + databaseName;
                        statement.execute(createDatabaseSql);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Bean
    @DependsOn("databaseInitializer")
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return flyway;
    }
}

