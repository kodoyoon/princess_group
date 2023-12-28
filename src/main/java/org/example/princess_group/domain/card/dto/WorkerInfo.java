package org.example.princess_group.domain.card.dto;

import lombok.Builder;
import org.example.princess_group.domain.card.entity.Worker;

@Builder
public record WorkerInfo(
    Long userId
) {
    public static WorkerInfo of(Worker worker){
        return new WorkerInfo(worker.getUserId());
    }
}
