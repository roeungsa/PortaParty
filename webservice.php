<?php 
include('dbconnect.php');

postMessage(); 
//viewMessage(); 





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


	}

	echo "postMessage works yay"; 
	return header('status: 201'); 

		 
	


}


function viewMessage(){
	if(isset($_GET['location_id'])){
		$location_id = $_GET['location_id']; 
		
		$db = connectToDB(); 

		/* query the list of messages*/
		$query = "SELECT * from message where message.location_id = $location_id"; 
		$result = performQuery($db, $query) ; 

		/* create array of messages */ 
		$messages = array();

		while($message=mysqli_fetch_array($result, MYSQLI_ASSOC)){
			
				$messages = array('comment'=>$message);
			
		}

		
			header('Content-type: application/json');
			echo json_encode($messages);
		
	}



}

?> 