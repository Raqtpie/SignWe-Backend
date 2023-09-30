package com.turingteam.filter;

import com.turingteam.common.AuthorizationException;
import com.turingteam.common.BaseContext;
import com.turingteam.utils.JwtUtil;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class CheckLoginFilter implements Filter {

    public static final AntPathMatcher PATH_PATTERN = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestURI = request.getRequestURI();

        //定义不需要处理的路径
        String[] urls = new String[]{"/login", "/register", "/getLabStatus", "/getChair", "/getChairById", "/getRecord", "/getRecordYesterday", "/favicon.ico", "/doc.html/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/**", "/error/**", "/webjars/**"};

        boolean check = check(urls, requestURI);
        if (check) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token;
        try {
            token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()  || JwtUtil.isTokenExpired(token)) {
                throw new AuthorizationException("用户未登录或token已过期");
            } else if (!JwtUtil.isTokenEffective(token)) {
                throw new AuthorizationException("token无效");
            }
            String id = JwtUtil.extractSubject(token);
            if (id == null) {
                throw new AuthorizationException("token无效");
            }
            BaseContext.setCurrentId(id);
        } catch (Exception e) {
            Exception exception = e;
            if (exception instanceof SignatureException) {
                exception = new AuthorizationException("token无效");
            }
            request.setAttribute("filter.error", exception);
            //将异常分发到/error/throw控制器
            request.getRequestDispatcher("/error/throw").forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param urls       不需要处理的路径
     * @param requestURI 本次请求的URI
     * @return true：不需要处理，false：需要处理
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_PATTERN.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
