package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuantityService {

    private final QuantityRepository quantityRepository;
    private final TotalQuantityRepository totalQuantityRepository;
    private final NotNeededQuantityService notNeededQuantityService;

    @Autowired
    public QuantityService(QuantityRepository quantityRepository,
                           TotalQuantityRepository totalQuantityRepository,
                           NotNeededQuantityService notNeededQuantityService) {
        this.quantityRepository = quantityRepository;
        this.totalQuantityRepository = totalQuantityRepository;
        this.notNeededQuantityService = notNeededQuantityService;
    }

    @Transactional()
    public void addQuantityProxy(Long quantity) {
        this.addQuantity(quantity);
    }

    //    @Transactional
    private void addQuantity(Long quantity) {

        // update total quantity
        List<TotalQuantity> totalQuantities = totalQuantityRepository.findAll();
        if (CollectionUtils.isEmpty(totalQuantities)) {
            // no record so far, so create one
            totalQuantityRepository.save(new TotalQuantity().setTotal(quantity));
        } else {
            // TODO check that findAll() returns back a list with just one item
            TotalQuantity totalQuantity = totalQuantities.get(0);
            totalQuantityRepository.save(totalQuantity.setTotal(totalQuantity.getTotal() + quantity));
        }

        // add new record for quantity
        quantityRepository.save(new Quantity().setQuantity(quantity));
        // notNeededQuantityService.addQuantity(quantity);

        // throws a runtime exception if the quantity is negative
        Preconditions.getInstance().checkValidQuantityThrowRuntimeException(quantity);
//        Preconditions.getInstance().checkValidQuantityThrowException(quantity);
    }
}
