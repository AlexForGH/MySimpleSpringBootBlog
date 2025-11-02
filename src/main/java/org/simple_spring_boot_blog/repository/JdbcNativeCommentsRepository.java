package org.simple_spring_boot_blog.repository;

import org.simple_spring_boot_blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcNativeCommentsRepository implements CommentsRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String dbName = "comments";

    @Autowired
    public JdbcNativeCommentsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return jdbcTemplate.query(
                "select id, post_id, content from " + dbName + " where post_id = ?",
                (rs, rowNum) -> new Comment(
                        rs.getLong("id"),
                        rs.getLong("post_id"),
                        rs.getString("content")
                ),
                postId
        );
    }

    @Override
    public Comment getCommentById(Long id) {
        return jdbcTemplate.queryForObject(
                "select id, post_id, content from " + dbName + " where id=?",
                new Object[]{id},
                (rs, rowNum) -> new Comment(
                        rs.getLong("id"),
                        rs.getLong("post_id"),
                        rs.getString("content")
                )
        );
    }

    @Override
    public void addComment(Comment comment) {
        jdbcTemplate.update(
                "insert into " + dbName + "(post_id, content) values(?, ?)",
                comment.getPost_id(),
                comment.getContent()
        );
    }

    @Override
    public void editComment(Comment comment) {
        jdbcTemplate.update(
                "update " + dbName + " set content = ?" + " where id=?",
                comment.getContent(),
                comment.getId()
        );
    }

    @Override
    public void deleteCommentById(Long id) {
        jdbcTemplate.update(
                "delete from " + dbName + " where id = ?", id
        );
    }

    @Override
    public List<Comment> getAllComments() {
        return jdbcTemplate.query(
                "select id, post_id, content from " + dbName,
                (rs, rowNum) -> new Comment(
                        rs.getLong("id"),
                        rs.getLong("post_id"),
                        rs.getString("content")
                )
        );
    }
}
