package com.hyunto.startspringboot;

import com.hyunto.startspringboot.persistence.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFetchJoin1() {
        List<Object[]> results = memberRepository.getMemberWithProfileCount("user1");
        results.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void testFetchJoin2() {
        List<Object[]> results = memberRepository.getMemberWithProfile("user1");
        results.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }
}
