package JoanRuiz.mindnet.dto;

import java.time.LocalDate;

public class UserRequestDTO {
    private String fullname;
    private String username;
    private String email;
    private String imageUrl;
    private String biography;
    private String cellphone;
    private LocalDate birthday;
    private String password;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String fullname, String username, String email, String imageUrl, String biography, String cellphone, LocalDate birthday, String password) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.biography = biography;
        this.cellphone = cellphone;
        this.birthday = birthday;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
