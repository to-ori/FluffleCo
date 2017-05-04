<?php
require "conn.php";
//this returns the id of the post, its title, content and the username of the person who posted it 
//the result is return to teh app as a json object
$sql="SELECT post_id, postTitle, post_content, fluffelUser.username FROM `fluffelPost` INNER JOIN fluffelUser on fluffelPost.post_user_id=fluffelUser.id";


$result = mysqli_query($conn, $sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response, array("post_id"=>$row[0],"post_title"=>$row[1],"post_content"=>$row[2],"post_username"=>$row[3]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($conn);
?>