package com.hyunto.startspringboot.part3.persistence;

import com.hyunto.startspringboot.part3.domain.QWebBoard;
import com.hyunto.startspringboot.part3.domain.QWebReply;
import com.hyunto.startspringboot.part3.domain.WebBoard;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

@Log
public class CustomCrudRepositoryImpl extends QuerydslRepositorySupport implements CustomWebBoard {

	public CustomCrudRepositoryImpl() {
		super(WebBoard.class);
	}

	@Override
	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page) {

		log.info("========================================");
		log.info("TYPE : " + type);
		log.info("KEYWORD : " + keyword);
		log.info("PAGE : " + page);
		log.info("========================================");

		QWebBoard qWebBoard = QWebBoard.webBoard;
		QWebReply qWebReply = QWebReply.webReply;

		JPQLQuery<WebBoard> query = from(qWebBoard);

		JPQLQuery<Tuple> tuple = query.select(qWebBoard.bno, qWebBoard.title, qWebReply.count(), qWebBoard.writer, qWebBoard.regdate);

		tuple.leftJoin(qWebReply);
		tuple.on(qWebBoard.bno.eq(qWebReply.board.bno));

		tuple.where(qWebBoard.bno.gt(0L));

		if (type != null) {
			switch (type.toLowerCase()) {
				case "t":
					tuple.where(qWebBoard.title.like("%" + keyword + "%"));
					break;
				case "c":
					tuple.where(qWebBoard.content.like("%" + keyword + "%"));
					break;
				case "w":
					tuple.where(qWebBoard.writer.like("%" + keyword + "%"));
					break;
			}
		}

		tuple.groupBy(qWebBoard.bno);
		tuple.orderBy(qWebBoard.bno.desc());

		tuple.offset(page.getOffset());
		tuple.limit(page.getPageSize());

		List<Tuple> list = tuple.fetch();

		List<Object[]> resultList = new ArrayList<>();
		list.forEach(t -> {
			resultList.add(t.toArray());
		});

		long total = tuple.fetchCount();

		return new PageImpl<>(resultList, page, total);
	}

}
