<?php
require "conn.php";
$id=$conn->escape_string($_POST["id"]);
$name=$conn->escape_string($_POST["name"]);
$email=$conn->escape_string($_POST["email"]);
$username=$conn->escape_string($_POST["username"]);
$password=$conn->escape_string($_POST["password"]);


$mysql_qry1="UPDATE fluffelUser SET name='$name', email='$email', username='$username', password='$password' WHERE id ='$id';";
$mysql_qry2= "SELECT * FROM fluffelUser WHERE id ='$id';";
mysqli_query($conn, $mysql_qry1);
$result = mysqli_query($conn ,$mysql_qry2);

$response = array();

while($row= mysqli_fetch_array($result))
{
array_push($response, array("id"=>$row[0],"name"=>$row[1],"email"=>$row[2],"username"=>$row[3],"password"=>$row[4]));
}

echo json_encode(array("server_responce"=>$response));

$conn->close();
?>