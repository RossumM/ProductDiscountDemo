package config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Merijn 
 * Configurate van de Root container. Bevat DataSource maar
 * daarnaast ook Spring Security (via import)
 */

@Import(SecurityConfig.class) //Configuratiebestand Spring Security
@Configuration
// Geen Componentscan nodig want alle niet in dit bestand gefinieerde beans zijn al ingeladen door de servlet. 
//@ComponentScan(basePackages={""}, excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})

public class RootConfig {

    @Bean //simpele MySQL datasource. Kan later vervangen worden door JNDI bron
    public BasicDataSource datasource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/ivikkortingsapp");
        ds.setUsername("root");
        ds.setPassword("");

        //ds.setInitializeSize(5);
        //ds.setMaxActive(10);
        return ds;
    }

}
