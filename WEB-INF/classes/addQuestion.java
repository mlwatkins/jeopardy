//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;


@WebServlet("/addQuestion")
public class addQuestion extends HttpServlet
{
   // Location of servlet.
   static String url = "http://localhost:8080/jeopardy/addQuestion";


   private static String LogoutServlet = "http://localhost:8080/jeopardy/logout";
   private static String GamesServlet = "http://localhost:8080/jeopardy/games";
   private static String FormServlet = "http://localhost:8080/jeopardy/form";


   private String user;
   private String gameID;
   private String description;

   public void doGet (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {
      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();
      
      HttpSession session = req.getSession(true);
      user = (String)session.getAttribute("UserID");
      gameID = (String)session.getAttribute("GameID");
      description = (String)session.getAttribute("Description");

      out.println("<html>");
      out.println("  <head>");
      out.println("    <title>Jeopardy Q/A Selector</title>");
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
      out.println("  <table width=\"25%\" align=\"left\" bgcolor=\"#E0E0E0\" border=\"0\" cellspacing=\"2\" cellpadding=\"5\"");
      out.println("    <tr>");
      out.println("      <td>");
      out.println("        <form action=\"" + GamesServlet  + "\" method=\"get\">");
      out.println("          <input type=\"submit\" value=\"Back to Games\"></input>");     
      out.println("        </form>");
      out.println("      </td>");
      out.println("    </tr>");
      out.println("  </table>");
      out.println("<br></br>");
      out.println("    <center><h1>Jeopardy<br /></h1></center>");
      out.println("    <center>");
      
      out.println("<form method=\"get\"action=\"" + FormServlet + "\" id=\"form1\" name=\"form1\">");                    
      out.println("  <table Cellspacing=\"0\" Cellpadding=\"3\" Border=\"0\" >");
      out.println("    <tr><td colspan=\"4\"><b>Update Game:</b></td></tr>");
      out.println("    <tr>");
      
      out.println("      <td>Game ID: " + gameID + "  " + "\t </td>");
      out.println("      <td>Description: " + description + " </td>");
      
      out.println("    </tr>");
      out.println("  </table>");      
      out.println("</form>");
      
      out.println("<br />");
      out.println("<hr />");
      out.println("<br />");

      out.println("<form action=\"addQuestion\" method=\"post\">");
      out.println("  <table Cellspacing=\"0\" Cellpadding=\"3\" Border=\"0\" >");
      out.println("    <tr><td colspan=\"4\"><b>Add another question: </b></td></tr>");
      out.println("    <tr>");
      out.println("      <td>Question:</td>");
      out.println("      <td><input type=\"text\" name=\"quest\" size=\"15\" maxLength=\"100\"></td>");
      out.println("      <td>Answer:</td>");
      out.println("      <td><input type=\"text\" name=\"ans\" size=\"15\" maxlength=\"100\"></td>");
      out.println("    </tr>");
      out.println("    <tr>");
      out.println("      <td>Category:</td>");
      out.println("      <td><input type=\"text\" name=\"cat\" size=\"15\" maxlength=\"100\"></td>");
      out.println("      <td>Score:</td>");
      out.println("      <td><input type=\"text\" name=\"score\" size=\"15\" maxlength=\"20\"></td>");
      out.println("    </tr>");
      out.println("    <tr>");
      out.println("        <td colspan=\"2\" align=\"center\"><button style=\"text-align:center\" type=\"submit\" name=\"create\" value=\"create\">Create Game</button></td>");
      out.println("        <td colspan=\"2\" align=\"center\"><button style=\"text-align:center\" type=\"submit\" name=\"add\" value=\"add\">Add Another Question</button></td></tr></table>");      
      out.println("    </tr>");
      out.println("  </table>");      
      out.println("      </form>");       
      out.println("    </center>");
      out.println("  </body>");
      out.println("</html>");

      out.close ();  
   }


   public void doPost (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
  {
      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();
      if (req.getParameter("add") != null) {
      	Enumeration<String> paramNames = req.getParameterNames();

        
        List<String> paramList = new ArrayList<String>();
        List<String> jeopardyQuestions = new ArrayList<String>();
        while (paramNames.hasMoreElements()) {
          paramList.add(req.getParameter(paramNames.nextElement()));
        }

        int numParams = paramList.size();

        if (paramList.get(numParams-1) == "create") {
          paramList.remove(numParams-1);
        } 

        List<String> questionList = new ArrayList<String>();
        questionList.add(req.getParameter("quest"));
        questionList.add(req.getParameter("ans"));
        questionList.add(req.getParameter("cat"));
        questionList.add(req.getParameter("score"));

        boolean success = writeToFile("/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/", questionList, user, gameID);
        
        if (success) {
          res.sendRedirect("http://localhost:8080/jeopardy/addQuestion");
        } else {
          res.sendRedirect("http://localhost:8080/jeopardy/form");
        }
        
      } else if (req.getParameter("create") != null) {
        Enumeration<String> paramNames = req.getParameterNames();

        
        List<String> paramList = new ArrayList<String>();
        List<String> jeopardyQuestions = new ArrayList<String>();
        while (paramNames.hasMoreElements()) {
          paramList.add(req.getParameter(paramNames.nextElement()));
        }

        int numParams = paramList.size();

        if (paramList.get(numParams-1) == "create") {
          paramList.remove(numParams-1);
        } 

        List<String> questionList = new ArrayList<String>();
        questionList.add(req.getParameter("quest"));
        questionList.add(req.getParameter("ans"));
        questionList.add(req.getParameter("cat"));
        questionList.add(req.getParameter("score"));

        boolean success = writeToFile("/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/", questionList, user, gameID);
        
        if (success) {
          res.sendRedirect("http://localhost:8080/jeopardy/play.jsp");
        } else {
          res.sendRedirect("http://localhost:8080/jeopardy/form");
        }
      }
  }



private boolean writeToFile( java.lang.String foldername, List<String> str, String user, String name )
  {   
  	String filename = name + ".txt";

    try {

          java.io.File file = new java.io.File( foldername,  filename );
          java.io.FileWriter fout = new java.io.FileWriter( file , true);

          fout.write(str.get(0) + ",");
          fout.write(str.get(1) + ",");
          fout.write(str.get(2) + ",");
          fout.write(str.get(3) + "\n");


          
          fout.flush();
          fout.close();
          return true;

          
    } catch ( java.io.IOException e ) {
          System.out.println( "Error: cannot write to file " + foldername + filename + " : " + e.toString() );
          e.printStackTrace();
          return false; 
      }
  }
 
}