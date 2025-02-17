package JoanRuiz.mindnet.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullname;
    private String username;
    private String email;
    private String password;
    private String imageUrl;
    private String biography;
    private String cellphone;
    private LocalDate birthday;
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follower",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_user_followed")
    )
    private List<User> following;  // Usuarios que este usuario sigue

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follower",
            joinColumns = @JoinColumn(name = "id_user_followed"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> followers;  // Usuarios que siguen a este usuario

    @ManyToMany(mappedBy = "mentionedUsers", fetch = FetchType.LAZY)
    private List<Post> mentionedPosts;

    @ManyToMany(mappedBy = "mentionedUsers", fetch = FetchType.LAZY)
    private List<Comment> mentionedComments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "reaction",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_post"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_user", "id_post"})
    )
    private List<Post> reactions;

    public User() {
    }

    public User(Integer id, String fullname, String username, String email, String password, String imageUrl, String biography, String cellphone, LocalDate birthday, String role, List<Post> posts, List<Comment> comments, List<Notification> notifications, List<User> following, List<User> followers, List<Post> mentionedPosts, List<Comment> mentionedComments, List<Post> reactions) {
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
        this.posts = posts;
        this.comments = comments;
        this.notifications = notifications;
        this.following = following;
        this.followers = followers;
        this.mentionedPosts = mentionedPosts;
        this.mentionedComments = mentionedComments;
        this.reactions = reactions;
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

    @Override
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

    @Override
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Post> getMentionedPosts() {
        return mentionedPosts;
    }

    public void setMentionedPosts(List<Post> mentionedPosts) {
        this.mentionedPosts = mentionedPosts;
    }

    public List<Comment> getMentionedComments() {
        return mentionedComments;
    }

    public void setMentionedComments(List<Comment> mentionedComments) {
        this.mentionedComments = mentionedComments;
    }

    public List<Post> getReactions() {
        return reactions;
    }

    public void setReactions(List<Post> reactions) {
        this.reactions = reactions;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }


}
