package com.adamdubaj.custompostgrestarter;

import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Bean;



import javax.sql.DataSource;

@AutoConfiguration
@EnableTransactionManagement
@EnableConfigurationProperties(CustomProperties.class)
@ConditionalOnClass(DataSource.class)
public class CustomPropertiesConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CustomPropertiesConfiguration.class);
    private CustomProperties customProperties;
    public CustomPropertiesConfiguration(CustomProperties customProperties) {
        log.info("Configuring starter with properties {}", customProperties);
        this.customProperties = customProperties;

    }

    @Bean
    //@ConditionalOnMissingBean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(customProperties.driverClassName());
        dataSource.setUrl(customProperties.url());
        dataSource.setUsername(customProperties.userName());
        dataSource.setPassword(customProperties.password());

        return dataSource;
    }

    @Bean
    @ConditionalOnBean(name = "dataSource")
    @ConditionalOnMissingBean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.adamdubaj.*");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }
}
