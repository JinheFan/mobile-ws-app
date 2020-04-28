package com.app.ws.mobileappws.repository;

import com.app.ws.mobileappws.data.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findRolesByName(String name);
}
