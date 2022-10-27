<?php

	$SUCCESS_CODE = 100;
	$INVALID_CREDENTIALS_CODE = 101;
	$DB_CONN_ERROR = 102;

	$post_data = trim(file_get_contents("php://input"));
	$json_data = json_decode($post_data, true);

	$username = $json_data["username"];
	$password = $json_data["password"];

	if ($username != null && $password != null) {

		$conn = pg_connect(getenv("DATABASE_URL"));

		if($conn) {

			$select_query = "SELECT * FROM user_login WHERE username='" . $username . "'AND password='" . $password . "';";

			$result = pg_query($conn, $select_query);
			if (pg_num_rows($result) == 0) {
				echo json_encode(array('response_code' => $INVALID_CREDENTIALS_CODE));
			} else {
				$r = pg_fetch_assoc($result);
				echo json_encode(array(
					'response_code' => $SUCCESS_CODE, 
					'data' => $r
				));
			}
		} else {
			echo json_encode(array(
				'response_code' => $DB_CONN_ERROR, 
				'error_msg' => pg_connect_error()
			));
		}
	}

?>