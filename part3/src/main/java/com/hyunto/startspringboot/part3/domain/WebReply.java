package com.hyunto.startspringboot.part3.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "tbl_webreplies")
@EqualsAndHashCode(of = "rno")
@ToString
public class WebReply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;

	private String replyText;

	private String replyer;

	@CreationTimestamp
	private Timestamp regdate;

	@UpdateTimestamp
	private Timestamp updatedate;

}
