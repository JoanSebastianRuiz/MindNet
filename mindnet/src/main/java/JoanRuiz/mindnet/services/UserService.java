package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.NotificationRequestDTO;
import JoanRuiz.mindnet.dto.UserBasicInfoDTO;
import JoanRuiz.mindnet.dto.UserRequestDTO;
import JoanRuiz.mindnet.dto.UserResponseDTO;
import JoanRuiz.mindnet.entities.NotificationType;
import JoanRuiz.mindnet.entities.User;
import JoanRuiz.mindnet.repositories.NotificationTypeRepository;
import JoanRuiz.mindnet.repositories.UserRepository;
import JoanRuiz.mindnet.util.validators.ImageValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    public Optional<List<UserResponseDTO>> getAll(){
        try {
            List<User> users = (List<User>) userRepository.findAll();
            List<UserResponseDTO> userDTOList = users.stream().map(user -> new UserResponseDTO(user)).collect(Collectors.toList());

            return Optional.of(userDTOList.stream().map(user -> {
                user.setFollowers(getFollowers(user.getUsername()).get());
                user.setFollowing(getFollowing(user.getUsername()).get());
                return user;
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<UserResponseDTO> getUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(()->new Exception("User not found"));
            UserResponseDTO userDTO = new UserResponseDTO(user);
            userDTO.setFollowers(getFollowers(username).get());
            userDTO.setFollowing(getFollowing(username).get());
            return Optional.of(userDTO);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public ResponseEntity<String> updateUser(Integer id, UserRequestDTO userRequestDTO) {
        try {
            User user = userRepository.findById(id).orElseThrow(()->new Exception("User not found"));

            user.setFullname(userRequestDTO.getFullname());

            if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent() && !user.getEmail().equals(userRequestDTO.getEmail())){
                return ResponseEntity.badRequest().body("Email already exists");
            }
            user.setEmail(userRequestDTO.getEmail());
            if(userRequestDTO.getImageUrl()!=null && ImageValidator.isValidImageUrl(userRequestDTO.getImageUrl())){
                user.setImageUrl(userRequestDTO.getImageUrl());
            }
            user.setImageUrl(userRequestDTO.getImageUrl());
            user.setBiography(userRequestDTO.getBiography());

            if(userRepository.findByCellphone(userRequestDTO.getCellphone()).isPresent() && !user.getCellphone().equals(userRequestDTO.getCellphone())){
                return ResponseEntity.badRequest().body("Cellphone already exists");
            }
            user.setCellphone(userRequestDTO.getCellphone());
            user.setBirthday(userRequestDTO.getBirthday());

            userRepository.save(user);
            return ResponseEntity.ok("User updated");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error updating user");
        }
    }

    @Transactional
    public Boolean followUser(String username, String usernameFollowed) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(()->new Exception("User not found"));
            User userFollowed = userRepository.findByUsername(usernameFollowed).orElseThrow(()->new Exception("User not found"));

            if(user!=null && userFollowed!=null && !user.getFollowing().contains(userFollowed)){
                user.getFollowing().add(userFollowed);
                userRepository.save(user);

                NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
                notificationRequestDTO.setUser(userFollowed);
                notificationRequestDTO.setUserTrigger(user);
                notificationRequestDTO.setMessage(user.getFullname()+" has followed you.");
                if(notificationTypeRepository.findByName("follow").isEmpty()){
                    NotificationType no = new NotificationType();
                    no.setName("follow");
                    notificationTypeRepository.save(no);
                }
                notificationRequestDTO.setNotificationType(notificationTypeRepository.findByName("follow").get());
                notificationService.createAndSendNotification(notificationRequestDTO);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public Boolean unfollowUser(String username, String usernameUnfollowed) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new Exception("User not found"));
            User userUnfollowed = userRepository.findByUsername(usernameUnfollowed)
                    .orElseThrow(() -> new Exception("User to unfollow not found"));

            if (user.getFollowing().contains(userUnfollowed)) {
                user.getFollowing().remove(userUnfollowed);
                userUnfollowed.getFollowers().remove(user);
                userRepository.save(user);
                return true;
            }

            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    private Optional<List<UserBasicInfoDTO>> getFollowers(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(()->new Exception("User not found"));
            List<UserBasicInfoDTO> followers = user.getFollowers().stream().map(follower -> new UserBasicInfoDTO(follower)).collect(Collectors.toList());
            return Optional.of(followers);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<List<UserBasicInfoDTO>> getFollowing(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(()->new Exception("User not found"));
            List<UserBasicInfoDTO> following = user.getFollowing().stream().map(userFollowing -> new UserBasicInfoDTO(userFollowing)).collect(Collectors.toList());
            return Optional.of(following);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }


}
