package com.shop.util;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ImageUrlValidator {
    private static final List<String> VALID_IMAGE_EXTENSIONS = Arrays.asList(
        ".jpg", ".jpeg", ".png", ".gif", ".webp", ".bmp", ".svg"
    );
    
    private static final List<String> VALID_IMAGE_DOMAINS = Arrays.asList(
        "googleusercontent.com",
        "google.com",
        "gstatic.com",
        "lh3.googleusercontent.com",
        "lh4.googleusercontent.com",
        "lh5.googleusercontent.com",
        "lh6.googleusercontent.com",
        "bp.blogspot.com",
        "blogger.com",
        "picsum.photos",
        "images.unsplash.com",
        "i.imgur.com",
        "raw.githubusercontent.com",
        "via.placeholder.com",
        "cloudfront.net",
        "ssl.pstatic.net",
        "dummyimage.com",
        "flickr.com",
        "staticflickr.com",
        "media.tenor.com",
        "giphy.com",
        "pinimg.com",
        "wp.com",
        "wordpress.com",
        "wikimedia.org",
        "pexels.com",
        "pixabay.com"
    );
    
    private static final String DEFAULT_IMAGE_URL = "https://picsum.photos/300/200";

    public static String validateAndGetImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return DEFAULT_IMAGE_URL;
        }

        String trimmedUrl = imageUrl.trim();
        
        try {
            URL url = new URL(trimmedUrl);
            String host = url.getHost().toLowerCase();
            
            // 신뢰할 수 있는 이미지 도메인인 경우 허용
            if (VALID_IMAGE_DOMAINS.stream().anyMatch(host::contains)) {
                return trimmedUrl;
            }
            
            // 이미지 확장자 검증
            boolean hasValidExtension = VALID_IMAGE_EXTENSIONS.stream()
                    .anyMatch(ext -> trimmedUrl.toLowerCase().endsWith(ext));
            
            // 구글 이미지 URL 패턴 확인
            boolean isGoogleImage = host.contains("google") && 
                                  (trimmedUrl.contains("/images") || 
                                   trimmedUrl.contains("&imgurl=") ||
                                   trimmedUrl.contains("?imgurl="));
            
            if (hasValidExtension || isGoogleImage) {
                return trimmedUrl;
            }
            
            // URL에 이미지 관련 파라미터가 있는지 확인
            boolean hasImageParam = trimmedUrl.toLowerCase().contains("image") || 
                                  trimmedUrl.toLowerCase().contains("img") ||
                                  trimmedUrl.toLowerCase().contains("photo") ||
                                  trimmedUrl.toLowerCase().contains("picture");
            
            if (hasImageParam) {
                return trimmedUrl;
            }
            
            return DEFAULT_IMAGE_URL;
        } catch (Exception e) {
            return DEFAULT_IMAGE_URL;
        }
    }
} 