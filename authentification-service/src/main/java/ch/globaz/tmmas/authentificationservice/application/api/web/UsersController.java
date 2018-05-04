package ch.globaz.tmmas.authentificationservice.application.api.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

import static ch.globaz.tmmas.authentificationservice.application.filter.SecurityConstants.HEADER_STRING;
import static ch.globaz.tmmas.authentificationservice.application.filter.SecurityConstants.SECRET;
import static ch.globaz.tmmas.authentificationservice.application.filter.SecurityConstants.TOKEN_PREFIX;
import static java.time.ZoneOffset.UTC;

@RestController
@RequestMapping(path = "/login")
public class UsersController {

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity login(){

		Date expiration = Date.from(LocalDateTime.now().plusHours(2).toInstant(UTC));

		String user = Jwts.builder()
				.setSubject("test")
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512,SECRET.getBytes())
				.setIssuer("ch.globaz.tmmas")
				.compact();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HEADER_STRING,TOKEN_PREFIX + user);

		return new ResponseEntity(user, headers, HttpStatus.OK);
	}
}
