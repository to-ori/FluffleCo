<?php
require "conn.php";
$origonalPostID=$conn->escape_string($_POST["op_id"]);
$userID=$conn->escape_string($_POST["user_id"]);
$reply=$conn->escape_string($_POST["reply_comment"]);

	
$sql="INSERT INTO `fluffelPostReply` (`op_id`, `reply_user_id`, `reply_comment`) VALUES ('$origonalPostID', '$userID', '$reply');";

if($conn->query($sql)===TRUE) {
	
	echo "Reply Successful";
}else {
	echo "Error: " . $sql . "<br>" . $conn->error;
}

mysqli_close($conn);
?>