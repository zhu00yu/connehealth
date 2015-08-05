package com.connehealth.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;


public class TokenUtils
{

    public static final String MAGIC_KEY = "obfuscate";


    public static String createToken(UserDetails userDetails)
    {
		/* Expires in one hour */
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;

        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(userDetails.getUsername());
        tokenBuilder.append(":");
        tokenBuilder.append(expires);
        tokenBuilder.append(":");
        tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));

        return tokenBuilder.toString();
    }


    public static String computeSignature(UserDetails userDetails, long expires)
    {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername());
        signatureBuilder.append(":");
        signatureBuilder.append(expires);
        signatureBuilder.append(":");
        signatureBuilder.append(userDetails.getPassword());
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }


    public static String getUserNameFromToken(String authToken)
    {
        if (null == authToken) {
            return null;
        }

        String[] parts = authToken.split(":");
        return parts[0];
    }


    public static String getUserNameFromToken(HttpServletRequest httpRequest)
    {
        String authToken = getAuthToken(httpRequest);
        return getUserNameFromToken(authToken);
    }


    public static String getUserNameFromToken(HttpHeaders headers)
    {
        String authToken = getAuthToken(headers);
        return getUserNameFromToken(authToken);
    }


    public static boolean validateToken(String authToken, UserDetails userDetails)
    {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];

        if (expires < System.currentTimeMillis()) {
            return false;
        }

        return signature.equals(TokenUtils.computeSignature(userDetails, expires));
    }


    public static String getAuthToken(HttpServletRequest httpRequest, String headerKey){
        String authToken = httpRequest.getHeader(headerKey == null ? "" : headerKey);
        return authToken;
    }
    public static String getAuthToken(HttpServletRequest httpRequest){
        String authToken = getAuthToken(httpRequest, "X-Auth-Token");
		/* If token not found get it from request parameter */
        if (authToken == null) {
            authToken = getAuthToken(httpRequest, "token");
        }

        return authToken;
    }
    public static String getAuthToken(HttpHeaders headers){
        String authToken = getAuthToken(headers, "X-Auth-Token");

		/* If token not found get it from request parameter */
        if (authToken == null) {
            authToken = getAuthToken(headers, "token");
        }

        return authToken;
    }
    public static String getAuthToken(HttpHeaders headers, String headerKey){
        String authToken = headers.getHeaderString(headerKey == null ? "" : headerKey);
        return authToken;
    }

}