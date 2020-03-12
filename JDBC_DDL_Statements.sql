
/* This table contains the group name, head writer, year formed and the subject of the writing group */
CREATE TABLE WritingGroup (
GroupName varchar(50) NOT NULL, --This holds the name of the writing group 
HeadWriter varchar(50) NOT NULL, -- This holds the name of the head writer
YearFormed int NOT NULL, --This holds the year that this group was formed 
Subject varchar(50) NOT NULL, --This holds the subject that is written of 
CONSTRAINT wg_pk PRIMARY KEY(GroupName)
 );

/*This table holds the name of the writing group, book title, publisher name, year published and 
number of pages  */
CREATE TABLE Books (
GroupName varchar(50) NOT NULL, -- This holds the name of the group of authors that contributed
                                -- to the book 
BookTitle varchar(50) NOT NULL, -- This holds the title of the book 
PublisherName varchar(50) NOT NULL, --This holds the name of the publishing company 
YearPublished int NOT NULL, -- This holds the year that the book was published 
NumberPages int NOT NULL, -- This holds the number of pages in the book 
CONSTRAINT books_pk PRIMARY KEY(GroupName, BookTitle),
CONSTRAINT books_ck UNIQUE(BookTitle, PublisherName),
CONSTRAINT books_fk_1 FOREIGN KEY(GroupName) REFERENCES WritingGroup(GroupName),
CONSTRAINT books_fk_2 FOREIGN KEY(PublisherName) REFERENCES Publishers(PublisherName)
);

/*This table holds the name, address, phone and email of the publisher */
CREATE TABLE Publishers(
PublisherName varchar(50) NOT NULL, --This holds the name of the publishing company 
PublisherAddress varchar(100) NOT NULL, --This holds the address of the publisher 
PublisherPhone int NOT NULL, -- This holds the phone number of the publisher
PublisherEmail varchar(50) NOT NULL, --This holds the email address of the publisher 
CONSTRAINT publishers_pk PRIMARY KEY(PublisherName)
);