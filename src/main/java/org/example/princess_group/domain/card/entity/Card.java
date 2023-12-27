package org.example.princess_group.domain.card.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseEntity {

    @Column(nullable = false)
    private Long listId;
    private String name;
    private String description;
    private String color;
    @Column(name = "orders")
    private Integer order;
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worker> workers = new ArrayList<>();

    @Builder
    private Card(Long listId, String name, String description, String color, Integer order,
        LocalDateTime deadline) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.color = color;
        this.order = order;
        this.deadline = deadline;
    }

    public void update(UpdateCardRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
        if (request.deadLine() != null) {
            this.deadline = request.deadLine();
        }
        if (request.color() != null) {
            this.color = request.color();
        }
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void removeWorker(Long userId) {
        workers.removeIf(worker -> worker.getId().equals(userId));
    }
}
