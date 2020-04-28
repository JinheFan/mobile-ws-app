package com.app.ws.mobileappws.repository;

import com.app.ws.mobileappws.MobileAppWsApplication;
import com.app.ws.mobileappws.data.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindSpecificColumns() {
        List<Object[]> objects = userRepository.findSpecificColumns();
        Object[] user = objects.get(0);
        String s = String.valueOf(user[0]);
        System.out.println(s);
    }

    @Test
    void testUpdateUserInfo() {
       userRepository.updateUserInfo("daya", "guo@qq.com");
        UserEntity userEntity = userRepository.findUsersByFirstName("daya");
        System.out.println(userEntity.getEmail());
    }
}
