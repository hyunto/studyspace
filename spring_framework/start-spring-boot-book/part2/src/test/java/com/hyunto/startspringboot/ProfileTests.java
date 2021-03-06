package com.hyunto.startspringboot;

import com.hyunto.startspringboot.domain.Member;
import com.hyunto.startspringboot.domain.Profile;
import com.hyunto.startspringboot.persistence.MemberRepository;
import com.hyunto.startspringboot.persistence.ProfileRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class ProfileTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Test
	public void testInsertMembers() {
		IntStream.range(1, 101).forEach(i -> {
			Member member = new Member();
			member.setUid("user" + i);
			member.setUpw("pw" + i);
			member.setUname("사용자" + i);

			memberRepository.save(member);
		});
	}

	@Test
	public void testInsertProfile() {
		Member member = new Member();
		member.setUid("user1");

		for (int i = 1; i < 5; i++) {
			Profile profile = new Profile();
			profile.setFname("face" + i + ".log");

			if (i == 3) {
				profile.setCurrent(true);
			}

			profile.setMember(member);

			profileRepository.save(profile);
		}

	}
}
