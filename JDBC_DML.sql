insert into Publishers(publishername,publisheraddress, publisherphone, publisheremail) VALUES
('J.B Lippincott & Co.', '1234 Orange ave.', 5667890, 'JBWriting@yahoo.com'),
('Little, Brown and Company', '4343 Main Street', 5553978, 'LittleGroup@yahoo.com'),
('Scholastic', '568 Crescent Street', 5653145, 'ScholasticBusiness@gmail.com');


insert into books(groupname, booktitle, publishername, yearpublished,numberpages ) VALUES 
('Storytellers', 'To Kill a MockingBird', 'J.B Lippincott & Co.', 1960, 281),
('Novice Narrators','The Catcher in the Rye', 'Little, Brown and Company',1951,277),
('Goosebumps Writing Group','Welcome to Dead House', 'Scholastic', 1992, 128),
('Goosebumps Writing Group', 'Return of the Mummy','Scholastic', 1994, 118);

insert into books(groupname, booktitle, publishername, yearpublished,numberpages ) VALUES 
('Novice Narrators','The Catcher in the Rye', 'Little, Brown and Company',1951,277);


insert into WritingGroup(groupname, headwriter, yearformed, subject) VALUES 
('Storytellers','Harper Lee', 1950, 'Morality'),
('Novice Narrators','J.D Salinger', 1940, 'Alienation'),
('Goosebumps Writing Group', 'R.L Stine', 1990, 'Horror');


-- (the DML used below was used FOR REFERENCE in the java program) 
-- --List all writing groups
-- Select groupName, headWriter, yearFormed, subject 
-- 	From WritingGroups;
-- 
-- --List all data for a group specified by the user
-- Select groupName, headWriter, yearFormed, subject 
-- 	From WritingGroups
-- 	Where groupName = 'userInput';
-- 
-- --List all publishers
-- Select publisherName, publisherAddress, publisherPhone, publisherEmail
-- 	From Publishers;
-- 
-- --List all data for a publisher specified by the user
-- Select publisherName, publisherAddress, publisherPhone, publisherEmail
-- 	From Publishers
-- 	Where publisherName = 'userInput';
-- 
-- --List all book titles
-- Select groupName, bookTitle, publisherName, yearPublished, numberPages
-- 	From Books;
-- 
-- --List all data for a book specified by the user
-- Select groupName, bookTitle, publisherName, yearPublished, numberPages
-- 	From Books
-- 	Where bookTitle = 'userInput';
-- 
-- --Insert new book
-- Insert Into Books (groupName, bookTitle, publisherName, yearPublished, numberPages)
-- 	Values ('userInputs');
-- 
-- --Insert new publisher and update all books published by one publisher to be published by new publisher. 
-- Insert Into Publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
-- 	Values ('userInputs');
-- Update Books
-- 	Set publisherName = 'newPublisherName'
-- 	Where publisherName = 'userInput';
-- 
-- --Remove a book specified by the user
-- Delete From Books
-- 	Where bookTitle = 'userInput';