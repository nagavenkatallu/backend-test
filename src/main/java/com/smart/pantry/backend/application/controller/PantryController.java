package com.smart.pantry.backend.application.controller;

import com.smart.pantry.backend.application.dto.PantryItemDTO;
import com.smart.pantry.backend.application.dto.PantryItemRequest;
import com.smart.pantry.backend.application.dto.UpdatePantryItemRequest;
import com.smart.pantry.backend.application.model.PantryItem;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.service.PantryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pantry")
public class PantryController {

    private final PantryService pantryService;

    public PantryController(PantryService pantryService){
        this.pantryService=pantryService;
    }
    @GetMapping("/items")
    public ResponseEntity<List<PantryItemDTO>> getUserPantryItems(@AuthenticationPrincipal User user){
        List<PantryItemDTO> userPantryItems=pantryService.getItemsForUser(user);

        return ResponseEntity.ok(userPantryItems);
    }

    @PostMapping("/items")
    public ResponseEntity<PantryItem> addItemToPantry(@AuthenticationPrincipal User user, @Valid @RequestBody PantryItemRequest pantryItemRequest){
        PantryItem newPantryItem=pantryService.addPantryItemForUser(user,pantryItemRequest);

        return new ResponseEntity<>(newPantryItem, HttpStatus.CREATED);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<PantryItem> updatePantryItem(@PathVariable Long itemId, @AuthenticationPrincipal User user, @Valid @RequestBody UpdatePantryItemRequest updatePantryItemRequest){
        PantryItem updatedItem=pantryService.updatePantryItem(itemId, user, updatePantryItemRequest);
        if(updatedItem == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deletePantryItem(@PathVariable Long itemId, @AuthenticationPrincipal User user){
        pantryService.deletePantryItem(itemId, user);
        return ResponseEntity.noContent().build();
    }
}
