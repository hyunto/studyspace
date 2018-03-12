package com.hyunto.startspringboot.part4finalproject.persistence;

import com.hyunto.startspringboot.part4finalproject.domain.QWebBoard;
import com.hyunto.startspringboot.part4finalproject.domain.WebBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>, QuerydslPredicateExecutor<WebBoard> {

    public default Predicate makePredicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        QWebBoard board = QWebBoard.webBoard;

        // bno > 0
        builder.and(board.bno.gt(0));

        // type if ~ else
        if (type == null) {
            return builder;
        }

        switch (type) {
            case "t":
                builder.and(board.title.like("%" + keyword + "%"));
                break;
            case "c":
                builder.and(board.content.like("%" + keyword + "%"));
                break;
            case "w":
                builder.and(board.writer.like("%" + keyword + "%"));
        }

        return builder;
    }

}
