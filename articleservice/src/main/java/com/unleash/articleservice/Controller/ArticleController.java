package com.unleash.articleservice.Controller;

import com.unleash.articleservice.DTO.FormData;
import com.unleash.articleservice.Service.ServiceInterface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewArticle(@RequestParam(value = "image", required = false) MultipartFile image,
                                           @RequestParam ("title") String  title,
                                           @RequestParam ("content") String content,
                                           @RequestParam ("relatedTo") int relatedTo,
                                           @RequestParam ("counselorName") String counselorName,
                                           @RequestHeader ("userId") int userId){

        return articleService.addNewArticle(title,content,relatedTo,counselorName,userId,image);

    }

    @GetMapping("/get-my-article")
    public ResponseEntity<?>getMyArticle(@RequestHeader ("userId") int userId){
        return articleService.getMyArticle(userId);
    }


    @GetMapping("/get-all-articles")
    public ResponseEntity<?> getAllArticles(){
        return articleService.getAllArticles();
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?>deleteArticle(@RequestParam ("articleId") int articleId){
        return articleService.deleteArticle(articleId);
    }

    @PutMapping("/edit-article")
    public ResponseEntity<?> editArticle(@RequestParam(value = "image", required = false) MultipartFile image,
                                         @RequestParam (value = "articleId", required = false) int articleId,
                                         @RequestParam (value = "title", required = false) String  title,
                                         @RequestParam (value = "content", required = false) String content,
                                         @RequestParam (value = "relatedTo", required = false) int relatedTo
                                         ){
        return articleService.editArticle(articleId,title,content,relatedTo,image);

    }
    @GetMapping("/public/get-all-articles")
    public ResponseEntity<?> getAllArticlesPublic(){
        return articleService.getAllArticles();
    }

}
