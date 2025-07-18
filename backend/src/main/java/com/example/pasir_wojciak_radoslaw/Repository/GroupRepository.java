package com.example.pasir_wojciak_radoslaw.Repository;

import com.example.pasir_wojciak_radoslaw.model.Group;
import com.example.pasir_wojciak_radoslaw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMemberships_User(User user);
}
