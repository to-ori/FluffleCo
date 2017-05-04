<?php
require "conn.php";

$posttitle=$conn->escape_string($_POST["p_title"]);
$post_content=$conn->escape_string($_POST["p_content"]);
$IDuserPosting=$conn->escape_string($_POST["post_user_id"]);

$sql="INSERT INTO `fluffelPost` (`postTitle`, `post_content`,`post_user_id`) VALUES ('$posttitle','$post_content', '$IDuserPosting')";


if($conn->query($sql)===TRUE) {
	
	echo "Post Successful";
}else {
	echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}

mysqli_close($conn);
?>