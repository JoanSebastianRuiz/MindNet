package JoanRuiz.mindnet.auth;

import JoanRuiz.mindnet.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", authService.login(request).getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);
        return ResponseEntity.ok("Login successful");


    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", authService.register(request).getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return ResponseEntity.ok("Register successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok("Logout exitoso");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            token = getTokenFromCookie(request);
        }

        if (token == null) {
            return ResponseEntity.status(401).body("No token provided");
        }

        String username = jwtService.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!jwtService.isTokenValid(token, userDetails)) {
            return ResponseEntity.status(401).body("Expired or invalid token");
        }

        return ResponseEntity.ok("Token is valid");
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
