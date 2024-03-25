package com.example.demo.Security;


import com.example.demo.Repository.UsuarioRepository;
import com.example.demo.Security.Filters.JwtAuthenticationFilter;
import com.example.demo.Security.Filters.JwtAuthotizationFilter;
import com.example.demo.Security.JWT.JwUtils;
import com.example.demo.Service.ObtenerUsuarioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //Habilitar las anotaciones de spring security
public class SecurityConfig {

    @Autowired
    ObtenerUsuarioRol obtenerUsuarioRol;

    @Autowired
    JwUtils jwUtils;

    @Autowired
    JwtAuthotizationFilter authotizationFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");//Manejar la url del framework
        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("auto/listar-un-Auto").permitAll();
                            auth.requestMatchers("usuario/version").permitAll();
                            auth.requestMatchers("usuario/registrar_usuario").permitAll();
                            auth.requestMatchers("auto/filtro/disponible").permitAll();
                            auth.requestMatchers("auto/busqueda/modelo").permitAll();
                            auth.requestMatchers("categoria").permitAll();
                            auth.requestMatchers("auto/busqueda/categoria").permitAll();
                            auth.requestMatchers("auto/filtro/precio").permitAll();
                            auth.requestMatchers("auto/filtro/estado").permitAll();
                            auth.requestMatchers("auto/filtro/marca").permitAll();
                            auth.requestMatchers("auto/busqueda/categoria_description").permitAll();
                            auth.anyRequest().authenticated();
                        })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authotizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //Metodo que se encarga de manejar la autenticacion para que pueda obtener los permisos
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(obtenerUsuarioRol)
                .passwordEncoder(passwordEncoder)
                .and().build();

    }
}
