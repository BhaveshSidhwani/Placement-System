<?php

	$SUCCESS_CODE = 300;
	$EMPTY_CODE = 301;
	$DB_CONN_ERROR = 302;

	$conn = pg_connect(getenv("DATABASE_URL"));

	if($conn) {

		$select_query = "SELECT * FROM company;";

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

?>