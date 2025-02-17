package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.CommentRequestDTO;
import JoanRuiz.mindnet.dto.CommentResponseDTO;
import JoanRuiz.mindnet.entities.Comment;
import JoanRuiz.mindnet.entities.Tag;
import JoanRuiz.mindnet.entities.User;
import JoanRuiz.mindnet.repositories.CommentRepository;
import JoanRuiz.mindnet.repositories.PostRepository;
import JoanRuiz.mindnet.repositories.TagRepository;
import JoanRuiz.mindnet.repositories.UserRepository;
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

    public Optional<List<CommentResponseDTO>> getCommentsByPostId(Integer idPost) {
        try {
            List<Comment> comments = commentRepository.findByPostId(idPost);
            return Optional.of(comments.stream().map(comment -> new CommentResponseDTO(comment)).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean createComment(CommentRequestDTO comment) {
        try {
            Set<Tag> tags = extractTags(comment.getBody());
            List<User> mentionedUsers = extractMentions(comment.getBody());
            Comment newComment = new Comment();
            newComment.setBody(comment.getBody());
            newComment.setPost(postRepository.findById(comment.getIdPost()).get());
            newComment.setTags(tags);
            newComment.setDatetime(comment.getDatetime());
            newComment.setUser(userRepository.findByUsername(comment.getUsername()).get());
            newComment.setMentionedUsers(mentionedUsers);
            commentRepository.save(newComment);

            return newComment.getId() != null;
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

    private List<User> extractMentions(String body) {
        List<User> mentionedUsers = new ArrayList<>();
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
