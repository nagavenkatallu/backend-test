package com.smart.pantry.backend.application.repository;

import com.smart.pantry.backend.application.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    List<PantryItem> findByUserId(Long userId);
}
