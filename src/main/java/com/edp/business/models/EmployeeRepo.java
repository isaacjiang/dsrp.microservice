package com.edp.business.models;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepo extends ReactiveMongoRepository<Employee, String> {

}
