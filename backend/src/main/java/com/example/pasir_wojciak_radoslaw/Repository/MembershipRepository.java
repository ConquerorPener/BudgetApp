package com.example.pasir_wojciak_radoslaw.Repository;

import com.example.pasir_wojciak_radoslaw.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByGroupId(Long groupId);
}
