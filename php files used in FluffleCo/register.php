<?php
require "conn.php";
$name=$conn->escape_string($_POST["name"]);
$email=$conn->escape_string($_POST["email"]);
$username=$conn->escape_string($_POST["username"]);
$password=$conn->escape_string($_POST["password"]);

$mysql_qry="INSERT INTO fluffelUser(name, email, username, password) VALUES('$name', '$email','$username','$password');";
$result = mysqli_query($conn ,$mysql_qry);

if($conn->query($mysql_qry)===TRUE) {
	
	echo "Registration Successful";
}else {
	echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}

$conn->close();
?>