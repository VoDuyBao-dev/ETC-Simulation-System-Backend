package com.example.ETCSystem.configuration;

import com.example.ETCSystem.dto.request.IntrospectRequest;
import com.example.ETCSystem.dto.response.IntrospectResponse;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final TokenValidator tokenValidator;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) {
        try {
            // Xác minh token hợp lệ, chưa bị thu hồi, chưa hết hạn
           tokenValidator.verifyToken(token);

            if (Objects.isNull(nimbusJwtDecoder)) {
                SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA512");
                nimbusJwtDecoder = NimbusJwtDecoder
                        .withSecretKey(secretKey)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            }
            return nimbusJwtDecoder.decode(token);

        } catch (ParseException | JOSEException e) {
                throw new JwtException("Invalid JWT: " + e.getMessage());
        }catch (AppException e) {
            log.warn("Token bị thu hồi hoặc hết hạn: {}", e.getErrorCode().getMessage());
            throw new JwtException("Invalid or revoked JWT: " + e.getErrorCode().getMessage());
        }

    }


}
