<?php
require "conn.php";
$email=$conn->escape_string($_POST["email"]);
$password=$conn->escape_string($_POST["password"]);
$mysql_qry="SELECT * FROM fluffelAdmin WHERE email like '$email' AND password like '$password';";
$result = mysqli_query($conn ,$mysql_qry);

if(mysqli_num_rows($result)>0) {
	$row = mysqli_fetch_assoc($result);
	$name = $row["name"];
	echo "true";
}else {
	echo "false";
}
$conn->close();
?>