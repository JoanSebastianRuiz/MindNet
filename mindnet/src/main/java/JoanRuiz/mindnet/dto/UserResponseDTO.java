package JoanRuiz.mindnet.dto;

import JoanRuiz.mindnet.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class UserResponseDTO {

    private Integer id;
    private String fullname;
    private String username;
    private String email;
    private String imageUrl;
    private String biography;
    private String cellphone;
    private LocalDate birthday;
    private List<UserBasicInfoDTO> following;
    private List<UserBasicInfoDTO> followers;


    public UserResponseDTO(User user) {
        id = user.getId();
        fullname = user.getFullname();
        username = user.getUsername();
        email = user.getEmail();
        imageUrl = user.getImageUrl();
        biography = user.getBiography();
        cellphone = user.getCellphone();
        birthday = user.getBirthday();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<UserBasicInfoDTO> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserBasicInfoDTO> following) {
        this.following = following;
    }

    public List<UserBasicInfoDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserBasicInfoDTO> followers) {
        this.followers = followers;
    }
}
