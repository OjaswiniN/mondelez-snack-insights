/* (C) 2023 Mondelez Insights */
package com.mondelez.api;

import com.mondelez.api.dto.TokenResponse;
import com.mondelez.api.dto.UserLoginRequest;
import com.mondelez.api.dto.UserSignupRequest;
import com.mondelez.common.Roles;
import com.mondelez.config.JwtTokenUtil;
import com.mondelez.dao.RoleDao;
import com.mondelez.dao.UserDao;
import com.mondelez.dao.model.RoleRow;
import com.mondelez.dao.model.UserRow;
import com.mondelez.exception.Exceptions;
import com.mondelez.service.LocalUserDetailService;
import com.mondelez.service.mappers.MetaMapper;
import com.mondelez.service.model.UserMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{

  @Autowired private LocalUserDetailService localUserDetailService;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired DaoAuthenticationProvider authenticationProvider;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private RoleDao roleDao;
  @Autowired private UserDao userDao;

  @GetMapping("guest")
  public ResponseEntity<TokenResponse> guest() {
    // Get guest identity and set it on the spring security context
    UserDetails userDetails = localUserDetailService.loadUserByUsername("guest@mondelez.com");
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    // generate token for the particular guest user
    String token = jwtTokenUtil.generateOAuth2Token(authentication);
    return ResponseEntity.ok(new TokenResponse(token));
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody @Validated UserLoginRequest request) {
    UserDetails userDetails = localUserDetailService.loadUserByUsername(request.getUsername());

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(
            userDetails, request.getPassword(), userDetails.getAuthorities());
    authenticationProvider.authenticate(authentication);
    // generate token for the particular guest user
    String token = jwtTokenUtil.generateOAuth2Token(authentication);
    return ResponseEntity.ok(new TokenResponse(token));
  }

  @PostMapping("signup")
  public ResponseEntity<?> singup(@RequestBody @Validated UserSignupRequest request) {
    UserMeta userMeta =
        new UserMeta(
            request.getUsername(),
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            request.getLocation(),
            request.getDob());
    if (userDao.exists(userMeta.getEmail()))
      throw new Exceptions.BadRequestException("An account with the same email is already exists");
    UserRow userRow = MetaMapper.toUserRow(userMeta);
    RoleRow roleRow = roleDao.findBy(Roles.ROLE_USER.name());
    userDao.insert(userRow);
    roleDao.insert(userRow.getId(), roleRow.getId());
    UserDetails userDetails = localUserDetailService.loadUserByUsername(request.getEmail());
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(
            userDetails, request.getPassword(), userDetails.getAuthorities());
    authenticationProvider.authenticate(authentication);
    // generate token for the particular guest user
    String token = jwtTokenUtil.generateOAuth2Token(authentication);
    return ResponseEntity.ok(new TokenResponse(token));
  }

  @PostMapping("logout")
  public ResponseEntity<?> logout() {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok().build();
  }
}
