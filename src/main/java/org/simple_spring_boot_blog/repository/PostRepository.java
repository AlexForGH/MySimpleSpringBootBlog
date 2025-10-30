package org.simple_spring_boot_blog.repository;

import org.simple_spring_boot_blog.model.Post;

import java.util.List;

public interface PostRepository {
    long getCountOfAllPosts();
    long getCountOfPostsByTag(String tag);
    List<Post> getAllPostsWithPaginationParams(long pageNumber, long pageSize);
    List<Post> getPostsByTagWithPaginationParams(String tag, long pageNumber, long pageSize);
    Post getPostById(Long id);
    void addPost(Post post);
    void editPost(Post post);
    void deletePostById(Long id);
}
