package JoanRuiz.mindnet.dto;

public class FollowRequestDTO {
    private String username;
    private String usernameFollowed;

    public FollowRequestDTO() {
    }

    public FollowRequestDTO(String username, String usernameFollowed) {
        this.username = username;
        this.usernameFollowed = usernameFollowed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameFollowed() {
        return usernameFollowed;
    }

    public void setUsernameFollowed(String usernameFollowed) {
        this.usernameFollowed = usernameFollowed;
    }
}
