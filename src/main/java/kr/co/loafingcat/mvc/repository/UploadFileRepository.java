package kr.co.loafingcat.mvc.repository;

import org.springframework.stereotype.Repository;

import kr.co.loafingcat.mvc.domain.UploadFile;
import kr.co.loafingcat.mvc.parameter.UploadFileParameter;

@Repository
public interface UploadFileRepository {

	void save(UploadFileParameter parameter);

	UploadFile get(int uploadFileSeq);
}
