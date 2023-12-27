package org.example.princess_group.domain.list.repository;

import java.util.List;
import org.example.princess_group.domain.list.entity.Lists;

public interface ListsOrderRepository {
    List<Lists> orderChangeDelete(Long number);
    List<Lists> orderChangeUpdate(Long number);
    long orderFind(Long id);
}
