package JoanRuiz.mindnet.dto;

import JoanRuiz.mindnet.entities.Post;

import java.sql.Timestamp;
import java.util.List;

public class PostResponseDTO {
    private Integer id;
    private Integer idUser;
    private String username;
    private String fullname;
    private String  body;
    private String  imageUrl;
    private Timestamp datetime;
    private List<CommentResponseDTO> comments;
    private Integer likesCount;
    private String imageUrlUser;
    private List<MentionedUser> mentionedUsers;
    private Integer trending;

    public PostResponseDTO(Post post, Integer likesCount) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.fullname = post.getUser().getFullname();
        this.body = post.getBody();
        this.imageUrl = post.getImageUrl();
        this.datetime = post.getDatetime();
        this.likesCount = likesCount;
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

    public List<CommentResponseDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDTO> comments) {
        this.comments = comments;
    }

    public List<MentionedUser> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(List<MentionedUser> mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

    public Integer getTrending() {
        return trending;
    }

    public void setTrending(Integer trending) {
        this.trending = trending;
    }
}
