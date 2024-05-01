package com.unleash.articleservice.Service.ServiceInterface;

import com.unleash.articleservice.DTO.FormData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    ResponseEntity<?>addNewArticle(String title, String content, int relatedTo, String counselorName, int CounselorId, MultipartFile image);


    ResponseEntity<?> getMyArticle(int userId);

    ResponseEntity<?> getAllArticles();

    ResponseEntity<?> deleteArticle(int articleId);

    ResponseEntity<?> editArticle(int articleId , String title, String content, int relatedTo,  MultipartFile image);
}
