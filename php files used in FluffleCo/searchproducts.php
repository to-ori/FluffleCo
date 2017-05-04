<?php
require "conn.php";




if(($conn->escape_string($_POST["productType"]))!=null)
{
	$productType=$conn->escape_string($_POST["productType"]);
	$sql="SELECT * FROM product_info WHERE 	product_type like '$productType';";
}
if(($conn->escape_string($_POST["productName"])) != null)
{
	$productName=$conn->escape_string($_POST["productName"]);
	$sql="SELECT * FROM product_info WHERE 	name like '%$productName%';";
}
if(($conn->escape_string($_POST["petType"])) != null)
{
	$petType=$conn->escape_string($_POST["petType"]);
	$sql="SELECT * FROM product_info WHERE 	pet_type like '$petType';";
}


$result = mysqli_query($conn, $sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response, array("id"=>$row[0],"name"=>$row[1],"description"=>$row[2],"pet_type"=>$row[3], "product_type"=>$row[4]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($conn);
?>