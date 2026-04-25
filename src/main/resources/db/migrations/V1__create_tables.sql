
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

create table programs(
    id integer primary key generated always as identity,
    name varchar(200) not null,
    program_length interval not null,
    created_on timestamptz default now(),
    updated_on timestamptz
);
create index idx_programs_program_length_interval on programs(program_length);

create table users(
    id integer primary key generated always as identity,
    public_id uuid not null default gen_random_uuid(),
    current_program_id integer not null references programs(id),
    user_name varchar(50) not null,
    password varchar(64) not null,
    email varchar not null,
    is_verified boolean default false,
    created_on timestamptz default now(),
    updated_on timestamptz,
    last_here timestamptz
);
create index idx_users_public_id on users(public_id);
create index idx_users_current_program_id on users(current_program_id);

create table workouts
(
    id integer primary key generated always as identity,
    programs_id integer not null references programs(id),
    name varchar(200) not null,
    day_of_week integer not null,
    created_on timestamptz default now(),
    updated_on timestamptz
);
create index idx_workouts_programs_id on workouts(programs_id);
create index idx_workouts_day_of_week on workouts(day_of_week);

create table lifts
(
    id integer primary key generated always as identity,
    workouts_id  integer not null references workouts (id),
    name varchar(150) not null,
    description varchar not null,
    lift_order integer not null,
    types lift_type[] not null,
    video_demo_link varchar,
    created_on timestamptz default now(),
    updated_on timestamptz
);
create index idx_lifts_workouts_id on lifts(workouts_id);
create index idx_lifts_name on lifts(name);
create index idx_lifts_lift_order on lifts(lift_order);

create table sets
(
    id integer primary key generated always as identity,
    lifts_id  integer not null references lifts(id),
    set_number integer not null,
    reps integer not null,
    weight integer not null,
    created_on timestamptz default now(),
    updated_on timestamptz
);
create index idx_sets_lifts_id on sets(lifts_id);
create index idx_sets_set_number on sets(set_number);

create table users_lift_maxes
(
    id integer primary key generated always as identity,
    lifts_id integer not null references lifts (id),
    users_id integer not null references users(id),
    weight integer not null,
    created_on timestamptz default now()
);

create table users_programs
(
    id integer primary key generated always as identity,
    users_id integer not null references users(id),
    programs_id integer not null references programs(id),
    started_on timestamptz not null,
    completed_on timestamptz
);
create index idx_users_programs_users_id on users_programs(users_id);
create index idx_users_programs_programs_id on users_programs(programs_id);

create table users_workouts
(
    id integer primary key generated always as identity,
    users_id integer not null references users(id),
    lifts_id integer not null references lifts(id),
    set_number integer not null, -- TODO: make foreign key to sets(set_number)?
    reps integer not null,
    weight integer not null,
    scheduled_date date not null,
    created_on timestamptz default now(),
    updated_on timestamptz
);
create index idx_users_workouts_users_id on users_workouts(users_id);
create index idx_users_workouts_lifts_id on users_workouts(lifts_id);
create index idx_users_workouts_created_on on users_workouts(created_on);
create index idx_users_workouts_scheduled_date on users_workouts(scheduled_date);
create unique index idx_users_workouts_unique_sets
    on users_workouts (
                       users_id,
                       lifts_id,
                       set_number,
                       scheduled_date,
                       ((created_on at time zone 'UTC')::date)
                     );

insert into programs(name, program_length)
values('basic', interval '4 weeks'),
      ('basic 2', interval '4 weeks');

insert into workouts(programs_id, name, day_of_week)
values
    ((select id from programs where name = 'basic'), 'squat focus', 1),
    ((select id from programs where name = 'basic'), 'bench focus', 2),
    ((select id from programs where name = 'basic'), 'deadlift focus', 3),
    ((select id from programs where name = 'basic 2'), 'other squat focus', 1),
    ((select id from programs where name = 'basic 2'), 'other bench focus', 2),
    ((select id from programs where name = 'basic 2'), 'other deadlift focus', 3);

insert into lifts(workouts_id, lift_order, name, description, types)
values
    ((select id from workouts where name = 'squat focus'), 1, 'high bar barbell squat', 'back squat', '{"lower", "legs"}'),
    ((select id from workouts where name = 'squat focus'), 2, 'bulgarian split squat', 'dumbbell bulgarian split squat', '{"lower", "legs"}'),
    ((select id from workouts where name = 'bench focus'), 1, 'pause bench', 'bench', '{"upper"}'),
    ((select id from workouts where name = 'bench focus'), 2, 'dumbbell bench', 'dumbbell bench', '{"upper"}'),
    ((select id from workouts where name = 'deadlift focus'), 1, 'conventional deadlift', 'deadlift', '{"back", "pull", "lower", "legs"}'),
    ((select id from workouts where name = 'deadlift focus'), 2, 'dumbbell rows', 'dumbbell rows', '{"back", "pull", "lower", "legs"}'),
    ((select id from workouts where name = 'other squat focus'), 1, 'low bar barbell squat', 'back squat', '{"lower", "legs"}'),
    ((select id from workouts where name = 'other squat focus'), 2, 'lunge', 'dumbbell bulgarian split squat', '{"lower", "legs"}'),
    ((select id from workouts where name = 'other bench focus'), 1, 'larsen press', 'bench', '{"upper"}'),
    ((select id from workouts where name = 'other bench focus'), 2, 'incline bench', 'dumbbell bench', '{"upper"}'),
    ((select id from workouts where name = 'other deadlift focus'), 1, 'deficit deadlift', 'deadlift', '{"back", "pull", "lower", "legs"}'),
    ((select id from workouts where name = 'other deadlift focus'), 2, 'barbell rows', 'dumbbell rows', '{"back", "pull", "lower", "legs"}');

insert into sets(lifts_id, set_number, reps, weight)
values
    ((select id from lifts where name = 'high bar barbell squat'), 1, 5, 250),
    ((select id from lifts where name = 'high bar barbell squat'), 2, 5, 250),
    ((select id from lifts where name = 'high bar barbell squat'), 3, 5, 250),
    ((select id from lifts where name = 'high bar barbell squat'), 4, 5, 250),
    ((select id from lifts where name = 'bulgarian split squat'), 1, 6, 25),
    ((select id from lifts where name = 'bulgarian split squat'), 2, 6, 25),
    ((select id from lifts where name = 'bulgarian split squat'), 3, 6, 25),
    ((select id from lifts where name = 'bulgarian split squat'), 4, 6, 25),
    ((select id from lifts where name = 'pause bench'), 1, 5, 185),
    ((select id from lifts where name = 'pause bench'), 2, 5, 185),
    ((select id from lifts where name = 'pause bench'), 3, 5, 185),
    ((select id from lifts where name = 'pause bench'), 4, 5, 185),
    ((select id from lifts where name = 'dumbbell bench'), 1, 8,  70),
    ((select id from lifts where name = 'dumbbell bench'), 2, 8,  70),
    ((select id from lifts where name = 'dumbbell bench'), 3, 8,  70),
    ((select id from lifts where name = 'dumbbell bench'), 4, 8,  70),
    ((select id from lifts where name = 'conventional deadlift'), 1, 5, 265),
    ((select id from lifts where name = 'conventional deadlift'), 2, 5, 265),
    ((select id from lifts where name = 'conventional deadlift'), 3, 5, 265),
    ((select id from lifts where name = 'conventional deadlift'), 4, 5, 265),
    ((select id from lifts where name = 'dumbbell rows'), 1, 8, 50),
    ((select id from lifts where name = 'dumbbell rows'), 2, 8, 50),
    ((select id from lifts where name = 'dumbbell rows'), 3, 8, 50),
    ((select id from lifts where name = 'dumbbell rows'), 4, 8, 50),
    ((select id from lifts where name = 'low bar barbell squat'), 1, 5, 250),
    ((select id from lifts where name = 'low bar barbell squat'), 2, 5, 250),
    ((select id from lifts where name = 'low bar barbell squat'), 3, 5, 250),
    ((select id from lifts where name = 'low bar barbell squat'), 4, 5, 250),
    ((select id from lifts where name = 'lunge'), 1, 6, 25),
    ((select id from lifts where name = 'lunge'), 2, 6, 25),
    ((select id from lifts where name = 'lunge'), 3, 6, 25),
    ((select id from lifts where name = 'lunge'), 4, 6, 25),
    ((select id from lifts where name = 'larsen press'), 1, 5, 185),
    ((select id from lifts where name = 'larsen press'), 2, 5, 185),
    ((select id from lifts where name = 'larsen press'), 3, 5, 185),
    ((select id from lifts where name = 'larsen press'), 4, 5, 185),
    ((select id from lifts where name = 'incline bench'), 1, 8,  70),
    ((select id from lifts where name = 'incline bench'), 2, 8,  70),
    ((select id from lifts where name = 'incline bench'), 3, 8,  70),
    ((select id from lifts where name = 'incline bench'), 4, 8,  70),
    ((select id from lifts where name = 'deficit deadlift'), 1, 5, 265),
    ((select id from lifts where name = 'deficit deadlift'), 2, 5, 265),
    ((select id from lifts where name = 'deficit deadlift'), 3, 5, 265),
    ((select id from lifts where name = 'deficit deadlift'), 4, 5, 265),
    ((select id from lifts where name = 'barbell rows'), 1, 8, 50),
    ((select id from lifts where name = 'barbell rows'), 2, 8, 50),
    ((select id from lifts where name = 'barbell rows'), 3, 8, 50),
    ((select id from lifts where name = 'barbell rows'), 4, 8, 50);

insert into users(user_name, password, email, is_verified, current_program_id)
values
    ('devin',
           (select encode(sha256('fart'::bytea), 'hex')),
           'devin.austin@gmail.com',
           true,
           (select id from programs where name = 'basic' limit 1)),
    ('devin 2',
           (select encode(sha256('fart'::bytea), 'hex')),
           'devin.austin@gmail.com',
           true,
           (select id from programs where name = 'basic 2' limit 1));

insert into users_programs(users_id, programs_id, started_on)
values
    ((select id from users where user_name = 'devin'), (select id from programs where name = 'basic'), now()),
    ((select id from users where user_name = 'devin 2'), (select id from programs where name = 'basic 2'), now());