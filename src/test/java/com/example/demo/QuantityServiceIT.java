package com.example.demo;

import com.example.demo.exception.QuantityException;
import com.example.demo.exception.QuantityRuntimeException;
import com.example.demo.model.entities.Quantity;
import com.example.demo.model.entities.Stock;
import com.example.demo.repository.QuantityRepository;
import com.example.demo.repository.StockyRepository;
import com.example.demo.service.QuantityService;
import org.assertj.core.api.Assertions;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class QuantityServiceIT {

    @Autowired
    private QuantityService quantityService;

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private StockyRepository stockyRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @After
    public void cleanUp() {
        System.out.println("Cleaning up...");
        stockyRepository.findAll().forEach(stock -> stockyRepository.delete(stock));
        quantityRepository.findAll().forEach(quantity -> quantityRepository.delete(quantity));
    }

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
//    @Transactional
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

        try {
            quantityService.addQuantityWithRuntimeException(firstValue);
        } catch (QuantityRuntimeException e) {
        }

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(1));


        try {
            quantityService.addQuantityWithRuntimeException(secondValue);
        } catch (QuantityRuntimeException e) {
        }

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

        try {
            quantityService.addQuantityWithRuntimeException(negativeValue);
        } catch (QuantityRuntimeException e) {
        }

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
    //@Transactional
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

        try {
            quantityService.addQuantityWithException(negativeValue);
        } catch (QuantityException e) {
            // do nothing
        }

        stockValue = stockyRepository.findAll();
        assertNotNull(stockValue);
        assertThat(stockValue, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));
        Assertions.assertThat(quantities).hasSize(2);
        Optional<Quantity> negativeQuantity = quantities.stream().filter(q -> q.getQuantityValue() < 0).findFirst();
        Assertions.assertThat(negativeQuantity.isPresent()).isFalse();

        currentStockValue = stockValue.get(0).getStockValue();
        assertThat(currentStockValue, is(firstValue + secondValue));

        sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantityValue).sum();
        assertThat(sumOfQuantities, is(firstValue + secondValue));
    }

    @Test
    @Transactional
    public void withTestEntityManager() {
        Quantity savedQuantity = testEntityManager.persistFlushFind(new Quantity().setQuantityValue(100L));
        Assertions.assertThat(savedQuantity.getQuantityValue()).isEqualTo(100);
    }
}
