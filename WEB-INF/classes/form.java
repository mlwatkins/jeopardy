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
   private static java.lang.String question_data = "/Applications/XAMPP/htdocs/Assignment-3/questions.txt";
   
   
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
      if (req.getParameter("add") != null) {
        res.sendRedirect("http://localhost/Assignment-3/assignment3.php");
      } else if (req.getParameter("create") != null) {
        Enumeration<String> paramNames = req.getParameterNames();


        List<String> paramList = new ArrayList<String>();
        List<String> jeopardyQuestions = new ArrayList<String>();
        while (paramNames.hasMoreElements()) {
          paramList.add(req.getParameter(paramNames.nextElement()));
        }

        int numParams = paramList.size();

        writeToFile("/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/", "jeopardy.txt", paramList);
        
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

      String qaData = readFile(question_data);
      String[] separatedQs = qaData.split("\n");

      out.println("<html>");
      out.println("  <head>");
      out.println("    <title>Jeopardy Q/A Selector</title>");
      out.println("  </head>");
      out.println("  <body>");
      out.println("    <center><h1>Jeopardy Q/A Selector<br /></h1></center>");
      out.println("    <center>");
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