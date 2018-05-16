package ch.globaz.tmmas.authentificationservice.application.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Getter
public class JwtUser implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Integer id;
	private final String username;
	private final String firstname;
	private final String lastname;
	private final String password;
	private final String authtype;
	private final String email;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;
	private final boolean admin;
	private final Date lastPasswordResetDate;

	public JwtUser(Integer id, String username, String firstname, String lastname, String password, String email, String authtype, boolean admin, Collection<? extends GrantedAuthority> authorities, boolean enabled, Date lastPasswordResetDate) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
		this.authtype = authtype;
		this.authorities = authorities;
		this.admin = admin;
		this.enabled = enabled;
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
}