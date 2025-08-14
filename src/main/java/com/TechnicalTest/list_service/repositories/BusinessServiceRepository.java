package com.TechnicalTest.list_service.repositories;

import com.TechnicalTest.list_service.entities.BusinessService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessServiceRepository extends JpaRepository<BusinessService, Long> {
}
