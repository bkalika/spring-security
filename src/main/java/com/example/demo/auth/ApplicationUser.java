package com.example.demo.auth;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUser implements UserDetails {
	
	private final String password;
	private final String username;
	private final Set<? extends GrantedAuthority> getAuthorities;
	private final boolean isAccountNonExpired;
	private final boolean isAccountNonLocked;
	private final boolean isCredentialsNonExpired;
	private final boolean isEnabled;

	public ApplicationUser(String username,
							String password,
							Set<? extends GrantedAuthority> getAuthorities,
							boolean isAccountNonExpired,
							boolean isAccountNonLocked,
							boolean isCredentialsNonExpired,
							boolean isEnabled) {
		super();
		this.getAuthorities = getAuthorities;
		this.password = password;
		this.username = username;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
