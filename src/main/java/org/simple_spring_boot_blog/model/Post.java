package org.simple_spring_boot_blog.model;

import java.util.List;
import java.util.Objects;

public class Post {
    private Long id;
    private String title;
    private String imagePath;
    private int likesCount;
    private String text;
    private List<String> tags;
    private List<Comment> comments;

    public Post() {
    }

    public Post(Long id, String title, String imagePath, int likesCount, String text, List<String> tags) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.likesCount = likesCount;
        this.text = text;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", likesCount=" + likesCount +
                ", text='" + text + '\'' +
                ", tags=" + tags +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return likesCount == post.likesCount &&
                Objects.equals(id, post.id) &&
                Objects.equals(title, post.title) &&
                Objects.equals(imagePath, post.imagePath) &&
                Objects.equals(text, post.text) &&
                Objects.equals(tags, post.tags) &&
                Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, imagePath, likesCount, text, tags, comments);
    }
}
