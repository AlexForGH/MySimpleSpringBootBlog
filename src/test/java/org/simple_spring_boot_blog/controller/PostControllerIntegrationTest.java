package org.simple_spring_boot_blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simple_spring_boot_blog.config.DataSourceTestConfig;
import org.simple_spring_boot_blog.model.Comment;
import org.simple_spring_boot_blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import(DataSourceTestConfig.class)
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSourceTestConfig.DataTool dataTool;

    private List<Post> posts;

    @BeforeEach
    public void setup() {
        Post progerPost = new Post(
                1L,
                "proger",
                "/opt/proger.jpg",
                22,
                "some proger text",
                List.of("tag1", "tag2", "tag3")
        );
        Post managerPost = new Post(
                2L,
                "manager",
                "/opt/manager.jpg",
                3,
                "some manager text",
                List.of("tag4", "tag5", "tag6")
        );
        Post devopsPost = new Post(
                3L,
                "devops",
                "/opt/devops.jpg",
                9,
                "some devops text",
                List.of("tag7", "tag8")
        );
        progerPost.setComments(List.of(
                        new Comment(1L, 1L, "some comment_1 for proger"),
                        new Comment(2L, 1L, "some comment_2 for proger"),
                        new Comment(3L, 1L, "some comment_3 for proger")
                )
        );
        managerPost.setComments(List.of());
        devopsPost.setComments(List.of(
                        new Comment(4L, 3L, "some comment_1 for devops"),
                        new Comment(5L, 3L, "some comment_2 for devops")
                )
        );

        posts = new ArrayList<>();
        posts.add(progerPost);
        posts.add(managerPost);
        posts.add(devopsPost);

        dataTool.recreateDB();
    }

    @Test
    void redirectToPosts_ShouldRedirectToPostsAction() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void getAllPosts_ShouldReturnPostsViewWithAttributes() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists(
                        "posts",
                        "postsAction",
                        "addPostAction",
                        "postsPage",
                        "allPostsCount")
                )
                .andExpect(model().attribute("posts", posts));
    }

    @Test
    void searchPostsByTag_WithEmptyTag_ShouldReturnAllPosts() throws Exception {
        mockMvc.perform(get("/posts").param("search_tag", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts"))
                .andExpect(model().attribute("posts", posts));
    }

    @Test
    void searchPostsByTag_WithTag_ShouldReturnFilteredPosts() throws Exception {
        mockMvc.perform(get("/posts").param("search_tag", "tag1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts"))
                .andExpect(model().attribute(
                                "posts",
                                posts.stream().filter(
                                        p -> p.getTags().contains("tag1")).collect(Collectors.toList()
                                )
                        )
                );
    }

    @Test
    void getPost_ShouldReturnPostViewWithAttributes() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists(
                        "post",
                        "postsAction",
                        "editPostAction",
                        "deletePostAction",
                        "likesPostAction",
                        "addCommentAction",
                        "editCommentAction",
                        "deleteCommentAction")
                )
                .andExpect(model().attribute("post", posts.getFirst()));
    }

    @Test
    void addPost_Get_ShouldReturnAddPostView() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("postsAction", "addPostAction"));
    }

    @Test
    void addPost_Post_ShouldSavePostAndRedirect() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg", "test image content".getBytes()
        );

        mockMvc.perform(multipart("/posts/add")
                        .file(imageFile)
                        .param("title", "Test Title")
                        .param("text", "Test Content")
                        .param("tags", "tag1,tag2")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void editPost_Get_ShouldReturnEditPostView() throws Exception {
        mockMvc.perform(get("/posts/edit/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("edit-post"))
                .andExpect(model().attributeExists("post", "postsAction", "editPostAction"))
                .andExpect(model().attribute("post", posts.getFirst()));
    }

    @Test
    void editPost_Post_WithImage_ShouldUpdatePostAndRedirect() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "new.jpg",
                "image/jpeg",
                "new image".getBytes()
        );

        mockMvc.perform(multipart("/posts/edit/1")
                        .file(imageFile)
                        .param("title", "Updated Title")
                        .param("text", "Updated Content")
                        .param("tags", "newTag"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void editPost_Post_WithoutImage_ShouldUpdatePostWithoutImage() throws Exception {
        mockMvc.perform(multipart("/posts/edit/1")
                        .param("title", "Updated Title")
                        .param("text", "Updated Content")
                        .param("tags", "newTag")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void deletePost_ShouldDeletePostAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/delete/1").param("_method", "delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void likePost_WithLikeTrue_ShouldIncrementLikes() throws Exception {
        mockMvc.perform(post("/posts/likes/1").param("like", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void dislikePost_WithLikeFalse_ShouldDecrementLikes() throws Exception {
        mockMvc.perform(post("/posts/likes/1").param("like", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void addComment_ShouldAddCommentAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/comment/add/1").param("content", "New Comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void editComment_ShouldEditCommentAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/comment/edit/1/1").param("content", "Updated Comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void deleteComment_ShouldDeleteCommentAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/comment/delete/1/1").param("_method", "delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }
}
