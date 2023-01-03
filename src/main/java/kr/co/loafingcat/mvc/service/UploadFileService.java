package kr.co.loafingcat.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.loafingcat.mvc.domain.UploadFile;
import kr.co.loafingcat.mvc.parameter.UploadFileParameter;
import kr.co.loafingcat.mvc.repository.UploadFileRepository;

@Service
public class UploadFileService {
	
	@Autowired
	private UploadFileRepository repository;
	
	public void save(UploadFileParameter parameter) {
		repository.save(parameter);
	}

	public UploadFile get(int uploadFileSeq) {
		return repository.get(uploadFileSeq);
		
	}
	
}
