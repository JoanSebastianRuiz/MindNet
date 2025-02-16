package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.dto.FollowRequestDTO;
import JoanRuiz.mindnet.dto.UnfollowRequestDTO;
import JoanRuiz.mindnet.dto.UserRequestDTO;
import JoanRuiz.mindnet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        if (userService.getUserByUsername(username).isPresent()) {
            return ResponseEntity.ok(userService.getUserByUsername(username).get());
        } else {
            return ResponseEntity.badRequest().body("Error getting user");
        }
    }

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody FollowRequestDTO followRequestDTO) {
        if (userService.followUser(followRequestDTO.getUsername(), followRequestDTO.getUsernameFollowed())) {
            return ResponseEntity.ok("User followed");
        } else {
            return ResponseEntity.badRequest().body("Error following user");
        }
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody UnfollowRequestDTO followRequestDTO) {
        boolean success = userService.unfollowUser(followRequestDTO.getUsername(), followRequestDTO.getUsernameUnfollowed());
        if (success) {
            return ResponseEntity.ok("User unfollowed successfully");
        }
        return ResponseEntity.badRequest().body("Error unfollowing user");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

}
