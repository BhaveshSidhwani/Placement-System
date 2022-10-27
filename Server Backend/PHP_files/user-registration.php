<?php

	$SUCCESS_CODE = 200;
	$USER_EXISTS_CODE = 201;
	$DB_CONN_ERROR = 202;

	$post_data = trim(file_get_contents("php://input"));
	$json_data = json_decode($post_data, true);

	$username = $json_data["username"];
	$password = $json_data["password"];
	$gender = $json_data["gender"];
	$cgpa = $json_data["cgpa"];
	$workex = $json_data["workex"];

	if ($username != null && $password != null) {

		$conn = pg_connect(getenv("DATABASE_URL"));

		if($conn) {

			$select_query = "SELECT * FROM user_login WHERE username='" . $username . "';";
			$result = pg_query($conn, $select_query);

			if (pg_num_rows($result) == 0) {
				$insert_query = "INSERT INTO user_login (username, password, gender, cgpa, workex) VALUES ('$username', '$password', '$gender', '$cgpa', '$workex')";

				if (pg_query($conn, $insert_query)) {
					echo json_encode(array('response_code' => $SUCCESS_CODE));
				}
			} else {
				echo json_encode(array('response_code' => $USER_EXISTS_CODE));
			}

		} else {
			echo json_encode(array(
				'response_code' => $DB_CONN_ERROR, 
				'error_msg' => pg_connect_error()
			));
		}
	}

?>