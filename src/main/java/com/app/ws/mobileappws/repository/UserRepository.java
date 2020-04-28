package com.app.ws.mobileappws.repository;

import com.app.ws.mobileappws.data.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findUsersByEmail(String email);
    UserEntity findUsersByUserId(String userId);
    UserEntity findUsersByFirstName(String firstName);

    @Query(value = "select * from users where users.user_id = :userId", nativeQuery = true)
    UserEntity QueryUserEntityByUserId(String userId);

    @Query(value = "select * from users", nativeQuery = true)
    List<UserEntity> getPage(Pageable pageable);

    @Query(value = "select first_name, last_name from users", nativeQuery = true)
    List<Object[]> findSpecificColumns();

    @Transactional
    @Modifying
    @Query(value = "update users set users.email = :email where users.first_name = :firstName", nativeQuery = true)
    void updateUserInfo(String firstName, String email);

}


