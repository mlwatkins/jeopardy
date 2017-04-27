<%@page import="java.util.*" 						  %>
<%@page import="java.io.*" 						  	  %>
<%@page import="java.lang.*" 						  %>


<%@ page import="javax.servlet.*"					  %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.*" 				  %>



<%
	String LogoutServlet = "http://localhost:8080/jeopardy/logout";
    String user = (String)session.getAttribute("UserID");
    //String game = (String)session.getAttribute("GameID");

    String game = "test";

    String filename = "/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/" + game + ".txt";

    String question = "";
    String answer = "";

    ArrayList<Integer> list = (ArrayList<Integer>)session.getAttribute("counter");
    list.add(Integer.parseInt(request.getParameter("param")));
    session.setAttribute("counter", list);


    //java.io.File file = new java.io.File(filename);


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

        #jeopardyTable tbody td, #jeopardyTable thead th { 
        	vertical-align: middle;
        	background-color:#2a3698; 
        	padding:5px;
        	text-align:center;
        	width:20%;
        	color:#ffff5f;
        	height:60px;
        	font-size:20px;
        }

        #jeopardyTable tbody td { 
        	cursor:pointer; 
        	height:100px; 
        	border:3px solid #2a3698;
        }

        #jeopardyTable tbody td.ie-hack { border:3px solid #ffff5f;}

        #jeopardyTable tbody td.dirty h3 { color:#2a3698;  }

        #jeopardyTable tfoot td {text-align: center; background-color:#2a3698;}

        }
.hide {
    display:none;   
}

</style>
    
    </head>
	<body>
    <form action="play.jsp" method="post">
        <button style="text-align:center" alight="right" type="submit" name="back" value="back"><b>Back</b></button>
    </form>
    <center><p style="color:#ffff5f;">Jason Ellington & Madeline Watkins </p></center>
    <% int num = Integer.parseInt(request.getParameter("param")); %>

    <%

            if (num % 5 == 0) {
                session.setAttribute("Category", "Music");
            } else if (num % 5 == 1) {
                session.setAttribute("Category", "Sports");
            } else if (num % 5 == 2) {
                session.setAttribute("Category", "Math");
            } else if (num % 5 == 3) {
                session.setAttribute("Category", "Celebrities");
            } else if (num % 5 == 4){
                session.setAttribute("Category", "History");
            }

            if (num <= 5) {
                session.setAttribute("Score", 100);
            } else if (num <= 10) {
                session.setAttribute("Score", 200);
            } else if (num <= 15) {
                session.setAttribute("Score", 300);
            } else if (num <= 20) {
                session.setAttribute("Score", 400);
            }
    %>


	<% 
        String category = (String)session.getAttribute("Category");
        Integer score = (Integer)session.getAttribute("Score");

        java.io.FileReader fr = new java.io.FileReader(filename);
        java.io.BufferedReader br = new java.io.BufferedReader(fr);

        String s; 
        String[] data;

        while ((s = br.readLine()) != null) {
            data = s.split(",");

            if ((data[2].equals(category)) && (Integer.parseInt(data[3]) == score)) {
                question = data[0];
                answer = data[1];
            }
            
        }

		out.println("<center><h1 style=\"color:#ffff5f;\">Question: \n" + question + "</h1></center>");
	%>

    <center><button onclick="myFunction()">Answer</button></center>

    <div id="myDIV">
    <% out.println("<center><h4 style=\"color:#ffffff;\">Answer: \n" + answer + "</h4></center>"); %>
    </div>


    <script>
    var x = document.getElementById('myDIV');
    x.style.display = 'none';
    function myFunction() {
        if (x.style.display === 'none') {
            x.style.display = 'block';
        } else {
            x.style.display = 'none';
        }
    }
    </script>
	</body>


</html>
