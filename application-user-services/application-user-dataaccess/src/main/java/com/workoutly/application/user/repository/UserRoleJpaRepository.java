package com.workoutly.application.user.repository;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, String> {

    UserRoleEntity getRoleEntityByPermissionName(String roleName);
}
