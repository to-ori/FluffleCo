<?php
require "conn.php";
$postID=$conn->escape_string($_POST["postId"]);

	
$sql="SELECT fluffelUser.username, reply_comment FROM `fluffelPostReply` INNER JOIN fluffelUser on fluffelPostReply.reply_user_id=fluffelUser.id WHERE op_id = $postID;";

$result = mysqli_query($conn, $sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response, array("username"=>$row[0], "reply"=>$row[1]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($conn);
?>