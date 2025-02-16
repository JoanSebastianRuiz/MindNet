package JoanRuiz.mindnet.dto;

public class UnfollowRequestDTO {
    private String username;
    private String usernameUnfollowed;

    public UnfollowRequestDTO() {
    }

    public UnfollowRequestDTO(String username, String usernameUnfollowed) {
        this.username = username;
        this.usernameUnfollowed = usernameUnfollowed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameUnfollowed() {
        return usernameUnfollowed;
    }

    public void setUsernameUnfollowed(String usernameUnfollowed) {
        this.usernameUnfollowed = usernameUnfollowed;
    }
}
