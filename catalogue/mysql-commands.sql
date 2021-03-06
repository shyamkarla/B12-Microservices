CREATE TABLE product
( id INT(11) NOT NULL,
product_name VARCHAR(32) NOT NULL,
product_description VARCHAR(32),
age VARCHAR(7),
PRIMARY KEY (id)
);

CREATE TABLE product_reviews
( review_id INT(11) NOT NULL,
product_id INT(11) NOT NULL,
product_review VARCHAR(32),
product_ratings VARCHAR(32) NOT NULL,
PRIMARY KEY (review_id),
FOREIGN KEY (product_id) REFERENCES PRODUCT(id)
);


insert into product (id, product_name, product_description)
values (1, "powder", "daily talcum powder");
 
insert into product (id, product_name, product_description) 
values (2, "lotion", "aloe vera body lotion");

insert into product (id, product_name, product_description)
values (3, "Apple IPhone XE", "Electronics Product");
insert into product (id, product_name, product_description)
values (4, "Samsung Galaxy 10", "Electronics Product");
insert into product (id, product_name, product_description)
values (5, "Haier", "Electronics Washing Machine");
insert into product (id, product_name, product_description)
values (6, "LG", "Electronics TV");

 

insert into product_reviews (review_id, product_id, product_review, product_ratings) 
values (1, 2, "nice powder","5");

insert into product_reviews (review_id, product_id, product_review, product_ratings) 
values (2, 1, "powder is good but delivery was one day late","3");

insert into product_reviews (review_id, product_id, product_review, product_ratings) 
values (3, 4, "Good Phone","5");

insert into product_reviews (review_id, product_id, product_review, product_ratings) 
values (4, 4, "Reasonable and good phone","4");


