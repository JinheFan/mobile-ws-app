package com.app.ws.mobileappws.data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = -1354083534614600497L;

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String addressId;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String streetName;

    @Column
    private String postalCode;

    @Column
    private String type;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

}
