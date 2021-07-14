package filter;

import entities.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
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

@WebFilter(filterName = "FilterPrivellege", urlPatterns = {"/parametre/*", "/securite/*", "/operation/*", "/analyse/*"})
public class FilterPrivellege
        implements Filter, Serializable {

    Properties properties;
    private static final boolean debug = true;
    Utilisateur utilisateur = new Utilisateur();

    private FilterConfig filterConfig = null;

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        log("FilterPrivellege:DoBeforeProcessing");
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        log("FilterPrivellege:DoAfterProcessing");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hRequest = (HttpServletRequest) request;
        HttpServletResponse hResponse = (HttpServletResponse) response;
        HttpSession session = hRequest.getSession();

        if (session.getAttribute("compte") != null) {
            List allAccesses = (List) session.getAttribute("allAccess");

            if (allAccesses.contains(hRequest.getRequestURI())) {
                List privileges = (List) session.getAttribute("accesses");

                if (!privileges.isEmpty()) {
                    boolean drapeau = false;

                    if (privileges.contains(Long.valueOf(1L))) {
                        drapeau = true;
                    }

                    if (drapeau) {
                        chain.doFilter(request, response);
                    } else {
                        List access = (List) session.getAttribute("access");
                        if (access.contains(hRequest.getRequestURI())) {
                            chain.doFilter(request, response);
                        } else {
                            request.getRequestDispatcher("/erreuracces.html?faces-redirect=true").forward(request, response);
                        }
                    }
                } else {
                    request.getRequestDispatcher("/erreuracces.html?faces-redirect=true").forward(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } else {
            request.getRequestDispatcher("/login.html?faces-redirect=true").forward(request, response);
        }
    }

    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            log("FilterPrivellege:Initializing filter");
        }
    }

    @Override
    public String toString() {
        if (this.filterConfig == null) {
            return "FilterPrivellege()";
        }
        StringBuilder sb = new StringBuilder("FilterPrivellege(");
        sb.append(this.filterConfig);
        sb.append(")");
        return sb.toString();
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception localException) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        /* 187 */ this.filterConfig.getServletContext().log(msg);
    }

    public String getPropertyValue(String key) {
        try {
            if (key == null) {
                System.out.println("=============== key null  ++++++++++++++++++++");
            }
            if (key == "") {
                System.out.println("=============== key empty  ++++++++++++++++++++");
            }
            if (this.properties == null) {
                System.out.println("=============== properties empty  ++++++++++++++++++++");
            }
            String propValue = this.properties.getProperty(key);

            System.out.println("key is: " + key);
            System.out.println("Property value is: " + propValue);
            return propValue;
        } catch (Exception ex) {
        }
        return null;
    }
}
