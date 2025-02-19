package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.*;
import JoanRuiz.mindnet.entities.NotificationType;
import JoanRuiz.mindnet.entities.Post;
import JoanRuiz.mindnet.entities.Tag;
import JoanRuiz.mindnet.entities.User;
import JoanRuiz.mindnet.repositories.NotificationTypeRepository;
import JoanRuiz.mindnet.repositories.PostRepository;
import JoanRuiz.mindnet.repositories.TagRepository;
import JoanRuiz.mindnet.repositories.UserRepository;
import JoanRuiz.mindnet.util.validators.ImageValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    public Boolean createPost(PostRequestDTO post) {
        try{
            Set<Tag> tags = extractTags(post.getBody());
            Set<User> mentionedUsers = extractMentions(post.getBody());
            Post newPost = new Post();
            newPost.setBody(post.getBody());
            if(post.getImageUrl() != null && ImageValidator.isValidImageUrl(post.getImageUrl())){
                newPost.setImageUrl(post.getImageUrl());
            }
            newPost.setUser(userRepository.findByUsername(post.getUsername()).get());
            newPost.setDatetime(post.getDatetime());
            newPost.setTags(tags);
            newPost.setMentionedUsers(mentionedUsers);
            postRepository.save(newPost);

            for(User user: mentionedUsers){
                if(Objects.equals(user.getId(), newPost.getUser().getId())){
                    continue;
                }
                NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
                notificationRequestDTO.setUser(user);
                notificationRequestDTO.setUserTrigger(newPost.getUser());
                notificationRequestDTO.setPost(newPost);
                notificationRequestDTO.setMessage(newPost.getUser().getFullname()+" has mentioned you in a post.");
                if(notificationTypeRepository.findByName("mention").isEmpty()){
                    NotificationType no = new NotificationType();
                    no.setName("mention");
                    notificationTypeRepository.save(no);
                }
                notificationRequestDTO.setNotificationType(notificationTypeRepository.findByName("mention").get());
                notificationService.createAndSendNotification(notificationRequestDTO);
            }

            return newPost.getId() != null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Optional<List<PostResponseDTO>> getPosts(String filter, String scope, Integer idUser) {
        try {
            if (filter != null && scope != null) {
                if (filter.equals("newest") && scope.equals("all")) {
                    // Obtener todos los posts ordenados por fecha
                    List<Post> posts = postRepository.findAllByOrderByDatetimeDesc();
                    return Optional.of(posts.stream().map(this::mapToPostResponseDTO).collect(Collectors.toList()));
                } else if (filter.equals("trending") && scope.equals("all")) {
                    // Obtener todos los posts
                    List<Post> posts = (List<Post>) postRepository.findAll();
                    return Optional.of(posts.stream()
                            .map(this::mapToPostResponseDTO)
                            .sorted(Comparator.comparingInt(PostResponseDTO::getTrending).reversed())
                            .collect(Collectors.toList()));
                } else if (filter.equals("trending") && scope.equals("following")) {
                    // Obtener posts de los usuarios seguidos, ordenados por trending
                    List<Post> posts = postRepository.findPostsByUsersFollowedOrdered(idUser);
                    return Optional.of(posts.stream()
                            .map(this::mapToPostResponseDTO)
                            .sorted(Comparator.comparingInt(PostResponseDTO::getTrending).reversed())
                            .collect(Collectors.toList()));
                } else if (filter.equals("newest") && scope.equals("following")) {
                    // Obtener posts de los usuarios seguidos, ordenados por fecha
                    List<Post> posts = postRepository.findPostsByUsersFollowedOrdered(idUser);
                    return Optional.of(posts.stream().map(this::mapToPostResponseDTO).collect(Collectors.toList()));
                }
            }

            // Si no coincide con ningún filtro/alcance, retornamos Optional.empty()
            return Optional.empty();

        } catch (Exception e) {
            e.printStackTrace();  // Mostrar detalles del error
            return Optional.empty();
        }
    }


    private PostResponseDTO mapToPostResponseDTO(Post post) {
        Integer likesCount = postRepository.countReactionsByPostId(post.getId());
        PostResponseDTO postResponseDTO = new PostResponseDTO(post, likesCount);

        // Mapear comentarios y usuarios mencionados
        postResponseDTO.setComments(post.getComments().stream().map(comment -> {
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO(comment);
            commentResponseDTO.setMentionedUsers(comment.getMentionedUsers().stream()
                    .map(user -> new MentionedUser(user.getUsername()))
                    .collect(Collectors.toList()));
            return commentResponseDTO;
        }).collect(Collectors.toList()));

        // Mapear usuarios mencionados en el post
        postResponseDTO.setMentionedUsers(post.getMentionedUsers().stream()
                .map(user -> new MentionedUser(user.getUsername()))
                .collect(Collectors.toList()));

        // Calcular el puntaje de trending (likes + comentarios)
        postResponseDTO.setTrending(postResponseDTO.getLikesCount() + postResponseDTO.getComments().size());
        return postResponseDTO;
    }

    public Optional<List<PostResponseDTO>> getPostsByUserUsername(String username) {
        try {
            List<Post> posts = postRepository.findByUserUsername(username);

            return Optional.of(posts.stream().map(post -> {
                Integer likesCount = postRepository.countReactionsByPostId(post.getId());
                PostResponseDTO postResponseDTO = new PostResponseDTO(post, likesCount);
                postResponseDTO.setComments(post.getComments().stream().map(comment -> {
                    CommentResponseDTO commentResponseDTO = new CommentResponseDTO(comment);
                    commentResponseDTO.setMentionedUsers(comment.getMentionedUsers().stream().map(user -> new MentionedUser(user.getUsername())).collect(Collectors.toList()));
                    return commentResponseDTO;
                }).collect(Collectors.toList()));
                postResponseDTO.setMentionedUsers(post.getMentionedUsers().stream().map(user -> new MentionedUser(user.getUsername())).collect(Collectors.toList()));
                return postResponseDTO;
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean deletePost(Integer id) {
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Boolean updatePost(Integer id, Post post) {
        try {
            Post postToUpdate = postRepository.findById(id).get();
            postToUpdate.setBody(post.getBody());
            if(post.getImageUrl() != null && ImageValidator.isValidImageUrl(post.getImageUrl())){
                postToUpdate.setImageUrl(post.getImageUrl());
            }
            postRepository.save(postToUpdate);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public ResponseEntity<String> reactToPost(Integer idPost, Integer idUser) {
        try {
            String response = "";
            Post post = postRepository.findById(idPost)
                    .orElseThrow();

            User user = userRepository.findById(idUser)
                    .orElseThrow();

            if (post.getUsersReacted().contains(user)) {
                post.getUsersReacted().remove(user);
                user.getReactions().remove(post);
                response = "Post unliked";
            } else {
                post.getUsersReacted().add(user);
                user.getReactions().add(post);
                response = "Post liked";
                if(!Objects.equals(post.getUser().getId(), idUser)){
                    registerNotification(idPost, idUser);
                }
            }

            postRepository.save(post);
            userRepository.save(user);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error reacting to post");
        }
    }

    private void registerNotification(Integer idPost, Integer idUser){
        Post post = postRepository.findById(idPost)
                .orElseThrow();

        User user = userRepository.findById(idUser)
                .orElseThrow();
        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
        notificationRequestDTO.setUser(post.getUser());
        notificationRequestDTO.setUserTrigger(user);
        notificationRequestDTO.setPost(post);
        notificationRequestDTO.setMessage(user.getFullname()+" has liked your post.");
        if(notificationTypeRepository.findByName("like").isEmpty()){
            NotificationType no = new NotificationType();
            no.setName("like");
            notificationTypeRepository.save(no);
        }

        notificationRequestDTO.setNotificationType(notificationTypeRepository.findByName("like").get());
        notificationService.createAndSendNotification(notificationRequestDTO);
    }

    public ResponseEntity<Boolean> userLikeToPost(Integer idPost, Integer idUser) {
        try {
            Post post = postRepository.findById(idPost)
                    .orElseThrow();

            User user = userRepository.findById(idUser)
                    .orElseThrow();

            return ResponseEntity.ok(post.getUsersReacted().contains(user));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }

    private Set<Tag> extractTags(String body) {
        Set<Tag> tags = new HashSet<>();
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(body);

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

    public Optional<PostResponseDTO> getPostById(Integer id) {
        try {
            Post post = postRepository.findById(id).orElseThrow();
            Integer likesCount = postRepository.countReactionsByPostId(post.getId());
            PostResponseDTO postResponseDTO = new PostResponseDTO(post, likesCount);
            postResponseDTO.setComments(post.getComments().stream().map(comment -> {
                CommentResponseDTO commentResponseDTO = new CommentResponseDTO(comment);
                commentResponseDTO.setMentionedUsers(comment.getMentionedUsers().stream().map(user -> new MentionedUser(user.getUsername())).collect(Collectors.toList()));
                return commentResponseDTO;
            }).collect(Collectors.toList()));
            postResponseDTO.setMentionedUsers(post.getMentionedUsers().stream().map(user -> new MentionedUser(user.getUsername())).collect(Collectors.toList()));
            return Optional.of(postResponseDTO);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

}
