package com.unleash.articleservice.Service;

import com.unleash.articleservice.DTO.FormData;
import com.unleash.articleservice.Model.Article;
import com.unleash.articleservice.Model.util.ArticleComparator;
import com.unleash.articleservice.Repository.ArticleRepo;
import com.unleash.articleservice.Service.ServiceInterface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ArticleRepo articleRepo;


    @Override
    public ResponseEntity<?>addNewArticle(String title, String content, int relatedTo, String counselorName, int counselorId, MultipartFile image){
        Article article = new Article();
        article.setContent(content);
        article.setTitle(title);
        article.setCounselorId(counselorId);
        article.setRelatedTo(relatedTo);
        article.setCounselorName(counselorName);
        article.setUploadedOn(Timestamp.valueOf(LocalDateTime.now()));
        if(image!=null){
            try{
               article.setImage(cloudinaryService.upload(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            article.setImage("https://res.cloudinary.com/datji9d5p/image/upload/v1712993697/unleash/w2exlkie3209jz0qepts.jpg");
        }
        articleRepo.save(article);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getMyArticle(int userId) {
        try {
            List<Article> articleList = articleRepo.findByCounselorId(userId);
            Collections.sort(articleList, new ArticleComparator());
            return ResponseEntity.ok().body(articleList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<?> getAllArticles() {
        try {
            List<Article> articleList = articleRepo.findAll();
            Collections.sort(articleList,new ArticleComparator());
            return ResponseEntity.ok().body(articleList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> deleteArticle(int articleId) {
        try{
            articleRepo.deleteById(articleId);
            return ResponseEntity.ok().body("Article Deleted Successfully");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<?> editArticle(int articleId,String title, String content, int relatedTo, MultipartFile image) {
        Article article= null;
        try {
            article = articleRepo.findById(articleId).orElseThrow();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        if(content!=null &&  !article.getContent().equals(content)){
            article.setContent(content);
        }
        if(title != null && !article.getTitle().equals(title)){
            article.setTitle(title);
        }
        if(relatedTo!=0 && article.getRelatedTo()!=relatedTo){
            article.setRelatedTo(relatedTo);
        }

        if(image!=null){
            try{
                article.setImage(cloudinaryService.upload(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        articleRepo.save(article);
        return ResponseEntity.ok().build();
    }


}
