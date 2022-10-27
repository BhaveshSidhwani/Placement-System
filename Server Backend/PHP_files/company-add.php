<?php

	$SUCCESS_CODE = 400;
	$COMPANY_EXISTS_CODE = 401;
	$DB_CONN_ERROR = 402;

	$post_data = trim(file_get_contents("php://input"));
	$json_data = json_decode($post_data, true);

	$company_name = $json_data["company_name"];
	$job_role = $json_data["job_role"];
	$job_description = $json_data["job_description"];
	$package = $json_data["package"];
	$apply_link = $json_data["apply_link"];

	if ($company_name != null && $job_role != null && $job_description != null && $package != null) {

		$conn = pg_connect(getenv("DATABASE_URL"));

		if($conn) {

			$select_query = "SELECT * FROM company WHERE company_name='" . $company_name . "' AND job_role='" . $job_role . "';";
			$result = pg_query($conn, $select_query);

			if (pg_num_rows($result) == 0) {
				$insert_query = "INSERT INTO company (company_name, job_role, job_description, package, apply_link) VALUES ('$company_name', '$job_role' , '$job_description', '$package', '$apply_link');";

				if (pg_query($conn, $insert_query)) {
					echo json_encode(array('response_code' => $SUCCESS_CODE));
				}
			} else {
				echo json_encode(array('response_code' => $COMPANY_EXISTS_CODE));
			}

		} else {
			echo json_encode(array(
				'response_code' => $DB_CONN_ERROR, 
				'error_msg' => pg_connect_error()
			));
		}
	}

?>