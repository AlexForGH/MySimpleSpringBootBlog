package org.simple_spring_boot_blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@TestConfiguration
public class DataSourceTestConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public ResourceDatabasePopulator populate() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("test-schema.sql"));
        return populator;
    }


    @Bean
    public DataTool dataTool() {
        return new DataTool();
    }

    public class DataTool {
        public void recreateDB() {
            populate().execute(dataSource);
        }
    }
}
