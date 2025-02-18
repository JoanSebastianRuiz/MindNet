package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.entities.Notification;
import JoanRuiz.mindnet.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
}
