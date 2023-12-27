package org.example.princess_group.domain.list.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.service.ListsService;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class ListsController {
    private final ListsService listsService;

    @GetMapping("")
    public ResponseEntity<?> getLists(@PathVariable(name = "boardId") Long id) {
        List<ReadListsResponse> response = listsService.getlists(id);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("성공했습니다.")
                .data(response)
                .build()
        );
    }
}
