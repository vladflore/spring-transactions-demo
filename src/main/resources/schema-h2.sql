create sequence if not exists HIBERNATE_SEQUENCE;

CREATE SEQUENCE if not exists seq_quantity START WITH 1;
create table if not exists quantity
(
  ID          INTEGER DEFAULT seq_quantity.nextval not null primary key,
  quantity_value integer
);

CREATE SEQUENCE if not exists seq_stock START WITH 1;
create table if not exists stock
(
  ID          INTEGER DEFAULT seq_stock.nextval not null primary key,
  stock_value integer
);