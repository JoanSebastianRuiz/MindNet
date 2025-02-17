package JoanRuiz.mindnet.dto;

public class LikeRequest {
    private Integer idUser;

    public LikeRequest(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
