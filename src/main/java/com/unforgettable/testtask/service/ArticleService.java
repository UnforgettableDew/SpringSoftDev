package com.unforgettable.testtask.service;

import com.unforgettable.testtask.entity.Article;
import com.unforgettable.testtask.entity.Author;
import com.unforgettable.testtask.exception.ArticleException;
import com.unforgettable.testtask.exception.AuthorException;
import com.unforgettable.testtask.exception.SiteException;
import com.unforgettable.testtask.model.CommonResponse;
import com.unforgettable.testtask.repository.ArticleRepository;
import com.unforgettable.testtask.repository.AuthorRepository;
import com.unforgettable.testtask.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final SiteRepository siteRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository, SiteRepository siteRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.siteRepository = siteRepository;
    }

    public List<Article> findArticlesBySiteId(Long siteId) {
        return articleRepository.findArticlesBySiteId(siteId);
    }

    @Transactional
    public CommonResponse addArticlesBySiteId(Long siteId, List<Article> articles) {
        setSite(siteId, articles);
        checkIfArticleListIsEmpty(articles);
        for (Article article : articles) {
            handleAuthors(article);
            articleRepository.save(article);
        }
        return new CommonResponse("Articles have been added successfully");
    }

    @Transactional
    public CommonResponse updateArticlesBySiteId(Long siteId, List<Article> articles) {
        setSite(siteId, articles);
        checkIfArticleListIsEmpty(articles);
        for (Article article : articles) {
            checkArticleId(article);
            Article updateArticle = articleRepository.findById(article.getId()).get();

            updateArticle.updateArticle(article);
            handleAuthors(updateArticle);
            articleRepository.save(updateArticle);
        }
        return new CommonResponse("Articles have been updated successfully");
    }

    @Transactional
    public CommonResponse deleteArticlesBySiteId(Long siteId, List<Article> articles) {
        setSite(siteId, articles);
        checkIfArticleListIsEmpty(articles);
        for (Article article : articles) {
            checkArticleId(article);
            articleRepository.delete(article);
        }
        return new CommonResponse("Articles have been deleted successfully");
    }

    public List<Article> findDuplicateArticles(Article article) {
        List<Article> duplicateArticles = new ArrayList<>();

        List<Article> allArticles = articleRepository.findAll();
        for (Article articleForCheck : allArticles) {
            int similarityScore = calculateSimilarityScore(article, articleForCheck);
            if (similarityScore >= 50) {
                duplicateArticles.add(articleForCheck);
            }
        }

        return duplicateArticles;
    }

    private int calculateSimilarityScore(Article article1, Article article2) {
        int similarityScore = 0;

        if (article1.getTitleEnglish().equals(article2.getTitleEnglish())) {
            similarityScore += 20;
        }

        if (article1.getTitleGerman().equals(article2.getTitleGerman())) {
            similarityScore += 20;
        }

        if (article1.getIssn().equals(article2.getIssn())) {
            similarityScore += 10;
        }

        if (article1.getIsbn().equals(article2.getIsbn())) {
            similarityScore += 10;
        }

        for (Author author : article1.getAuthors()) {
            if (article2.getAuthors().contains(author)) {
                similarityScore += 15;
                break;
            }
        }

        if (article1.getYearOfPublication().equals(article2.getYearOfPublication())) {
            similarityScore += 5;
        }

        if (article1.getEditionNumber().equals(article2.getEditionNumber())) {
            similarityScore += 15;
        }

        return similarityScore;
    }

    private void checkIfArticleListIsEmpty(List<Article> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException("Article list is empty");
    }

    private void checkIfAuthorListIsEmpty(List<Author> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException("Author list is empty");
    }

    private void setSite(Long siteId, List<Article> articles) {
        for (Article article : articles) {
            article.setSite(siteRepository.findById(siteId)
                    .orElseThrow(() -> new SiteException("Site with id = " + siteId + " not found", HttpStatus.NOT_FOUND)));
        }
    }

    private void checkArticleId(Article article) {
        if (article.getId() == null)
            throw new ArticleException("Check whether the article id is specified in each object", HttpStatus.NOT_FOUND);
        if (!articleRepository.existsById(article.getId()))
            throw new ArticleException("Article with id = " + article.getId() + " not found", HttpStatus.NOT_FOUND);
    }

    private void handleAuthors(Article article) {
        List<Author> authors = new ArrayList<>();
        checkIfAuthorListIsEmpty(article.getAuthors());
        for (Author author : article.getAuthors()) {
            if (author.getId() == null) {
                authorRepository.save(author);
                authors.add(author);
            } else {
                Author existingAuthor = authorRepository
                        .findById(author.getId())
                        .orElseThrow(() -> new AuthorException("Author with id = " + author.getId() + " not found", HttpStatus.NOT_FOUND));
                authors.add(existingAuthor);
            }
        }
        article.setAuthors(authors);
    }
}
