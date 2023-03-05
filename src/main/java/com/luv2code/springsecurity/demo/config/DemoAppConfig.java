package com.luv2code.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages="com.luv2code.springsecurity.demo")
@PropertySource({"classpath:security-persistence-mysql.properties", "classpath:persistence-mysql.properties"})
public class DemoAppConfig {

	// set up variable to hold the properties
	@Autowired
	private Environment env;
	
	// set up a logger for diagnostics
	private Logger logger = Logger.getLogger(getClass().getName());
	
    @Configuration
    @ComponentScan("com.luv2code.springsecurity")
    @EnableWebMvc
    public class WebAppConfig implements WebMvcConfigurer {
       
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
              .addResourceHandler("/resources/**")
              .addResourceLocations("/resources/"); 
        }	
    }
	
	// define a bean for ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	// Define a bean for datasource for web_customer_tracker
	@Bean
	public DataSource dataSource() {
		
		// create connection pool
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		try {
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");		
		}
		catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		// for sanity's sake, let's log url and user ... just to make sure we are reading the data
		logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info("jdbc.user=" + env.getProperty("jdbc.user"));
		
		// set database connection props
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		
		// set connection pool props
		dataSource.setInitialPoolSize(
		getIntProperty("connection.pool.initialPoolSize"));

		dataSource.setMinPoolSize(
				getIntProperty("connection.pool.minPoolSize"));
		
		dataSource.setMaxPoolSize(
				getIntProperty("connection.pool.maxPoolSize"));
		
		dataSource.setMaxIdleTime(
				getIntProperty("connection.pool.maxIdleTime"));
				
		return dataSource;
	}
	
	// need a helper method 
	// read environment property and convert to int
	
	private int getIntProperty(String propName) {
		
		String propVal = env.getProperty(propName);
		
		// now convert to int
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
	
	private Properties getHibernateProperties() {

		// set hibernate properties
		Properties props = new Properties();

		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
	
		return props;				
	}

	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		
		// create session factorys
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}	
		
	// For Security custom user registration and custom database:
	
	// Define a bean for our security datasource for spring security, custom user registration and database:
	@Bean
	public DataSource securityDataSource() {
		
		// create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		try {
			securityDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");		
		}
		catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		// for sanity's sake, let's log url and user ... just to make sure we are reading the data
		logger.info("jdbc.url=" + env.getProperty("security.jdbc.url"));
		logger.info("jdbc.user=" + env.getProperty("security.jdbc.user"));
		
		// set database connection props
		securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
		securityDataSource.setUser(env.getProperty("security.jdbc.user"));
		securityDataSource.setPassword(env.getProperty("security.jdbc.password"));
		
		// set connection pool props
		securityDataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("security.connection.pool.initialPoolSize")));

		securityDataSource.setMinPoolSize(Integer.parseInt(env.getProperty("security.connection.pool.minPoolSize")));
		
		securityDataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("security.connection.pool.maxPoolSize")));
		
		securityDataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("security.connection.pool.maxIdleTime")));
				
		return securityDataSource;
	}
	
	private Properties getSecurityHibernateProperties() {

		// set hibernate properties
		Properties securityProps = new Properties();

		securityProps.setProperty("hibernate.dialect", env.getProperty("security.hibernate.dialect"));
		securityProps.setProperty("hibernate.show_sql", env.getProperty("security.hibernate.show_sql"));
	
		return securityProps;				
	}

	
	@Bean
	public LocalSessionFactoryBean securitySessionFactory(){
		
		// create session factorys
		LocalSessionFactoryBean securitySessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		securitySessionFactory.setDataSource(securityDataSource());
		securitySessionFactory.setPackagesToScan(env.getProperty("security.hiberante.packagesToScan"));
		securitySessionFactory.setHibernateProperties(getSecurityHibernateProperties());
		
		return securitySessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager securityTransactionManager(SessionFactory securitySessionFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager securityTransactionManager = new HibernateTransactionManager();
		securityTransactionManager.setSessionFactory(securitySessionFactory);

		return securityTransactionManager;
	}	

}
















