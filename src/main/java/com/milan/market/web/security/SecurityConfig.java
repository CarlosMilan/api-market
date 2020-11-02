package com.milan.market.web.security;

import com.milan.market.domain.service.MilanUserDetailsService;
import com.milan.market.web.security.filter.JwtFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MilanUserDetailsService milanUserDetailsService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( milanUserDetailsService );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //deshabilitamos las peticiones cruzadas
            .authorizeRequests() //Autorizamos el servicio de autenticación
                .antMatchers("/**/authenticate").permitAll()
                .anyRequest().authenticated() // Las demás peticiones necesitan autenticación
                .and()
                .sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS ); //Vamos a decirle que la sesión sea sin estado

        // Indicamos que el token debe pasar por el filtro creado. Este es un filtro de usuario y contraseña
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
