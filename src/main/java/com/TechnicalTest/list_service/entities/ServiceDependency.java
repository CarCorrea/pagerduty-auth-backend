package com.TechnicalTest.list_service.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "service_dependencies")
public class ServiceDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Service service;

    @ManyToOne
    private Service dependentOn;
}
