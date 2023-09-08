package vn.techmaster.finalproject.controller.RestController;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.techmaster.finalproject.request.LoginRequest;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class AuthController {
    private AuthenticationManager authenticationManager;

    @PostMapping("/login-authenticate")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("JOBHUNT_SESSION", authentication.getName());
        return ResponseEntity.ok(authentication);
    }
}
