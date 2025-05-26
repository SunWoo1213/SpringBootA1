package com.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    private final String uploadDir = "uploads";

    public FileService() {
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        // 원본 파일명에서 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        // 고유한 파일명 생성
        String filename = UUID.randomUUID().toString() + extension;
        
        // 파일 저장 경로 생성
        Path filePath = Paths.get(uploadDir, filename);
        
        // 파일 저장
        Files.copy(file.getInputStream(), filePath);
        
        // 저장된 파일의 URL 경로 반환
        return "/uploads/" + filename;
    }
} 