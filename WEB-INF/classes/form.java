//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;


@WebServlet("/form")
public class form extends HttpServlet
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
   private static String GamesServlet = "http://localhost:8080/jeopardy/games";
   private static String FormServlet = "http://localhost:8080/jeopardy/form";
   
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
      if (req.getParameter("add") != null) {
        res.sendRedirect("http://plato.cs.virginia.edu/~mlw5ea/Assignment-3/assignment3.php");
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
        int numQuestions = (numParams-1)/4; 

        List<String> questionList = new ArrayList<String>();

        for (int i = 0; i < numQuestions; i++) {
          if ((req.getParameter("category"+String.valueOf(i)) != "") && (req.getParameter("score"+String.valueOf(i)) != "")) {
            questionList.add(req.getParameter("question"+String.valueOf(i)));
            questionList.add(req.getParameter("answer"+String.valueOf(i)));
            questionList.add(req.getParameter("category"+String.valueOf(i)));
            questionList.add(req.getParameter("score"+String.valueOf(i)));
          }
        }

        writeToFile("/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/", "jeopardy.txt", questionList, user, gameID);
        
        res.sendRedirect("http://localhost:8080/jeopardy/table");
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
      
      HttpSession session = req.getSession(true);
      user = (String)session.getAttribute("UserID");
      gameID = (String)session.getAttribute("GameID");
      description = (String)session.getAttribute("Description");


      String qaData = readFile(question_data);
      String[] separatedQs = qaData.split("\n");

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

      out.println("      <form action=\"form\" method=\"post\">");
      for (int i = 0; i < separatedQs.length; i++) {
        String[] data = separatedQs[i].split(",");
        String question = data[0];
        String answers = "";
        for (int x = 1; x < data.length; x++) {
          if (x == data.length - 1) answers = answers + data[x];
          else answers = answers + data[x] + ", ";
        }
        out.println("         <table id=\"qaTable\" border=\"1\" align=\"center\" cellpadding=\"3\" >");
      
        out.println("           <tr> <td><b>Question: </b>");
        out.println(" <textarea readonly name=\"question" + i + "\">" + question + "</textarea><br><b>Answer: </b>"); 
        out.println(" <textarea readonly name=\"answer" + i + "\">" + answers + "</textarea></td>");

        // We decided to use Categories and Scores instead of Rows and Columns
        // Professor Upsorn said this was okay, just to let you know that this is the wasy we're doing it

        out.println("        <td>Category: <input type=\"text\" name=\"category" + i + "\" </td>");
        out.println("        <td>Score: <input type=\"text\" name=\"score" + i + "\"</td></tr>");
      }
      out.println("        <tr><td colspan=\"2\" align=\"center\"><button style=\"text-align:center\" type=\"submit\" name=\"create\" value=\"create\">Create Game</button></td>");

       // This button links to the assignment 3 php, however our assignment 3 isn't working properly. We need to fix this for the next assignment
      out.println("        <td colspan=\"2\" align=\"center\"><button style=\"text-align:center\" type=\"submit\" name=\"add\" value=\"add\">Add More Q/A</button></td></tr></table>");      
      out.println("      </form>");       
      out.println("    </center>");
      out.println("  </body>");
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

  private  void writeToFile( java.lang.String foldername, java.lang.String filename, List<String> str, String user, String name )
  {
      try {
          java.io.File file = new java.io.File( foldername, filename );
          java.io.FileWriter fout = new java.io.FileWriter( file , true);

          Scanner inputFile = new Scanner(file);

          java.io.File tempFile = new java.io.File( foldername, "temp-games.txt" );
          java.io.FileWriter tout = new java.io.FileWriter( tempFile , false);

          // Read lines from the file until no more are left.
          while (inputFile.hasNext())
          {
         // Read the next name.
            String data = inputFile.nextLine();
            if (data.contains("User:")) {
              String[] userCheck = data.split(":");
              String data2 = inputFile.nextLine();
              String[] gameCheck = data2.split(":");
              if ((userCheck[1] == user) && (gameCheck[1] == name)) {
                tout.write("User:"+user+"\n");
                tout.write("GameID:"+name+"\n");
                for (int i = 0; i < str.size(); i++) {
                  tout.write( str.get(i) + "," );
                }
                tout.write("\n");
                tout.write("\n");
              } else {
                tout.write(data + "\n");
                tout.write(data2 + "\n");
              }
            } else {
              tout.write(data + "\n");
            }
            
          }

          
          tout.flush();
          tout.close();
          // Close the file.
          inputFile.close();
          // have user enter a game name
          // fout.write("User:"+user+"\n");
          // fout.write("GameID:"+name+"\n");
          // for (int i = 0; i < str.size(); i++) {
          //   fout.write( str.get(i) + "," );
          // }

          boolean success = tempFile.renameTo(file);
          
      } catch ( java.io.IOException e ) {
          System.out.println( "Error: cannot write to file " + foldername + filename + " : " + e.toString() );
          e.printStackTrace();
      }
  }

}