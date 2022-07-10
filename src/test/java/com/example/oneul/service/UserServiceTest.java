package com.example.oneul.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.oneul.domain.user.dao.UserRepository;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.dto.LoginDTO;
import com.example.oneul.domain.user.service.UserServiceImpl;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    // private UserService userService;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    protected MockHttpSession httpSession;

    // static {
    //     GenericContainer redis = new GenericContainer("redis:3-alpine")
    //             .withExposedPorts(6379);
    //     redis.start();

    //     System.setProperty("spring.redis.host", redis.getContainerIpAddress());
    //     System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    // }
    
    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void signUpTest() throws Exception {
        // given
        LoginDTO loginDTO = createLoginDTO();
        UserEntity userEntity = loginDTO.toEntity();

        Long mockUserId = 1L;
        ReflectionTestUtils.setField(userEntity, "id", mockUserId);
        
        // mocking
        given(userRepository.findByUsername(userEntity.getUsername())).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(userEntity);
        given(userRepository.findById(mockUserId)).willReturn(Optional.ofNullable(userEntity));

        // when
        UserEntity user = userService.signUp(userEntity);

        // then
        UserEntity userFoundById = userRepository.findById(1L).get();
        assertEquals(user.getUsername(),userFoundById.getUsername());
    }

    private LoginDTO createLoginDTO() {
        return new LoginDTO("zzzinho", "password");
    }
}
