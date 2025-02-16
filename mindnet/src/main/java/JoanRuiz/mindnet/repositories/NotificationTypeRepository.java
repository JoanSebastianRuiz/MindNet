package JoanRuiz.mindnet.repositories;

import JoanRuiz.mindnet.entities.NotificationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTypeRepository extends CrudRepository<NotificationType, Integer> {
}
