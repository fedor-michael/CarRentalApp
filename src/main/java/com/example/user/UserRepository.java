package com.example.user;

import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    // todo jakaś custom metodka

    // find all personIDs
//    @Transactional
//    @Modifying
//    @Query("")
//    void findAllPersonID()

}
