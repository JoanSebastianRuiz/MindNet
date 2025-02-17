package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.CommentResponseDTO;
import JoanRuiz.mindnet.dto.PostRequestDTO;
import JoanRuiz.mindnet.dto.PostResponseDTO;
import JoanRuiz.mindnet.entities.Post;
import JoanRuiz.mindnet.entities.Tag;
import JoanRuiz.mindnet.entities.User;
import JoanRuiz.mindnet.repositories.PostRepository;
import JoanRuiz.mindnet.repositories.TagRepository;
import JoanRuiz.mindnet.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public Boolean createPost(PostRequestDTO post) {
        try{
            Set<Tag> tags = extractTags(post.getBody());
            Post newPost = new Post();
            newPost.setBody(post.getBody());
            newPost.setImageUrl(post.getImageUrl());
            newPost.setUser(userRepository.findByUsername(post.getUsername()).get());
            newPost.setDatetime(post.getDatetime());
            newPost.setTags(tags);
            postRepository.save(newPost);
            return newPost.getId() != null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Optional<List<PostResponseDTO>> getPosts() {
        try {
            List<Post> posts = (List<Post>) postRepository.findAll();
            return Optional.of(posts.stream().map(post -> {
                Integer likesCount = postRepository.countReactionsByPostId(post.getId());
                PostResponseDTO postResponseDTO = new PostResponseDTO(post, likesCount);
                postResponseDTO.setComments(post.getComments().stream().map(CommentResponseDTO::new).collect(Collectors.toList()));
                return postResponseDTO;
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<List<PostResponseDTO>> getPostsByUserUsername(String username) {
        try {
            List<Post> posts = postRepository.findByUserUsername(username);

            return Optional.of(posts.stream().map(post -> {
                Integer likesCount = postRepository.countReactionsByPostId(post.getId());
                PostResponseDTO postResponseDTO = new PostResponseDTO(post, likesCount);
                postResponseDTO.setComments(post.getComments().stream().map(CommentResponseDTO::new).collect(Collectors.toList()));
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
            postToUpdate.setImageUrl(post.getImageUrl());
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
            }

            postRepository.save(post);
            userRepository.save(user);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error reacting to post");
        }
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

}
