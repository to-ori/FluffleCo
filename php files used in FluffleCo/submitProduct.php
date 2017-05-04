<?php
require "conn.php";
$productName=$conn->escape_string($_POST["pname"]);
$productDescription=$conn->escape_string($_POST["description"]);
$pet_type=$conn->escape_string($_POST["pet_type"]);
$product_type=$conn->escape_string($_POST["product_types"]);


$mysql_qry="INSERT INTO `product_info` (`name`, `description`, `pet_type`, `product_type`) VALUES ('$productName', '$productDescription', '$pet_type', '$product_type')";


if($conn->query($mysql_qry)===TRUE) {
	
	echo $productName . " Submited";
}else {
	echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}

$conn->close();
?>