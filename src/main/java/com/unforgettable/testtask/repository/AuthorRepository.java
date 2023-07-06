package com.unforgettable.testtask.repository;

import com.unforgettable.testtask.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFullName(String fullName);
    Optional<Author> findByIdAndFullName(Long id, String fullName);
}
