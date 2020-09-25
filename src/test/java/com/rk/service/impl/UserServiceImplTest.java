package com.rk.service.impl;

import com.rk.dao.jdbc.JdbcUserDao;
import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @Mock
    JdbcUserDao userDao;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void save() {
        User userActual = User.builder().id(new LongId<>(1L))
                .email("test-ADMIN").name("test-ADMIN")
                .password("test-ADMIN").role(UserRole.ADMIN).build();
        userService.save(userActual);
        verify(userDao, atLeast(1)).save(userActual);
    }
}