package org.simple_spring_boot_blog.controller;

import org.junit.jupiter.api.Test;
import org.simple_spring_boot_blog.model.Post;
import org.simple_spring_boot_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
public class PostControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    void getAllPosts_ShouldReturnPostsViewWithAttributes() throws Exception {

        Post testPost = new Post(
                1L,
                "proger",
                "/opt/proger.jpg",
                22,
                "some proger text",
                List.of("tag1", "tag2", "tag3")
        );
        testPost.setComments(List.of());

        when(postService.getPostById(1L)).thenReturn(Optional.of(testPost));

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
                .andExpect(model().attribute("post", testPost));
    }

    //etc.
}
