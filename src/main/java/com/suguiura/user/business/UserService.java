package com.suguiura.user.business;

import com.suguiura.user.business.converter.UserConverter;
import com.suguiura.user.business.dto.UserDTO;
import com.suguiura.user.infrastructure.entity.UserEntity;
import com.suguiura.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO saveUser(UserDTO userDTO){
        UserEntity user = userConverter.toUser(userDTO);
        user = userRepository.save(user);
        return userConverter.toUserDTO(user);
    }
}
