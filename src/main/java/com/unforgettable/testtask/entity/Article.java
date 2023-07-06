package com.unforgettable.testtask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_english")
    @NotEmpty(message = "Title English cannot be empty")
    @Size(max = 20, message = "Title English cannot exceed 20 characters")
    private String titleEnglish;

    @Column(name = "title_german")
    private String titleGerman;

    @Column(name = "issn")
    private String issn;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "year_of_publication")
    private Date yearOfPublication;

    @Column(name = "edition_number")
    private Integer editionNumber;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    @ManyToMany
    @JoinTable(name = "author_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    public void updateArticle(Article article){
        this.id = article.getId();
        this.titleEnglish = article.getTitleEnglish();
        this.titleGerman = article.getTitleGerman();
        this.issn = article.getIssn();
        this.isbn = article.getIsbn();
        this.yearOfPublication = article.getYearOfPublication();
        this.site = article.getSite();
        this.authors = article.getAuthors();
    }


}
