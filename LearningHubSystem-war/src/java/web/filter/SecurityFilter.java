package web.filter;

import entity.Administrator;
import entity.Lecturer;
import entity.Student;
import entity.TeachingAssistant;
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

public class SecurityFilter implements Filter {

    FilterConfig filterConfig;

    private static final String CONTEXT_ROOT = "/LearningHubSystem-war";

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        System.err.println("********** requestServletPath: " + requestServletPath);

        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");

        if (!excludeLoginCheck(requestServletPath)) {

            if (isLogin == true) {
                String currentRole = (String) httpSession.getAttribute("role");

                if (checkAccessRight(requestServletPath, currentRole)) {
                    chain.doFilter(request, response);
                } else {
                    httpServletResponse.sendRedirect(CONTEXT_ROOT + "/error.xhtml");
                }
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/home.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }

    private Boolean checkAccessRight(String path, String currentRole) {
        if (currentRole.equals("student")) {
            if (path.equals("/studentAnnouncement.xhtml")
                    || path.equals("/studentClassAndGroups.xhtml")
                    || path.equals("/studentFacilitators.xhtml")
                    || path.equals("/studentFiles.xhtml")
                    || path.equals("/studentModuleDetails.xhtml")
                    || path.equals("/studentDashboard.xhtml")
                    || path.equals("/studentModule.xhtml")
                    || path.equals("/studentSetting.xhtml")
                    || path.equals("/studentSchedule.xhtml")) {
                return true;
            } else {
                return false;
            }
        } else if (currentRole.equals("lecturer")) {
            if (path.equals("/lecturerAnnouncement.xhtml")
                    || path.equals("/lecturerClassAndGroups.xhtml")
                    || path.equals("/lecturerFacilitators.xhtml")
                    || path.equals("/lecturerFiles.xhtml")
                    || path.equals("/lecturerModuleDetails.xhtml")
                    || path.equals("/lecturerDashboard.xhtml")
                    || path.equals("/lecturerModule.xhtml")
                    || path.equals("/lecturerSetting.xhtml")
                    || path.equals("/lecturerSchedule.xhtml")) {
                return true;
            } else {
                return false;
            }

        } else if (currentRole.equals("TA")) {
            if (path.equals("/TAAnnouncement.xhtml")
                    || path.equals("/TAClassAndGroups.xhtml")
                    || path.equals("/TAFacilitators.xhtml")
                    || path.equals("/TAFiles.xhtml")
                    || path.equals("/TAModuleDetails.xhtml")
                    || path.equals("/TADashboard.xhtml")
                    || path.equals("/TAModule.xhtml")
                    || path.equals("/TASetting.xhtml")
                    || path.equals("/TASchedule.xhtml")) {
                return true;
            } else {
                return false;
            }

        } else if (currentRole.equals("admin")) {
            if (path.equals("/adminDashboard.xhtml")
                    || path.equals("/adminModuleManagement.xhtml")
                    || path.equals("/adminModuleAssignment.xhtml")
                    || path.equals("/adminUsersManagement.xhtml")
                    || path.equals("/adminSetting.xhtml")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml")
                || path.equals("/home.xhtml")
                || path.equals("/error.xhtml")
                || path.equals("/home.xhtml")
                || path.equals("/loginAdmin.xhtml")
                || path.equals("/loginLecturer.xhtml")
                || path.equals("/loginStudent.xhtml")
                || path.equals("/loginTA.xhtml")
                || path.equals("/register.xhtml")
                || path.startsWith("/images")
                || path.startsWith("/resources/images")
                || path.startsWith("/javax.faces.resource")
                || path.startsWith("/home.xhtml")
                ) {
            return true;
        } else {
            return false;
        }
    }
}
