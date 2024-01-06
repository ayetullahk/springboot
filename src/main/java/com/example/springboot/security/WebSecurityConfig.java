package com.example.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration//konfigrasyon yapılacağını belirtir
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(). //csrf() koruması iptal edildi API de ihtiyaç olmadığı için
                authorizeHttpRequests().
                antMatchers("/", "index.html", "/css/*").permitAll().//bu end-pointlere security uygulanmasın
                anyRequest().
                authenticated().
                and().
                httpBasic();//basic authebtication yapılacağını belirmek

    }
/*
    //inmemory olarak userları oluşturma
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userIsmail= User.builder().
                username("ismail").
                password(passwordEncoder().encode("123456")).
                roles("Admin").
                build();
        UserDetails userSuleyman=User.builder().
                username("suleyman").
                password(passwordEncoder().encode("123456")).
                roles("Student").
                build();
        UserDetails userTarik=User.builder().
                username("tarik").
                password(passwordEncoder().encode("123456")).
                roles("Student","Admin").
                build();

        return new InMemoryUserDetailsManager(new UserDetails[]{userSuleyman,userTarik,userIsmail});
    }
*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider autProvider = new DaoAuthenticationProvider();
        //kullanıcı bildileri
        autProvider.setUserDetailsService(userDetailsService);
        //kullanılacak encoder-decoder metodu belirteceğiz
        autProvider.setPasswordEncoder(passwordEncoder());
        return autProvider;
    }
}
