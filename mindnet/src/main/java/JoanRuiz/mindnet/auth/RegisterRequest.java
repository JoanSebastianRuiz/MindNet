package JoanRuiz.mindnet.auth;

import JoanRuiz.mindnet.user.Role;

import java.time.LocalDate;



public class RegisterRequest {
    private Integer id;
    private String fullname;
    private String username;
    private String email;
    private String password;
    private String imageUrl;
    private String biography;
    private String cellphone;
    private LocalDate birthday;
    private Role role;

    public RegisterRequest() {
    }

    public RegisterRequest(Integer id, String fullname, String username, String email, String password, String imageUrl, String biography, String cellphone, LocalDate birthday, Role role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.biography = biography;
        this.cellphone = cellphone;
        this.birthday = birthday;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
