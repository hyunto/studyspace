package com.hyunto.startspringboot.part4;

import com.hyunto.startspringboot.part4.domain.Member;
import com.hyunto.startspringboot.part4.domain.MemberRole;
import com.hyunto.startspringboot.part4.persistence.MemberRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class MemberTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testInsert() {
		for (int i = 0; i <= 100; i++) {
			Member member = new Member();
			member.setUid("user" + i);
			member.setUpw("pw" + i);
			member.setUname("사용자" + i);

			MemberRole role = new MemberRole();

			if (i<= 80) {
				role.setRoleName("BASIC");
			} else if (i <= 90) {
				role.setRoleName("MANAGER");
			} else {
				role.setRoleName("ADMIN");
			}

			member.setRoles(Arrays.asList(role));

			memberRepository.save(member);
		}
	}

	@Test
	public void testRead() {
		Optional<Member> result = memberRepository.findById("user85");
		result.ifPresent(member -> {
			log.info("member : " + member);
		});
	}

	@Test
	public void testUpdateOldMember() {
		List<String> ids = new ArrayList<>();

		for (int i = 0; i<= 100; i++) {
			ids.add("user" + i);
		}

		memberRepository.findAllById(ids).forEach(member -> {
			member.setUpw(passwordEncoder.encode(member.getUpw()));
			memberRepository.save(member);
		});
	}

}
