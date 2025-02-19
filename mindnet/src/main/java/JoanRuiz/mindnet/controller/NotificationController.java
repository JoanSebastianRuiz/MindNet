package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getNotificationsByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndSeen(id));
    }

    @PutMapping("/markseen/{id}")
    public ResponseEntity<?> markAsSeen(@PathVariable Integer id) {
        notificationService.markAsSeen(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count/user/{id}")
    public ResponseEntity<?> countNotificationsByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.countNotificationsByUserIdAndSeen(id));
    }
}
