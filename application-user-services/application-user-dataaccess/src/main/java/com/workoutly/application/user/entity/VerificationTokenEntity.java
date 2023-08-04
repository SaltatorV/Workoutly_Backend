package com.workoutly.application.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_token")
@Entity
public class VerificationTokenEntity {

    @Id
    @Column(name = "verification_token_id")
    private String id;
    private String token;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "expire_date")
    private Date expireDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
