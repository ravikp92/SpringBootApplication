package com.ravi.boot.PatientApplication.filestore;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ravi.boot.PatientApplication.model.FileModel;
import com.ravi.boot.PatientApplication.repository.FileDBRepository;

@Service
public class FileStorageService {

	@Autowired
	private FileDBRepository fileDBRepository;

	public FileModel store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getBytes());

		return fileDBRepository.save(fileModel);
	}

	public FileModel getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	public Stream<FileModel> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}
}
