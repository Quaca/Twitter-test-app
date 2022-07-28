package com.example.twitter.service;

import com.example.twitter.exception.NoResourceException;
import com.example.twitter.model.Comment;
import com.example.twitter.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment getCommentById(Long id) throws NoResourceException {
        return commentRepository.findById(id).orElseThrow(() -> new NoResourceException(String.valueOf(id), "There is no comment with id " + id, "#CommentNotExisting"));
    }
}
