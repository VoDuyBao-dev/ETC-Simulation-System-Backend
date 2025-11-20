package com.example.ETCSystem.services;

import com.example.ETCSystem.configuration.TokenValidator;
import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.*;
import com.example.ETCSystem.dto.response.AuthenticationResponse;
import com.example.ETCSystem.dto.response.IntrospectResponse;
import com.example.ETCSystem.entities.InvalidatedToken;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.InvalidatedTokenRepository;
import com.example.ETCSystem.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    InvalidatedTokenRepository invalidatedTokenRepository;
    TokenValidator tokenValidator;

    @NonFinal
    @Value("${jwt.secret}")
    protected String JWT_SECRET;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token;
        try {
            token = generateToken(user);
        } catch (KeyLengthException e) {
            log.error("KeyLengthException", e);
            throw new RuntimeException(e);
        }

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();



    }

    public AuthenticationResponse authenticateAdmin(AdminLoginRequest adminLoginRequest) {
        User user = userRepository.findByUsername(adminLoginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(adminLoginRequest.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token;
        try {
            token = generateToken(user);
        } catch (KeyLengthException e) {
            log.error("KeyLengthException", e);
            throw new RuntimeException(e);
        }

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();



    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        String token = introspectRequest.getToken();
        boolean isValid = true;

        try{
            tokenValidator.verifyToken(token, false);
        }catch (AppException e){
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        SignedJWT signedJWT = tokenValidator.verifyToken(refreshTokenRequest.getRefreshToken(), true);
        try{
            invalidateToken(signedJWT);
        }catch (Exception e){
            log.error("Lỗi khi thu hồi token để refresh token: ", e);
            throw new AppException(ErrorCode.SAVE_INVALIDATED_TOKEN_FAILED);
        }
        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String newToken = generateToken(user);
        return AuthenticationResponse.builder()
                .token(newToken)
                .authenticated(true)
                .build();

    }

    public void logout (LogoutRequest logoutRequest) throws ParseException, JOSEException {

        try{
            SignedJWT signToken = tokenValidator.verifyToken(logoutRequest.getToken(), false);
            invalidateToken(signToken);
        }catch (AppException e){
            log.info("token already expired");

        }

    }

//    đưa token hết hạn hoặc bị thu hồi vào bảng invalidated_tokens
    private void invalidateToken(SignedJWT signedJWT) throws ParseException {
        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }



    private String generateToken(User user) throws KeyLengthException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
//                ID của token để đánh dấu nó là duy nhất
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScopeClaim(user))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
//        kí
        try {
            jwsObject.sign(new MACSigner(JWT_SECRET.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Lỗi khi tạo token JWT: ", e);
            throw new RuntimeException(e);
        }


    }

    private String buildScopeClaim(User user) {
        StringJoiner scopeBuilder = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(scopeBuilder::add);
        }
        return scopeBuilder.toString();
    }

}
