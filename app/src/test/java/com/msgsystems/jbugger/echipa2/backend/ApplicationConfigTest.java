package com.msgsystems.jbugger.echipa2.backend;

import com.msgsystems.jbugger.echipa2.backend.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = ApplicationConfig.class)
public class ApplicationConfigTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void testDataSource() {
        assertNotNull(dataSource);
    }

    @Test
    public void testNamedParameterJdbcTemplate() {
        assertNotNull(namedParameterJdbcTemplate);
    }
}