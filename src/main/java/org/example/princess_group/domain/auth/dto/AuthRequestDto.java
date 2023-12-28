package org.example.princess_group.domain.auth.dto;

import lombok.Getter;
import org.example.princess_group.domain.auth.entity.Authority;

@Getter
public class AuthRequestDto {
    private Long userId;
    private Authority authority;
    private String type;
    private Long domainId;
}
