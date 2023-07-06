insert into author(full_name)
values ('John Doe'),
       ('Jane Smith'),
       ('Michael Johnson'),
       ('William Modsu');

insert into site(weblink)
values ('https://www.example1.com'),
       ('https://www.example2.com'),
       ('https://www.example3.com'),
       ('https://www.example4.com'),
       ('https://www.example5.com');

insert into article (title_english, title_german, issn, isbn, year_of_publication, edition_number, site_id)
values ('The Great Gatsby', 'Der große Gatsby', '1234567', '978-3-1234-5678-9', '1925-04-10', 1, 1),
       ('Mockingbird', 'Nachtigall stört', '9876543', '978-3-9876-5432-1', '1960-07-11', 2, 2),
       ('1984', '1984', '4567890', '978-1-2345-6789-0', '1949-06-08', 1, 5),
       ('The Pride', 'Vorurteil', '5678901', '978-9-8765-4321-0', '1813-01-28', 3, 3),
       ('The Catcher', 'Der Fänger im Roggen', '0987654', '978-5-4321-0987-6', '1951-07-16', 1, 2),
       ('The Lighthouse', 'Zum Leuchtturm', '2345678', '978-7-6543-2109-8', '1927-05-05', 2, 4),
       ('Moby-Dick', 'Moby-Dick', '7654321', '978-0-9876-5432-1', '1851-10-18', 1, 1),
       ('The Lord ', 'der Ringe', '3210987', '978-2-1098-7654-3', '1954-07-29', 3, 3),
       ('Jane Eyre', 'Jane Eyre', '8901234', '978-4-3210-9876-5', '1847-10-16', 2, 2),
       ('Brave New World', 'Schöne neue Welt', '5432109', '978-6-5432-1098-7', '1932-05-01', 1, 4);

insert into author_article(author_id, article_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 5),
       (3, 7),
       (4, 1),
       (4, 8),
       (4, 9),
       (4, 10);
