package com.hyunto.startspringboot.part2thymeleaf.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class MemberVO {

	private int mno;

	private String mid;

	private String mpw;

	private String mname;

	private Timestamp regdate;

}
