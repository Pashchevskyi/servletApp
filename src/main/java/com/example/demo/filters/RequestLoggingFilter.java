package com.example.demo.filters;

import com.example.demo.service.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Servlet Filter implementation class RequestLoggingFilter
 */
@WebFilter("/demo")
public class RequestLoggingFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
        this.context.log("RequestLoggingFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        this.context.log(Logger.logRequest(req));
        this.context.log(Logger.logCookie(req));
        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}

