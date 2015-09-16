package config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 *
 * @author Merijn
 * Dit is de configuratieclass voor de servlet. Alle beans
 * (behalve Datasource) worden hier gedefinieerd danwel gedetecteerd via
 * ComponentScan. DataSource is gedefinieerd in de bovenliggende root-container
 * omdat deze ook nodig is voor Spring Security. In plaats van JSP wordt gebruik
 * gemaakt van de templateEngine Thymeleaf. Dit om de leesbaarheid en toegankelijkheid
 * van het html gedeelte te behouden. Binnen de templates wordt gebruik gemaakt van 
 * Spring Expression language om toegang tot objectdata te krijgen. Javascript en HTML-
 * validatie is gebruikt voor sommige formuleren en dynamische velden. De template zelf
 * is omgezet uit een bestaand ontwerp, zoals ook zichtbaar in de footer. Thymeleaf
 * fragments zijn gebruikt om terugkerende onderdelen zoals header, footer en sidebar 
 * los te trekken van de individuele templates.
 *
 */
@Configuration //configureert de webonderdelen
@EnableWebMvc //Hiermee wordt annotation based webmvc geactiveerd
@ComponentScan({"controller", "models", "DAOs"})
@EnableTransactionManagement //nodig voor herkenning van @Transactional annotatie in Database Objects
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired //gedefinieerd in root container
    BasicDataSource datasource;

    @Bean //viewresolver van Thymeleaf (stap 3)
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

    @Bean //viewresolver van Thymeleaf (stap 2)
    public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean //viewresolver van Thymeleaf (stap 1)
    public TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "true");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }


    @Bean //(name = "sessionFactory")
    public SessionFactory getSessionFactory(BasicDataSource dataSource) {

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        sessionBuilder.addAnnotatedClasses(models.User.class, models.GenericProduct.class);
        sessionBuilder.addProperties(getHibernateProperties());

        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Override //zorgt voor forwarden requests voor static resources naar de servlet default servlet
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();

    }

}
