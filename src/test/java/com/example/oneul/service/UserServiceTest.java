package com.example.oneul.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.oneul.domain.user.dao.UserRepository;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.dto.LoginDTO;
import com.example.oneul.domain.user.service.UserService;
import com.example.oneul.domain.user.service.UserServiceImpl;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService userService;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    protected MockHttpSession httpSession;
    
    @BeforeEach
    public void setUp() throws Exception {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        httpSession = new MockHttpSession();
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

    @Test
    public void loginTest() throws Exception {
        // given
        LoginDTO loginDTO = createLoginDTO();
        UserEntity loginUser = loginDTO.toEntity();

        // mocking 
        Long mockUserId = 1L;
        UserEntity userEntity = mockUser(mockUserId);
        given(userRepository.findByUsername(loginUser.getUsername())).willReturn(Optional.ofNullable(userEntity));
        given(passwordEncoder.matches(any(), eq(userEntity.getPassword()))).willReturn(true);

        // when
        UserEntity user = userService.login(userEntity, httpSession);

        // then
        assertNotEquals(httpSession.getAttribute("user"), null);
        assertEquals((UserEntity)httpSession.getAttribute("user"), user);
    }   

    @Test
    public void logoutTest() throws Exception {
        //given
        Long mockUserId = 1L;
        UserEntity userEntity = mockUser(mockUserId);
        httpSession.setAttribute("user", userEntity);

        // when
        userService.logout(httpSession);

        // then
        assertEquals(httpSession.getAttribute("user"), null);
    }

    private LoginDTO createLoginDTO() {
        return new LoginDTO("zzzinho", "password");
    }

    private UserEntity mockUser(Long id){
        return UserEntity.builder().id(id)
                                    .username("zzzinho")
                                    .password(passwordEncoder.encode("password"))
                                    .build();
    }
}
