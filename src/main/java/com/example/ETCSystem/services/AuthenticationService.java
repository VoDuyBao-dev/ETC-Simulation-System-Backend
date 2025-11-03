package com.example.ETCSystem.services;

import com.example.ETCSystem.configuration.TokenValidator;
import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AuthenticationRequest;
import com.example.ETCSystem.dto.request.IntrospectRequest;
import com.example.ETCSystem.dto.request.LogoutRequest;
import com.example.ETCSystem.dto.request.UserRequest;
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
    String jwtSecret;


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

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        String token = introspectRequest.getToken();
        boolean isValid = true;

        try{
            tokenValidator.verifyToken(token);
        }catch (AppException e){
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    public void logout (LogoutRequest logoutRequest) throws ParseException, JOSEException {
        SignedJWT signToken = tokenValidator.verifyToken(logoutRequest.getToken());

        String jti = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

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
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
//                ID của token để đánh dấu nó là duy nhất
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScopeClaim(user))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
//        kí
        try {
            jwsObject.sign(new MACSigner(jwtSecret.getBytes()));
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
