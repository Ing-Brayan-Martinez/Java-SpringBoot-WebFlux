package com.example.spring.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

/**
 * Configuración de R2DBC y populador de la base de datos.
 */
@Configuration
@EnableAutoConfiguration
public class R2dbcConfig {

    /**
     * Configurar la creacion de las tablas.
     * @param factory Conceccion de la base de datos.
     * @return Retorna la configuracion
     */
    @Bean
    public ConnectionFactoryInitializer populator(
            final ConnectionFactory factory) {

        ConnectionFactoryInitializer initializer =
                new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(factory);

        CompositeDatabasePopulator populator =
                new CompositeDatabasePopulator();
        populator.addPopulators(
                new ResourceDatabasePopulator(
                        new ClassPathResource("schema.sql")));
        populator.addPopulators(
                new ResourceDatabasePopulator(
                        new ClassPathResource("data.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

}
