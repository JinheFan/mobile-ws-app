package com.app.ws.mobileappws.service.impl;

import com.app.ws.mobileappws.data.AddressEntity;
import com.app.ws.mobileappws.data.UserEntity;
import com.app.ws.mobileappws.exception.ErrorMessage;
import com.app.ws.mobileappws.exception.UserServiceException;
import com.app.ws.mobileappws.repository.UserRepository;
import com.app.ws.mobileappws.service.UserSerive;
import com.app.ws.mobileappws.shared.AddressDto;
import com.app.ws.mobileappws.shared.UserDto;
import com.app.ws.mobileappws.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSeriveImpl implements UserSerive {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity usersByEmail = userRepository.findUsersByEmail(userDto.getEmail());
        if(usersByEmail != null) {
            throw new UserServiceException(ErrorMessage.RECORD_ALREADY_EXISTS.getMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        String userId = utils.generateUserId(10);
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        //UserEnity set List<AddressEntity>
        List<AddressDto> addressDtoList = userDto.getAddressDtoList();
        List<AddressEntity> addressEntityList = addressDtoList.stream().map(addressDto -> modelMapper.map(addressDto, AddressEntity.class)).collect(Collectors.toList());
        for(AddressEntity addressEntity : addressEntityList) {
            addressEntity.setUserEntity(userEntity);
        }
        userEntity.setAddresses(addressEntityList);

        UserEntity savedUserEntity = userRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(savedUserEntity, UserDto.class);

        List<AddressEntity> addressEntities = savedUserEntity.getAddresses();
        List<AddressDto> addressDtos = addressEntities.stream().map(addressEntity -> modelMapper.map(addressEntity, AddressDto.class)).collect(Collectors.toList());
        returnValue.setAddressDtoList(addressDtos);

        return returnValue;
    }

    @Override
    public UserDto getUser(String userId) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = userRepository.findUsersByUserId(userId);
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> userDtoList = userEntities.stream().map(userEntity -> modelMapper.map(userEntity, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserEntity storedUserEntity = userRepository.findUsersByUserId(userId);
        storedUserEntity.setFirstName(userDto.getFirstName());
        storedUserEntity.setLastName(userDto.getLastName());
        storedUserEntity.setEmail(userDto.getEmail());
        UserEntity savedUserEntity = userRepository.save(storedUserEntity);
        ModelMapper modelMapper = new ModelMapper();
        UserDto dto = modelMapper.map(savedUserEntity, UserDto.class);
        return dto;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findUsersByUserId(userId);
        userRepository.delete(userEntity);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUsersByEmail(email);
        if(userEntity == null) {
            throw new UserServiceException(ErrorMessage.NO_RECORD_FOUND.getMessage());
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}

