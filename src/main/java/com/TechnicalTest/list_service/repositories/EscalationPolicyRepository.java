package com.TechnicalTest.list_service.repositories;

import com.TechnicalTest.list_service.entities.EscalationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalationPolicyRepository extends JpaRepository<EscalationPolicy, Long> {
}
