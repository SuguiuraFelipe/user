package com.suguiura.user.business;

import com.suguiura.user.business.converter.UserConverter;
import com.suguiura.user.business.dto.AddressDTO;
import com.suguiura.user.business.dto.PhoneNumberDTO;
import com.suguiura.user.business.dto.UserDTO;
import com.suguiura.user.infrastructure.entity.Address;
import com.suguiura.user.infrastructure.entity.PhoneNumber;
import com.suguiura.user.infrastructure.entity.UserEntity;
import com.suguiura.user.infrastructure.exceptions.ConflictException;
import com.suguiura.user.infrastructure.exceptions.ResourceNotFoundException;
import com.suguiura.user.infrastructure.repository.AddressRepository;
import com.suguiura.user.infrastructure.repository.PhoneNumberRepository;
import com.suguiura.user.infrastructure.repository.UserRepository;
import com.suguiura.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AddressRepository addressRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public UserDTO saveUser(UserDTO userDTO){
        emailExists(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity user = userConverter.toUser(userDTO);
        user = userRepository.save(user);
        return userConverter.toUserDTO(user);
    }

    public void emailExists(String email){
        try{
            boolean exist = verifyEmailExists(email);
            if (exist){
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    public boolean verifyEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public UserDTO findUserByEmail(String email) {
        try {
            return userConverter.toUserDTO(userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email))); // o orElseThrow evita que o programa quebre por null pointer exception
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserDTO updateUserData(String token, UserDTO dto){
        // Aqui é feita a busca do email do usuário através do token(tirar a obrigatoriedade do email)
        String email = jwtUtil.extractUsername(token.substring(7));
        // criptografia de senha
        dto.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);
        //Busca os dados do usuário no banco de dados
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));
        //Mescla de dados entre o a requisição e o DB
        UserEntity userEntity1 = userConverter.updateUser(dto, userEntity);
        //Salva os dados do usuário convertido e depois retornou esses dados convertidos para DTO
        return userConverter.toUserDTO(userRepository.save(userEntity1));
    }

    public AddressDTO updateAddress(Long id, AddressDTO addressDTO){
        Address entity = addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado " + id));
        Address address = userConverter.updateAddress(addressDTO, entity);
        return  userConverter.toAddressDTO(addressRepository.save(address));
    }

    public PhoneNumberDTO updatePhoneNumber(Long id, PhoneNumberDTO dto){
        PhoneNumber entity = phoneNumberRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado " + id));
        PhoneNumber phoneNumber = userConverter.updatePhoneNumber(dto, entity);
        return userConverter.toPhoneNumberDTO(phoneNumberRepository.save(phoneNumber));
    }

    public AddressDTO registerAddress(String token, AddressDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado " + email));
        Address address = userConverter.toAddressEntity(dto, user.getId());
        Address addressEntity = addressRepository.save(address);
        return userConverter.toAddressDTO(addressEntity);
    }

    public PhoneNumberDTO registerPhoneNumber(String token, PhoneNumberDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado " + email));
        PhoneNumber phoneNumber = userConverter.toPhoneNumberEntity(dto, user.getId());
        PhoneNumber phoneNumberEntity = phoneNumberRepository.save(phoneNumber);
        return userConverter.toPhoneNumberDTO(phoneNumberEntity);
    }
}
