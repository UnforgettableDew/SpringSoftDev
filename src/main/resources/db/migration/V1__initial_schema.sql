create table author
(
    id        bigserial primary key,
    full_name varchar(128)
);

create table site
(
    id      bigserial primary key,
    weblink varchar(255)
);

create table article
(
    id                  bigserial primary key,
    title_english       varchar(20),
    title_german        varchar(20),
    issn                varchar(10),
    isbn                varchar(20),
    year_of_publication date,
    edition_number      int,
    site_id             bigint,
    foreign key (site_id) references site (id)
);


create table author_article
(
    author_id  bigint,
    article_id bigint,
    foreign key (author_id) references author (id)
        on delete cascade
        on update cascade,
    foreign key (article_id) references article (id)
        on delete cascade
        on update cascade
)