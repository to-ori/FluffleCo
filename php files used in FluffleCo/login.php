<?php
require "conn.php";
$user_name=$conn->escape_string($_POST["user_name"]);
$user_pass=$conn->escape_string($_POST["password"]);
$mysql_qry="SELECT * FROM fluffelUser WHERE username like'$user_name' and password like '$user_pass';";
$result = mysqli_query($conn ,$mysql_qry);



if(mysqli_num_rows($result)>0) {
	$row = mysqli_fetch_assoc($result);
	$name = $row["name"];
	$username = $row["username"];
	echo "true";
}else {
	echo "false";
}
$conn->close();
?>