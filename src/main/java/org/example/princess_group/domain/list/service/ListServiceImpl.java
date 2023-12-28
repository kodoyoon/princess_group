package org.example.princess_group.domain.list.service;

import org.springframework.stereotype.Service;

@Service
public class ListServiceImpl implements ListService {

    @Override
    public boolean isValidId(Long listId) {
        return false;
    }
}