package JoanRuiz.mindnet.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    private String  body;

    @Column(nullable = true)
    private String  imageUrl;

    private Timestamp datetime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "mention_post",
            joinColumns = @JoinColumn(name = "id_post"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> mentionedUsers;

    @ManyToMany(mappedBy = "reactions")
    private List<User> usersReacted;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag_post",
            joinColumns = @JoinColumn(name = "id_post"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<Tag> tags;

    public Post() {
    }

    public Post(Integer id, User user, String body, String imageUrl, Timestamp datetime, List<Comment> comments, List<User> mentionedUsers, List<User> usersReacted, Set<Tag> tags) {
        this.id = id;
        this.user = user;
        this.body = body;
        this.imageUrl = imageUrl;
        this.datetime = datetime;
        this.comments = comments;
        this.mentionedUsers = mentionedUsers;
        this.usersReacted = usersReacted;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(List<User> mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

    public List<User> getUsersReacted() {
        return usersReacted;
    }

    public void setUsersReacted(List<User> usersReacted) {
        this.usersReacted = usersReacted;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
