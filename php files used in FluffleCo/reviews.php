<?php
require "conn.php";
$productID=$conn->escape_string($_POST["productId"]);

	
$sql="SELECT reviewID, reviewTitle, reviewContent, reviewRating, fluffelUser.username from Review INNER JOIN fluffelUser on Review.userID=fluffelUser.id INNER JOIN product_info on Review.productID = product_info.id where product_info.id='$productID';";

$result = mysqli_query($conn, $sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response, array("reviewid"=>$row[0], "title"=>$row[1],"content"=>$row[2],"rating"=>$row[3],"username"=>$row[4]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($conn);
?>