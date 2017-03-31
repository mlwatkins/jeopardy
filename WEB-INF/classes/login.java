import javax.servlet.*; 
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Properties;
 
// import mail service libraries
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
//*********************************************************************

@WebServlet("/login")
public class login extends HttpServlet
{
   private static final long serialVersionUID = 2L;
   
 //**** setting for local  ****/ 
   private static String LoginServlet = "http://localhost:8080/jeopardy/login";   
   private static String GameServlet = "http://localhost:8080/jeopardy/games";
   
   // a data file containing username and password 
   // note: this is a simple login information without encryption. 
   // In reality, credential must be encrypted for security purpose
   public static String user_info = "/Applications/apache-tomcat/webapps/jeopardy/WEB-INF/data/user-info.txt";
   

   // Form parameters.
   private static String btn;
   // info for returning users
   private static String userID;
   private static String pwd;
   // info for new users
   private static String newUserID;
   private static String newPwd;
   private static String confirmPwd;
   private static String email;
   
   
   // doPost() tells doGet() when the login is invalid.
   private static boolean invalidID = false;
   
   private int number_attempts = 0;

   private Random rand = new Random();
 
   final String from_email = "mlw5ea@virginia.edu";
   final String to_email = "mlw5ea@virginia.edu";
   private String str_cofm = "";
 
   final String username = "mlw5ea";
   final String password = "IEd5a4e-";

   /** *****************************************************
    *  Overrides HttpServlet's doGet().
    *  prints the login form.
   ********************************************************* */
   public void doGet (HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
   {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter ();

      out.println("<html>");
   
      out.println("<head>");
      out.println("  <title>Jeopardy - Login</title>");
   
      out.println("   <style>");
      out.println("      body, html {");
      out.println("         margin: 10 auto;");
      out.println("         padding: 10;");
      out.println("         color: #202020;");
      out.println("         background-color: #ddeeff;");
      out.println("         font-family: 'Lucida Grande',Verdana,Helvetica,Arial,Geneva,'Bitstream Vera Sans',Sans-Serif;");
      out.println("         font-size: 12px;");
      out.println("      }");     
         
      out.println("      input[type=text] {");  
      out.println("         border: 1px solid #cccccc;");
      out.println("         font: 11px Verdana;"); 
      out.println("         color: black;"); 
      out.println("         line-height: 1.4em;"); 
      out.println("      }");

      out.println("   </style>");

      out.println("</head>");

      out.println("<body onLoad=\"setFocus()\" >");
      out.println("<b><center><h1>Jeopardy Game Login<h1></center></b>");
   
      out.println("<br /><br />");
      
      if (invalidID && number_attempts < 5)
      {  // called from doPost(), invalid ID entered.
         invalidID = false;
         out.println("<br><font color=\"red\">Invalid user ID, password pair. This is attempt " + number_attempts + " out of 5. Please try again.</font><br><br>");
      } else if (invalidID) {
         invalidID = false;
         out.println("<br><font color=\"red\">Invalid user ID, password pair. You have reached the maximum number of attempts. A new password will be sent to the email associated with the given userID.</font><br><br>");
      }  
            
      // Returning users
      out.println("<form method=\"post\"");
      out.println("        action=\"" + LoginServlet + "\" id=\"form1\" name=\"form1\">");                      
      out.println("  <table Cellspacing=\"0\" Cellpadding=\"3\" Border=\"0\" >");
      out.println("    <tr><td colspan=\"4\"><b>Returning Users:</b></td></tr>");
      out.println("    <tr>");
      out.println("      <td>UserID:</td>");
      out.println("      <td><input autofocus type=\"text\" name=\"UserID\" size=\"15\" maxLength=\"20\"><td>");
      out.println("      <td>Password:</td>");
      out.println("      <td><input type=\"password\" name=\"pwd\" size=\"15\" maxlength=\"20\"></td>");
      out.println("    </tr>");
      out.println("    <tr>");
      out.println("      <td colspan=\"4\" ><input type=\"submit\" value=\"Log in\" name=\"btn\"></input>");
      out.println("    </tr>");
      out.println("  </table>");      
      out.println("</form>");
      
      out.println("<br />");
      out.println("<hr />");
      out.println("<br />");
      
      // Register new user
      out.println("<form method=\"post\"");
      out.println("        action=\"" + LoginServlet + "\" id=\"form2\" name=\"form2\">");                      
      out.println("  <table Cellspacing=\"0\" Cellpadding=\"3\" Border=\"0\" >");
      out.println("    <tr><td colspan=\"4\"><b>Register New User:</b></td></tr>");
      out.println("    <tr>");
      out.println("      <td>UserID:</td>");
      out.println("      <td><input type=\"text\" name=\"newUserID\" size=\"15\" maxLength=\"20\"></td>");
      out.println("      <td>Contact E-mail:</td>");
      out.println("      <td><input type=\"text\" name=\"email\" size=\"50\" maxlength=\"50\"></td>");
      out.println("    </tr>");
      out.println("    <tr>");
      out.println("      <td>Password:</td>");
      out.println("      <td><input type=\"password\" name=\"newPwd\" size=\"15\" maxlength=\"20\"></td>");      
      out.println("      <td>Confirm Password:</td>");
      out.println("      <td><input type=\"password\" name=\"confirmPwd\" size=\"15\" maxlength=\"20\"></td>");
      out.println("    </tr>");
      out.println("    <tr>");
      out.println("      <td colspan=\"4\"><input type=\"submit\" value=\"Register\" name=\"btn\"></input");
      out.println("    </tr>");
      out.println("  </table>");      
      out.println("</form>");
      
      out.println("<br />");
      out.println("<hr />");
      out.println("<br />");
      out.println("<br />");
      
      out.println("</body>");
      out.println("</html>");

      out.close();
   }

   /*******************************************************
    *  Overrides HttpServlet's doPost().
    *            
      // assume an account will locked after 3 failed attempts
      // write code to check and handle this business logic 
      // (optional) 

   ********************************************************* */
   public void doPost (HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
   {
      HttpSession session = request.getSession (true);
	      
      btn = request.getParameter("btn");
	   
      userID = request.getParameter("UserID");
      pwd = request.getParameter("pwd");
      
      newUserID = request.getParameter("newUserID");
      newPwd = request.getParameter("newPwd");
      confirmPwd = request.getParameter("confirmPwd");
      email = request.getParameter("email");
            
      // need a more sophisticated input validation (not implemented yet)
            
      if (btn.equals("Register"))
      {
         if (newPwd.equals(confirmPwd) && newUserID.length() > 0 && newPwd.length() > 0 && email.length() > 0)
         {
            registerNewUser(newUserID, newPwd, email);
            userID = newUserID;
            pwd = newPwd;
         }
      }
      
      if (isValid(userID, pwd))
      {  // successful
         session.setAttribute("isLogin", "Yes");
         session.setAttribute("UserID", userID);

         response.sendRedirect(GameServlet);

         
      }
      else
      {  // unsuccessful
         session.setAttribute("isLogin", "No");
         session.setAttribute("UserID", "");  
         number_attempts += 1;       

         invalidID = true;
         
         if (number_attempts == 5) {
            setNewPassword(userID);
            doGet( request, response);
         } else
         {
            doGet(request, response);
         }

      }
     
   } 
   
   
   /**
    * isValid: verify userid and password
    * @param userid
    * @param password
    * @return true -- userid/password matches, 
    *         false -- non-existent userid or userid/password does not match
    */
   private boolean isValid(String userid, String password)
   {
      boolean is_valid = false;
      if (userid.length() == 0 || password.length() ==0)
         return false;
      
      try 
      {
         BufferedWriter fileOut = new BufferedWriter(new FileWriter(user_info, true));    	  
         Scanner scanner = new Scanner (new BufferedReader(new FileReader(user_info))); 

         
         while (scanner.hasNextLine())
         {
            String line = scanner.nextLine(); 
            String existing_user = line.substring(0, line.indexOf(","));
            String existing_pwd = line.substring(line.indexOf(",")+1, line.lastIndexOf(","));
            if (userid.equals(existing_user) && password.equals(existing_pwd))
            {
               is_valid = true;
               number_attempts = 0;
               break; 
            }
         }
      
         scanner.close();
         fileOut.close();
      
      } catch (Exception e) {
         System.out.println("Unable to verify the user information ");
         
         // One potential cause of this exception is the path to the data file
         // To verify, open the data file in a web browser
         // Use the path you saw in the browser's address bar to access the data file
         // (note: excluding "file:///")
      
      }
      
      return is_valid;	   
   }
   

   /**
    * registerNewUser: register a new userid/password/email to user_info.txt
    * @param userid
    * @param pwd
    * @param email
    * note: need to verify if userid already exists (not implemented)
    *       need to encrypt the credential (not implemented)
    */
   private void registerNewUser(String userid, String pwd, String email)  
   {
      FileWriter fw = null;
      PrintWriter pw = null;
	 
      try
      {
         fw = new FileWriter(user_info, true);      
         pw = new PrintWriter(fw);
		 
         pw.println(userid + "," + pwd + "," + email );
	  } catch (Exception e)
      {
         System.out.println("ERROR while registering new users " + e );
      } finally
      {
         try
         {
            pw.flush();     // flush output stream to file
            pw.close();     // close print writer
         } catch (Exception ignored)    { }
         try
         {
            fw.close();
		 } catch (Exception ignored)    { }
      }
   }
   
   private void setNewPassword(String userid) 
   {
      String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
      String newPassword = "";
      for (int i = 0; i < 8; i++) {
         char nextLetter = alphaNumeric.charAt(rand.nextInt(61));
         newPassword = newPassword + String.valueOf(nextLetter);
      }
      String email_message = "Your new password is " + newPassword + ". Please use this password to login to your account with userID " + userid + "."; 
       
      try {
         BufferedWriter fileOut = new BufferedWriter(new FileWriter(user_info, true));         
         Scanner scanner = new Scanner (new BufferedReader(new FileReader(user_info)));
             
         while (scanner.hasNextLine())
         {
            String line = scanner.nextLine(); 
            String existing_user = line.substring(0, line.indexOf(","));
            String existing_email = line.substring(line.lastIndexOf(",")+1);
            if (userid.equals(existing_user))
            {
               registerNewUser(userid, newPassword, existing_email);
               sendEmail(existing_email, email_message);
               break; 
            }
         }
       
         scanner.close();
         fileOut.close();
      } catch (Exception e) {
         System.out.println("Unable to verify the user information ");
      }
 
       
 
   }
 
   private void sendEmail(String email_address, String email_message)
   {
      Properties props = new Properties();
       
      // Specifies the IP address of your default mail server
      // for example, if you are using gmail server as an email sever
      // you will pass smtp.gmail.com as value of mail.smtp host.
       
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");
 
      // pass properties object and authenticator object
      // for authentication to Session instance
       
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
         }
      });        
                
      if (email_address.length() > 0 && email_message.length() > 0)
      {
         try
         {
            // this example was designed to send an email to only *one* recipient. 
            // how should the code be modified to send to multiple recipients? 
            // how should the code be modified to send a "CC" recipient?
           
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from_email));       // from which email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_email));     // send to which email address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email_address));   // add more recipients
            message.setSubject("Password Reset");               // set a subject of an email
            message.setContent(email_message, "text/html; charset=utf-8");     // set a message of an email
             
            Transport.send(message);
            System.out.println("Email sent");                              
                
            // always provide feedback, so that the users know what happens, what to do next
            str_cofm = "Email notification was sent";
             
         } catch (MessagingException e) {
            // if something's wrong, let the user knows            
            str_cofm = "There is a problem while sending an email. " + 
                       "Please check your code and try sending an email again."; 
            throw new RuntimeException(e);
         }
       }                 
   }

} 
