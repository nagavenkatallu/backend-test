package com.smart.pantry.backend.application.service;

import com.smart.pantry.backend.application.dto.PantryItemDTO;
import com.smart.pantry.backend.application.dto.PantryItemRequest;
import com.smart.pantry.backend.application.dto.UpdatePantryItemRequest;
import com.smart.pantry.backend.application.model.PantryItem;
import com.smart.pantry.backend.application.model.PantryItemStatus;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.repository.PantryItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    public PantryItem addPantryItemForUser(User user, PantryItemRequest pantryItemRequest){
        PantryItem newPantryItem=new PantryItem();
        newPantryItem.setUser(user);
        newPantryItem.setName(pantryItemRequest.getName());
        newPantryItem.setQuantity(pantryItemRequest.getQuantity());
        newPantryItem.setUnit(pantryItemRequest.getUnit());
        newPantryItem.setPurchaseDate(pantryItemRequest.getPurchaseDate());
        LocalDate expiryDate = (pantryItemRequest.getExpiryDate() != null)
                ? pantryItemRequest.getExpiryDate()
                : pantryItemRequest.getPurchaseDate().plusDays(14); // Default to 14 days if not provided
        newPantryItem.setExpiryDate(expiryDate);
        newPantryItem.setStatus(calculateStatus(expiryDate));
        return pantryItemRepository.save(newPantryItem);
    }

    // Helper method to determine the item's status
    private PantryItemStatus calculateStatus(LocalDate expiryDate) {
        LocalDate today = LocalDate.now();
        LocalDate warningDate = expiryDate.minusDays(3); // 3-day warning period

        if (today.isAfter(expiryDate)) {
            return PantryItemStatus.RED;
        } else if (today.isAfter(warningDate) || today.isEqual(warningDate)) {
            return PantryItemStatus.ORANGE;
        } else {
            return PantryItemStatus.GREEN;
        }
    }

    public PantryItem updatePantryItem(Long itemId, User user, UpdatePantryItemRequest updatePantryItemRequest){
        PantryItem item=verifyOwnerShip(itemId,user);
        if(updatePantryItemRequest.getQuantity().compareTo(BigDecimal.ZERO)==0){
            pantryItemRepository.delete(item);
            return null;
        }

        item.setQuantity(updatePantryItemRequest.getQuantity());
        if (updatePantryItemRequest.getExpiryDate() != null) {
            item.setExpiryDate(updatePantryItemRequest.getExpiryDate());
            item.setStatus(calculateStatus(updatePantryItemRequest.getExpiryDate()));
        }
        return pantryItemRepository.save(item);
    }

    public void deletePantryItem(Long itemId,User user){
        verifyOwnerShip(itemId,user);
        pantryItemRepository.deleteById(itemId);
    }

    private PantryItem verifyOwnerShip(Long itemId, User user) {
        PantryItem pantryItem=pantryItemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException("Pantry item not found with id:"+itemId)
        );
        if(!Objects.equals(pantryItem.getUser().getId(),user.getId())){
            throw new AccessDeniedException("You do not have permission to modify this item.");
        }
        return pantryItem;
    }


}
