package com.hyunto.startspringboot.part4.security;

import com.hyunto.startspringboot.part4.domain.Member;
import com.hyunto.startspringboot.part4.domain.MemberRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class HyuntoSecurityUser extends User {

	private static final String ROLE_PREFIX = "ROLE_";

	private Member member;

	public HyuntoSecurityUser(Member member) {
		// Before password encoder
//		super(member.getUid(), "{noop}" + member.getUpw(), makeGrantedAuthority(member.getRoles()));

		// After password encoder
		super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));

		this.member = member;
	}

	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
		List<GrantedAuthority> list = new ArrayList<>();

		roles.forEach(role -> list.add(
				new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())
		));

		return list;
	}

	public HyuntoSecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public HyuntoSecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

}
