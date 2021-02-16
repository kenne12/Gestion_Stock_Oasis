package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
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

@WebFilter(filterName = "FilterConnexion", urlPatterns = {"/login.html"})
public class FilterConnexion implements Filter, Serializable {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    public FilterConnexion() {
        System.err.println("FilterConnexion -->>>>>>>>>>>>> le constructeur");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        log("FilterConnexion:DoBeforeProcessing");

        System.err.println("FilterConnexion--------------<< doBeforeProcessing");
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
         System.err.println("FilterConnexion--------------<< doAfterProcessing");

         log("FilterConnexion:DoAfterProcessing");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
         HttpServletRequest hRequest = (HttpServletRequest) request;
         HttpServletResponse hResponse = (HttpServletResponse) response;
         HttpSession session = hRequest.getSession();

         if (session.getAttribute("compte") != null) {
            request.getRequestDispatcher("/index.html?faces-redirect=true").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public FilterConfig getFilterConfig() {
        /*  94 */ return this.filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        /* 103 */ this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        /* 120 */ this.filterConfig = filterConfig;
        /* 121 */ if (filterConfig != null) {
            /* 123 */ log("FilterConnexion:Initializing filter");
        }
    }

    public String toString() {
        /* 133 */ if (this.filterConfig == null) {
            /* 134 */ return "FilterConnexion()";
        }
        /* 136 */ StringBuffer sb = new StringBuffer("FilterConnexion(");
        /* 137 */ sb.append(this.filterConfig);
        /* 138 */ sb.append(")");
        /* 139 */ return sb.toString();
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        /* 143 */ String stackTrace = getStackTrace(t);

        /* 145 */ if ((stackTrace != null) && (!stackTrace.equals(""))) {
            try {
                /* 147 */ response.setContentType("text/html");
                /* 148 */ PrintStream ps = new PrintStream(response.getOutputStream());
                /* 149 */ PrintWriter pw = new PrintWriter(ps);
                /* 150 */ pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");

                /* 153 */ pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                /* 154 */ pw.print(stackTrace);
                /* 155 */ pw.print("</pre></body>\n</html>");
                /* 156 */ pw.close();
                /* 157 */ ps.close();
                /* 158 */ response.getOutputStream().close();
            } catch (Exception localException) {
            }
        } else {
            try {
                /* 163 */ PrintStream ps = new PrintStream(response.getOutputStream());
                /* 164 */ t.printStackTrace(ps);
                /* 165 */ ps.close();
                /* 166 */ response.getOutputStream().close();
            } catch (Exception localException1) {
            }
        }
    }

    /* 173 */ public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            /* 175 */ StringWriter sw = new StringWriter();
            /* 176 */ PrintWriter pw = new PrintWriter(sw);
            /* 177 */ t.printStackTrace(pw);
            /* 178 */ pw.close();
            /* 179 */ sw.close();
            /* 180 */ stackTrace = sw.getBuffer().toString();
        } catch (Exception localException) {
        }
        /* 183 */ return stackTrace;
    }

    public void log(String msg) {
        /* 187 */ this.filterConfig.getServletContext().log(msg);
    }
}

