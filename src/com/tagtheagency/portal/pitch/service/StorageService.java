package com.tagtheagency.portal.pitch.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.tagtheagency.portal.model.PitchPage;

import java.awt.Dimension;
import java.awt.Image;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file, int pitch, int page);

    Stream<Path> loadAll();

    Path load(int pitchId, int page, String filename);

    Resource loadAsResource(int pitch, int page, String filename);

    void deleteAll();

	List<String> getUploadedFiles(int pitch, int page);

	void deleteUploadedFile(PitchPage actualPage, String filename);

	Dimension getImageDimensions(int pitch, int page, String filename);

}