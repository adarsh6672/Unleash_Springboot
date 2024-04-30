package com.unleash.articleservice.Repository;

import com.unleash.articleservice.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article , Integer> {
    List<Article>findByCounselorId(int counselorId);
}
