

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;


@WebServlet("/games")
public class games extends HttpServlet
{
   // Location of servlet.
   static String url = "http://localhost:8080/jeopardy/games";
   // note: domain="localhost", port="8080", path="/cs4640/", servlet="examples.simpleform"
   // put simpleform.class in /tomcat/webapps/cs4640/WEB-INF/classes/examples/ folder
   
   // to access the servlet via a browser
   // http://localhost:8080/cs4640/examples.simpleform
   
   // input text file (from CSLAB server)
   private static java.lang.String games_data = "http://plato.cs.virginia.edu/~jte4hm/games.txt";

   private static final long serialVersionUID = 2L;
   
 //**** setting for local  ****/ 
   private static String LoginServlet = "http://localhost:8080/jeopardy/login";
   private static String LogoutServlet = "http://localhost:8080/jeopardy/logout";

   // a data file containing username and password 
   // note: this is a simple login information without encryption. 
   // In reality, credential must be encrypted for security purpose
   public static String user_info = "/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/user-info.txt";
   
   // doPost() tells doGet() when the login is invalid.
   private static boolean invalidID = false;
   
   private String user;
   
   
   String row = "";
   String column = "";
   String score = "";
   String str_data = "";
   
   /** *****************************************************
    *  Overrides HttpServlet's doPost().
    *  Access the form data entry and present it back to the client
   **********************************************************/
   public void doPost (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
  {
      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();
      System.out.println(req.getParameter("update"));
      if (req.getParameter("update") != null) {
        res.sendRedirect("http://localhost:8080/jeopardy/form");
      }

      if (req.getParameter("play") != null) {
        res.sendRedirect("http://localhost:8080/jeopardy/table");
      }

      else {
        out.println(" <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>");


        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        out.println("<title>Jeopardy</title><meta name=\"ROBOTS\" content=\"NOODP\" />");
        out.println("<meta name=\"google-site-verification\" content=\"UYS7arlGlbDobqQSsucWwkbZyiC-6_cCWlUn9Eb5b2M\" />");
        out.println("<link rel=\"shortcut icon\" href=\"/media/favicon.ico\" type=\"image/x-icon\" />");
        out.println("<link rel=\"icon\" href=\"/media/favicon.ico\" type=\"image/x-icon\" />");

        // To get the styling for this jeopardy table, we inspected the website "https://jeopardylabs.com/play/html-jeopardy9"
        // I spoke to Professor Upsorn and she said as long as we were just using the styling, that this was okay. 

        out.println("<style type=\"text/css\"> body {");
          
        out.println("background-color:#2a3698; height:100%; font-family:Verdana, Arial, Helvetica, sans-serif;padding-bottom:100px; }");
        out.println("label { display:block; font-size:16px; }");
        out.println("input, textarea, select { display:block; font-family:Verdana, Arial, Helvetica, sans-serif; margin:auto;}");

        out.println("textarea { padding:5px;  }");

        out.println("#title {");
        out.println("text-align:center;");
        out.println("font-size:36px;");
        out.println("margin:auto;");
        out.println("line-height:36px; }");

        out.println("textarea#title {");
        out.println("height:50px;");
        out.println("width:90%;");
        out.println("color:#012b45;}");

        out.println("#jeopardyTable { width:100%; background-color:#000000; padding:0; margin:0; margin:auto; font-size:16px;}");

        out.println(" #jeopardyTable textarea { width:90%; height:30px; font-size:18px; line-height:22px;}");

        out.println("#jeopardyTable h3 { color:#ffff5f; text-align:center; font-size:20px; font-weight:bold;}");

        out.println("#jeopardyTable tbody td, #jeopardyTable thead th {vertical-align: middle;background-color:#2a3698; padding:5px;text-align:center;width:20%;color:#ffff5f;height:60px;font-size:20px;}");

        out.println("#jeopardyTable tbody td { cursor:pointer; height:100px; border:3px solid #2a3698;}");

        out.println("#jeopardyTable tbody td.ie-hack { border:3px solid #ffff5f;}");

        out.println("#jeopardyTable tbody td.dirty h3 { color:#2a3698;  }");

        out.println("#jeopardyTable tfoot td {text-align: center; background-color:#2a3698;}");

        out.println("</style></head>");

        out.println("<body><table id=\"jeopardyTable\" cellspacing=\"5\" cellpadding=\"0\">");
        out.println("<thead>");
        
        // Categories

        out.println("<tr><th>Sports</th><th>Math</th><th>Celebrities</th><th>History</th><th>Music</th></tr>");

        out.println("</thead>");
        out.println("<tbody>");


        for (int i = 0; i < 4; i++){
          out.println("<tr>");
          for (int j = 0; j < 5; j++) {
            out.println("<td>" + String.valueOf((i+1)*100) + "</td>");
          }
          out.println("</tr>");
        }

        out.println("</tbody></table>");

        out.println("</body></html>");
        Enumeration<String> paramNames = req.getParameterNames();


        List<String> paramList = new ArrayList<String>();
        List<String> jeopardyQuestions = new ArrayList<String>();
        while (paramNames.hasMoreElements()) {
          paramList.add(req.getParameter(paramNames.nextElement()));
        }

        int numParams = paramList.size();
       
        out.close();

        writeToFile("/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/", "jeopardy.txt", paramList);
    }
  } 
   
   /** *****************************************************
    *  Overrides HttpServlet's doGet().
    *  Prints an HTML page with a blank form.
   ********************************************************* */
   public void doGet (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {
      HttpSession session = req.getSession(true);
      user = (String)session.getAttribute("UserID");

      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();

      String qaData = readFile(games_data);
      String[] separatedQs = qaData.split("\n");

      out.println("<html>");
      out.println("  <head>");
      out.println("    <title>Home - Browse Games</title>");
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
      
      out.println("  <br /><br />");
      out.println("    <center><h1> Jeopardy Games List<br /></h1></center>");
      out.println("    <center>");
      out.println("    <div class=\"container\">");
      out.println("<table class=\"table\">");
      out.println("<thead class=\"thead-inverse\">");
      out.println(" <tr>");
      out.println("    <th>#</th>");
      out.println("    <th>Name</th> ");
      out.println("    <th>Owner</th> ");
      out.println("    <th>Description</th> ");
      out.println("    <th>Action</th> ");
      out.println("  </tr> ");
      out.println(" </thead> ");
      for (int i = 0; i < separatedQs.length; i++) {
        String[] data = separatedQs[i].split(",");
        String game = data[0];
        String owner = data[1];
         String info = data[2];
         String description = "";
        for (int x = 1; x < data.length; x++) {
          if (x == data.length - 1) description = description + data[x];
          else description = description + data[x] + ", ";
        }
        int game_num = i+1;
      out.println(" <tbody> ");
      out.println("   <tr> ");
      out.println("  <th scope=\"row\">" + game_num + "</th> ");
      out.println("   <td>" + game + "</td> ");
      out.println(" <td>" + owner + " </td> ");
      out.println("   <td>" + info + " </td> ");
      out.println("<td> ");
      out.println("          <button type=\"submit\" style=\"text-align:center\" name=\"play\" id=\"play\">Play</button>");
      out.println("          <button type=\"submit\" style=\"text-align:center\" name=\"update\" id=\"update\">Update</button>");
      out.println("          <button type=\"submit\" style=\"text-align:center\" name=\"delete\" id=\"delete\">Delete</button> ");
      out.println("      </td> ");
      out.println(" </tr> ");
      }
      out.println("</tbody> ");
      out.println(" </table> ");
      out.println("     </div>");
      out.println("    </center>");
      out.println("</body>");
      out.println("</html>");
      out.close ();  
   }
   
   private  String readFile(String data)
   {
      String cleared_str_data = ""; 
      try 
      {
         java.net.URL url = new java.net.URL( data );
         java.net.URLConnection urlcon = url.openConnection();
         java.io.BufferedReader input_file = new java.io.BufferedReader( new java.io.InputStreamReader( urlcon.getInputStream() ) );
         java.lang.String line = new java.lang.String();
         while ((line = input_file.readLine()) != null) {            
            if (line.length() > 0 && cleared_str_data == "") cleared_str_data = line;  
            else cleared_str_data += "\n" + line; 
         }
         input_file.close();
      } catch ( java.lang.Exception e ) {
         System.out.println( "ERROR: Cannot read input file !!" );
      }
      return cleared_str_data;
   }
  
  private  void writeToFile( java.lang.String foldername, java.lang.String filename, List<String> str )
  {
      try {
          java.io.File file = new java.io.File( foldername, filename );
          java.io.FileWriter fout = new java.io.FileWriter( file , true);

          for (int i = 0; i < str.size(); i++) {
            fout.write( str.get(i) + "," );
            if (i % 4 == 0) {
                fout.write(str.get(i) + "\n");
            } 
          }
          fout.flush();
          fout.close();
      } catch ( java.io.IOException e ) {
          System.out.println( "Error: cannot write to file " + foldername + filename + " : " + e.toString() );
          e.printStackTrace();
      }
  }

}