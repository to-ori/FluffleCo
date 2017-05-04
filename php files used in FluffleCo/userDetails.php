<?php
require "conn.php";
 
$sql = "select * from fluffelUser ";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('id'=>$row[0],
'name'=>$row[1],
'email'=>$row[2],
'username'=>$row[3],
'password'=>$row[4]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($conn);
 
?>