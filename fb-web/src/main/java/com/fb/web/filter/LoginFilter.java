
package com.fb.web.filter;

import com.fb.common.util.JsonUtils;
import com.fb.user.domain.AbstractUser;
import com.fb.user.response.UserDTO;
import com.fb.user.service.IUserService;
import com.fb.web.utils.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
@Component
public class LoginFilter implements Filter {

    @Autowired
    private IUserService userService;

    private final static List<String> excludeUrlList = Arrays.asList("/user/getVerifyCode/**",
            "/user/login/**",
            "/pay/notify",
            "/user/signUp",
            "/user/listHobbyTagName",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-ui.html/**",
            "/v2/api-docs");



    private final static AntPathMatcher DEFAULT_PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //校验，校验不通过直接写response返回
        // 校验通过，把用户信息放在request里面
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("token");
        UserDTO user;
        if (needCheck(httpServletRequest.getRequestURI())) {
            if (StringUtils.isBlank(token) || Objects.isNull(user = userService.checkAndRefresh(token)))
                responseError(servletResponse);
            else {
                Map<String, Object> map = new HashMap<>();
                map.put("user", user);
                CommonHttpRequest request = new CommonHttpRequest(httpServletRequest, map);
                filterChain.doFilter(request, servletResponse);
            }
        }else {
        filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean needCheck(String url) {
        for (String s : excludeUrlList) {
            if (DEFAULT_PATH_MATCHER.match(s, url))
                return false;
        }
        return true;
    }

    private void responseError(ServletResponse servletResponse) throws IOException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = servletResponse.getWriter();
        JsonObject jsonObject = JsonObject.newErrorJsonObject(2000, "token失效，请重新登录");
        out.append(JsonUtils.object2Json(jsonObject));
    }
}
