<?php

	$SUCCESS_CODE = 600;
	$ERROR_CODE = 601;
	$DB_CONN_ERROR = 602;

	$post_data = trim(file_get_contents("php://input"));
	$json_data = json_decode($post_data, true);

	$id = $json_data["_id"];
	$username = $json_data["username"];
	$password = $json_data["password"];
	$gender = $json_data["gender"];
	$cgpa = $json_data["cgpa"];
	$workex = $json_data["workex"];
	$quants = $json_data["quants"];
	$logical_reasoning = $json_data["logical_reasoning"];
	$verbal = $json_data["verbal"];
	$programming = $json_data["programming"];

	if ($id != null) {

		$conn = pg_connect(getenv("DATABASE_URL"));

		if($conn) {

			$update_query = "UPDATE user_login SET username = '$username', password = '$password', gender = '$gender', cgpa = '$cgpa', workex = '$workex', quants = '$quants', logical_reasoning = '$logical_reasoning', verbal = '$verbal', programming = '$programming' WHERE _id = '$id';";

			if (pg_query($conn, $update_query)) {
				echo json_encode(array('response_code' => $SUCCESS_CODE));
			} else {
				echo json_encode(array('response_code' => $ERROR_CODE));
			}

		} else {
			echo json_encode(array(
				'response_code' => $DB_CONN_ERROR, 
				'error_msg' => pg_connect_error()
			));
		}
	}
?>