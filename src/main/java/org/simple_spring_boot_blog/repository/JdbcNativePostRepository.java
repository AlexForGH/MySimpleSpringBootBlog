package org.simple_spring_boot_blog.repository;

import org.simple_spring_boot_blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String dbName = "posts";

    @Autowired
    public JdbcNativePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public long getCountOfAllPosts() {
        return jdbcTemplate.queryForObject("select count(*) from posts", Long.class);
    }

    @Override
    public long getCountOfPostsByTag(String tag) {
        return jdbcTemplate.queryForObject(
                "select count(*) from posts where lower(tags) like ?",
                Long.class,
                "%" + tag + "%"
        );
    }

    @Override
    public List<Post> getAllPostsWithPaginationParams(long pageNumber, long pageSize) {
        return jdbcTemplate.query(
                "select id, title, imagePath, likesCount, text, tags from " + dbName + " limit ? offset ?",
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("imagePath"),
                        rs.getInt("likesCount"),
                        rs.getString("text"),
                        tagsFromDatabaseValue(rs.getString("tags"))
                ),
                pageSize, (pageNumber - 1) * pageSize
        );
    }

    @Override
    public List<Post> getPostsByTagWithPaginationParams(String tag, long pageNumber, long pageSize) {
        return jdbcTemplate.query(
                "select id, title, imagePath, likesCount, text, tags from " + dbName + " where lower(tags) like ? limit ? offset ?",
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("imagePath"),
                        rs.getInt("likesCount"),
                        rs.getString("text"),
                        tagsFromDatabaseValue(rs.getString("tags"))
                ),
                "%" + tag + "%", pageSize, (pageNumber - 1) * pageSize
        );
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        try {
            Post post = jdbcTemplate.queryForObject(
                    "select * from " + dbName + " where id=?",
                    new Object[]{id},
                    (rs, rowNum) -> new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("imagePath"),
                            rs.getInt("likesCount"),
                            rs.getString("text"),
                            tagsFromDatabaseValue(rs.getString("tags"))
                    )
            );
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void addPost(Post post) {
        jdbcTemplate.update(
                "insert into " + dbName + "(title, imagePath, likesCount, text, tags) values(?, ?, ?, ?, ?)",
                post.getTitle(),
                post.getImagePath(),
                post.getLikesCount(),
                post.getText(),
                tagsToDatabaseValue(post.getTags())
        );
    }

    @Override
    public void editPost(Post post) {
        jdbcTemplate.update(
                "update " + dbName + " set title = ?, imagePath = ?, likesCount = ?, text = ?, tags = ? where id = ?",
                post.getTitle(),
                post.getImagePath(),
                post.getLikesCount(),
                post.getText(),
                tagsToDatabaseValue(post.getTags()),
                post.getId()
        );
    }

    @Override
    public void deletePostById(Long id) {
        jdbcTemplate.update(
                "delete from " + dbName + " where id = ?", id
        );
    }

    private String tagsToDatabaseValue(List<String> tags) {
        return (tags == null || tags.isEmpty()) ? "" : String.join(",", tags);
    }

    private List<String> tagsFromDatabaseValue(String dbData) {
        return (dbData == null || dbData.isEmpty()) ? List.of() : Arrays.asList(dbData.split(","));
    }
}
