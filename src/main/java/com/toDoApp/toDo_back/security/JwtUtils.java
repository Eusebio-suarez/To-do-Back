package com.toDoApp.toDo_back.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtUtils {
    //llave secreta para para firmar el token
    @Value("${spring.jwt.secretKey}")
    private String secretKey;


    //tiempo de expiracion del token
    @Value("${spring.jwt.expiration}")
    private String expiration;


    //obtener firma del token
    public Key getSignature(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //generar el token con jwt
    public String generateToken(String userName){

        return Jwts.builder()
                .setSubject(userName) //poner el nombre de usuario como sujeto
                .setIssuedAt(new Date())//fecha de creacion del token
                .setExpiration(new Date(System.currentTimeMillis()//fecha de expiracion del token
                    +Long.parseLong(expiration)))
                .signWith(getSignature())//firmar con la clave secreta
                .compact();//copactar todo par devolver el string
    }

    //obtener el username del token
    public String getUsername(String token){
        
        return Jwts.parserBuilder()
                .setSigningKey(getSignature())
                .build()
                .parseClaimsJws(token)//parsear y validar firma
                .getBody()//obtener el conido del token
                .getSubject();//obtener el subject
    }

    //validar el token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                .setSigningKey(getSignature())
                .build()
                .parseClaimsJws(token);//parsear y validar la firma, lanza excepcion si no es valido
            
            return true; 
        }
        catch(JwtException | IllegalArgumentException e){
            //token valido o expirado
            return false;
        }
    }
}