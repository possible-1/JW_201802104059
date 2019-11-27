package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter 0", urlPatterns = {"/*"})

public class Filter0_Encoding implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 0 - encoding begins");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String path= request.getRequestURI();
        String method=request.getMethod();
        if (path.contains("/login")||path.contains("/myapp")){
            System.out.println("未设置字符编码格式");
        }else {
            System.out.println(method);
            response.setContentType("text/html;charset=UTF-8");
            if (("POST-PUT").contains(method)){
                request.setCharacterEncoding("UTF-8");
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);//执行其他过滤器，如过滤器执行完毕，则执行原请求
        System.out.println("Filter 0 - encoding ends"+"\n");
    }
}
