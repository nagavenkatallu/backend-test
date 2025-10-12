package com.smart.pantry.backend.application.controller;

import com.smart.pantry.backend.application.dto.PantryItemDTO;
import com.smart.pantry.backend.application.model.PantryItem;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.service.PantryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pantry")
public class PantryController {

    private final PantryService pantryService;

    public PantryController(PantryService pantryService){
        this.pantryService=pantryService;
    }
    @GetMapping("items")
    public ResponseEntity<List<PantryItemDTO>> getUserPantryItems(@AuthenticationPrincipal User user){
        List<PantryItemDTO> userPantryItems=pantryService.getItemsForUser(user);

        return ResponseEntity.ok(userPantryItems);
    }
}
