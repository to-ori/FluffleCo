<?php
require "conn.php";
$reviewTitle=$conn->escape_string($_POST["reviewTitle"]);
$reviewContent=$conn->escape_string($_POST["reviewContent"]);
$reviewRating=$conn->escape_string($_POST["reviewRating"]);
$userID=$conn->escape_string($_POST["userID"]);
$productID=$conn->escape_string($_POST["productID"]);

$mysql_qry="INSERT INTO `Review` (`reviewTitle`, `reviewContent`, `reviewRating`, `userID`, `productID`) VALUES ( '$reviewTitle', '$reviewContent', '$reviewRating', '$userID', '$productID')";


if($conn->query($mysql_qry)===TRUE) {
	
	echo "Review Successful";
}else {
	echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}

$conn->close();
?>