package com.example.demo.Security.Filters;

import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.UsuarioRepository;
import com.example.demo.Security.JWT.JwUtils;
import com.example.demo.Service.UsuarioService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwUtils jwtUtils;

    private UsuarioService usuarioService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    //Al no ser una clase de configuracion de spring de esta forma inyectamos correctamente la clase de jwtUtils
    public JwtAuthenticationFilter(JwUtils jwtUtils, UsuarioService usuarioService) {
        this.jwtUtils = jwtUtils;
        this.usuarioService = usuarioService;
    }

    //Metodo que verifica al usuario dentro de la base de datos y asigna el token
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsuarioEntity userEntity = new ObjectMapper().readValue(request.getInputStream(), UsuarioEntity.class);
            String username = userEntity.getUsername();
            String contrasena = userEntity.getContrasena();

            if (username == null || contrasena == null) {
                throw new BadCredentialsException("username y contrasena no pueden ser nulos");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, contrasena);


            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new BadCredentialsException("Error al leer el cuerpo de la solicitud", e);
        }
    }

    //En caso de que el metodo anterior funcione correctamente pase a este
    //Que imprime la respuesta y verifica los permisos si siguen activos del usuario
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateAccessToken(user.getUsername());
        response.addHeader("Authorization", token);
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("Message", "Autenticacion Correcta");
        httpResponse.put("Username", user.getUsername());
        String rol = usuarioService.BuscarRol(user.getUsername());
        httpResponse.put("Rol", rol);
        response.getWriter().write(objectMapper.writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
        response.getWriter().close();
    }
}

