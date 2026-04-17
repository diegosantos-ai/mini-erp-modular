package br.com.diegosantos.minierp.identity.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_ERROR_MESSAGE_ATTRIBUTE = "jwt.auth.error.message";

    private static final String INVALID_TOKEN_MESSAGE = "Token ausente, inválido ou expirado";
    private static final String INACTIVE_USER_MESSAGE = "Usuário inativo";
    private static final String AUTHENTICATED_USER_NOT_FOUND_MESSAGE = "Usuário autenticado não encontrado";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        try {
            String username = jwtService.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (!userDetails.isEnabled()) {
                    registerAuthenticationFailure(request, INACTIVE_USER_MESSAGE);
                    filterChain.doFilter(request, response);
                    return;
                }

                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    registerAuthenticationFailure(request, INVALID_TOKEN_MESSAGE);
                }
            }
        } catch (UsernameNotFoundException ex) {
            registerAuthenticationFailure(request, AUTHENTICATED_USER_NOT_FOUND_MESSAGE);
        } catch (JwtException | IllegalArgumentException ex) {
            registerAuthenticationFailure(request, INVALID_TOKEN_MESSAGE);
        }

        filterChain.doFilter(request, response);
    }

    private void registerAuthenticationFailure(HttpServletRequest request, String message) {
        SecurityContextHolder.clearContext();
        request.setAttribute(AUTH_ERROR_MESSAGE_ATTRIBUTE, message);
    }
}
