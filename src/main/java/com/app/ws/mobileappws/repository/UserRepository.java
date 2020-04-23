package com.app.ws.mobileappws.repository;

import com.app.ws.mobileappws.data.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUsersByEmail(String email);
    UserEntity findUsersByUserId(String userId);
    UserEntity deleteUsersByUserId(String userId);
}

