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
    public ResponseEntity<?> addNewArticle(@RequestParam ("image") MultipartFile image,
                                           @RequestParam ("title") String  title,
                                           @RequestParam ("content") String content,
                                           @RequestParam ("relatedTo") int relatedTo,
                                           @RequestParam ("counselorName") String counselorName,
                                           @RequestHeader ("userId") int userId){
        System.out.println(title+""+ content + " " + relatedTo +" " + counselorName);
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

}
