package com.app.ws.mobileappws.repository;

import com.app.ws.mobileappws.data.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {
    AuthorityEntity findAuthoritiesByName(String name);
}
