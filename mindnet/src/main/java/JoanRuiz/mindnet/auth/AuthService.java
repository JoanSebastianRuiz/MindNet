package JoanRuiz.mindnet.auth;

import JoanRuiz.mindnet.entities.User;
import JoanRuiz.mindnet.jwt.JwtService;
import JoanRuiz.mindnet.repositories.UserRepository;
import JoanRuiz.mindnet.user.Role;
import JoanRuiz.mindnet.util.validators.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setFullname(request.getFullname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if(request.getImageUrl()!=null && ImageValidator.isValidImageUrl(request.getImageUrl())){
            user.setImageUrl(request.getImageUrl());
        }
        user.setBiography(request.getBiography());
        user.setCellphone(request.getCellphone());
        user.setBirthday(request.getBirthday());
        user.setRole(Role.USER.name());

        userRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));
    }
}
