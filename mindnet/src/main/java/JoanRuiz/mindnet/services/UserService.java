package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.UserBasicInfoDTO;
import JoanRuiz.mindnet.dto.UserResponseDTO;
import JoanRuiz.mindnet.entities.User;
import JoanRuiz.mindnet.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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

    @Transactional
    public Boolean followUser(String username, String usernameFollowed) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(()->new Exception("User not found"));
            User userFollowed = userRepository.findByUsername(usernameFollowed).orElseThrow(()->new Exception("User not found"));

            if(user!=null && userFollowed!=null && !user.getFollowing().contains(userFollowed)){
                user.getFollowing().add(userFollowed);
                userRepository.save(user);
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
            User user = userRepository.findByUsername(username).orElseThrow(()->new Exception("User not found"));
            User userFollowed = userRepository.findByUsername(usernameUnfollowed).orElseThrow(()->new Exception("User not found"));

            if(user!=null && userFollowed!=null && user.getFollowing().contains(userFollowed)){
                user.getFollowing().remove(usernameUnfollowed);
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
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
