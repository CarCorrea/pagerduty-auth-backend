package com.TechnicalTest.list_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private BusinessService businessService;

    @OneToMany(mappedBy = "service")
    private List<ServiceDependency> dependencies;

    @ManyToOne
    private EscalationPolicy escalationPolicy;
}
