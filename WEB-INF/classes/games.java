

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
   private static java.lang.String games_data = "/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/games.txt";

   private static final long serialVersionUID = 2L;
   
 //**** setting for local  ****/ 
   private static String LoginServlet = "http://localhost:8080/jeopardy/login";
   private static String LogoutServlet = "http://localhost:8080/jeopardy/logout";
   private static String CreateServlet = "http://localhost:8080/jeopardy/create";

   // a data file containing username and password 
   // note: this is a simple login information without encryption. 
   // In reality, credential must be encrypted for security purpose
   public static String user_info = "/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/user-info.txt";
   
   // doPost() tells doGet() when the login is invalid.
   private static boolean invalidID = false;
   
   private String user;
   private String game = ""; 
   private int numberOfGames = 0;
   
   
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
      HttpSession session = req.getSession (true);
      user = (String)session.getAttribute("UserID");

      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();

      String gameData = readFile(games_data);
      String[] games = gameData.split("\n");
      user = (String)session.getAttribute("UserID");

      if (req.getParameter("play") != null) {
        res.sendRedirect("http://localhost:8080/jeopardy/table");
      }

      for (int i = 0; i < numberOfGames; i++) {
        if (req.getParameter("update"+String.valueOf(i)) != null) {
          String[] gameInfo = games[i].split(",");

          boolean userCheck = gameInfo[1].equals(user);
          if (userCheck == true) {
            session.setAttribute("GameID", games[i].split(",")[0]);
            session.setAttribute("Description", games[i].split(",")[2]);
          }
        }  
        if (req.getParameter("delete" + String.valueOf(i)) != null) {
          game = games[i].split(",")[0];
        }  
      }

      if ((String)session.getAttribute("GameID") != null) {
        res.sendRedirect("http://localhost:8080/jeopardy/form");
      } else if (game != "") {
        deleteGame("/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/games.txt", user, game);
        res.sendRedirect("http://localhost:8080/jeopardy/games");
      } else {
        res.sendRedirect("http://localhost:8080/jeopardy/games");
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
      session.setAttribute("GameID", null);
      session.setAttribute("Description", null);


      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();

      String gameData = readFile(games_data);
      String[] games = gameData.split("\n");
      numberOfGames = games.length;

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
      out.println("        <form action=\"" + CreateServlet  + "\" method=\"get\">");
      out.println(" <button type=\"submit\">Create New Game</button>");
      out.println("</form>");

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
      for (int i = 0; i < games.length; i++) {
        String[] data = games[i].split(",");
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
      out.println("      <form action=\"http://localhost:8080/jeopardy/table\" method=\"get\">");
      out.println("          <button type=\"submit\" style=\"text-align:center\" name=\"play\" value=\"play\">Play</button>");
      out.println("</form>");
      out.println("      <form action=\"http://localhost:8080/jeopardy/games\" method=\"post\">");
      out.println("          <button type=\"submit\" style=\"text-align:center\" name=\"update" + i +"\" value=\"update\">Update</button>");
      out.println("</form>");
      out.println("      <form action=\"http://localhost:8080/jeopardy/games\" method=\"post\">");
      out.println("          <button type=\"submit\" style=\"text-align:center\" name=\"delete"+ i +"\" value=\"delete\">Delete</button> ");
      out.println("</form>");
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
   
   private  String readFile(String filename)
   {
      String cleared_str_data = ""; 
      try 
      {
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);

      // Read lines from the file until no more are left.
        while (inputFile.hasNext())
        {
         // Read the next name.
          String game = inputFile.nextLine();
          cleared_str_data = cleared_str_data + game + "\n";
        }

      // Close the file.
        inputFile.close();
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

  private void deleteGame( String filename, String user, String game) {
    String gameData = readFile(filename);
    String[] games = gameData.split("\n");
    try {
      //String tempFile = "/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/tempfile.txt";
      java.io.File file = new java.io.File( filename );
      java.io.FileWriter fout = new java.io.FileWriter( file );

      for (int i = 0; i < games.length; i++) {
        String[] gameInfo = games[i].split(",");
        boolean gameCheck = gameInfo[0].equals(game);
        boolean userCheck = gameInfo[1].equals(user);
        if (gameCheck == false || userCheck == false) {
          fout.write(gameInfo[0]+","+gameInfo[1]+","+gameInfo[2]+"\n");
        }
      }
      fout.flush();
      fout.close();

    } catch ( java.io.IOException e ) {
      System.out.println( "Error: cannot write to file " + filename + " : " + e.toString() );
      e.printStackTrace();
    }
    
  }

}