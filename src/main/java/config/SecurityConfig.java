/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author Merijn
 * Configuratie van Spring Security. 
 */


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired //Is gedefinieerd in RootCOnfig en komt hier via Autowire binnen
	BasicDataSource datasource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth
	.jdbcAuthentication()
		.dataSource(datasource)
		.usersByUsernameQuery("select username, password, enabled from Users where username=?")
		.authoritiesByUsernameQuery( //vereiste user query toegespitst op eigen tabel
				"select username, 'ROLE_USER' from Users where username=?")
		.passwordEncoder(new StandardPasswordEncoder()); //standaard hash-encryptie van Spring.
	}
	
	//  Spring gebruikt een filterketen als laag bovenop de applicatie.
        //Deze filter definiëren we hier. Voor testdoeleinden houden we deze simpel. Alles is open behalve 1 pagina.
        //Later kunnen we eventueel ook filteren op gebruikersrollen en dit nog combineren met andere logica.
        @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.formLogin()
			.loginPage("/login")
		.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //zonder deze toevoeging kan er alleen met een POST request worden uitgelogd i.p.v. GET request.
				.logoutSuccessUrl("/loggedout")
		.and()
			.rememberMe()
		        .tokenRepository(new InMemoryTokenRepositoryImpl()) //gebruikers via in Memory map onthouden. Alleen voor testing/Niet geschikt voor productieomgeving
		        .tokenValiditySeconds(2419200) // geldigheid sessie (1 maand)
		        .key("dreamersKey") //key die specifiek is voor deze applicatie
                .and()
			.authorizeRequests()
			.antMatchers("/secured").authenticated()
                        .antMatchers("/secured/").authenticated() //Altijd dubbel definiëren (met en zonder slash) om eenvoudig omzeilen beveiliging via url te voorkomen
//                        .antMatchers("/addproduct").authenticated()
//                        .antMatchers("/addproduct/").authenticated()
			.anyRequest().permitAll(); //resterende pagina's zonder beveiliging
	}
	
}
