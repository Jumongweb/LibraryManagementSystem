package com.jumong.librarymanagementsystem.data.repositories;

import com.jumong.librarymanagementsystem.data.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
