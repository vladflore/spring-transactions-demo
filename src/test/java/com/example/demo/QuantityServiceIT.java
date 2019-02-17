package com.example.demo;

import org.hamcrest.collection.IsEmptyCollection;
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
    private TotalQuantityRepository totalQuantityRepository;

    @Autowired
    private EntityManager entityManager;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Transactional
    public void addPositiveQuantity_shouldAddQuantity_andIncreaseTotalQuantity() {
        // before state
        List<TotalQuantity> totalQuantity = totalQuantityRepository.findAll();
        assertNotNull(totalQuantity);
        assertThat(totalQuantity, IsEmptyCollection.empty());

        List<Quantity> quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, IsEmptyCollection.empty());

        // change state

        quantityService.addQuantityProxy(3L);

        entityManager.flush();


        // after state
        totalQuantity = totalQuantityRepository.findAll();
        assertNotNull(totalQuantity);
        assertThat(totalQuantity, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(1));

        // change state again

        quantityService.addQuantityProxy(7L);

        entityManager.flush();


        // after state
        totalQuantity = totalQuantityRepository.findAll();
        assertNotNull(totalQuantity);
        assertThat(totalQuantity, hasSize(1));

        Long newTotalQuantityValue = totalQuantity.get(0).getTotal();
        assertThat(newTotalQuantityValue, is(10L));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));
        long sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantity).sum();
        assertThat(sumOfQuantities, is(newTotalQuantityValue));
    }

    @Test
    @Transactional
    public void addNegativeQuantity_shouldThrowException() {
        expectedException.expect(QuantityRuntimeException.class);
        quantityService.addQuantityProxy(-3L);
    }

    @Test
    @Transactional
    public void addNegativeQuantity_shouldNOTAddQuantity_andShouldNOTIncreaseTotalQuantity() {

        // before state
        List<TotalQuantity> totalQuantity = totalQuantityRepository.findAll();
        assertNotNull(totalQuantity);
        assertThat(totalQuantity, IsEmptyCollection.empty());

        List<Quantity> quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, IsEmptyCollection.empty());

        // change state

        quantityService.addQuantityProxy(2L);

        entityManager.flush();


        // after state
        totalQuantity = totalQuantityRepository.findAll();
        assertNotNull(totalQuantity);
        assertThat(totalQuantity, hasSize(1));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(1));

        // change state again


        quantityService.addQuantityProxy(8L);

        entityManager.flush();


        // after state
        totalQuantity = totalQuantityRepository.findAll();
        assertNotNull(totalQuantity);
        assertThat(totalQuantity, hasSize(1));

        Long newTotalQuantityValue = totalQuantity.get(0).getTotal();
        assertThat(newTotalQuantityValue, is(10L));

        quantities = quantityRepository.findAll();
        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));
        long sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantity).sum();
        assertThat(sumOfQuantities, is(newTotalQuantityValue));

        // try to add a negative quantity

        try {
            quantityService.addQuantityProxy(-3L);
        } catch (QuantityRuntimeException e) {
        } finally {
            entityManager.flush();
        }


        totalQuantity = totalQuantityRepository.findAll();
        quantities = quantityRepository.findAll();

        assertNotNull(totalQuantity);
        assertThat(totalQuantity, hasSize(1));

        newTotalQuantityValue = totalQuantity.get(0).getTotal();

        assertNotNull(quantities);
        assertThat(quantities, hasSize(2));
        sumOfQuantities = quantities.stream().mapToLong(Quantity::getQuantity).sum();

        assertThat(sumOfQuantities, is(newTotalQuantityValue));
        assertThat(newTotalQuantityValue, is(10L));
    }

}
