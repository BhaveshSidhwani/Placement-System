<?php

	$SUCCESS_CODE = 500;
	$EMPTY_CODE = 501;
	$INCORRECT_TEST_CODE = 502;
	$DB_CONN_ERROR = 503;

	$QUANTS_TEST_CODE = 1;
	$LOGICAL_TEST_CODE = 2;
	$VERBAL_TEST_CODE = 3;
	$PROGRAMMING_TEST_CODE = 4;

	$post_data = trim(file_get_contents("php://input"));
	$json_data = json_decode($post_data, true);

	$test_type = $json_data["test_type"];

	if($test_type != null) {

		$conn = pg_connect(getenv("DATABASE_URL"));

		if($conn) {

			$select_query = "";
			switch ($test_type) {
				case $QUANTS_TEST_CODE:
					$select_query = "SELECT * FROM quants ORDER BY random() LIMIT 25;";
					break;
				case $LOGICAL_TEST_CODE:
					$select_query = "SELECT * FROM logical ORDER BY random() LIMIT 25;";
					break;
				case $VERBAL_TEST_CODE:
					$select_query = "SELECT * FROM verbal ORDER BY random() LIMIT 25;";
					break;
				case $PROGRAMMING_TEST_CODE:
					$select_query = "SELECT * FROM technical ORDER BY random() LIMIT 25;";
					break;
				default:
					echo json_encode(array('response_code' => $INCORRECT_TEST_CODE));
					break;
			}

			$result = pg_query($conn, $select_query);
			$rows = array();
			while ($r = pg_fetch_assoc($result)) {
				$rows[] = $r;
			}

			if (pg_num_rows($result) == 0) {
				echo json_encode(array('response_code' => $EMPTY_CODE));
			} else {
				$r = pg_fetch_assoc($result);
				echo json_encode(array(
					'response_code' => $SUCCESS_CODE, 
					'data' => $rows
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