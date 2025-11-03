package com.example.ETCSystem.configuration;

import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.InvalidatedTokenRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class TokenValidator {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public TokenValidator(InvalidatedTokenRepository invalidatedTokenRepository) {
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    public SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());

        boolean verified = signedJWT.verify(verifier);
        Date expiry = signedJWT.getJWTClaimsSet().getExpirationTime();

        if (!verified || expiry.before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Kiểm tra xem token đã bị thu hồi chưa
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
