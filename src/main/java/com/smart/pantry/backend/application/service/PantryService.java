package com.smart.pantry.backend.application.service;

import com.smart.pantry.backend.application.dto.PantryItemDTO;
import com.smart.pantry.backend.application.model.PantryItem;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.repository.PantryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PantryService {
    private final PantryItemRepository pantryItemRepository;

    public PantryService(PantryItemRepository pantryItemRepository) {
        this.pantryItemRepository = pantryItemRepository;
    }

    public List<PantryItemDTO> getItemsForUser(User user) {
        return pantryItemRepository.findByUserId(user.getId())
                .stream()
                .map(PantryItemDTO::new)
                .collect(Collectors.toList());
    }
}
