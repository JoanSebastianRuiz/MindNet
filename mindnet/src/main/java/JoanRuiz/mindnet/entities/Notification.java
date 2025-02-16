package JoanRuiz.mindnet.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @OneToOne
    @JoinColumn(name = "idComment", nullable = true)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "idPost", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "idUserTrigger")
    private User userTrigger;

    @ManyToOne
    @JoinColumn(name = "idNotificationType")
    private NotificationType notificationType;

    private String message;
    private Boolean seen;
    private Timestamp createdAt;

    public Notification() {
    }

    public Notification(Integer id, User user, Comment comment, Post post, User userTrigger, NotificationType notificationType, String message, Boolean seen, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.post = post;
        this.userTrigger = userTrigger;
        this.notificationType = notificationType;
        this.message = message;
        this.seen = seen;
        this.createdAt = createdAt;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUserTrigger() {
        return userTrigger;
    }

    public void setUserTrigger(User userTrigger) {
        this.userTrigger = userTrigger;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}


