package com.zenveus.authservice.controller;


import com.zenveus.authservice.dto.AuthDTO;
import com.zenveus.authservice.dto.LoginRequest;
import com.zenveus.authservice.entity.User;
import com.zenveus.authservice.service.UserService;
import com.zenveus.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping(value = "/auth")
@RestController
public class UserController {
     @Autowired
     private UserService userService;

     @Autowired
     private JwtUtil jwtUtil;

     @PostMapping("/register")
     public AuthDTO addUser(@RequestBody User user){

         System.out.println(user.getEmail());

         try{
             if (user.getEmail().endsWith("@zenveus.com")){
                 user.setRole("ADMIN");
             }else {
                 user.setRole("USER");
             }

             User savedUser = userService.addUser(user);

             if (savedUser != null){
                 AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(savedUser.getEmail());
                    authDTO.setToken(jwtUtil.generateToken(savedUser));

                    return authDTO;
             }

         } catch (Exception e) {
             throw new RuntimeException(e);
         }

         return  null;
     }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody User user) {
        // Fetch the user by username
        User logUser = userService.fetchUserByEmail(user.getEmail());

        // If user not found or password doesn't match
        if (logUser == null || !logUser.getPassword().equals(user.getPassword())) {
            System.out.println("Login failed for user: " + user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Respond with 401 Unauthorized
        } else {
            // Generate JWT token
            String token = jwtUtil.generateToken(logUser);

            // Create AuthDTO with email and token
            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(user.getEmail());
            authDTO.setToken(token);

            System.out.println("Login successful for user: " + user.getEmail());

            // Respond with 200 OK and AuthDTO
            return ResponseEntity.ok(authDTO);
        }
    }

    @GetMapping("/user/{id}")
     public User fetchUserById(@PathVariable Long id){
         return userService.fetchUserById(id);
     }

     @GetMapping("/user/username/{username}")
     public User fetchUserByUsername(@PathVariable String username){
         return userService.fetchUserByUsername(username);
     }
}
