//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;


@WebServlet("/create")
public class create extends HttpServlet
{
   // Location of servlet.
   static String url = "http://localhost:8080/jeopardy/form";
   // note: domain="localhost", port="8080", path="/cs4640/", servlet="examples.simpleform"
   // put simpleform.class in /tomcat/webapps/cs4640/WEB-INF/classes/examples/ folder
   
   // to access the servlet via a browser
   // http://localhost:8080/cs4640/examples.simpleform
   
   // input text file (from CSLAB server)
   private static java.lang.String question_data = "http://plato.cs.virginia.edu/~mlw5ea/Assignment-3/actual-data.txt";
   
   private static String LogoutServlet = "http://localhost:8080/jeopardy/logout";
   private static String CreateServlet = "http://localhost:8080/jeopardy/create";
   
   String row = "";
   String column = "";
   String score = "";
   String str_data = "";

   private String user;
   private String gameID;
   private String description;
   
   /** *****************************************************
    *  Overrides HttpServlet's doPost().
    *  Access the form data entry and present it back to the client
   **********************************************************/   
  public void doPost (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
  {
      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();
      if (req.getParameter("create_btn") != null) {
        gameID = req.getParameter("gameID");
        description = req.getParameter("description");

        writeGames("/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/games.txt", user, gameID, description);
        
        res.sendRedirect("http://localhost:8080/jeopardy/games");
      } 
       
        out.close();
  }
   
   /** *****************************************************
    *  Overrides HttpServlet's doGet().
    *  Prints an HTML page with a blank form.
   ********************************************************* */
   public void doGet (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {
      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();
      if (req.getParameter("gameID") != null) {
        gameID = req.getParameter("gameID");
      }
      if (req.getParameter("description") != null) {
        description = req.getParameter("description");
      }
      HttpSession session = req.getSession(true);
      user = (String)session.getAttribute("UserID");


      out.println("<html>");
      out.println("  <head>");
      out.println("    <title>Jeopardy</title>");
      out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");

      out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");

      out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");
      out.println("   <meta http-equiv=\"content-type\" content=\"text/xhtml; charset=utf-8\">");
      out.println("   <title>CS4640 Session example</title>");
             
      out.println("   <style>");
      out.println("      body, html {");
      out.println("         margin: 10 auto;");
      out.println("         padding: 10;");
      out.println("         color: #202020;");
      out.println("         background-color: #ddeeff;");
      out.println("         font-family: 'Lucida Grande',Verdana,Helvetica,Arial,Geneva,'Bitstream Vera Sans',Sans-Serif;");
      out.println("         font-size: 12px;");
      out.println("      }");     
         
      out.println("      input[type=text], textarea {");  
      out.println("         border: 1px solid #cccccc;");
      out.println("         font: 11px Verdana;"); 
      out.println("         color: black;"); 
      out.println("         line-height: 1.2em;"); 
      out.println("      }");

      out.println("      #top {");
      out.println("         padding-top: 12px;");
      out.println("         padding-left: 10px;");
      out.println("         padding-right: 4px;");
      out.println("         float: left;");
      out.println("         text-align: justify;");
      out.println("         width: 90%;");
      out.println("       }");

      out.println("      #main {");
      out.println("         padding-top: 12px;");
      out.println("         padding-left: 10px;");
      out.println("         padding-right:4px;");
      out.println("         float: left;");
      out.println("         width: 90%;");
      out.println("         text-align: center;");
      out.println("      }");
         
      out.println("      #note {");
      out.println("         font: 10px Verdana;");
      out.println("         color: red;");
      out.println("      }");
         
      out.println("      .errorMsg {");
      out.println("         background-color: #eeeeee;");
      out.println("         width: 100%; ");
      out.println("      }");

      out.println("      .list {");
      out.println("         width: 90%;");
      out.println("         background-color: #eeeeee;");
      out.println("      }");
         
      out.println("   </style>");
      out.println("  </head>");
      out.println("  <body>");
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
      out.println("<br></br>");
      out.println("    <center><h1>Create Jeopardy Game<br /></h1></center>");
      out.println("    <center>");
      
      out.println("<form method=\"post\"action=\"" + CreateServlet + "\" id=\"form1\" name=\"form1\">");                    
      out.println("  <table Cellspacing=\"0\" Cellpadding=\"3\" Border=\"0\" >");
      out.println("    <tr><td colspan=\"4\"><b>Create Game:</b></td></tr>");
      out.println("    <tr>");
        out.println("      <td>Game ID:</td>");
        out.println("      <td><input autofocus type=\"text\" name=\"gameID\" size=\"15\" maxLength=\"20\"><td>");
        out.println("      <td>Description:</td>");
        out.println("      <td><input type=\"text\" name=\"description\" size=\"15\" maxlength=\"20\"></td>");
      out.println("    </tr>");
      out.println("    <tr>");
      out.println("      <td colspan=\"4\" ><button type=\"submit\" value=\"Create\" name=\"create_btn\">Create</button>");
      out.println("    </tr>");
      out.println("  </table>");      
      out.println("</form>");
      
      out.println("<br />");
      out.println("<hr />");
      out.println("<br />");
    }
   
  private void writeGames(java.lang.String filename, String user, String name, String description) {
    try {
          java.io.File file = new java.io.File( filename );
          java.io.FileWriter fout = new java.io.FileWriter( file , true);
          fout.write(name+","+user+","+description+"\n");
          fout.flush();
          fout.close();
      } catch ( java.io.IOException e ) {
          System.out.println( "Error: cannot write to file " + filename + " : " + e.toString() );
          e.printStackTrace();
      }

  }

}