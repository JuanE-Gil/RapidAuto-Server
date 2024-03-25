package com.example.demo.Security.Filters;

import com.example.demo.Security.JWT.JwUtils;
import com.example.demo.Service.ObtenerUsuarioRol;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthotizationFilter  extends OncePerRequestFilter {

    @Autowired
    private JwUtils jwtUtils;

    @Autowired
    private ObtenerUsuarioRol obtenerUsuarioRol;

    private final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader !=null && tokenHeader.startsWith(BEARER_PREFIX)){
            String  token = tokenHeader.substring(BEARER_PREFIX.length());

            if(jwtUtils.isTokenValid(token)){
                String usuario = jwtUtils.getUsernameFromToken(token);
                UserDetails userDetails = obtenerUsuarioRol.loadUserByUsername(usuario);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response); //Continua el filtro si es que el if es diferente y deniega el acceso

    }
}

