package com.example.demo.web;

import com.example.demo.exception.QuantityException;
import com.example.demo.service.QuantityService;
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

    @PostMapping(value = "/quantities/checked/{q}")
    public ResponseEntity addQuantityWithException(@PathVariable(name = "q") Long quantity) throws QuantityException {
        quantityService.addQuantityWithException(quantity);
        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/quantities/unchecked/{q}")
    public ResponseEntity addQuantityWithRuntimeException(@PathVariable(name = "q") Long quantity) {
        quantityService.addQuantityWithRuntimeException(quantity);
        return ResponseEntity.ok(null);
    }

}
