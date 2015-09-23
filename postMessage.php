<?php
include('dbconnect.php');

postMessage(); 

function postMessage(){
/* require the message the parameter */

	if(isset($_GET['message']) && isset($_GET['location_id']) ){
		
		$message = $_GET['message']; 
		$location_id = isset($_GET['location_id']) ? $_GET['location_id'] : 1; //default is 1 
		

		/* connect to the database */

		$dbc = connectToDB(); 

		

		/* insert the message into the message table query*/
		$query = "INSERT INTO message (comment, location_id) VALUES (\"$message\", $location_id)" ;

		
			$result = performQuery($dbc, $query) ; 

			echo "postMessage works yay"; 
			return header('status: 200'); 
	}

	

		 
	


}

?>