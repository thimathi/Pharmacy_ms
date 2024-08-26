package the_boys.Pharmacy_Management_System.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinesRepository extends JpaRepository <MedicinesDetails, Integer> {
    // JpaRepository provides basic CRUD operations
    // Integer specifies the type of primary key for User entity
}
