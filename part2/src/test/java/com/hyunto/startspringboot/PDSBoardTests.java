package com.hyunto.startspringboot;

import com.hyunto.startspringboot.domain.PDSBoard;
import com.hyunto.startspringboot.domain.PDSFile;
import com.hyunto.startspringboot.persistence.PDSBoardRepository;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class PDSBoardTests {

    @Autowired
    private PDSBoardRepository pdsBoardRepository;

    @Test
    public void testInsertPDS() {
        PDSBoard board = new PDSBoard();
        board.setPname("DOCUMENT 1 - 2");

        PDSFile file1 = new PDSFile();
        file1.setPdsfile("file1.doc");

        PDSFile file2 = new PDSFile();
        file2.setPdsfile("file2.doc");

        board.setFiles(Arrays.asList(file1, file2));

        log.info("try to save pds");

        pdsBoardRepository.save(board);
    }

    @Transactional
    @Test
    public void testUpdateFileName1() {
        Long fno = 1L;
        String newName = "updateFile1.doc";

        int count = pdsBoardRepository.updatePDSFile(fno, newName);
        log.info("update count : " + count);
    }

    @Transactional
    @Test
    public void testUpdateFileName2() {
        String newName = "updateFile2.doc";

        Optional<PDSBoard> result = pdsBoardRepository.findById(2L);

        result.ifPresent(pdsBoard -> {
            log.info("데이터가 존재하므로 update 시도");
            PDSFile target = new PDSFile();
            target.setFno(2L);
            target.setPdsfile(newName);

            int idx = pdsBoard.getFiles().indexOf(target);

            if (idx > -1) {
                List<PDSFile> list = pdsBoard.getFiles();
                list.remove(idx);
                list.add(target);
                pdsBoard.setFiles(list);
            }

            pdsBoardRepository.save(pdsBoard);
        });
    }

    @Transactional
    @Test
    public void testDeletePDSFile() {
        Long fno = 2L;

        int count = pdsBoardRepository.deletePDSFile(fno);

        log.info("DELETE PDSFILE : " + count);
    }

    @Test
    public void insertDummies() {
        List<PDSBoard> list = new ArrayList<>();

        IntStream.range(1, 100).forEach(i -> {
            PDSBoard pds = new PDSBoard();
            pds.setPname("자료 " + i);

            PDSFile file1 = new PDSFile();
            file1.setPdsfile("file1.doc");

            PDSFile file2 = new PDSFile();
            file2.setPdsfile("file2.doc");

            pds.setFiles(Arrays.asList(file1, file2));

            log.info("try to save pds");

            list.add(pds);
        });

        pdsBoardRepository.saveAll(list);
    }

}
