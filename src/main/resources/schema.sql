CREATE TABLE quantities (
                       id         NUMBER NOT NULL,
                       quantity   NUMBER,
                       CONSTRAINT quantities_pk PRIMARY KEY ( id ) ENABLE
);

CREATE TABLE total_quantity (
                          id         NUMBER NOT NULL,
                          total   NUMBER,
                          CONSTRAINT total_quantity_pk PRIMARY KEY ( id ) ENABLE
);