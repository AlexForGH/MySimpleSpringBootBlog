package org.simple_spring_boot_blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simple_spring_boot_blog.config.DataSourceTestConfig;
import org.simple_spring_boot_blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({DataSourceTestConfig.class, JdbcNativePostRepository.class})
public class JdbcNativePostRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DataSourceTestConfig.DataTool dataTool;

    @BeforeEach
    public void setUp() {
        dataTool.recreateDB();
    }

    @Test
    void getAllPostsWithPaginationParams_WhenNoPosts_ShouldReturnEmptyList() {
        jdbcTemplate.execute("DELETE FROM posts");
        List<Post> result = postRepository.getAllPostsWithPaginationParams(1, 5);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllPostsWithPaginationParams_WhenPostsExist_ShouldReturnAllPosts() {
        List<Post> result = postRepository.getAllPostsWithPaginationParams(1, 5);
        assertEquals(3, result.size());
        assertEquals("proger", result.get(0).getTitle());
        assertEquals("manager", result.get(1).getTitle());
        assertEquals("devops", result.get(2).getTitle());
    }

    @Test
    void getPostsByTagWithPaginationParams_WhenNoPosts_ShouldReturnEmptyList() {
        jdbcTemplate.execute("DELETE FROM posts");
        List<Post> result = postRepository.getPostsByTagWithPaginationParams("tag33", 1, 5);
        assertTrue(result.isEmpty());
    }

    @Test
    void getPostsByTagWithPaginationParams_WhenPostsExist_ShouldReturnAllPosts() {
        List<Post> result = postRepository.getPostsByTagWithPaginationParams("tag1", 1, 5);
        assertEquals(1, result.size());
        assertEquals("proger", result.get(0).getTitle());
    }

    @Test
    void getPostById_WhenPostNotExists_EqualsAnEmptyPost() {
        assertEquals(
                Optional.empty(),
                postRepository.getPostById(999L)
        );
    }

    @Test
    void getPostById_WhenPostExists_ShouldReturnPost() {
        Post result = postRepository.getPostById(2L).get();
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("manager", result.getTitle());
        assertEquals("/opt/manager.jpg", result.getImagePath());
        assertEquals(3, result.getLikesCount());
        assertEquals("some manager text", result.getText());
        assertEquals(3, result.getTags().size());
        assertTrue(result.getTags().contains("tag4"));
        assertTrue(result.getTags().contains("tag5"));
        assertTrue(result.getTags().contains("tag6"));
    }

    @Test
    void addPost_ShouldInsertNewPost() {
        postRepository.addPost(
                new Post(
                        null,
                        "New Title",
                        "/new.jpg",
                        0,
                        "New Text",
                        List.of("newTag")
                )
        );
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM posts",
                Integer.class
        );
        assertEquals(4, count);
    }

    @Test
    void editPost_ShouldUpdateExistingPost() {
        Post progerUpdatedPost = new Post(
                1L,
                "new proger",
                "/opt/proger.jpg",
                22,
                "some new proger text",
                List.of("new tag1", "new tag2", "tag3")
        );
        postRepository.editPost(progerUpdatedPost);
        Post result = jdbcTemplate.queryForObject(
                "SELECT * FROM posts WHERE id = ?",
                new Object[]{progerUpdatedPost.getId()},
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("imagePath"),
                        rs.getInt("likesCount"),
                        rs.getString("text"),
                        Arrays.asList(rs.getString("tags").split(","))
                )
        );
        assertEquals("new proger", result.getTitle());
        assertEquals("some new proger text", result.getText());
        assertTrue(result.getTags().contains("new tag1"));
        assertTrue(result.getTags().contains("new tag2"));
    }

    @Test
    void deletePostById_ShouldRemovePost() {
        postRepository.deletePostById(1L);
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM posts", Integer.class);
        assertEquals(2, count);
    }

    @Test
    void deletePostById_WhenPostNotExists_ShouldNotThrow() {
        assertDoesNotThrow(() -> postRepository.deletePostById(999L));
    }
}
