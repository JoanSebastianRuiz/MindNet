package JoanRuiz.mindnet.repositories;

import JoanRuiz.mindnet.entities.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
}
