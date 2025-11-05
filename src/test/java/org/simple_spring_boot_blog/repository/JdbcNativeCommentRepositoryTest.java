package org.simple_spring_boot_blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simple_spring_boot_blog.config.DataSourceTestConfig;
import org.simple_spring_boot_blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({DataSourceTestConfig.class, JdbcNativeCommentRepository.class})
public class JdbcNativeCommentRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DataSourceTestConfig.DataTool dataTool;

    @BeforeEach
    public void setUp() {
        dataTool.recreateDB();
    }

    @Test
    void getCommentsByPostId_WhenNoComments_ShouldReturnEmptyList() {
        jdbcTemplate.execute("DELETE FROM comments");
        List<Comment> result = commentRepository.getCommentsByPostId(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCommentsByPostId_WhenCommentsExist_ShouldReturnCommentsForPost() {
        List<Comment> result = commentRepository.getCommentsByPostId(1L);
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getPost_id());
        assertEquals("some comment_1 for proger", result.get(0).getContent());
        assertEquals(2L, result.get(1).getId());
        assertEquals(1L, result.get(1).getPost_id());
        assertEquals("some comment_2 for proger", result.get(1).getContent());
        assertEquals(3L, result.get(2).getId());
        assertEquals(1L, result.get(2).getPost_id());
        assertEquals("some comment_3 for proger", result.get(2).getContent());
    }

    @Test
    void getCommentsByPostId_WhenPostNotExists_ShouldReturnEmptyList() {
        List<Comment> result = commentRepository.getCommentsByPostId(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCommentById_WhenCommentExists_ShouldReturnComment() {
        Comment result = commentRepository.getCommentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getPost_id());
        assertEquals("some comment_1 for proger", result.getContent());
    }

    @Test
    void getCommentById_WhenCommentNotExists_ShouldThrowException() {
        assertThrows(
                EmptyResultDataAccessException.class,
                () -> commentRepository.getCommentById(999L)
        );
    }

    @Test
    void addComment_ShouldInsertNewComment() {
        Comment newCommentForProger = new Comment(null, 1L, "New comment for proger");
        commentRepository.addComment(newCommentForProger);
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM comments WHERE post_id = ?",
                Integer.class,
                1L
        );
        assertEquals(4, count);
    }

    @Test
    void editComment_ShouldUpdateCommentContent() {
        Comment updatedCommentForProger = new Comment(1L, 1L, "Updated content");
        commentRepository.editComment(updatedCommentForProger);
        String content = jdbcTemplate.queryForObject(
                "SELECT content FROM comments WHERE id = ?",
                String.class,
                1L
        );
        assertEquals("Updated content", content);
    }

    @Test
    void deleteCommentById_ShouldRemoveComment() {
        commentRepository.deleteCommentById(1L);
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM comments",
                Integer.class
        );
        assertEquals(4, count);
    }

    @Test
    void deleteCommentById_WhenCommentNotExists_ShouldNotThrow() {
        assertDoesNotThrow(() -> commentRepository.deleteCommentById(999L));
    }
}
