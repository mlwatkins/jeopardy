//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;


@WebServlet("/settings")
public class settings extends HttpServlet
{

  private static String LogoutServlet = "http://localhost:8080/jeopardy/logout";

  private String user;


	public void doGet (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {

   		res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();
      HttpSession session = req.getSession(true);
      user = (String)session.getAttribute("UserID");

 		   out.println(" <!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<title>Jeopardy - Settings</title>");
        out.println("<link rel=\"shortcut icon\" href=\"/media/favicon.ico\" type=\"image/x-icon\" />");
        out.println("<link rel=\"icon\" href=\"/media/favicon.ico\" type=\"image/x-icon\" />");

        // To get the styling for this jeopardy table, we inspected the website "https://jeopardylabs.com/play/html-jeopardy9"
        // I spoke to Professor Upsorn and she said as long as we were just using the styling, that this was okay. 

        out.println("<style type=\"text/css\"> body {");
          
        out.println("body { background-color:#ffffff; height:100%;font-family:Verdana, Arial, Helvetica, sans-serif;padding-bottom:100px;}");
        out.println("label {display:block;font-size:16px;}");
        out.println("input, textarea, select {display:block;font-family:Verdana, Arial, Helvetica, sans-serif;margin:auto;}");
        out.println("textarea {padding:5px;  }");

        out.println("#settings {background-color:#fff;width:500px;padding:10px;margin:auto;margin-top:75px;}");

        out.println("#settings h1 {color:#2a3698;font-size:22px;text-align:center;}");

        out.println("#settings h3 {color:#2a3698;font-size:16px;text-align:center;}");

        out.println("#settings label {text-align: center;font-size:14px;}");

        out.println("#settings select, #settings label, #settings input {margin-top:5px;}");

        out.println("#settings select {width:130px;  }");

        out.println("#settings input {width:100px; }");
        out.println("#settings p {margin-bottom:0px; }");

        out.println("</style></head>");
        out.println("<body> ");
        out.println("  <table width=\"25%\" align=\"right\" bgcolor=\"#E0E0E0\" border=\"0\" cellspacing=\"2\" cellpadding=\"5\"");
        out.println("    <tr>");
        out.println("      <td align=\"right\">UserID:  " + user + "</td>" );
        out.println("      <td>");
        out.println("        <form action=\"" + LogoutServlet  + "\" method=\"post\">");
        out.println("          <input type=\"submit\" value=\"Logout\"></input>");     
        out.println("        </form>");
        out.println("      </td>");
        out.println("    </tr>");
        out.println("  </table>");
        out.println(" <div id=\"settings\"> ");
        out.println(" <h1>Jeopardy</h1>");
        out.println(" <h3>mlw5ea & jte4hm</h3>");
        out.println(" <label>Number of Teams</label>");
        out.println(" <select name=\"teams\" id=\"teams\">");
        out.println("  <option value=\"1\">1 team</option> ");  
        out.println("  <option value=\"2\">2 teams</option>");
        out.println(" <option value=\"3\">3 teams</option>");
        out.println(" <option value=\"4\">4 teams</option>");
        out.println(" </select>");
        out.println("<form action=\"http://localhost:8080/jeopardy/table\" method=\"get\">");
        out.println("<button type=\"submit\" style=\"text-align:center\" name=\"play\" value=\"play\">Play</button>");
        out.println("</form>");
        
        out.println("<div class=\"clear\"></div>");
        out.println("</div> ");

        out.println("</body>");
        out.println("</html>");
        out.println("</form>"); 
        
        out.close();

   }

   public void doPost (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {
   		res.setContentType ("text/html");
      	PrintWriter out = res.getWriter ();

      if (req.getParameter("play") != null) {
        res.sendRedirect("http://localhost:8080/jeopardy/table");
      }

      	out.close();
  }
}