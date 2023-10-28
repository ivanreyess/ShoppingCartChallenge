package com.sv.userservice.repository;

import com.sv.usercommonsservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path="findUserName")
    public User findByUserName(@Param("userName") String userName);

    @Query("select u from User u where u.userName=?1")
    public User getByUsername(String username);
}
