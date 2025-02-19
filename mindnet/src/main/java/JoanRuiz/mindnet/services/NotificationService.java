package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.NotificationBasicInfoDTO;
import JoanRuiz.mindnet.dto.NotificationResponseDTO;
import JoanRuiz.mindnet.dto.NotificationRequestDTO;
import JoanRuiz.mindnet.entities.Notification;
import JoanRuiz.mindnet.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Permite enviar mensajes en WebSockets

    public void createAndSendNotification(NotificationRequestDTO notificationRequestDTO) {
        Notification notification = new Notification();
        notification.setUser(notificationRequestDTO.getUser());
        notification.setUserTrigger(notificationRequestDTO.getUserTrigger());
        if (notificationRequestDTO.getComment() != null){
            notification.setComment(notificationRequestDTO.getComment());
        }
        if (notificationRequestDTO.getPost() != null){
            notification.setPost(notificationRequestDTO.getPost());
        }
        notification.setMessage(notificationRequestDTO.getMessage());
        notification.setNotificationType(notificationRequestDTO.getNotificationType());
        notification.setSeen(false);
        notification.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Guardar en base de datos
        notificationRepository.save(notification);

        NotificationBasicInfoDTO notificationBasicInfo = new NotificationBasicInfoDTO();
        notificationBasicInfo.setIdUser(notification.getUser().getId());
        notificationBasicInfo.setMessage(notification.getMessage());
        notificationBasicInfo.setCreatedAt(notification.getCreatedAt().toString());
        notificationBasicInfo.setNameNotificationType(notification.getNotificationType().getName());
        notificationBasicInfo.setIdUserTrigger(notification.getUserTrigger().getId());
        if (notification.getPost() != null){
            notificationBasicInfo.setIdPost(notification.getPost().getId());
        }

        System.out.println("📢 Enviando notificación a usuario: " + notification.getUser().getId().toString());
        // Enviar la notificación en tiempo real por WebSocket
        messagingTemplate.convertAndSend("/topic/notifications", notificationBasicInfo);
    }

    public void markAsSeen(Integer id) {
        notificationRepository.findById(id).ifPresent(notification -> {
            notification.setSeen(true);
            notificationRepository.save(notification);
        });
    }

    public List<NotificationResponseDTO> getNotificationsByUserIdAndSeen(Integer id) {
        return notificationRepository.findByUserIdAndSeenOrderByCreatedAtDesc(id,false).stream().map(notification -> {
            NotificationResponseDTO notificationResponse = new NotificationResponseDTO();
            notificationResponse.setId(notification.getId());
            notificationResponse.setIdUser(notification.getUser().getId());
            notificationResponse.setMessage(notification.getMessage());
            notificationResponse.setCreatedAt(notification.getCreatedAt().toString());
            notificationResponse.setSeen(notification.getSeen());
            notificationResponse.setUsernameUserTrigger(notification.getUserTrigger().getUsername());
            notificationResponse.setFullNameUserTrigger(notification.getUserTrigger().getFullname());
            if (notification.getComment() != null){
                notificationResponse.setIdComment(notification.getComment().getId());
            }
            if (notification.getPost() != null){
                notificationResponse.setIdPost(notification.getPost().getId());
            }
            notificationResponse.setNameNotificationType(notification.getNotificationType().getName());
            return notificationResponse;
        }).collect(Collectors.toList());
    }

    public Integer countNotificationsByUserIdAndSeen(Integer id) {
        return notificationRepository.countByUserIdAndSeen(id,false);
    }
}
