package JoanRuiz.mindnet.dto;

import JoanRuiz.mindnet.entities.Comment;
import JoanRuiz.mindnet.entities.NotificationType;
import JoanRuiz.mindnet.entities.Post;
import JoanRuiz.mindnet.entities.User;

public class NotificationRequestDTO {
    private User user;
    private User userTrigger;
    private Comment comment;
    private Post post;
    private String message;
    private NotificationType notificationType;

    public NotificationRequestDTO() {
    }

    public NotificationRequestDTO(User user, User userTrigger, Comment comment, Post post, String message, NotificationType notificationType) {
        this.user = user;
        this.userTrigger = userTrigger;
        this.comment = comment;
        this.post = post;
        this.message = message;
        this.notificationType = notificationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserTrigger() {
        return userTrigger;
    }

    public void setUserTrigger(User userTrigger) {
        this.userTrigger = userTrigger;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
