package io.g2tech.jred.console.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.manager.Constants;
import org.apache.catalina.manager.ManagerServlet;
import org.apache.catalina.util.ContextName;
import org.apache.tomcat.util.res.StringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = { "/manager/*" })
public class JredManagerServlet extends ManagerServlet {
  private static final long serialVersionUID = -6858487572419869859L;

  private static final Logger logger = LoggerFactory.getLogger(JredManagerServlet.class);

  /**
   * Initialize this servlet.
   */
  @Override
  public void init() throws ServletException {
    super.init();

    logger.info("----------------------------------------------------");
  }

  /**
   * Process a GET request for the specified resource.
   *
   * @param request  The servlet request we are processing
   * @param response The servlet response we are creating
   *
   * @exception IOException      if an input/output error occurs
   * @exception ServletException if a servlet-specified error occurs
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    logger.debug("doGet: Process a GET request for the specified resource '{}'", 
        req.getContextPath());

    StringManager smClient = StringManager.getManager(Constants.Package, req.getLocales());

    // Identify the request parameters that we need
    String command = req.getPathInfo();
    
    logger.trace("command '{}'", command);

    String path = req.getParameter("path");
    ContextName cn = null;
    if (path != null) {
      cn = new ContextName(path, req.getParameter("version"));
    }

    logger.trace("ContextName '{}'", cn);

    // Prepare our output writer to generate the response message
    res.setContentType("application/json; charset=" + Constants.CHARSET);
    // res.setHeader("X-Content-Type-Options", "nosniff");
    PrintWriter writer = res.getWriter();

    // Process the requested command
    if (command == null || command.equals("/")) {
      // No command == list
    } else if (command.equals("/list")) {
      // List always displayed - nothing to do here
    } else {
      throw new ServletException(smClient.getString("managerServlet.unknownCommand", command));
    }

    list(writer, smClient);
  }

  /**
   * Render a list of the currently active Contexts in our virtual host.
   *
   * @param writer   Writer to render to
   * @param smClient i18n support for current client's locale
   */
  protected void list(PrintWriter writer, StringManager smClient) {
    logger.debug("list: Listing contexts for virtual host '{}'", host.getName());

    JsonArray result = new JsonArray();

    Container[] contexts = host.findChildren();
    for (int i = 0; i < contexts.length; i++) {
      Context context = (Context) contexts[i];
      if (context != null) {
        
        logger.debug("context: AltDDName={}, BaseName={}, DisplayName={}, DocBase={}, Name={}, Path={}"
            , context.getAltDDName()
            , context.getBaseName()
            , context.getDisplayName()
            , context.getDocBase()
            , context.getName()
            , context.getPath());
        
        
        String displayPath = context.getPath();
        if (displayPath.equals(""))
          displayPath = "/";
        if (context.getState().isAvailable()) {
          JsonObject o = new JsonObject();
          o.addProperty("displayPath", displayPath);
          o.addProperty("version", context.getWebappVersion());
          o.addProperty("running", true);
          o.addProperty("sessions", context.getManager().findSessions().length);
          o.addProperty("docBase", context.getDocBase());
          result.add(o);
        } else {
          JsonObject o = new JsonObject();
          o.addProperty("displayPath", displayPath);
          o.addProperty("version", context.getWebappVersion());
          o.addProperty("running", false);
          o.addProperty("sessions", 0);
          o.addProperty("docBase", context.getDocBase());
          result.add(o);
        }
      }
    }

    writer.print(result.toString());

    // Finish up the response
    writer.flush();
    writer.close();
  }

  /**
   * Process a POST request for the specified resource.
   *
   * @param request  The servlet request we are processing
   * @param response The servlet response we are creating
   *
   * @exception IOException      if an input/output error occurs
   * @exception ServletException if a servlet-specified error occurs
   */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

  }
}
