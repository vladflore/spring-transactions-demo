package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuantityController {

    private final QuantityService quantityService;

    @Autowired
    public QuantityController(QuantityService quantityService) {
        this.quantityService = quantityService;
    }

    @PostMapping(value = "/quantities/{q}")
    public ResponseEntity addQuantity(@PathVariable(name = "q") Long quantity) {
//         quantityService.addQuantity(quantity);
        quantityService.addQuantityProxy(quantity);
        return ResponseEntity.ok(null);
    }

}
