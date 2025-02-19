package JoanRuiz.mindnet.dto;

public class NotificationBasicInfoDTO {
    Integer idUser;
    Integer idUserTrigger;
    Integer idPost;
    String message;
    String createdAt;
    String nameNotificationType;

    public NotificationBasicInfoDTO() {
    }

    public NotificationBasicInfoDTO(Integer idUser, Integer idUserTrigger, Integer idPost, String message, String createdAt, String nameNotificationType) {
        this.idUser = idUser;
        this.idUserTrigger = idUserTrigger;
        this.idPost = idPost;
        this.message = message;
        this.createdAt = createdAt;
        this.nameNotificationType = nameNotificationType;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getNameNotificationType() {
        return nameNotificationType;
    }

    public void setNameNotificationType(String nameNotificationType) {
        this.nameNotificationType = nameNotificationType;
    }

    public Integer getIdUserTrigger() {
        return idUserTrigger;
    }

    public void setIdUserTrigger(Integer idUserTrigger) {
        this.idUserTrigger = idUserTrigger;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }
}
