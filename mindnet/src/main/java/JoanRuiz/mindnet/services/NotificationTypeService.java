package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.repositories.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationTypeService {
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;
}
