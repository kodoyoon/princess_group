package org.example.princess_group.domain.list.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.example.princess_group.domain.list.entity.Lists;
import org.example.princess_group.suppport.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ListsRepositoryTest extends RepositoryTest {

    @Autowired
    ListsRepository listsRepository;

    @BeforeEach
    void setup(){
        Lists lists1 = Lists.builder().boardId(1L).name("첫번째").order(1L).build();
        Lists lists2 = Lists.builder().boardId(1L).name("두번째").order(2L).build();
        Lists lists3 = Lists.builder().boardId(2L).name("첫번째").order(1L).build();
        Lists lists4 = Lists.builder().boardId(2L).name("두번째").order(2L).build();
        listsRepository.save(lists1);
        listsRepository.save(lists2);
        listsRepository.save(lists3);
        listsRepository.save(lists4);
    }

    @Test
    void findAllByBoardId(){
        List<Lists> lists = listsRepository.findAllByBoardId(1L);
        assertThat(lists.get(0).getBoardId()).isEqualTo(1L);
        assertThat(lists.get(0).getName()).isEqualTo("첫번째");
        assertThat(lists.get(0).getOrder()).isEqualTo(1L);
    }

}