package org.example.princess_group.domain.list.repository;

import java.util.List;
import org.example.princess_group.domain.list.entity.Lists;

public interface ListsOrderRepository {
    List<Lists> orderChange(Long number);
    long orderFind(Long id);
}
