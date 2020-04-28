package com.app.ws.mobileappws.data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "authorities")
@Data
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = 1064165061902143993L;

    public AuthorityEntity() {
    }

    public AuthorityEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles;

}
