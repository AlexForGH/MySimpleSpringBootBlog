package org.simple_spring_boot_blog.controller;

import org.simple_spring_boot_blog.dto.PagingInfoDto;
import org.simple_spring_boot_blog.dto.PostDto;
import org.simple_spring_boot_blog.model.Comment;
import org.simple_spring_boot_blog.model.Post;
import org.simple_spring_boot_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PostController {

    private final PostService postService;

    private final String postsAction = "/posts";
    private final String addPostAction = postsAction + "/add";
    private final String editPostAction = postsAction + "/edit";
    private final String deletePostAction = postsAction + "/delete";
    private final String likesPostAction = postsAction + "/likes";
    private final String addCommentAction = postsAction + "/comment/add";
    private final String editCommentAction = postsAction + "/comment/edit";
    private final String deleteCommentAction = postsAction + "/comment/delete";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String redirectToPosts() {
        return "redirect:" + postsAction;
    }


    @GetMapping(postsAction)
    public String getAllPostsOrSearchPostsByTagWithPagination(
            @RequestParam(name = "pageNumber", defaultValue = "1") long pageNumber,
            @RequestParam(name = "page_size", defaultValue = "5") long pageSize,
            @RequestParam(name = "search_tag", required = false) String searchTag,
            Model model
    ) {
        Page<Post> postsPage = postService.getAllPostsOrByTagWithPagination(searchTag, pageNumber, pageSize);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsAction", postsAction);
        model.addAttribute("addPostAction", addPostAction);
        model.addAttribute("postsPage", new PagingInfoDto(
                postsPage.getNumber() + 1,
                postsPage.getTotalPages(),
                postsPage.getSize(),
                postsPage.hasPrevious(),
                postsPage.hasNext()
        ));
        model.addAttribute("search_tag", searchTag);
        model.addAttribute("allPostsCount", postService.getCountOfAllPosts());

        return "posts";
    }

    @GetMapping(postsAction + "/{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            model.addAttribute("postsAction", postsAction);
            model.addAttribute("editPostAction", editPostAction);
            model.addAttribute("deletePostAction", deletePostAction);
            model.addAttribute("likesPostAction", likesPostAction);
            model.addAttribute("addCommentAction", addCommentAction);
            model.addAttribute("editCommentAction", editCommentAction);
            model.addAttribute("deleteCommentAction", deleteCommentAction);
            return "post";
        }
        return errorNotFound(model, id);
    }

    @GetMapping(addPostAction)
    public String addPost(Model model) {
        model.addAttribute("post", null);
        model.addAttribute("postsAction", postsAction);
        model.addAttribute("addPostAction", addPostAction);
        return "add-post";
    }

    @PostMapping(addPostAction)
    public String addPost(
            @ModelAttribute PostDto postDto,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        postService.addPost(postDto, imageFile);
        return "redirect:" + postsAction;
    }

    @GetMapping(editPostAction + "/{id}")
    public String editPost(@PathVariable("id") Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            model.addAttribute("postsAction", postsAction);
            model.addAttribute("editPostAction", editPostAction);
            return "edit-post";
        }
        return errorNotFound(model, id);
    }

    @PostMapping(editPostAction + "/{id}")
    public String editPost(
            Model model,
            @PathVariable("id") Long id,
            @ModelAttribute PostDto postDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            Post postToEdit = post.get();
            if (!postToEdit.getTitle().equals(postDto.getTitle())) postToEdit.setTitle(postDto.getTitle());
            if (!postToEdit.getText().equals(postDto.getText())) postToEdit.setText(postDto.getText());
            List<String> tags = Arrays
                    .stream(postDto.getTags().split("[,\\s]+"))
                    .filter(s -> !s.isEmpty())
                    .toList();
            if (!postToEdit.getTags().equals(tags)) postToEdit.setTags(tags);
            postService.editPost(postToEdit, imageFile);
            return "redirect:" + postsAction;
        }
        return errorNotFound(model, id);
    }

    @PostMapping(
            value = deletePostAction + "/{id}",
            params = "_method=delete"
    )
    public String deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePostById(id);
        return "redirect:" + postsAction;
    }

    @PostMapping(likesPostAction + "/{id}")
    public String likePost(
            @PathVariable(name = "id") Long id,
            @RequestParam(value = "like") Boolean like,
            Model model) throws IOException {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            Post postToLike = post.get();
            int currentLikes = postToLike.getLikesCount();
            if (like) postToLike.setLikesCount(currentLikes + 1);
            else postToLike.setLikesCount(currentLikes - 1);
            postService.editPost(postToLike, null);
            return "redirect:" + postsAction + "/" + id;
        }
        return errorNotFound(model, id);
    }

    @PostMapping(addCommentAction + "/{post_id}")
    public String addComment(
            @PathVariable(name = "post_id") Long post_id,
            @RequestParam(name = "content") String content
    ) {
        Comment comment = new Comment();
        comment.setPost_id(post_id);
        comment.setContent(content);
        postService.addComment(comment);
        return "redirect:" + postsAction + "/" + post_id;
    }

    @PostMapping(editCommentAction + "/{post_id}" + "/{comment_id}")
    public String editComment(
            @PathVariable(name = "comment_id") Long comment_id,
            @PathVariable(name = "post_id") Long post_id,
            @RequestParam(name = "content") String content
    ) {
        postService.editComment(new Comment(comment_id, post_id, content));
        return "redirect:" + postsAction + "/" + post_id;
    }

    @PostMapping(
            value = deleteCommentAction + "/{post_id}" + "/{comment_id}",
            params = "_method=delete"
    )
    public String deleteComment(
            @PathVariable(name = "post_id") Long post_id,
            @PathVariable(name = "comment_id") Long comment_id
    ) {
        postService.deleteCommentById(comment_id);
        return "redirect:" + postsAction + "/" + post_id;
    }

    private String errorNotFound(Model model, Long id) {
        model.addAttribute("errorTitle", "Пост не найден");
        model.addAttribute("errorMessage", "Запрошенный пост с ID " + id + " не существует или был удален");
        model.addAttribute("backLink", "/posts");
        return "error/not-found";
    }
}
