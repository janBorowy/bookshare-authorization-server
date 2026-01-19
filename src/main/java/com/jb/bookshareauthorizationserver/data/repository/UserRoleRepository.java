package com.jb.bookshareauthorizationserver.data.repository;

import com.jb.bookshareauthorizationserver.data.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntity.PrimaryKey> {

    @Query("select ur.role from UserRoleEntity ur where ur.user.id = ?1")
    String[] findRolesById(Long userId);

}
