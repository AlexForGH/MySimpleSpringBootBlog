package org.simple_spring_boot_blog.service;

import org.simple_spring_boot_blog.dto.PostDto;
import org.simple_spring_boot_blog.model.Comment;
import org.simple_spring_boot_blog.model.Post;
import org.simple_spring_boot_blog.repository.CommentsRepository;
import org.simple_spring_boot_blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public PostService(PostRepository postRepository, CommentsRepository commentsRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
    }

    public Page<Post> getAllPostsOrByTagWithPagination(String searchTag, long pageNumber, long pageSize) {
        List<Post> posts;
        long totalItems;
        if (searchTag == null || searchTag.isEmpty()) {
            posts = getAllPostsWithPaginationParams(pageNumber, pageSize);
            totalItems = postRepository.getCountOfAllPosts();
        } else {
            posts = getPostsByTagWithPaginationParams(searchTag, pageNumber, pageSize);
            totalItems = postRepository.getCountOfPostsByTag(searchTag);
        }

        long totalPages = (long) Math.ceil((double) totalItems / pageSize);

        if (pageNumber < 1) pageNumber = 1;
        if (pageNumber > totalPages && totalPages > 0) pageNumber = totalPages;

        long fromIndex = (pageNumber - 1) * pageSize;

        if (fromIndex >= totalItems) {
            return new PageImpl<>(
                    List.of(),
                    PageRequest.of((int) (pageNumber - 1),
                            (int) pageSize),
                    totalItems
            );
        }

        return new PageImpl<>(
                posts,
                PageRequest.of((int) (pageNumber - 1),
                        (int) pageSize),
                totalItems
        );
    }

    private List<Post> getAllPostsWithPaginationParams(long pageNumber, long pageSize) {
        List<Post> posts = postRepository.getAllPostsWithPaginationParams(pageNumber, pageSize);
        posts.forEach(post -> post.setComments(getCommentsByPostId(post.getId())));
        return posts;
    }

    private List<Post> getPostsByTagWithPaginationParams(String tag, long pageNumber, long pageSize) {
        List<Post> posts = postRepository.getPostsByTagWithPaginationParams(tag, pageNumber, pageSize);
        posts.forEach(post -> post.setComments(getCommentsByPostId(post.getId())));
        return posts;
    }

    public long getCountOfAllPosts() {
        return postRepository.getCountOfAllPosts();
    }

    public Post getPostById(Long id) {
        Post post = postRepository.getPostById(id);
        post.setComments(getCommentsByPostId(id));
        return post;
    }

    public void addPost(PostDto postDto, String imagePath) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setImagePath(imagePath);
        post.setText(postDto.getText());
        post.setTags(
                Arrays
                        .stream(postDto.getTags().split("[,\\s]+"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList())
        );
        postRepository.addPost(post);
    }

    public void editPost(Post post) {
        postRepository.editPost(post);
    }

    public void deletePostById(Long id) {
        postRepository.deletePostById(id);
    }

    public void addComment(Comment comment) {
        commentsRepository.addComment(comment);
    }

    public void editComment(Comment comment) {
        commentsRepository.editComment(comment);
    }

    public void deleteCommentById(Long id) {
        commentsRepository.deleteCommentById(id);
    }

    private List<Comment> getCommentsByPostId(Long id) {
        List<Comment> comments = commentsRepository.getCommentsByPostId(id);
        if (comments.isEmpty()) return List.of();
        return comments;
    }
}