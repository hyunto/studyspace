package com.hyunto.startspringboot.persistence;

import com.hyunto.startspringboot.domain.PDSBoard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {

    @Modifying
    @Query("UPDATE FROM PDSFile AS f SET f.pdsfile = ?2 WHERE f.fno = ?1")
    int updatePDSFile(Long fno, String newFileName);

    @Modifying
    @Query("DELETE FROM PDSFile AS f WHERE f.fno = ?1")
    int deletePDSFile(Long fno);

}
