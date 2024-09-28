package com.Junit.CURDOperationsTest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Junit.CURDOperationsTest.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
