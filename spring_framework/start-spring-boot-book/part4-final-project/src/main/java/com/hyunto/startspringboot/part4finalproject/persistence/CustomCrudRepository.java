package com.hyunto.startspringboot.part4finalproject.persistence;

import com.hyunto.startspringboot.part4finalproject.domain.WebBoard;
import org.springframework.data.repository.CrudRepository;

public interface CustomCrudRepository extends CrudRepository<WebBoard, Long>, CustomWebBoard {

}
