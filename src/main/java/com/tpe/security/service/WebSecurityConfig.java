package com.tpe.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //method seviyesinde güvenlik katmani calistirmak istiyoruz.
                                                    //Bu yüzden bu anatation ekledik.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    //ilk olarak ta bu methodu override ettik.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeHttpRequests().
                antMatchers("/","index.html","/css/*","/js/*","/register").permitAll().
                //Bu satirda verdigimiz endpointleri security muaf tut demis olduk
                and().authorizeRequests().antMatchers("/students/**").hasRole("ADMIN").
                //Bu satirda endpoint bazinda role kismini calistirabiliyoruz.
                // Bu sekilde verdigimiz endpoint ve altindaki kisimlarda admin auth. olanlar request edebiliyor.
                // bu yüzden biz istersek bu satirla nasil autorization yapicagimiza karar verebilir ya da yukaridaki
                //EnableGlobalMethodSecurity ile yapabiliyoruz.
                anyRequest().authenticated().and().httpBasic(); //onun haricindekileri authenticated et diyoruz. yani sifre iste.
    }

    //1. olarak PsswordEncoder olusturduk

    @Bean //Bu class spring security den geliyor biz icini setliyoruz method olarak.
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(11); //4 ile 31 arasinda deger yazabiliriz. bu deger cok yüksekse encod etme islemi uzun sürüyor
                                            // bu yüzden 10-15 arasi iyi

    }

    //2. olarak DaoAuthenticationProvider olusturuyoruz.
    @Bean
    public DaoAuthenticationProvider authProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    //3. olarak bu configure class ini override ettik.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
