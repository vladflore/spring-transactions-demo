# spring-transactions-demo

Small Spring based REST App to play around with the ```@Transactional``` annotation.

Business logic:

Calling the endpoints allows adding quantities and summing them up. Negative quantities are not allowed.

There are two tabels: ```QUANTITY``` for quantities and ```STOCK``` for the sum of the quantities.

The Service layer throws checked exception ```QuantityException``` and unchecked exception ```QuantityRuntimeException```, according to the type of the endpoint used:

___checked___

```http://localhost:8080/quantities/checked/3```

```http://localhost:8080/quantities/checked/7```

```http://localhost:8080/quantities/checked/-10```

In the logs, one could see:

```Applying rules to determine whether transaction should rollback on com.example.demo.exception.QuantityException: Invalid quantity: -10```

```Winning rollback rule is: RollbackRuleAttribute with pattern [com.example.demo.exception.QuantityException]```

___unchecked___

```http://localhost:8080/quantities/unchecked/3```

```http://localhost:8080/quantities/unchecked/7```

```http://localhost:8080/quantities/unchecked/-10```

In the logs, one could see:

```Applying rules to determine whether transaction should rollback on com.example.demo.exception.QuantityRuntimeException: Invalid quantity: -10```

```Winning rollback rule is: null```

```No relevant rollback rule found: applying default rules```

Production code seems to be working just fine, integration test for trying to add a negative value, fails, a.k.a the negative quantity is persisted, even though an exception is thrown and a roll-back is done, which can be seen in the logs too.


