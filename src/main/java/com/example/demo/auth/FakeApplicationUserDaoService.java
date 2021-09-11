package com.example.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDAO {
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers()
				.stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}
	
	private List<ApplicationUser> getApplicationUsers() {
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				new ApplicationUser(
						"anna",
						passwordEncoder.encode("password"),
						ApplicationUserRole.STUDENT.getGrantedAuthorities(),
						true,
						true,
						true,
						true
				),
				new ApplicationUser(
						"linda",
						passwordEncoder.encode("password"),
						ApplicationUserRole.ADMIN.getGrantedAuthorities(),
						true,
						true,
						true,
						true
				),
				new ApplicationUser(
						"tom",
						passwordEncoder.encode("password"),
						ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
						true,
						true,
						true,
						true
				)
				);
		return applicationUsers;
	}

}
