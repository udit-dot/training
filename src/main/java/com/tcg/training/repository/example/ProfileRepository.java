package com.tcg.training.repository.example;

import com.tcg.training.entity.example.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}