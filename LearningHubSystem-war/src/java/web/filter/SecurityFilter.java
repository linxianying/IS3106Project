package web.filter;

import entity.Student;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})

public class SecurityFilter implements Filter
{    
    FilterConfig filterConfig;
    
    private static final String CONTEXT_ROOT = "/LearningHubSystem-war";
    
   

    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }



    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        if(httpSession.getAttribute("isLogin") == null)
        {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean)httpSession.getAttribute("isLogin");

        System.err.println("********** requestServletPath: " + requestServletPath);
        
        if(!excludeLoginCheck(requestServletPath))
        {
            System.err.println("********* HERE 1");
            
            if(isLogin == true)
            {
                chain.doFilter(request, response);
            }
            else
            {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/index.xhtml");
            }
        }
        else
        {
            System.err.println("********* HERE 2");
            chain.doFilter(request, response);
        }
    }



    public void destroy()
    {

    }
    
    private Boolean excludeLoginCheck(String path)
    {
        if( path.equals("/index.xhtml") ||
            path.equals("/error.xhtml") ||
            path.equals("/loginAdmin.xhtml")||
            path.equals("/loginLecturer.xhtml")||
            path.equals("/loginStudent.xhtml")||
            path.equals("/loginTA.xhtml")||    
            path.startsWith("/images") ||
            path.startsWith("/javax.faces.resource"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
