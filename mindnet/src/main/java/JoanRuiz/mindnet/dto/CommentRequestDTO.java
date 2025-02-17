package JoanRuiz.mindnet.dto;

import java.sql.Timestamp;

public class CommentRequestDTO {
    private String username;
    private String body;
    private Integer idPost;
    private Timestamp datetime;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(String username, String body, Integer idPost, Timestamp datetime) {
        this.username = username;
        this.body = body;
        this.idPost = idPost;
        this.datetime = datetime;
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

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}

