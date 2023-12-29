package org.example.princess_group.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.global.entity.BaseEntity;
@Getter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "author", nullable = false)
  private String author;
  @Column(name = "backgroundcolor", nullable = false)
  private String backgroundcolor;
  @Column(name = "contents", nullable = false )
  private String contents;


      public Board(String title, String author, String backgroundcolor, String contents) {
        this.title = title;
        this.author = author;
        this.backgroundcolor = backgroundcolor;
        this.contents = contents;
      }

      public void updateTitle(String title) {
        this.title = title;

      }
      public void updateAuthor(String author) {
        this.author = author;
  }

      public void updateBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
  }

      public void updateContents(String contents) {
        this.contents = contents;
  }
}
