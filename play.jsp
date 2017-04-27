<%@page import="java.util.*" 						  %>
<%@page import="java.io.*" 						  	  %>
<%@page import="java.lang.*" 						  %>


<%@ page import="javax.servlet.*"					  %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.*" 				  %>



<%
	String LogoutServlet = "http://localhost:8080/jeopardy/logout";
    String user = (String)session.getAttribute("UserID");
    String game = (String)session.getAttribute("GameID");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>


        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Jeopardy</title><meta name="ROBOTS" content="NOODP" />
        <meta name="google-site-verification" content="UYS7arlGlbDobqQSsucWwkbZyiC-6_cCWlUn9Eb5b2M" />
        <link rel="shortcut icon" href="/media/favicon.ico" type="image/x-icon" />
        <link rel="icon" href="/media/favicon.ico" type="image/x-icon" />

        <!-- To get the styling for this jeopardy table, we inspected the website "https://jeopardylabs.com/play/html-jeopardy9"
        I spoke to Professor Upsorn and she said as long as we were just using the styling, that this was okay.  -->

<style type="text/css"> 

        body {  
        	background-color:#2a3698; 
        	height:100%; 
        	font-family:Verdana, Arial, Helvetica, sans-serif;
        	padding-bottom:100px; 
        }
        
        label { 
        	display:block; 
        	font-size:16px; 
        }

        input, textarea, select { 
        	display:block; 
        	font-family:Verdana, Arial, Helvetica, sans-serif; 
        	margin:auto;
        }

        textarea { padding:5px;  }

      	td a {
            display:block;
          	width:100%;
      		  color:#ffff5f;
      		}

      	.hide {
      			display:none;
      		}

        #title {
	        text-align:center;
	        font-size:36px;
	        margin:auto;
	        line-height:36px; }

        textarea #title {
	        height:50px;
	        width:90%;
	        color:#012b45;}

        #jeopardyTable { width:100%; background-color:#000000; padding:0; margin:0; margin:auto; font-size:16px;}

        #jeopardyTable textarea { width:90%; height:30px; font-size:18px; line-height:22px;}

        #jeopardyTable h3 { color:#ffff5f; text-align:center; font-size:20px; font-weight:bold;}

        #jeopardyTable h1 { color:#ffff5f; text-align:center; font-size:30px; font-weight:bold;}

        #jeopardyTable tbody td, #jeopardyTable thead th {vertical-align: middle;background-color:#2a3698; padding:5px;text-align:center;width:20%;color:#ffff5f;height:60px;font-size:20px;}

        #jeopardyTable tbody td { cursor:pointer; height:100px; border:3px solid #2a3698;}

        #jeopardyTable tbody td.ie-hack { border:3px solid #ffff5f;}

        #jeopardyTable tbody td.dirty h3 { color:#2a3698;  }

        #jeopardyTable tfoot td {text-align: center; background-color:#2a3698;}

</style>


       	<script src="showQuestion.js"></script>
        </head>
             <body> 
          <table width="25%" align="right" bgcolor="#E0E0E0" border="0" cellspacing="2" cellpadding="5">
            <tr>
              <td align="right">UserID: <% out.println(user); %> </td>
              <td>
                <form action="http://localhost:8080/jeopardy/logout" method="post">
                  <input type="submit" value="Logout"></input>     
                </form>
              </td>
            </tr>
          </table>
        <form action="question.jsp">
        <table id="jeopardyTable" cellspacing="5" cellpadding="0">
        <h1 style="color:#ffff5f;"> Jeopardy Game </h1>
        <thead>

        <tr><th>Sports</th><th>Math</th><th>Celebrities</th><th>History</th><th>Music</th></tr>

        </thead>
        <tbody>


        <% 
        int count = 1; 
        for (int i = 0; i < 4; i++){
          out.println("<tr>");
          for (int j = 0; j < 5; j++) {
            String score = String.valueOf((i+1)*100); 
            out.println("<td id=\"score" + count + "\" onclick=\"showQuestion("+count+")\">"+ score);
            count += 1; 
           }

        %>
  
        </tr>
        <%
        }
		%>


        </tbody>
        </table>

        </body>
        <button style="text-align:center" alight="right" type="submit" name="back" value="back"><b>Back</b></button>
        <button style="text-align:center" alight="right" type="submit" name="settings" value="settings"><b>Settings</b></button></html>

              </form> 