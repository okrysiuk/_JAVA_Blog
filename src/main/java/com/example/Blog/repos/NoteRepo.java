package com.example.Blog.repos;

import com.example.Blog.domain.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepo extends CrudRepository<Note, Long> {
}
