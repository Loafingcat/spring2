package kr.co.loafingcat.configuration.http;

public enum BaseResponseCode {

	SUCCESS, // 성공
	ERROR, // 에러
	LOGIN_REQUIRED,
	DATA_IS_NULL, //Null
	VALIDATE_REQUIRED, //필수 체크
	UPLOAD_FILE_IS_NULL
	;
}
