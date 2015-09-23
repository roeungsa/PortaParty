<?php
include('dbconnect.php');

postDrawing(); 

function postDrawing(){
/* require the message the parameter */

	if(isset($_GET['image']) && isset($_GET['location_id']) ){
		
		$image = $_GET['image']; 
		$location_id = isset($_GET['location_id']) ? $_GET['location_id'] : 1; //default is 1 
			$dbc = connectToDB(); 

		/* insert the message into the message table query*/
			$query = "INSERT INTO drawing (image, location_id) VALUES (\"$image\", $location_id)" ;

		
			$result = performQuery($dbc, $query); 

			
				//inserted in the database
				echo "postMessage works yay"; 
				return header('status: 200'); 
			
		
	}
	else{
				echo "malfunction"; 
				errorJson('Upload malfunction'); 
	}
		
	
	

		

		

			
}

// helper function, which outputs error messages in JSON format
// so that the iPhone app can read them
// the function just takes in a dictionary with one key "error" and
// encodes it in JSON, then prints it out and then exits the program
function errorJson($msg){
print json_encode(array('error'=>$msg));
exit();
}
	

		 
	




?>