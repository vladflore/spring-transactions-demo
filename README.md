# spring-transactions-demo

**_!!!working example!!!_**

Small Spring based REST App to play around with the ```@Transactional``` annotation.

**Business logic:**

Calling the endpoints allows adding quantities and summing them up. Negative quantities are not allowed.

There are two tabels: ```QUANTITY``` for quantities and ```STOCK``` for the sum of the quantities. The app uses an Oracle XE database.

The Service layer throws checked exception ```QuantityException``` and unchecked exception ```QuantityRuntimeException```, according to the type of the endpoint used:

_**checked**_

```http://localhost:8080/quantities/checked/3```

```http://localhost:8080/quantities/checked/7```

```http://localhost:8080/quantities/checked/-10```

In the logs, one could see:

```Applying rules to determine whether transaction should rollback on com.example.demo.exception.QuantityException: Invalid quantity: -10```

```Winning rollback rule is: RollbackRuleAttribute with pattern [com.example.demo.exception.QuantityException]```

_**unchecked**_

```http://localhost:8080/quantities/unchecked/3```

```http://localhost:8080/quantities/unchecked/7```

```http://localhost:8080/quantities/unchecked/-10```

In the logs, one could see:

```Applying rules to determine whether transaction should rollback on com.example.demo.exception.QuantityRuntimeException: Invalid quantity: -10```

```Winning rollback rule is: null```

```No relevant rollback rule found: applying default rules```