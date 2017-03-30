<!doctype html>
<html>
<head>
  <title>Inclass exercise 3: PHP</title>
    <style>
      a:hover
      {
        background-color:white;
      }    
      button
      {
        margin:auto;
      }   
    </style>
 
    <script type="text/javascript">
      function setFocus()
      {
    	  document.forms[0].elements[0].focus();
      }
    </script>
</head>

<body onload="setFocus()">
  <center>
  <h2>Add Jeopardy Questions</h2>
  <!-- Need to get name of game from games.java -->
  <h2>Game name: </h2>
  <h2>Choose Question Type:</h2>
        <select id="selectQuestion" onchange="update_screen()">
            <option value="ct"> -Choose Type- </option>
            <option value="multiple-choice">Multiple Choice</option>
            <option value="true-false">True/False</option>
            <option value="short-answer">Short Answer</option>
        </select> 
 
        <form id="multiple-choice" style="display:none" method="post" action="assignment3.php">
            MC Question:
            <input type="text" id="mcQuestion" name="mcQuestion" />
              <ul class="answers">
                    Answer 1:
                    <input type="text" name="MCa" value="" id="MCa"/>
                    <br/> Answer 2:
                    <input type="text" name="MCb" value="" id="MCb"/>
                    <br/> Answer 3:
                    <input type="text" name="MCc" value="" id="MCc"/>
                    <br/> Answer 4:
                    <input type="text" name="MCd" value="" id="MCd"/>
                    <br/>
                </ul>
               <button type="submit" id="submit" value="Submit" onclick="submitQuestion()">Submit</button>
                <button type="reset"  value="Reset" onclick="reset()">Clear</button>
                <button type="random"  value="random" onclick="random()">Random</button>
        </form>

            <form id="true-false" style="display:none" method="post" action="assignment3.php">
                T/F Question:
                <input type="text" id="tfQuestion" name="tfQuestion" />
                <ul class="answers">
                    Answer:
                    <input type="text" name="TF" value="" id="TF"/>
                </ul>
                <button type="submit" id="submit" value="Submit" onclick="submitQuestion()">Submit</button>
                <button type="reset"  value="Reset" onclick="reset()">Clear</button>
                <button type="random"  value="random" onclick="random()">Random</button>
            </form>

            <form id="short-answer" style="display:none" method="post" action="assignment3.php">
                SA Question:
                <input type="text" id="saQuestion" name="saQuestion" /> 
                <br/>
                Answer:
                <textarea rows="4" name="saAnswer" id="saAnswer"></textarea>
                <br/>
                <button type="submit" id="submit" value="Submit" onclick="submitQuestion()">Submit</button>
                <button type="reset"  value="Reset" onclick="reset()">Clear</button>
                <button type="random"  value="Random" onclick="random()">Random</button>
            </form>


    <script>
        function update_screen()
     {
      console.log("update_screen");
      document.getElementById("confirm").style.display = "none";
      document.getElementById("edit").style.display = "none";
      document.getElementById("data").style.display = "none";
      var selected_value = document.getElementById("selectQuestion").value;
        if (selected_value == "multiple-choice") {
          document.getElementById("multiple-choice").style.display = "block";
          document.getElementById("true-false").style.display = "none";
          document.getElementById("short-answer").style.display = "none";
        } else if (selected_value == "true-false") {
        document.getElementById("true-false").style.display = "block";
          document.getElementById("multiple-choice").style.display = "none";
          document.getElementById("short-answer").style.display = "none";
      } else if (selected_value == "short-answer") {
        document.getElementById("short-answer").style.display = "block"; 
        document.getElementById("true-false").style.display = "none";
          document.getElementById("multiple-choice").style.display = "none";  
       }
     }

     function reset() {
      alert("In reset");
      var selected_value = document.getElementById("selectQuestion").value;
      if (selected_value == "multiple-choice") {
            document.getElementById("MCa").value = "";
            document.getElementById("MCb").value = "";
            document.getElementById("MCc").value = "";
            document.getElementById("MCd").value = "";
            document.getElementById("mcQuestion").value = "";
        }
        else if (selected_value == "true-false") {
            document.getElementById("tfQuestion").value = "";
            document.getElementById("TF").value = "";
        }
        else if (selected_value == "short-answer") {
            document.getElementById("saQuestion").value = "";
            document.getElementById("saAnswer").value = "";
        }
        document.getElementById("data").style.display = "none";
     }

     function random() {
        alert("In random");
        var selected_value = document.getElementById("selectQuestion").value;
        if (selected_value == "multiple-choice") {
            document.getElementById("MCa").value = "1820";
            document.getElementById("MCb").value = "1819";
            document.getElementById("MCc").value = "1816";
            document.getElementById("MCd").value = "1817";
            document.getElementById("mcQuestion").value = "What Year Was UVA Founded?";
        }
        else if (selected_value == "true-false") {
            document.getElementById("tfQuestion").value = "Thomas Jefferson Was the 4th Prsident of the US";
            document.getElementById("TF").value = "false";
        }
        else if (selected_value == "short-answer") {
            document.getElementById("saQuestion").value = "What is the meaning of life?";
            document.getElementById("saAnswer").value = "What makes the meaning of life is people, so you try to be good to people immediately around you and in your broader community. So a lot of my projects are about how I can affect the world in the hundreds of millions. Reid Hoffman";
        }
     }

     function validateInput() 
     {
      console.log("validateInput");
      if (selected_value == "multiple-choice" && (document.getElementById("MCa").value == "" || document.getElementById("MCb").value == "" || document.getElementById("MCc").value == "" || document.getElementById("MCd").value == "" ||document.getElementById("mcQuestion").value == "")) {
                alert("Please fill out all boxes!");
        }
        else if (selected_value == "true-false" && (document.getElementById("tfQuestion").value == "" || document.getElementById("TF").value == "")) {
                alert("Please fill out all boxes!");
        }
        else if (selected_value == "short-answer" && (document.getElementById("saQuestion").value == "" || document.getElementById("saAnswer").value == "")) {
            alert("Please fill out all boxes!");
        }
        else {
          alert("Thank you for your submission!");
        }
     }

     function submitQuestion() {
        location.href = "assignment3.php";
        document.getElementById("confirm").style.display = "block";
        document.getElementById("edit").style.display = "block";
        document.getElementById("data").style.display = "block";
    }
    </script>
   

  </center>
  
</body>
</html>

 
<?php
   print_r($_POST);
   // retrieve data from the form submission
   $questions = extract_data();      
   // print_array($project_scores);

   // prepare data to be written to file
   $data = "";
   while ($curr = each($questions)) 
   {
      $k = $curr["key"];
      $v = $curr["value"];

      if (!empty($data))
         $data = $data.",";
      
      $data = (string)$data.(string)$v;    
   }
   
   # specify a path, using a file system, not a URL
   # [server]    /cslab/home/<em>your-username</em>/public_html/<em>your-project</em>/data/filename.txt
   # [local]     /XAMPP/htdocs/<em>your-project</em>/data/filename.txt
   
   
   $filename = "/Applications/XAMPP/htdocs/Assignment-3/test-data.txt"; 

   $datafile = "/Applications/XAMPP/htdocs/Assignment-3/questions.txt";
   
   // if there is nothing, don't write it 
   if (!empty($data))
      write_to_file($filename, $data);
?>
   <hr />   
    
<?php
   // read from file and display data in a table
   $file_data = read_file($filename);
   if (!empty($file_data))
   {
?>
      <table id="data" style="display:block" border="1" align="center" cellpadding="3" width="20%">
        <tr>
          <th>Question</th>
          <th>Answer(s)</th>       
        </tr>
<?php  
      while ($curr_line = each($file_data))    // each value of a $data array is a line from the file
      {
         $v_line = $curr_line["value"];        // each value is a string of scores separated by a comma
?>
         <tr> 
<?php        
         // to use individual scores, split the value 
         $splitted_answers = split("\,", $v_line);
         while ($curr_q = each($splitted_answers))
         {
            $v_q = $curr_q["value"];      // each project score value
            if (!empty($v_q))
               echo "<td align='center'>$v_q</td>";
         }    
?>
         </tr>
<?php             
      }
?>   
      </table>
<?php
  }
?> 

<form action="assignment3.php" method="post">
  <button type="submit" style="display:block" name="confirm" id="confirm">Confirm</button>
  <button type="submit" style="display:block" name="edit" id="edit" onclick="reset()">Edit</button>
</form>


</body>
</html>

<?php
  if (isset($_POST['confirm']))
  {
    $data = read_file($filename);
    write_to_datafile($datafile, $data[0]);
  }
?>

<?php   

   /* Retrieve data from the form submission,
    * Convert project scores to percentages
    * Return an array of project scores
    */
   function extract_data()
   {
    $data = array();

      // To retrieve all param-value pairs from a post object
    foreach ($_POST as $key => $val)
      {
         $data[$key] = $val;      // record all form data to an array
      }
    reset($data);
    return $data;
   }   
   
   function write_to_file($filename, $data)
   {
      
      $file = fopen($filename, "w");      // if the file doesn't exist, create a new file
      // chmod($filename, 0775);             // set permission. 
                                          // Note: consider chmod 755 here but 777 when manually creating a file
                                          //    who is the owner?
      fputs($file, $data);
      fclose($file);
   }

   function write_to_datafile($filename, $data)
   {

      
      $file = fopen($filename, "a");      // if the file doesn't exist, create a new file
      // chmod($filename, 0775);             // set permission. 
                                          // Note: consider chmod 755 here but 777 when manually creating a file
                                          //    who is the owner?
      fputs($file, $data."\n");
      fclose($file);
   }

   function read_file($filename)
   {
      $file = fopen($filename, "r");      // r: read only
      $data_array = "";

      while (!feof($file)) 
      {
         $data_array[] = fgets($file);
      }
      fclose($file);
      return $data_array;
   }
?>

