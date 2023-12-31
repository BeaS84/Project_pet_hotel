package com.hotel.pethotel.repository;

import java.util.Optional;

import com.hotel.pethotel.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
Optional<UserModel> findByEmail(String userEmail);

}