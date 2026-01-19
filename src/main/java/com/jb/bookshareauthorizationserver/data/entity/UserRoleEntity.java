package com.jb.bookshareauthorizationserver.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user_role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRoleEntity {

    public record PrimaryKey(Long userId, String role) implements Serializable {
    }

    @Id
    private PrimaryKey id;

    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
