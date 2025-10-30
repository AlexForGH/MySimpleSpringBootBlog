drop table if exists comments;
drop table if exists posts;

create table if not exists posts (
        id bigserial primary key,
        title varchar(256) not null,
        imagePath varchar(512) not null,
        likesCount integer not null default 0,
        text varchar(1024) not null,
        tags varchar(1024) not null
);

insert into posts(title, imagePath, likesCount, text, tags) values (
        'proger',
        '/opt/proger.jpg',
        22,
        'some proger text',
        'tag1,tag2,tag3'
);

insert into posts(title, imagePath, likesCount, text, tags) values (
        'manager',
        '/opt/manager.jpg',
        3,
        'some manager text',
        'tag4,tag5,tag6'
);

insert into posts(title, imagePath, likesCount, text, tags) values (
        'devops',
        '/opt/devops.jpg',
        9,
        'some devops text',
        'tag7,tag8'
);


create table if not exists comments (
        id bigserial primary key,
        post_id bigserial not null references posts(id) on delete cascade,
        content varchar(256) not null
);

insert into comments(post_id, content) values (
        1,
        'some comment_1 for proger'
);

insert into comments(post_id, content) values (
        1,
        'some comment_2 for proger'
);

insert into comments(post_id, content) values (
        1,
        'some comment_3 for proger'
);

insert into comments(post_id, content) values (
        3,
        'some comment_1 for devops'
);

insert into comments(post_id, content) values (
        3,
        'some comment_2 for devops'
);

