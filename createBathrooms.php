<?php
include('dbconnect.php'); 

generateMarkers(); 

function generateMarkers(){
	$dbc = connectToDB(); 
	$query = "SELECT * FROM location"; 
	$result = performQuery($dbc, $query) ; 

	$results = array(); 

	while($row=mysqli_fetch_array($result, MYSQLI_ASSOC)){
			
				$results[] = $row;
			
		}

		
			header('Content-type: application/json');
			echo json_encode(array('results' => $results));
}

?>