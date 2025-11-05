package org.simple_spring_boot_blog.repository;

import org.simple_spring_boot_blog.model.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getCommentsByPostId(Long postId);
    Comment getCommentById(Long id);
    void addComment(Comment comment);
    void editComment(Comment comment);
    void deleteCommentById(Long id);
    List<Comment> getAllComments();
}
