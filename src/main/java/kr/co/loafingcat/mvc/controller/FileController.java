package kr.co.loafingcat.mvc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.loafingcat.configuration.GlobalConfig;
import kr.co.loafingcat.configuration.http.BaseResponse;





@RestController

@RequestMapping("/file")

@Api(tags = "파일 API")

public class FileController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GlobalConfig config;
	/**
	 * 업로드 리턴.
	 * @return
	 */

	@GetMapping
	@ApiOperation(value = "업로드", notes = "")
	public BaseResponse<Boolean> save() {
		logger.debug("config : {}", config);
		String uploadFilePath = config.getUploadFilePath();
		logger.debug("uploadFilePath : {}", uploadFilePath);
		return new BaseResponse<Boolean>(true);
		}

}