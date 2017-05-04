<?php
require "conn.php";

	$productType=$conn->escape_string($_POST["productType"]);
	$sql="SELECT * FROM product_info WHERE 	product_type like '$productType';";

$result = mysqli_query($conn, $sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response, array("id"=>$row[0],"name"=>$row[1],"description"=>$row[2],"pet_type"=>$row[3], "product_type"=>$row[4]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($conn);
?>