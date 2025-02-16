package JoanRuiz.mindnet.dto;

import JoanRuiz.mindnet.entities.Post;

import java.sql.Timestamp;

public class PostResponseDTO {
    private Integer id;
    private Integer idUser;
    private String username;
    private String fullname;
    private String  body;
    private String  imageUrl;
    private Timestamp datetime;
    private Integer likesCount;
    private Integer commentsCount;
    private String imageUrlUser;

    public PostResponseDTO(Post post, Integer likesCount, Integer commentsCount) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.fullname = post.getUser().getFullname();
        this.body = post.getBody();
        this.imageUrl = post.getImageUrl();
        this.datetime = post.getDatetime();
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.imageUrlUser = post.getUser().getImageUrl();
        this.idUser = post.getUser().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageUrlUser() {
        return imageUrlUser;
    }

    public void setImageUrlUser(String imageUrlUser) {
        this.imageUrlUser = imageUrlUser;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
