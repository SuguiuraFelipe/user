package com.suguiura.user.business.converter;

import com.suguiura.user.business.dto.AddressDTO;
import com.suguiura.user.business.dto.PhoneNumberDTO;
import com.suguiura.user.business.dto.UserDTO;
import com.suguiura.user.infrastructure.entity.Address;
import com.suguiura.user.infrastructure.entity.PhoneNumber;
import com.suguiura.user.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public UserEntity toUser(UserDTO userDTO) {
        return UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public List<Address> toListAddress(List<AddressDTO> addressDTOS){
        return addressDTOS.stream().map(this::toAddress).toList();
    }

    public Address toAddress(AddressDTO addressDTO){
        return  Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .complement(addressDTO.getComplement())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .build();
    }

    public List<PhoneNumber> toListPhoneNumber(List<PhoneNumberDTO> phoneNumberDTOS){
        return phoneNumberDTOS.stream().map(this::toPhoneNumber).toList();
    }

    public PhoneNumber toPhoneNumber(PhoneNumberDTO phoneNumberDTO){
        return PhoneNumber.builder()
                .number(phoneNumberDTO.getNumber())
                .build();
    }



    //Conversão de DTO para Entity
    public UserDTO toUserDTO(UserEntity userDTO) {
        return UserDTO.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public List<AddressDTO> toListAddressDTO(List<Address> addressDTOS){
        return addressDTOS.stream().map(this::toAddressDTO).toList();
    }

    public AddressDTO toAddressDTO(Address address){
        return  AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public List<PhoneNumberDTO> toListPhoneNumberDTO(List<PhoneNumber> phoneNumberDTOS){
        return phoneNumberDTOS.stream().map(this::toPhoneNumberDTO).toList();
    }

    public PhoneNumberDTO toPhoneNumberDTO(PhoneNumber phoneNumber){
        return PhoneNumberDTO.builder()
                .id(phoneNumber.getId())
                .number(phoneNumber.getNumber())
                .build();
    }

    public UserEntity updateUser(UserDTO userDTO, UserEntity userEntity){
        return UserEntity.builder()
                .name(userDTO.getName() != null ? userDTO.getName() : userEntity.getName())
                .id(userEntity.getId())
                .password(userDTO.getPassword() != null ? userDTO.getPassword() : userEntity.getPassword())
                .email(userDTO.getEmail() != null ? userDTO.getEmail() : userEntity.getEmail())
                .address(userEntity.getAddress())
                .phoneNumbers(userEntity.getPhoneNumbers())
                .build();
    }

    public Address updateAddress(AddressDTO dto, Address entity){
        return Address.builder()
                .id(entity.getId())
                .street(dto.getStreet() != null ? dto.getStreet() : entity.getStreet())
                .number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
                .complement(dto.getComplement() != null ? dto.getComplement() : entity.getComplement())
                .city(dto.getCity() != null ? dto.getCity() : entity.getCity())
                .state(dto.getState() != null ? dto.getState() : entity.getState())
                .zipCode(dto.getZipCode() != null ? dto.getZipCode() : entity.getZipCode())
                .build();
    }

    public PhoneNumber updatePhoneNumber(PhoneNumberDTO dto, PhoneNumber entity){
        return PhoneNumber.builder()
                .id(entity.getId())
                .number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
                .build();
    }

    public Address toAddressEntity(AddressDTO dto, Long idUser){
        return Address.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .user_id(idUser)
                .build();
    }

    public PhoneNumber toPhoneNumberEntity(PhoneNumberDTO dto, Long idUser){
        return PhoneNumber.builder()
                .number(dto.getNumber())
                .user_id(idUser)
                .build();
    }
}
