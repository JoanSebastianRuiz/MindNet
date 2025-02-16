package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.services.NotificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificationTypes")
public class NotificationTypeController{
    @Autowired
    private NotificationTypeService notificationTypeService;
}
