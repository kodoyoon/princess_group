package org.example.princess_group.domain.list.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.request.OrderChangeListsRequest;
import org.example.princess_group.domain.list.dto.response.CreateListsResponse;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.service.ListsService;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class ListsController {
    private final ListsService listsService;

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getLists(@PathVariable(name = "boardId") Long id) {
        List<ReadListsResponse> response = listsService.getlists(id);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("리스트 조회에 성공했습니다.")
                .data(response)
                .build()
        );
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<?> createLists(@PathVariable(name = "boardId") Long id,
        CreateListsRequest request) {
        CreateListsResponse response = listsService.createLists(id,request);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("리스트 저장에 성공했습니다.")
                .data(response)
                .build()
        );
    }

    @PatchMapping("/{listId}")
    public ResponseEntity<?> updateLists(@PathVariable(name = "listId") Long id,
        CreateListsRequest request) {
        CreateListsResponse response = listsService.updateLists(id,request);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("리스트 수정에 성공했습니다.")
                .data(response)
                .build()
        );
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<?> deleteLists(@PathVariable(name = "listId") Long id) {
        listsService.deleteLists(id);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("리스트 삭제에 성공했습니다.")
                .build()
        );
    }

    @PutMapping("/{listId}")
    public ResponseEntity<?> orderChangeLists(@PathVariable(name = "listId") Long id, OrderChangeListsRequest request) {
        List<ReadListsResponse> response = listsService.orderChangeLists(id,request);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("순서 변경에 성공했습니다.")
                .data(response)
                .build()
        );
    }
}
