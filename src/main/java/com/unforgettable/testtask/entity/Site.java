package com.unforgettable.testtask.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "site")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weblink")
    private String weblink;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "site")
    @JsonIgnore
    private List<Article> articles;
}
