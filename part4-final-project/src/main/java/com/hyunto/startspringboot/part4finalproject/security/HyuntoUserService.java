package com.hyunto.startspringboot.part4finalproject.security;

import com.hyunto.startspringboot.part4finalproject.persistence.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log
@Service
public class HyuntoUserService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		/* ~ p.440
		User sampleUser = new User(username, "{noop}1111", Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));
		return sampleUser;
		*/

		/*
		memberRepository.findById(username).ifPresent(member -> {
			log.info("" + member);
		});
		*/

		return memberRepository.findById(username).filter(member -> member != null).map(member -> new HyuntoSecurityUser(member)).get();
	}

}
