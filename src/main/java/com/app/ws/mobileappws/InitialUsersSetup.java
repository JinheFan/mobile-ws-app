package com.app.ws.mobileappws;

import com.app.ws.mobileappws.data.AuthorityEntity;
import com.app.ws.mobileappws.data.RoleEntity;
import com.app.ws.mobileappws.data.UserEntity;
import com.app.ws.mobileappws.repository.AuthorityRepository;
import com.app.ws.mobileappws.repository.RoleRepository;
import com.app.ws.mobileappws.repository.UserRepository;
import com.app.ws.mobileappws.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
@Transactional
public class InitialUsersSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    public void onApplicatoinEvent(ApplicationReadyEvent event) {
        System.out.println("From application ready event...");
        AuthorityEntity read_authority = createAuthortity("READ_AUTHORITY");
        AuthorityEntity write_authority = createAuthortity("WRITE_AUTHORITY");
        AuthorityEntity delete_authority = createAuthortity("DELETE_AUTHORITY");

        RoleEntity role_user = createRole("ROLE_USER", Arrays.asList(read_authority, write_authority));
        RoleEntity role_admin = createRole("ROLE_ADMIN", Arrays.asList(read_authority, write_authority, delete_authority));
/*
        if(role_admin == null) {
            return;
        }

        UserEntity adminUser = new UserEntity();
        adminUser.setFirstName("Jinhe");
        adminUser.setLastName("Fan");
        adminUser.setEmail("fanjinhe321@gmail.com");
        adminUser.setUserId(utils.generateUserId(10));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("fjh123"));
        adminUser.setRoles(Arrays.asList(role_admin));
        userRepository.save(adminUser);

 */
    }

    private AuthorityEntity createAuthortity(String name) {

        AuthorityEntity authorityEntity = authorityRepository.findAuthoritiesByName(name);
        if(authorityEntity == null) {
            authorityEntity = new AuthorityEntity(name);
            authorityRepository.save(authorityEntity);
        }
        return authorityEntity;
    }

    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        RoleEntity role = roleRepository.findRolesByName(name);
        if(role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}

