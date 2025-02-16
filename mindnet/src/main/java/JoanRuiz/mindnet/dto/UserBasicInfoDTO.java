package JoanRuiz.mindnet.dto;

import JoanRuiz.mindnet.entities.User;

public class UserBasicInfoDTO {
    String username;
    String fullname;
    String imageUrl;

    public UserBasicInfoDTO(User user) {
        this.username = user.getUsername();
        this.fullname = user.getFullname();
        this.imageUrl = user.getImageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
