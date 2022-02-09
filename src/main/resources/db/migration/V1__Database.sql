create table game_days
(
    id          int auto_increment
        primary key,
    date        date          not null,
    quote       varchar(255)  null,
    author      varchar(50)   null,
    description varchar(2048) null
);

create table matches
(
    id          int auto_increment
        primary key,
    game_day_id int not null,
    match_order int not null,
    constraint matches_day_games_id_fk
        foreign key (game_day_id) references game_days (id)
);

create table match_players
(
    id             int auto_increment
        primary key,
    match_id       int             not null,
    team           enum ('A', 'B') not null,
    nickname       varchar(50)     not null,
    goals_in_favor tinyint         not null,
    goals_against  tinyint         not null,
    yellow_cards   tinyint         not null,
    blue_cards     tinyint         not null,
    red_cards      tinyint         not null,
    constraint match_players_matches_id_fk
        foreign key (match_id) references matches (id)
);
