package org.simple_spring_boot_blog.service;

import org.simple_spring_boot_blog.dto.PostDto;
import org.simple_spring_boot_blog.model.Comment;
import org.simple_spring_boot_blog.model.Post;
import org.simple_spring_boot_blog.repository.CommentRepository;
import org.simple_spring_boot_blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Value("${upload.path}")
    private String uploadDir;

    @Autowired
    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
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
        // posts.forEach(post -> post.setComments(getCommentsByPostId(post.getId())));
        List<Comment> allComments = commentRepository.getAllComments();
        posts.forEach(post -> post.setComments(
                        allComments.stream().filter(
                                c -> post.getId().equals(c.getPost_id())
                        ).collect(Collectors.toList()
                        )
                )
        );
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

    public Optional<Post> getPostById(Long id) {
        Optional<Post> post = postRepository.getPostById(id);
        post.ifPresent(p -> p.setComments(getCommentsByPostId(id)));
        return post;
    }

    public void addPost(PostDto postDto, MultipartFile imageFile) throws IOException {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setImagePath("/images/" + getImagePathAndCopyFile(imageFile, uploadDir));
        post.setText(postDto.getText());
        post.setTags(
                Arrays
                        .stream(postDto.getTags().split("[,\\s]+"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList())
        );
        postRepository.addPost(post);
    }

    public void editPost(Post post, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            post.setImagePath("/images/" + getImagePathAndCopyFile(imageFile, uploadDir));
        }
        postRepository.editPost(post);
    }

    public void deletePostById(Long id) {
        postRepository.deletePostById(id);
    }

    public void addComment(Comment comment) {
        commentRepository.addComment(comment);
    }

    public void editComment(Comment comment) {
        commentRepository.editComment(comment);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteCommentById(id);
    }

    private List<Comment> getCommentsByPostId(Long id) {
        List<Comment> comments = commentRepository.getCommentsByPostId(id);
        if (comments.isEmpty()) return List.of();
        return comments;
    }

    private String getImagePathAndCopyFile(MultipartFile imageFile, String uploadDir) throws IOException {
        String filename = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        try (InputStream inputStream = imageFile.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return filename;
    }
}