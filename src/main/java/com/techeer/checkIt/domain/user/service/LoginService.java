package com.techeer.checkIt.domain.user.service;

import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.exception.InValidPasswordException;
import com.techeer.checkIt.domain.user.exception.UserNotFoundException;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import com.techeer.checkIt.global.jwt.JwtToken;
import com.techeer.checkIt.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public JwtToken login(String username, String password) {
        // 로그인 시  일치하면 유저 정보 가져오기
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new InValidPasswordException();

        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwt = jwtTokenProvider.generateToken(authentication);

        //redis에 username을 key로 저장
        redisTemplate.opsForValue().set("ID: " + user.getUsername(), jwt.getRefreshToken(), jwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return jwt;
    }

    public void logout(JwtToken jwtToken) {
//        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken.getAccessToken());
//        if (redisTemplate.opsForValue().get("ID: " + authentication.getName()) != null) {
//            redisTemplate.delete("ID: " + authentication.getName()); //Token 삭제
//        }
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!jwtTokenProvider.validateToken(jwtToken.getAccessToken())){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User email을 가져온다
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken.getAccessToken());

        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("ID: "+ authentication.getName()) != null){
            // Refresh Token을 삭제
            redisTemplate.delete("ID: " + authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        Long expiration = jwtTokenProvider.getExpiration(jwtToken.getAccessToken());
        redisTemplate.opsForValue().set(jwtToken.getAccessToken(),"logout", expiration, TimeUnit.MILLISECONDS);
    }
}
