package JoanRuiz.mindnet.dto;

import JoanRuiz.mindnet.entities.Comment;

import java.sql.Timestamp;
import java.util.List;

public class CommentResponseDTO {
    private Integer id;
    private String fullname;
    private String username;
    private String body;
    private Timestamp datetime;
    private String imageUrlUser;
    private List<MentionedUser> mentionedUsers;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.fullname = comment.getUser().getFullname();
        this.username = comment.getUser().getUsername();
        this.body = comment.getBody();
        this.datetime = comment.getDatetime();
        this.imageUrlUser = comment.getUser().getImageUrl();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getImageUrlUser() {
        return imageUrlUser;
    }

    public void setImageUrlUser(String imageUrlUser) {
        this.imageUrlUser = imageUrlUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MentionedUser> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(List<MentionedUser> mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

}
