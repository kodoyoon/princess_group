package org.example.princess_group.domain.board.dto;
import lombok.Getter;

@Getter
public class UpdateBoardRequest {
  private String title;
  private String author;
  private String backgroundcolor;
  private String contents;
}
