package JoanRuiz.mindnet.dto;

public class NotificacionResponseDTO {
    private Integer id;
    private Integer idUser;
    private String message;
    private String createdAt;
    private Boolean seen;
    private String usernameUserTrigger;
    private Integer idComment;
    private Integer idPost;
    private String nameNotificationType;

    public NotificacionResponseDTO() {
    }

    public NotificacionResponseDTO(Integer id, Integer idUser, String message, String createdAt, Boolean seen, String usernameUserTrigger, Integer idComment, Integer idPost, String nameNotificationType) {
        this.id = id;
        this.idUser = idUser;
        this.message = message;
        this.createdAt = createdAt;
        this.seen = seen;
        this.usernameUserTrigger = usernameUserTrigger;
        this.idComment = idComment;
        this.idPost = idPost;
        this.nameNotificationType = nameNotificationType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getUsernameUserTrigger() {
        return usernameUserTrigger;
    }

    public void setUsernameUserTrigger(String usernameUserTrigger) {
        this.usernameUserTrigger = usernameUserTrigger;
    }

    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public String getNameNotificationType() {
        return nameNotificationType;
    }

    public void setNameNotificationType(String nameNotificationType) {
        this.nameNotificationType = nameNotificationType;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
