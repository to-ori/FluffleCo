<?php
require "conn.php";

$mysql_qry="SELECT * FROM fluffelUser ;";
$result = mysqli_query($conn ,$mysql_qry);

$response = array();

while($row= mysqli_fetch_array($result))
{
array_push($response, array("id"=>$row[0],"name"=>$row[1],"email"=>$row[2],"username"=>$row[3],"password"=>$row[4]));
}

echo json_encode(array("server_responce"=>$response));

$conn->close();
?>