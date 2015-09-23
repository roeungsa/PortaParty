<?php
function connectToDB(){
	// This uses cscilab.bc.edu as the hostname because it lives on
	// cslab.  Use "localhost" on cscilab.bc.edu.
	$hostname = "localhost"; 
	$username = "roeungsa"; 
	$pw = "fe8pV8cj"; 
	$db = "roeungsa";

	$dbc= @mysqli_connect($hostname, $username, $pw, $db) or
					die("Connect failed: ". mysqli_connect_error());
	return ($dbc);
}
function disconnectFromDB($dbc, $result){
	mysqli_free_result($result);
	mysqli_close($dbc);
}

function performQuery($dbc, $query){
	
	//echo "My query is >$query< <br />";
	$result = mysqli_query($dbc, $query) or die("bad query".mysqli_error($dbc));
	return ($result);
}
?>