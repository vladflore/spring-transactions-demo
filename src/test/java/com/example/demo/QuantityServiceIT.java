package com.example.demo;

import com.example.demo.exception.QuantityException;
import com.example.demo.exception.QuantityRuntimeException;
import com.example.demo.model.entities.Quantity;
import com.example.demo.model.entities.Stock;
import com.example.demo.repository.QuantityRepository;
import com.example.demo.repository.StockyRepository;
import com.example.demo.service.QuantityService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuantityServiceIT {

    @Autowired
    private QuantityService quantityService;

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private StockyRepository stockyRepository;

    @Autowired
    private EntityManager entityManager;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Transactional
    public void addPositiveQuantity_shouldAddQuantity_andIncreaseStock() {

        Long firstValue = 3L;
        Long secondValue = 7L;

        List<Stock> stockValue = stockyRepository.findAll();

        assertNotNull(stockValue);
        assertThat(stockValue, IsEmptyCollection.empty());

        List<Quantity> quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, IsEmptyCollection.empty());

        try {
            quantityService.addQuantityWithException(firstValue);
        } catch (QuantityException e) {
        }
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(1));

        try {
            quantityService.addQuantityWithException(secondValue);
        } catch (QuantityException e) {
        }
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));

        Long currentStockValue = stockValue.get(0).getStockValue();
        assertThat(currentStockValue, is(firstValue + secondValue));

        long sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantityValue).sum();
        assertThat(sumOfQuantities, is(currentStockValue));
    }

    @Test
    @Transactional
    public void addNegativeQuantity_shouldThrowRuntimeException() {
        expectedException.expect(QuantityRuntimeException.class);
        quantityService.addQuantityWithRuntimeException(-3L);
    }

    @Test
    @Transactional
    public void addNegativeQuantity_shouldThrowException() throws QuantityException {
        expectedException.expect(QuantityException.class);
        quantityService.addQuantityWithException(-3L);
    }

    @Test
    @Ignore
    @Transactional
    public void addNegativeQuantity_withRuntimeException_shouldNOTAddQuantity_andShouldNOTIncreaseStock() {

        Long firstValue = 3L;
        Long secondValue = 7L;
        Long negativeValue = -5L;

        List<Stock> stockValue = stockyRepository.findAll();

        assertNotNull(stockValue);
        assertThat(stockValue, IsEmptyCollection.empty());

        List<Quantity> quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, IsEmptyCollection.empty());


        quantityService.addQuantityWithRuntimeException(firstValue);
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(1));


        quantityService.addQuantityWithRuntimeException(secondValue);
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));

        Long currentStockValue = stockValue.get(0).getStockValue();
        assertThat(currentStockValue, is(firstValue + secondValue));

        long sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantityValue).sum();
        assertThat(sumOfQuantities, is(firstValue + secondValue));

        /////////////////////////////////////////////////////////////////////////////////////////

        // !!! should roll-back
        quantityService.addQuantityWithRuntimeException(negativeValue);
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));

        currentStockValue = stockValue.get(0).getStockValue();
        assertThat(currentStockValue, is(firstValue + secondValue));

        sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantityValue).sum();
        assertThat(sumOfQuantities, is(firstValue + secondValue));
    }

    @Test
    @Transactional
    public void addNegativeQuantity_withException_shouldNOTAddQuantity_andShouldNOTIncreaseStock() {
        Long firstValue = 3L;
        Long secondValue = 7L;
        Long negativeValue = -5L;

        List<Stock> stockValue = stockyRepository.findAll();

        assertNotNull(stockValue);
        assertThat(stockValue, IsEmptyCollection.empty());

        List<Quantity> quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, IsEmptyCollection.empty());


        try {
            quantityService.addQuantityWithException(firstValue);
        } catch (QuantityException e) {
        }
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(1));


        try {
            quantityService.addQuantityWithException(secondValue);
        } catch (QuantityException e) {
        }
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));

        Long currentStockValue = stockValue.get(0).getStockValue();
        assertThat(currentStockValue, is(firstValue + secondValue));

        long sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantityValue).sum();
        assertThat(sumOfQuantities, is(firstValue + secondValue));

        /////////////////////////////////////////////////////////////////////////////////////////

        // !!! should roll-back
        try {
            quantityService.addQuantityWithException(negativeValue);
        } catch (QuantityException e) {
        }
        // entityManager.flush();

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));

        currentStockValue = stockValue.get(0).getStockValue();
        assertThat(currentStockValue, is(firstValue + secondValue));

        sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantityValue).sum();
        assertThat(sumOfQuantities, is(firstValue + secondValue));
    }
}
