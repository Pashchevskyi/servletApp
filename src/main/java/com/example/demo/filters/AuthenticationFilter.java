package com.example.demo.filters;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    private final Set<String> allowedURNs;

    private final Properties properties;

    public AuthenticationFilter() {
        allowedURNs = new HashSet<>();
        allowedURNs.add("/demo/saveServlet");
        allowedURNs.add("/demo/loginServlet");
        allowedURNs.add("/demo/viewServlet");

        properties = new Properties();
        try (InputStreamReader reader = new FileReader("./src/main/resources/application.properties")) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("<<< Exception: " + e.getMessage());
        }
    }

    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String requestedResource = "Requested Resource::"+properties.getProperty("server.scheme")+
            "://"+properties.getProperty("server.host")+":"+properties.getProperty("server.port") +
            uri;
        this.context.log(requestedResource);

        HttpSession session = req.getSession(false);

        if (session == null && !(allowedURNs.contains(uri))) {
            this.context.log("<<< Unauthorized access request");
            PrintWriter out = res.getWriter();
            out.println("No access!!!");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        //close any resources here
    }
}
