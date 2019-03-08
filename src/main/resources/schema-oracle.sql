CREATE TABLE quantity
(
  id             NUMBER NOT NULL,
  quantity_value NUMBER,
  CONSTRAINT quantities_pk PRIMARY KEY (id) ENABLE
);

CREATE TABLE stock
(
  id          NUMBER NOT NULL,
  stock_value NUMBER,
  CONSTRAINT stock_pk PRIMARY KEY (id) ENABLE
);