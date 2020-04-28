package com.app.ws.mobileappws.controller;

import com.app.ws.mobileappws.form.Address;
import com.app.ws.mobileappws.form.UserRegisterModel;
import com.app.ws.mobileappws.form.UserUpdateModel;
import com.app.ws.mobileappws.service.UserSerive;
import com.app.ws.mobileappws.shared.AddressDto;
import com.app.ws.mobileappws.shared.UserDto;
import com.app.ws.mobileappws.vo.UserRest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserSerive userSerive;

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "${authorizationHeader.desc}", paramType = "header")
    })
    public UserRest getUser(@PathVariable String userId) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = userSerive.getUser(userId);
        UserRest userRest = modelMapper.map(userDto, UserRest.class);
        return userRest;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getUsers() {
        List<UserDto> userDtoList = userSerive.getUsers();
        ModelMapper modelMapper = new ModelMapper();
        List<UserRest> userRestList = userDtoList.stream().map(userDto -> modelMapper.map(userDto, UserRest.class)).collect(Collectors.toList());
        return userRestList;
    }

    @GetMapping(value = "/query/{userId}")
    public UserRest QueryUserEntityByUserId(@PathVariable String userId){
        UserDto userDto = userSerive.QueryUserEntityByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        UserRest userRest = modelMapper.map(userDto, UserRest.class);
        return userRest;
    }

    @GetMapping
    public List<UserRest> getPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "1") int limit) {
        List<UserDto> userDtoList = userSerive.getPage(page, limit);
        ModelMapper modelMapper = new ModelMapper();
        List<UserRest> userRestList = userDtoList.stream().map(userDto -> modelMapper.map(userDto, UserRest.class)).collect(Collectors.toList());
        return userRestList;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@Valid @RequestBody UserRegisterModel userRegisterModel) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userRegisterModel, UserDto.class);

        List<Address> addressList = userRegisterModel.getAddressList();
        List<AddressDto> addressDtoList = addressList.stream().map(address -> modelMapper.map(address, AddressDto.class)).collect(Collectors.toList());
        userDto.setAddressDtoList(addressDtoList);

        UserDto createdUserDto = userSerive.createUser(userDto);
        List<AddressDto> addressDtos = createdUserDto.getAddressDtoList();
        List<Address> addresses = addressDtos.stream().map(addressDto -> modelMapper.map(addressDto, Address.class)).collect(Collectors.toList());
        UserRest rest = modelMapper.map(createdUserDto, UserRest.class);
        rest.setAddressList(addresses);
        return rest;
    }

    @PutMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String userId, @RequestBody UserUpdateModel userUpdateModel) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userUpdateModel, UserDto.class);
        UserDto updateUser = userSerive.updateUser(userId, userDto);
        UserRest userRest = modelMapper.map(updateUser, UserRest.class);
        return userRest;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{userId}")
    public String deleteUser(@PathVariable String userId) {
        userSerive.deleteUser(userId);
        return "Delete Success";
    }
}

