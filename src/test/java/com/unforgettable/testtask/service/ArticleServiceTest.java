package com.unforgettable.testtask.service;

import com.unforgettable.testtask.entity.Article;
import com.unforgettable.testtask.entity.Author;
import com.unforgettable.testtask.entity.Site;
import com.unforgettable.testtask.exception.ArticleException;
import com.unforgettable.testtask.exception.AuthorException;
import com.unforgettable.testtask.exception.SiteException;
import com.unforgettable.testtask.model.CommonResponse;
import com.unforgettable.testtask.repository.ArticleRepository;
import com.unforgettable.testtask.repository.AuthorRepository;
import com.unforgettable.testtask.repository.SiteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private SiteRepository siteRepository;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void findArticlesBySiteId() {
        when(articleRepository.findArticlesBySiteId(1L)).thenReturn(List.of(
                new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, new Site(), new ArrayList<>())
        ));
        Long id = 1L;

        List<Article> articles = articleService.findArticlesBySiteId(id);

        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);

        verify(articleRepository).findArticlesBySiteId(captorId.capture());

        assertEquals(id, captorId.getValue());
        assertEquals(1, articles.size());
    }

    @Test
    void addArticlesBySiteIdThrowArticleIdNotFoundException() {
        Long id = 1L;
        List<Article> articles = List.of(
                new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, new Site(), new ArrayList<>())
        );

        assertThatThrownBy(() -> articleService.addArticlesBySiteId(id, articles)).isInstanceOf(SiteException.class).hasMessage("Site with id = " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Test
    void addArticlesBySiteIdThrowArticleEmptyListException() {
        Long id = 1L;
        List<Article> articles = new ArrayList<>();

        assertThatThrownBy(() -> articleService.addArticlesBySiteId(id, articles)).isInstanceOf(IllegalArgumentException.class).hasMessage("Article list is empty", HttpStatus.NOT_FOUND);
    }


    @Test
    void addArticlesBySiteIdThrowAuthorEmptyListException() {
        Long siteId = 1L;
        Site site = new Site(siteId, "asafadf", new ArrayList<>());
        List<Article> articles = List.of(
                new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, new Site(), new ArrayList<>())
        );

        when(siteRepository.findById(1L)).thenReturn(Optional.of(site));

        assertThatThrownBy(() -> articleService.addArticlesBySiteId(siteId, articles)).isInstanceOf(IllegalArgumentException.class).hasMessage("Author list is empty", HttpStatus.NOT_FOUND);

        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);

        verify(siteRepository).findById(captorId.capture());

        assertEquals(siteId, captorId.getValue());
    }

    @Test

    void addArticlesBySiteIdDefaultCase() {
        Long siteId = 1L;
        Author author = new Author();
        List<Article> articles = List.of(
                new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, new Site(),
                        new ArrayList<>(List.of(author)))
        );

        when(siteRepository.findById(siteId)).thenAnswer(invocationOnMock -> Optional.of(new Site(invocationOnMock.getArgument(0), "qekrjbajfaj", new ArrayList<>())));

        CommonResponse response = articleService.addArticlesBySiteId(siteId, articles);

        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);

        verify(siteRepository).findById(captorId.capture());
        assertEquals("Articles have been added successfully", response.getMessage());
        assertEquals(siteId, captorId.getValue());
    }

    @Test
    void addArticlesBySiteIdIfAuthorIdIsNotPresent() {
        Site site = new Site(1L, "asafadf", new ArrayList<>());
        Long id = 1L;
        Author author = new Author();
        List<Article> articles = List.of(
                new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, site,
                        new ArrayList<>(List.of(author)))
        );

        when(siteRepository.findById(1L)).thenReturn(Optional.of(site));

        CommonResponse response = articleService.addArticlesBySiteId(id, articles);

        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Author> captorAuthor = ArgumentCaptor.forClass(Author.class);

        verify(siteRepository).findById(captorId.capture());
        verify(authorRepository).save(captorAuthor.capture());

        assertEquals("Articles have been added successfully", response.getMessage());
        assertEquals(author, captorAuthor.getValue());
        assertEquals(id, captorId.getValue());
    }

    @Test
    void addArticlesBySiteIdIfAuthorIsPresent() {
        Long siteId = 1L;
        Long authorId = 1L;
        Site site = new Site(siteId, "https://www.example1.com", new ArrayList<>());
        Author author = new Author(authorId, "William Code", new ArrayList<>());
        List<Article> articles = List.of(
                new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, site,
                        new ArrayList<>(List.of(author)))
        );

        when(siteRepository.findById(siteId)).thenReturn(Optional.of(site));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));


        CommonResponse response = articleService.addArticlesBySiteId(siteId, articles);

        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> captorAuthorId = ArgumentCaptor.forClass(Long.class);

        verify(siteRepository).findById(captorId.capture());
        verify(authorRepository).findById(captorAuthorId.capture());

        assertEquals("Articles have been added successfully", response.getMessage());
        assertEquals(author.getId(), captorAuthorId.getValue());
        assertEquals(siteId, captorId.getValue());
    }

    @Test
    void addArticlesBySiteIdIfAuthorIsPresentThrowException() {
        Long siteId = 1L;
        Long authorId = 1L;
        Site site = new Site(siteId, "https://www.example1.com", new ArrayList<>());
        Author author = new Author(authorId, "William Code", new ArrayList<>());
        List<Article> articles = List.of(
                new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, site,
                        new ArrayList<>(List.of(author)))
        );

        when(siteRepository.findById(siteId)).thenReturn(Optional.of(site));

        assertThatThrownBy(() -> articleService.addArticlesBySiteId(siteId, articles)).isInstanceOf(AuthorException.class).hasMessage("Author with id = " + author.getId() + " not found", HttpStatus.NOT_FOUND);

        ArgumentCaptor<Long> captorSiteId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> captorAuthorId = ArgumentCaptor.forClass(Long.class);

        verify(siteRepository).findById(captorSiteId.capture());
        verify(authorRepository).findById(captorAuthorId.capture());

        assertEquals(author.getId(), captorAuthorId.getValue());
        assertEquals(siteId, captorSiteId.getValue());
    }

    @Test
    void updateArticlesBySiteIdArticleIdIsNotPresent() {
        Long siteId = 1L;
        List<Article> articles = List.of(
                new Article(null, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, new Site(), new ArrayList<>())
        );

        when(siteRepository.findById(siteId)).thenReturn(Optional.of(new Site(1L, "qekrjbajfaj", new ArrayList<>())));

        assertThatThrownBy(() -> articleService.updateArticlesBySiteId(siteId, articles)).isInstanceOf(ArticleException.class).hasMessage("Check whether the article id is specified in each object", HttpStatus.NOT_FOUND);
    }

    @Test
    void updateArticlesBySiteIdArticleIdIsNotFount() {
        Long siteId = 1L;
        List<Article> articles = List.of(
                new Article(1L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, new Site(), new ArrayList<>())
        );
        when(siteRepository.findById(siteId)).thenReturn(Optional.of(new Site(1L, "qekrjbajfaj", new ArrayList<>())));

        assertThatThrownBy(() -> articleService.updateArticlesBySiteId(siteId, articles)).isInstanceOf(ArticleException.class).hasMessage("Article with id = " + articles.get(0).getId() + " not found", HttpStatus.NOT_FOUND);
    }

    @Test
    void updateArticlesBySiteIdDefaultCase() {
        Long siteId = 1L;
        Site site = new Site(siteId, "https://www.example1.com", new ArrayList<>());
        Article article = new Article(1L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, site, List.of(new Author()));
        List<Article> articles = List.of(article);

        when(siteRepository.findById(siteId)).thenReturn(Optional.of(site));
        when(articleRepository.existsById(article.getId())).thenReturn(true);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        CommonResponse response = articleService.updateArticlesBySiteId(siteId, articles);

        ArgumentCaptor<Long> captorSiteId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> captorArticleId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Article> captorArticle = ArgumentCaptor.forClass(Article.class);

        verify(siteRepository).findById(captorSiteId.capture());
        verify(articleRepository).existsById(captorArticleId.capture());
        verify(articleRepository).findById(captorArticleId.capture());
        verify(articleRepository).save(captorArticle.capture());

        assertEquals("Articles have been updated successfully", response.getMessage());
        assertEquals(siteId, captorSiteId.getValue());
        assertEquals(article.getId(), captorArticle.getValue().getId());
        assertEquals(article, captorArticle.getValue());
    }

    @Test
    void deleteArticlesBySiteIdWhenArticleIdDoesNotExist() {
        Long siteId = 1L;
        Site site = new Site(siteId, "https://www.example1.com", new ArrayList<>());
        Article article = new Article(1L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, site, new ArrayList<>());
        List<Article> articles = List.of(article);

        when(siteRepository.findById(siteId)).thenReturn(Optional.of(site));

        assertThatThrownBy(() -> articleService.deleteArticlesBySiteId(siteId, articles)).isInstanceOf(ArticleException.class).hasMessage("Article with id = " + articles.get(0).getId() + " not found", HttpStatus.NOT_FOUND);

        ArgumentCaptor<Long> captorSiteId = ArgumentCaptor.forClass(Long.class);

        verify(siteRepository).findById(captorSiteId.capture());

        assertEquals(siteId, captorSiteId.getValue());
    }

    @Test
    void deleteArticlesBySiteIdDefaultCase() {
        Long siteId = 1L;
        Site site = new Site(siteId, "https://www.example1.com", new ArrayList<>());
        Article article = new Article(1L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, site, new ArrayList<>());
        List<Article> articles = List.of(article);

        when(siteRepository.findById(siteId)).thenReturn(Optional.of(site));
        when(articleRepository.existsById(article.getId())).thenReturn(true);

        CommonResponse response = articleService.deleteArticlesBySiteId(siteId, articles);

        ArgumentCaptor<Long> captorSiteId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> captorArticleId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Article> captorArticle = ArgumentCaptor.forClass(Article.class);

        verify(siteRepository).findById(captorSiteId.capture());
        verify(articleRepository).existsById(captorArticleId.capture());
        verify(articleRepository).delete(captorArticle.capture());

        assertEquals("Articles have been deleted successfully", response.getMessage());
        assertEquals(siteId, captorSiteId.getValue());
        assertEquals(articles.get(0).getId(), captorArticleId.getValue());
        assertEquals(articles.get(0), captorArticle.getValue());
    }

    @Test
    void findDuplicateArticlesDefaultCase() {
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", new Date(), 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(article);

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(articles, result);
    }

    @Test
    void findDuplicateArticlesEnglishTitlePresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "Mockingbird", "German", "17781347", "978-3-9876-5432-3",
                new Date(), 3, null, new ArrayList<>(List.of(new Author(3L, "Robert Bull", null)))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(0, result.size());
    }

    @Test
    void findDuplicateArticlesGermanTitlePresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "English Title", "Nachtigall stört", "17781347", "978-3-9876-5432-3",
                new Date(), 3, null, new ArrayList<>(List.of(new Author(3L, "Robert Bull", null)))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(0, result.size());
    }

    @Test
    void findDuplicateArticlesEnglishAndGermanTitlePresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "Mockingbird", "Nachtigall stört", "17781347", "978-3-9876-5432-3",
                new Date(), 3, null, new ArrayList<>(List.of(new Author(3L, "Robert Bull", null)))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(0, result.size());
    }

    @Test
    void findDuplicateArticlesEnglishAndGermanTitleAndIssnPresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-3",
                new Date(), 3, null, new ArrayList<>(List.of(new Author(3L, "Robert Bull", null)))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findDuplicateArticlesEnglishTitleAndIssnAndIsbnAndAuthorPresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "Mockingbird", "German", "9876543", "978-3-9876-5432-1",
                new Date(), 3, null, new ArrayList<>(List.of(author))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findDuplicateArticlesIssnAndIsbnAndAuthorAndEditionNumberPresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "English", "German", "9876543", "978-3-9876-5432-1",
                new Date(), 2, null, new ArrayList<>(List.of(author))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findDuplicateArticlesEnglishTitleAndIssnAndIsbnAndYearOfPublicationPresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "Mockingbird", "German", "9876543", "978-3-9876-5432-1",
                date, 1, null, new ArrayList<>(List.of(new Author()))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(0, result.size());
    }

    @Test
    void findDuplicateArticlesIssnAndIsbnAndAuthorAndYearOfPublicationPresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "English", "German", "9876543", "978-3-9876-5432-1",
                date, 1, null, new ArrayList<>(List.of(author))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(0, result.size());
    }

    @Test
    void findDuplicateArticlesEnglishTitleAndGermanTitleAndYearOfPublicationPresent() {
        Date date = new Date();
        Author author = new Author(1L, "William Code", new ArrayList<>());
        Article article = new Article(2L, "Mockingbird", "Nachtigall stört", "9876543", "978-3-9876-5432-1", date, 2, null,
                new ArrayList<>(List.of(author)));

        List<Article> articles = List.of(new Article(3L, "Mockingbird", "Nachtigall stört", "9876542", "978-3-9876-5432-2",
                date, 1, null, new ArrayList<>(List.of(new Author()))));

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.findDuplicateArticles(article);

        verify(articleRepository).findAll();

        assertEquals(0, result.size());
    }

}