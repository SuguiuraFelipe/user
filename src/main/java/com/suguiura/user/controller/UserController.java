package com.suguiura.user.controller;


import com.suguiura.user.business.UserService;
import com.suguiura.user.business.dto.AddressDTO;
import com.suguiura.user.business.dto.PhoneNumberDTO;
import com.suguiura.user.business.dto.UserDTO;
import com.suguiura.user.infrastructure.entity.UserEntity;
import com.suguiura.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserDTO> saveUSer(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam("email") String email){ //Requisição via parametro
        return ResponseEntity.ok(userService.findUserByEmail(email)); // ResponseEntity.ok serve para formatar a resposta de maneira correta para o response HTTP
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build(); // garantir o retorno em caso de erro
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUserData(@RequestBody UserDTO dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.updateUserData(token, dto));
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO dto, @RequestParam("id") Long id){
        return ResponseEntity.ok(userService.updateAddress(id, dto));
    }

    @PutMapping("/phone")
    public ResponseEntity<PhoneNumberDTO> updateAddress(@RequestBody PhoneNumberDTO dto, @RequestParam("id") Long id){
        return ResponseEntity.ok(userService.updatePhoneNumber(id, dto));
    }
}
