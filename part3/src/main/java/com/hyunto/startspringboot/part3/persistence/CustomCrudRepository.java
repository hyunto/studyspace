package com.hyunto.startspringboot.part3.persistence;

import com.hyunto.startspringboot.part3.domain.WebBoard;
import org.springframework.data.repository.CrudRepository;

public interface CustomCrudRepository extends CrudRepository<WebBoard, Long>, CustomWebBoard {

}
