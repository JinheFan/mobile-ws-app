package com.app.ws.mobileappws.vo;

import com.app.ws.mobileappws.form.Address;
import lombok.Data;

import java.util.List;

@Data
public class UserRest {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private List<Address> addressList;

}

