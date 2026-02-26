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
                .address(toListAddress((userDTO.getAddress())))
                .phoneNumbers(toListPhoneNumber((userDTO.getPhoneNumbers())))
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
                .address(toListAddressDTO((userDTO.getAddress())))
                .phoneNumbers(toListPhoneNumberDTO((userDTO.getPhoneNumbers())))
                .build();
    }

    public List<AddressDTO> toListAddressDTO(List<Address> addressDTOS){
        return addressDTOS.stream().map(this::toAddressDTO).toList();
    }

    public AddressDTO toAddressDTO(Address addressDTO){
        return  AddressDTO.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .complement(addressDTO.getComplement())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .build();
    }

    public List<PhoneNumberDTO> toListPhoneNumberDTO(List<PhoneNumber> phoneNumberDTOS){
        return phoneNumberDTOS.stream().map(this::toPhoneNumberDTO).toList();
    }

    public PhoneNumberDTO toPhoneNumberDTO(PhoneNumber phoneNumberDTO){
        return PhoneNumberDTO.builder()
                .number(phoneNumberDTO.getNumber())
                .build();
    }
}
