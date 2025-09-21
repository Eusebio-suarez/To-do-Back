package com.toDoApp.toDo_back.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toDoApp.toDo_back.security.JwtUtils;
import com.toDoApp.toDo_back.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// este filtro se ejecutara por cada una de las peticiones
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //se obtiene el valor del campo con el nobre que se especifica
        String authorizationtionHeader = request.getHeader("Authorization");

        //validar que exita el valor
        if(authorizationtionHeader !=null && authorizationtionHeader.startsWith("Bearer ")){

            //obtener el valor del token quitando los caracteres "Bearer"
            String token = authorizationtionHeader.substring(7);

            //validar el token
            if(jwtUtils.validateToken(token)){

                //obtener el correo extrayendolo del token
                String email = jwtUtils.getUsername(token);

                //cargar el usuarioDetails con el servicio que trae el usuario por correo
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                //definir la peticion como autenticada
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //si el token no exite la peticion sigue sin authenticación
        filterChain.doFilter(request, response);
    }
    
}
