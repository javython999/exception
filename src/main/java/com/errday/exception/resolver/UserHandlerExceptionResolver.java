package com.errday.exception.resolver;

import com.errday.exception.exception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("UserHandlerExceptionResolver ex={}", ex.getMessage());

        try {
             if (ex instanceof UserException) {
                 log.info("UserException resolver to 400");
                 String acceptHeader = request.getHeader("accept");
                 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                 if ("application/json".equals(acceptHeader)) {
                     Map<String, Object> errorResult = new HashMap<>();
                     errorResult.put("ex", ex.getClass());
                     errorResult.put("message", ex.getMessage());
                     String result = objectMapper.writeValueAsString(errorResult);
                     response.setContentType("application/json");
                     response.setCharacterEncoding("UTF-8");
                     response.getWriter().write(result);
                     return new ModelAndView();
                 }

                 return new ModelAndView("error-page/500");
             }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
