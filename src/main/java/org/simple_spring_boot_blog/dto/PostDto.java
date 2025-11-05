package org.simple_spring_boot_blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDto {

    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 5, max = 100, message = "Заголовок должен быть от 5 до 100 символов")
    private String title;
    @NotBlank(message = "Текст поста не может быть пустым")
    @Size(min = 10, max = 5000, message = "Текст поста должен быть от 10 до 5000 символов")
    private String text;
    private String tags;

    public PostDto() {
    }

    public PostDto(String title, String text, String tags) {
        this.title = title;
        this.text = text;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
