package assignment4;



//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;


@WebServlet("/table")
public class table extends HttpServlet
{
	public void doGet (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {

   		res.setContentType ("text/html");
      	PrintWriter out = res.getWriter ();

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

        out.println("</body>");
        out.println("<button style=\"text-align:center\" alight=\"right\" type=\"submit\" name=\"back\" value=\"back\"><b>Back</b></button></html>");
        
       
        out.close();

   }

   public void doPost (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {
   		res.setContentType ("text/html");
      	PrintWriter out = res.getWriter ();
      	out.println(req.getParameter("back"));
   		if (req.getParameter("back") != null) {
        	res.sendRedirect("http://localhost:8080/jeopardy/form");
      	}

      	out.close();
   }





}