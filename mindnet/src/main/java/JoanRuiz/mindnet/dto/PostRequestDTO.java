package JoanRuiz.mindnet.dto;

import java.sql.Timestamp;

public class PostRequestDTO {
    private String username;
    private String body;
    private String imageUrl;
    private Timestamp datetime;

    public PostRequestDTO(String username, String body, String imageUrl, Timestamp datetime) {
        this.username = username;
        this.body = body;
        this.imageUrl = imageUrl;
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
}
