package demo.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Necessary to use two independent Liquibase set because conditional "include"
 * in not yet available (see https://github.com/liquibase/liquibase/pull/6440).
 */
@Configuration
public class LiquibaseConfiguration {

    @Bean
    @Qualifier("liquibase-demo")
    @ConfigurationProperties(prefix = "spring.liquibase.demo")
    public LiquibaseProperties demoLiquibaseProperties() {
        
        return new LiquibaseProperties();
        
    }
    
    @Bean
    public SpringLiquibase demoLiquibase(
            final DataSource dataSource,
            @Qualifier("liquibase-demo")
            final LiquibaseProperties demoLiquibaseProperties) {

        final var liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(demoLiquibaseProperties.getChangeLog());
        liquibase.setDatabaseChangeLogTable(demoLiquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(demoLiquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDefaultSchema(demoLiquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(demoLiquibaseProperties.getLiquibaseSchema());
        liquibase.setDropFirst(demoLiquibaseProperties.isDropFirst());
        liquibase.setShouldRun(demoLiquibaseProperties.isEnabled());
        liquibase.setChangeLogParameters(demoLiquibaseProperties.getParameters());
        liquibase.setRollbackFile(demoLiquibaseProperties.getRollbackFile());
        return liquibase;
        
    }

    @Bean
    @Qualifier("liquibase-camunda8")
    @ConfigurationProperties(prefix = "spring.liquibase.camunda8")
    @ConditionalOnProperty(prefix = "spring.liquibase.camunda8", name = "change-log")
    public LiquibaseProperties camunda8LiquibaseProperties() {

        return new LiquibaseProperties();

    }

    @Bean
    @ConditionalOnBean(name = "camunda8LiquibaseProperties")
    public SpringLiquibase c8Liquibase(
            final DataSource dataSource,
            @Qualifier("liquibase-camunda8")
            final LiquibaseProperties camunda8LiquibaseProperties) {

        final var liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(camunda8LiquibaseProperties.getChangeLog());
        liquibase.setDatabaseChangeLogTable(camunda8LiquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(camunda8LiquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDefaultSchema(camunda8LiquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(camunda8LiquibaseProperties.getLiquibaseSchema());
        liquibase.setDropFirst(camunda8LiquibaseProperties.isDropFirst());
        liquibase.setShouldRun(camunda8LiquibaseProperties.isEnabled());
        liquibase.setChangeLogParameters(camunda8LiquibaseProperties.getParameters());
        liquibase.setRollbackFile(camunda8LiquibaseProperties.getRollbackFile());
        return liquibase;

    }

}
