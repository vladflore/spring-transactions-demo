package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotNeededQuantityService {
    private final QuantityRepository quantityRepository;

    @Autowired
    public NotNeededQuantityService(QuantityRepository quantityRepository) {
        this.quantityRepository = quantityRepository;
    }

    // @Transactional
    public void addQuantity(Long quantity) {

        // add new record for quantity
        quantityRepository.save(new Quantity().setQuantity(quantity));

        // throws a runtime exception if the quantity is negative
        Preconditions.getInstance().checkValidQuantityThrowRuntimeException(quantity);
    }
}
