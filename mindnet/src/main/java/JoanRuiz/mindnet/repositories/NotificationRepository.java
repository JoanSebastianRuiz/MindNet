package JoanRuiz.mindnet.repositories;

import JoanRuiz.mindnet.entities.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    List<Notification> findByUserId(Integer userId);
}
