package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.CommentRequestDTO;
import JoanRuiz.mindnet.dto.CommentResponseDTO;
import JoanRuiz.mindnet.dto.NotificationRequestDTO;
import JoanRuiz.mindnet.entities.*;
import JoanRuiz.mindnet.exception.CommentException;
import JoanRuiz.mindnet.exception.PostException;
import JoanRuiz.mindnet.exception.UserException;
import JoanRuiz.mindnet.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private NotificationService notificationService;

    public Optional<List<CommentResponseDTO>> getCommentsByPostId(Integer idPost) {
        try {
            List<Comment> comments = commentRepository.findByPostId(idPost);
            if (comments.isEmpty()) {
                throw new CommentException("Comments not found");
            }
            return Optional.of(comments.stream().map(comment -> new CommentResponseDTO(comment)).collect(Collectors.toList()));
        } catch (CommentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean createComment(CommentRequestDTO comment) {
        try {
            Set<Tag> tags = extractTags(comment.getBody());
            Set<User> mentionedUsers = extractMentions(comment.getBody());
            Comment newComment = new Comment();
            newComment.setBody(comment.getBody());
            newComment.setPost(postRepository.findById(comment.getIdPost()).orElseThrow(() -> new PostException("Post not found")));
            newComment.setTags(tags);
            newComment.setDatetime(comment.getDatetime());
            newComment.setUser(userRepository.findByUsername(comment.getUsername()).orElseThrow(() -> new UserException("User not found")));
            newComment.setMentionedUsers(mentionedUsers);
            commentRepository.save(newComment);

            // Crear notificaciones
            for (User mentionedUser : mentionedUsers) {
                if (Objects.equals(mentionedUser.getId(), newComment.getUser().getId())) {
                    continue;
                }

                NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
                notificationRequestDTO.setUser(mentionedUser);
                notificationRequestDTO.setUserTrigger(newComment.getUser());
                notificationRequestDTO.setComment(newComment);
                notificationRequestDTO.setMessage(newComment.getUser().getFullname()+" has mentioned you in a comment.");
                notificationRequestDTO.setPost(newComment.getPost());
                if(notificationTypeRepository.findByName("mention").isEmpty()){
                    NotificationType no = new NotificationType();
                    no.setName("mention");
                    notificationTypeRepository.save(no);
                }
                notificationRequestDTO.setNotificationType(notificationTypeRepository.findByName("mention").get());
                notificationService.createAndSendNotification(notificationRequestDTO);
            }

            if(Objects.equals(newComment.getUser().getId(), newComment.getPost().getUser().getId())){
                return newComment.getId() != null;
            }

            NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
            notificationRequestDTO.setUser(newComment.getPost().getUser());
            notificationRequestDTO.setUserTrigger(newComment.getUser());
            notificationRequestDTO.setComment(newComment);
            notificationRequestDTO.setMessage(newComment.getUser().getFullname()+" has commented on your post.");
            notificationRequestDTO.setPost(newComment.getPost());
            if(notificationTypeRepository.findByName("comment").isEmpty()){
                NotificationType no = new NotificationType();
                no.setName("comment");
                notificationTypeRepository.save(no);
            }
            notificationRequestDTO.setNotificationType(notificationTypeRepository.findByName("comment").get());
            notificationService.createAndSendNotification(notificationRequestDTO);

            return newComment.getId() != null;
        } catch (PostException | UserException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    private Set<Tag> extractTags(String content) {
        Set<Tag> tags = new HashSet<>();
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String tagName = matcher.group(1);
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> tagRepository.save(new Tag(tagName)));
            tags.add(tag);
        }
        return tags;
    }

    private Set<User> extractMentions(String body) {
        Set<User> mentionedUsers = new HashSet<>();
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(body);

        while (matcher.find()) {
            String mentionedUsername = matcher.group(1);
            Optional<User> mentionedUserOptional = userRepository.findByUsername(mentionedUsername);
            mentionedUserOptional.ifPresent(mentionedUsers::add);
        }

        return mentionedUsers;
    }

}
