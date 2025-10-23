package za.ac.cput.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        String method = req.getMethod();

        System.out.println("🔐 JWT Filter - " + method + " " + requestURI);

        // ✅ CRITICAL: Skip OPTIONS requests completely
        if ("OPTIONS".equalsIgnoreCase(method)) {
            System.out.println("🔐 JWT Filter - Skipping OPTIONS request");
            chain.doFilter(req, res);
            return;
        }

        // ✅ For public endpoints, skip JWT validation
        if (isPublicEndpoint(requestURI)) {
            System.out.println("🔐 JWT Filter - Public endpoint, skipping auth: " + requestURI);
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println("🔐 JWT Filter - Validating token for protected endpoint");

            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                System.out.println("🔐 JWT Filter - Valid token for user: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } else {
                System.out.println("🔐 JWT Filter - Invalid token");
            }
        } else {
            System.out.println("🔐 JWT Filter - No Bearer token found for: " + requestURI);
        }

        chain.doFilter(req, res);
    }

    private boolean isPublicEndpoint(String requestURI) {
        return requestURI.startsWith("/digital_artDB/api/") ||
                requestURI.startsWith("/digital_artDB/users/") ||
                requestURI.startsWith("/digital_artDB/cart_item/") ||
                requestURI.startsWith("/digital_artDB/categories/") ||
                requestURI.startsWith("/digital_artDB/artist/") ||
                requestURI.startsWith("/digital_artDB/error") ||
                requestURI.equals("/digital_artDB") ||
                requestURI.equals("/digital_artDB/") ||
                requestURI.startsWith("/api/") ||
                requestURI.startsWith("/users/") ||
                requestURI.startsWith("/cart_item/") ||
                requestURI.startsWith("/categories/") ||
                requestURI.startsWith("/artist/") ||
                requestURI.startsWith("/error") ||
                requestURI.equals("/") ||
                requestURI.contains("."); // static resources
    }
}