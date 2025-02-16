package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
}
