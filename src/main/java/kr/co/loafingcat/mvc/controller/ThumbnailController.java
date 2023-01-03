package kr.co.loafingcat.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.loafingcat.configuration.exception.BaseException;
import kr.co.loafingcat.configuration.http.BaseResponseCode;
import kr.co.loafingcat.mvc.domain.ThunbnailType;
import kr.co.loafingcat.mvc.domain.UploadFile;
import kr.co.loafingcat.mvc.service.UploadFileService;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/thumbnail")
public class ThumbnailController {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@GetMapping("/make/{uploadFileSeq}/{thumbnailType}")
	public void make(@PathVariable int uploadFileSeq,
			@PathVariable ThunbnailType thumbnailType, HttpServletResponse response) {
		UploadFile uploadFile = uploadFileService.get(uploadFileSeq);
		if (uploadFile == null) {
			throw new BaseException(BaseResponseCode.UPLOAD_FILE_IS_NULL);
		}
		String pathname = uploadFile.getPathname();
		File file = new File(pathname);
		if (!file.isFile()) {
			throw new BaseException(BaseResponseCode.UPLOAD_FILE_IS_NULL);
		}
			
		try {
			String thumbnailPathname = uploadFile.getPathname().replace(".", "_" + thumbnailType.width() + "_" + thumbnailType.height() + ".");
			File thumbnailFile = new File(thumbnailPathname);
			if(!thumbnailFile.isFile()) {
				Thumbnails.of(pathname)
				.size(thumbnailType.width(), thumbnailType.height())
				.toFile(thumbnailPathname);
			}
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			FileCopyUtils.copy(new FileInputStream(thumbnailFile), response.getOutputStream());
			logger.info("ThunmbnailFilename : {}", thumbnailPathname);
		} catch (IOException e) {
			logger.error("e", e);
		}
	}
}
