package com.milan.market.web.security.filter;

import com.milan.market.domain.service.MilanUserDetailsService;
import com.milan.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Esta clase debe extender de la clase OncePerRequestFilter para que se ejecute por cada petición
 */
@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MilanUserDetailsService milanUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * 1. Debemos revisar que en el header venga el token
         * 2. Debemos revisar que ese token sea correcto
         */
        String authorizationHeader = request.getHeader("Authorization");
        if ( authorizationHeader != null && authorizationHeader.startsWith("Bearer") ) {
            // Tomamos lo que viene desde la posición 7, lo que esta después de "Bearer_"
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername( jwt );

            // Comprobamos que el username no sea null y que no exista autenticación para este usuario
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = milanUserDetailsService.loadUserByUsername( username );

                if (jwtUtil.validateToken( jwt, userDetails )) {
                    // Levantamos la sesión para ese usuario. El getAuthorities viene vacío porque no le hemos asignado ninguno
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());

                    // Vamos agregarle al token los detalles de la conexion (que navegador esta usando, horario de conexion, S.O etc)
                    authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));

                    // Asignamos la autenticación, para que despues no deba pasar por toda la autenticacion
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
