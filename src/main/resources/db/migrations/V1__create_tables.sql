create table users(
    id integer primary key generated always as identity,
    user_name varchar(50) not null,
    password varchar(64) not null,
    email varchar not null,
    is_verified boolean default false,
    created_on timestamptz default now(),
    updated_on timestamptz,
    last_here timestamptz
);

create table programs(
    id integer primary key generated always as identity,
    name varchar(200) not null,
    start_date timestamptz not null,
    end_date timestamptz not null,
    created_on timestamptz default now(),
    updated_on timestamptz
);

create table workouts(
    id integer primary key generated always as identity,
    programs_id integer not null references programs(id),
    sequence integer not null,
    created_on timestamptz default now(),
    updated_on timestamptz
);

create type lift_type
    as enum(
    'push',
    'pull',
    'upper',
    'lower',
    'back',
    'arms',
    'legs',
    'chest',
    'accessory');

create table lifts(
    id integer primary key generated always as identity,
    name varchar(150) not null,
    description varchar not null,
    types lift_type[] not null,
    video_demo_link varchar,
    created_on timestamptz default now(),
    updated_on timestamptz
);

create table lift_maxes(
    id integer primary key generated always as identity,
    lifts_id integer not null references lifts(id),
    users_id integer not null references users(id),
    weight integer not null,
    created_on timestamptz default now()
);

create table workouts_lifts(
    id integer primary key generated always as identity,
    workouts_id integer not null references workouts(id),
    lifts_id integer not null references lifts(id),
    reps integer not null,
    weight integer not null,
    created_on timestamptz default now(),
    updated_on timestamptz
);

insert into users(user_name, password, email, is_verified)
values (
           'devin',
           (select encode(sha256('fart'::bytea), 'hex')),
           'devin.austin@gmail.com',
           true
       );

insert into lifts(name, description, types)
values
    ('high bar barbell squat', 'back squat', '{"lower", "legs"}'),
    ('pause bench', 'bench', '{"lower", "legs"}'),
    ('high bar barbell squat', 'back squat', '{"lower", "legs"}'),
    ('conventional deadlift', 'deadlift', '{"back", "pull", "lower", "legs"}');