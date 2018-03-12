package com.hyunto.startspringboot.part4finalproject.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomWebBoard {

	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page);

}
