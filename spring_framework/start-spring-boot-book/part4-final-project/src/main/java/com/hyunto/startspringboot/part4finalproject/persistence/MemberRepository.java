package com.hyunto.startspringboot.part4finalproject.persistence;

import com.hyunto.startspringboot.part4finalproject.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

}
