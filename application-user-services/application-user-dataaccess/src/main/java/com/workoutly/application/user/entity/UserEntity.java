package com.workoutly.application.user.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Table(name = "users")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private String userId;
    private String username;
    private String email;
    private String password;
    @Column(name = "is_enabled")
    private boolean isEnabled;

    @OneToOne
    @JoinColumn(name = "role_id")
    private UserRoleEntity role;

}
