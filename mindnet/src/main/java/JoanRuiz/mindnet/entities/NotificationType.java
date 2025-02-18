package JoanRuiz.mindnet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class NotificationType {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public NotificationType() {
    }

    public NotificationType(Integer id, String notification) {
        this.id = id;
        this.name = notification;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotification() {
        return name;
    }

    public void setNotification(String notification) {
        this.name = notification;
    }
}
