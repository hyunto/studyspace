package com.hyunto.startspringboot.part4.persistence;

import com.hyunto.startspringboot.part4.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

}
