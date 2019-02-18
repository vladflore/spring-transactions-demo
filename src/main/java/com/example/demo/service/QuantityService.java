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
    public void addQuantityWithException(Long quantity) throws QuantityException {
        addQuantity(quantity);

        // throw exception
        // both saves should roll-back
        Preconditions.getInstance().checkValidQuantityThrowException(quantity);
    }

    @Transactional
    public void addQuantityWithRuntimeException(Long quantity) {
        addQuantity(quantity);

        // throw exception
        // both saves should roll-back
        Preconditions.getInstance().checkValidQuantityThrowRuntimeException(quantity);
    }

    private void addQuantity(Long quantity) {
        List<Stock> stock = stockyRepository.findAll();
        if (CollectionUtils.isEmpty(stock)) {
            stockyRepository.saveAndFlush(new Stock().setStockValue(quantity));
        } else {
            Stock stockValue = stock.get(0);
            stockyRepository.saveAndFlush(stockValue.setStockValue(stockValue.getStockValue() + quantity));
        }

        // add new record for quantity
        quantityRepository.saveAndFlush(new Quantity().setQuantityValue(quantity));
    }
}
