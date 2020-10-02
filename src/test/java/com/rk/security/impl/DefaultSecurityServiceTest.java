package com.rk.security.impl;

import com.rk.dao.UserDao;
import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultSecurityServiceTest {
    @Mock
    UserDao userDao;
    @InjectMocks
    DefaultSecurityService securityService;

    @Test
    void hash() {
        String actualHash = securityService.hash("admin","admin");
        String expectedHash ="-12480-20-962210181109-102-218323118732-73-124-107803855-5597-110-562722-125-4545105269101-493127-20-88-71-19-98-26-4106-72-14127-50-113119-60-3-1017468420-449123-12655-26";
        assertEquals(expectedHash,actualHash);
    }

    @Test
    void login() {
        User userAdmin = User.builder()
                .email("admin")
                .sole("admin")
                .password("-12480-20-962210181109-102-218323118732-73-124-107803855-5597-110-562722-125-4545105269101-493127-20-88-71-19-98-26-4106-72-14127-50-113119-60-3-1017468420-449123-12655-26").build();
        when(userDao.findByLogin("admin")).thenReturn(userAdmin);

        securityService.login("admin","admin");

        verify(userDao).findByLogin("admin");
        assertEquals(securityService.login(userAdmin.getSole(),"admin"),userAdmin);
    }
}