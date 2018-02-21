package com.hyunto.startspringboot.persistence;

import com.hyunto.startspringboot.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {
}
