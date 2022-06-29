package com.example.oneul.domain.user.api;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.dto.LoginDTO;
import com.example.oneul.domain.user.dto.SignUpDTO;
import com.example.oneul.domain.user.service.UserService;
import com.example.oneul.global.common.Code;
import com.example.oneul.global.common.Response;

@RestController
@RequestMapping(value = "/user")
public class UserApi {
    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(UserApi.class);

    public UserApi(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value="/signup/", method=RequestMethod.POST)
    public ResponseEntity<Response> signUp(@RequestBody SignUpDTO signUpDTO) {
        // TODO: password1, password2 같은지 validator로 검사
        UserEntity user = userService.signUp(signUpDTO.toEntity());
        log.info("signUp: " + user.toString());
        return ResponseEntity.ok(new Response(Code.SUCESS, "유저 생성 성공"));
    }

    @RequestMapping(value="/login/", method=RequestMethod.POST)
    public ResponseEntity<Response> login(HttpSession httpSession, @RequestBody LoginDTO loginDTO) {
        UserEntity user = userService.login(loginDTO.toEntity(), httpSession);
        log.info("login: " + user.toString());
        return ResponseEntity.ok(new Response(Code.SUCESS, "로그인 성공"));
    }
    
    @RequestMapping(value="/logout/", method=RequestMethod.POST)
    public ResponseEntity<Response> logout(HttpSession httpSession) {
        log.info("logout: " + httpSession.toString());
        userService.logout(httpSession);
        return ResponseEntity.ok(new Response(Code.SUCESS, "로그아웃 성공"));
    }
}
