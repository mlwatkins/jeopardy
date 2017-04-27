
<%@page import="java.util.*"                %>
<%@page import="java.io.*"                  %>
<%@page import="java.lang.*"                %>



<%@ page import="javax.servlet.*"           %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.*"          %>



<%
    String LogoutServlet = "http://localhost:8080/jeopardy/logout";
    String user = (String)session.getAttribute("UserID");
%>


<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Jeoaprdy Start Screen</title>
  <link rel="shortcut icon" href="/media/favicon.ico" type="image/x-icon" />
  <link rel="icon" href="/media/favicon.ico" type="image/x-icon" />
  <style type="text/css"> 
    body {
      background-color:#ffffff; 
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
        
    textarea {
      padding:5px;  
    }

    #settings { 
      background-color:#fff;
      width:500px;
      padding:10px;
      margin:auto;
      margin-top:75px;
    }

    #settings h1 { 
      color:#2a3698;
      font-size:22px;
      text-align:center;
    }

    #settings h3 {
      color:#2a3698;
      font-size:16px;
      text-align:center;
    }

    #settings label {
      text-align:center;
      font-size:14px;
    }

    #settings select, #settings label, #settings input {
      margin-top:5px;
    }

    #settings select { 
      width:130px;  
    }

    #settings input { 
      width:100px; 
    }

    #settings p {
      margin-bottom:0px; 
    }

  </style>
</head>

<body>   
<!-- HOW DO WE GET LIKE THE GAME NAME / USER FROM THE PREVIOUS SCREEN -->
  <table width="25%" align="right" bgcolor="#E0E0E0" border="0" cellspacing="2" cellpadding="5"
    <tr>
      <td align="right">UserID: <% out.println(user); %> </td>
      <td>
        <form action="http://localhost:8080/jeopardy/logout" method="post">
          <input type="submit" value="Logout"></input>     
        </form>
      </td>
    </tr>
  </table>
  <h2>Jeopardy</h2>
  <h4>Please select the number of teams playing this game.</h4>
      
    <form action="play.jsp">
    <label>Number of Teams: </label>
      <select name="teams" id="teams">
        <option value="1" id="numteams">1 Team</option>
        <option value="2" id="numteams">2 Teams</option>
        <option value="3" id="numteams">3 Teams</option>
        <option value="4" id="numteams">4 Teams</option>
      </select>
        <button type="submit" style="text-align:center" name="start" value="start" onclick="redirectPlay()">Play</button>
    </form>

</body>
<script>

 function redirectPlay() {
  var param = Integer(getElementById("numteams")); 
  window.location = "play.jsp?param="+param; 
}

                            </script>

</html>