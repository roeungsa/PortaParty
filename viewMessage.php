<?php
include('dbconnect.php');

viewMessage(); 


function viewMessage(){
	if(isset($_GET['location_id'])){
		$location_id = $_GET['location_id']; 
		
		$db = connectToDB(); 

		/* query the list of messages*/
		$query = "SELECT comment from message where message.location_id = $location_id ORDER BY time ASC"; 
	// 	$query = "SELECT comment,image from message join drawing where message.location_id = $location_id  GROUP BY comment
 // ORDER BY message.time ASC"; 
		$result = performQuery($db, $query) ; 

		/* create array of messages */ 
		$messages = array(); 

		while($message=mysqli_fetch_array($result, MYSQLI_ASSOC)){
			
				$messages[] = $message;
			
		}

		
			header('Content-type: application/json');
			echo json_encode(array('messages' => $messages));
				
			
	}



}
?>