package org.example.princess_group.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.global.util.StatusUtil;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "검증 및 인가")
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final StatusUtil statusUtil;
    private final ObjectMapper objectMapper;

    private static final RequestMatcher login = new AntPathRequestMatcher(
        "/api/users/login",
        HttpMethod.POST.name());
    private static final RequestMatcher signup = new AntPathRequestMatcher(
        "/api/users/signup", HttpMethod.POST.name());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        CreateUserRequest loginUser = statusUtil.getLoginUser(request);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(
                new UsernamePasswordAuthenticationToken(loginUser, null, null));
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return login.matches(request) || signup.matches(request);
    }

}
