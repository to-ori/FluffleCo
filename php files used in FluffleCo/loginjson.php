<?php
require "conn.php";
$user_name=$conn->escape_string($_POST["user_name"]);
$user_pass=$conn->escape_string($_POST["password"]);
$mysql_qry="SELECT * FROM fluffelUser WHERE username like'$user_name' and password like '$user_pass';";
$result = mysqli_query($conn ,$mysql_qry);

$response = array();

while($row= mysqli_fetch_array($result))
{
array_push($response, array("id"=>$row[0],"name"=>$row[1],"email"=>$row[2],"username"=>$row[3],"password"=>$row[4]));
}

echo json_encode(array("server_responce"=>$response));

$conn->close();
?>