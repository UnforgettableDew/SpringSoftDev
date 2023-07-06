package com.unforgettable.testtask.repository;

import com.unforgettable.testtask.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
//    List<Article> findArticleBySiteId(Long siteId);

    List<Article> findArticlesBySiteId(Long siteId);

    boolean existsByIssnAndIsbn(String isnn, String isbn);
}
