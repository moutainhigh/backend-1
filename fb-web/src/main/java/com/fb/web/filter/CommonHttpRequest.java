package com.fb.web.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class CommonHttpRequest extends HttpServletRequestWrapper {


    public CommonHttpRequest(HttpServletRequest request) {
        super(request);
    }

    public CommonHttpRequest(HttpServletRequest request, Map<String, Object> extraParams) {
        super(request);
        extraParams.forEach(this::setAttribute);
    }
}
