package com.example.demo.service;

import com.example.demo.exception.QuantityException;
import com.example.demo.model.entities.Quantity;
import com.example.demo.model.entities.Stock;
import com.example.demo.repository.QuantityRepository;
import com.example.demo.repository.StockyRepository;
import com.example.demo.validation.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuantityService {

    private final QuantityRepository quantityRepository;
    private final StockyRepository stockyRepository;

    @Autowired
    public QuantityService(QuantityRepository quantityRepository,
                           StockyRepository stockyRepository) {
        this.quantityRepository = quantityRepository;
        this.stockyRepository = stockyRepository;
    }

    @Transactional(rollbackOn = QuantityException.class)
    public void addQuantityWithException(Long value) throws QuantityException {
        Quantity quantity = new Quantity();
        quantity.setQuantityValue(value);

        addQuantity(quantity);

        // intentionally placed after save
        Preconditions.getInstance().checkValidQuantityThrowException(quantity.getQuantityValue());
    }

    @Transactional
    public void addQuantityWithRuntimeException(Long value) {
        Quantity quantity = new Quantity();
        quantity.setQuantityValue(value);

        addQuantity(quantity);

        // intentionally placed after save
        Preconditions.getInstance().checkValidQuantityThrowRuntimeException(quantity.getQuantityValue());
    }

    private void addQuantity(Quantity quantity) {
        List<Stock> stock = stockyRepository.findAll();
        if (CollectionUtils.isEmpty(stock)) {
            stockyRepository.saveAndFlush(new Stock().setStockValue(quantity.getQuantityValue()));
        } else {
            Stock stockValue = stock.get(0);
            stockyRepository.saveAndFlush(stockValue.setStockValue(stockValue.getStockValue() + quantity.getQuantityValue()));
        }
        quantityRepository.saveAndFlush(quantity);
    }
}
