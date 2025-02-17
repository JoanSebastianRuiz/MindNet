package JoanRuiz.mindnet.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idPost")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToMany(mappedBy = "mentionedComments", fetch = FetchType.LAZY)
    private List<User> mentionedUsers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag_comment",
            joinColumns = @JoinColumn(name = "id_comment"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<Tag> tags;

    private String  body;
    private Timestamp datetime;

    public Comment() {
    }

    public Comment(Integer id, Post post, User user, List<User> mentionedUsers, Set<Tag> tags, String body, Timestamp datetime) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.mentionedUsers = mentionedUsers;
        this.tags = tags;
        this.body = body;
        this.datetime = datetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(List<User> mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}
