package com.raiadrogasil.backommerce.componente.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	private static final String SECRET = "XXXXX";
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";
	private static final String ROLES = "roles";

	private static final String EXP = "exp";
	private static final String BEARER = "Bearer ";
	private static final String AUTHORIZATION = "Authorization";
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Date dataValidadeToken;
	private static Jwt jwt;

	public static String getJWT(Authentication authentication, Date dataTokenExpiration) {
		List<String> grants = new ArrayList<>();
		authentication.getAuthorities().forEach(ga -> grants.add("ROLE_" + ga.getAuthority()));
		String JWT = Jwts.builder().setSubject(authentication.getName()).claim(ROLES, grants)
				.setExpiration(dataTokenExpiration)
				//.setExpiration(new Date(tokenExpiration))

				.signWith(SignatureAlgorithm.HS512, "RDSecret").compact();

		return JWT;
	}

	//public static void addAuthentication(HttpServletResponse response, Authentication autenticate, long tokenExpiration) {
		//response.addHeader("Authorization", "Bearer " + getJWT(autenticate, tokenExpiration));
	//}

	@SuppressWarnings("unchecked")
	public static Authentication getAuthentication(HttpServletRequest request) throws ExpiredJwtException {
		String token = request.getHeader(HEADER_STRING);

		if (isTokenBearer(token)) {
			// tenta fazer o parse do token.

			Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

			String user = body.getSubject();

			List<String> stringRoles = (List<String>) body.get(ROLES);
			List<GrantedAuthority> authorities = new ArrayList<>();
			stringRoles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) : null;
		}
		return null;
	}

	private static boolean isTokenBearer(String token) {
		return (token != null && token.startsWith(TOKEN_PREFIX)) ? true : false;
	}

	public static boolean isTokenJWTExpirado(HttpServletRequest request) {

		String token = getTokenStringDaRequest(request);
		if (token != null) {

			if (isFormatoTokenValido(token)) {

				Map<String, Object> tokenHashMap = tentaExtrairToken(token);

				if (isTokenExpirado(tokenHashMap)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	private static String getTokenStringDaRequest(HttpServletRequest request) {

		String header = request.getHeader(AUTHORIZATION);
		if (header == null || !header.startsWith(BEARER)) {
			return null;
		}

		String token = header.substring(BEARER.length());
		return token;
	}

	private static boolean isFormatoTokenValido(String token) {
		jwt = JwtHelper.decode(token);
		if (jwt.getClaims() == null) {
			return false;
		} else {
			return true;
		}
	}

	private static Map<String, Object> tentaExtrairToken(String token) {

		Map<String, Object> tokenHashMap = null;

		try {
			tokenHashMap = objectMapper.readValue(jwt.getClaims(), new TypeReference<HashMap<String, Object>>() {
			});
		} catch (Exception erro) {
			jogaExcecao();
		}

		if (tokenHashMap == null) {
			jogaExcecao();
		}

		return tokenHashMap;
	}

	private static boolean isTokenExpirado(Map<String, Object> tokenHashMap) {

		Long timestamp = (long) ((Integer) tokenHashMap.get(EXP)) * 1000;

		Date expirationTime = new Date(timestamp);

		dataValidadeToken = expirationTime;

		Date now = new Date();
		if (now.after(expirationTime)) {
			return true;
		} else {
			return false;
		}
	}

	public static Date getDataValidadeToken() {
		return dataValidadeToken;
	}

	private static void jogaExcecao() {
		throw new AuthenticationServiceException("Erro ao interpretar o token");
	}

}
