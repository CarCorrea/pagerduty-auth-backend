package com.TechnicalTest.list_service.repositories;

import com.TechnicalTest.list_service.entities.ServiceDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceDependencyRepository extends JpaRepository<ServiceDependency, Long> {
}
