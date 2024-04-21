package com.example.main.filters;

import com.example.main.entities.User;
import com.example.main.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException
    {
        try {

            // Pass tokens không cần xác thực
            if(isByPassToken(request)){
                filterChain.doFilter(request, response);
                return;
            }

            // Else
            String authHeader =request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Authentication header is null or not starts with 'Bearer' !"
                );
                return;
            }

            String token = authHeader.substring(7);
            String username = jwtTokenUtils.getUsernameFromToken(token);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(username);

                if(jwtTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                filterChain.doFilter(request, response); // enable bypass
            }


        }catch(AccessDeniedException e){
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );
            response.getWriter().write(e.getMessage());
        } catch (Exception e){
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED
            );
            response.getWriter().write(e.getMessage());
        }

    }


    private boolean isByPassToken(@NotNull HttpServletRequest request) {
        List<Pair<String, String>> byPassToken = Arrays.asList(
            // Find all
            Pair.of(String.format("%s/categories**",apiPrefix), "GET"),
            Pair.of(String.format("%s/appointment_types**", apiPrefix), "GET" ),
            Pair.of(String.format("%s/doctors**", apiPrefix), "GET"),
            Pair.of(String.format("%s/rooms**", apiPrefix), "GET"),

            // Test
            Pair.of(String.format("%s/doctors/register", apiPrefix), "POST"),
            Pair.of(String.format("%s/doctors/create", apiPrefix), "POST"),
            Pair.of(String.format("%s/patients/register", apiPrefix), "POST"),
            Pair.of(String.format("%s/patients", apiPrefix), "GET"),
            Pair.of(String.format("%s/users/login", apiPrefix), "POST")
        );

        for(Pair<String, String> pair : byPassToken){
            String servletPath = pair.getLeft();
            String requestMethod = pair.getRight();

            if(request.getServletPath().matches(servletPath.replace("**", ".*"))
                    && request.getMethod().equalsIgnoreCase(requestMethod)
            ){
                return true;
            }
        }

        return false;
    }

}
