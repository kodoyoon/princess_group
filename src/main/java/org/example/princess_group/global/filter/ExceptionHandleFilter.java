package org.example.princess_group.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.example.princess_group.global.dto.RootResponse;
import org.example.princess_group.global.error.ErrorCode;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ServiceException e) {
            setResponse(response);
            ErrorCode code = e.getErrorCode();
            RootResponse<Object> errResponse = RootResponse.builder()
                .status(code.code())
                .msg(code.message())
                .build();
            response.setStatus(code.status().value());

            try {
                response.getWriter().write(new ObjectMapper().writeValueAsString(errResponse));
            } catch (IOException ignored) {
            }
        }
    }

    private void setResponse(HttpServletResponse response) {
        response.setStatus(response.getStatus()); // http status 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // JSON 설정
        response.setCharacterEncoding(StandardCharsets.UTF_8.name()); // UTF8 설정
    }
}
