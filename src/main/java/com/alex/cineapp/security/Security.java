package com.alex.cineapp.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.alex.cineapp.util.Roles;




@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public UserDetailsManager usersCustom(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select cuenta,pwd,activo from usuarios where cuenta=?");
        users.setAuthoritiesByUsernameQuery("select cuenta,perfil from perfiles where cuenta = ? ");
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        

        http.authorizeHttpRequests(authoriize -> authoriize 

            .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**").permitAll()
            .requestMatchers("/", "/search/**", "/detail/**", "/acerca/**", "/usuarios/**").permitAll()
            .requestMatchers("/peliculas/**", "/horarios/**", "/noticias/**").hasAnyAuthority(Roles.GERENTE.toString(), Roles.EDITOR.toString())
            .requestMatchers("/banners/**").hasAnyAuthority(Roles.GERENTE.toString())
            .anyRequest().authenticated());

            http.formLogin(form -> form.loginPage("/login").permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // @Bean
    // UserDetailsManager users(DataSource dataSource){
    // JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    // return users;
    // }

}
