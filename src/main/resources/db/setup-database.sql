drop database giantdad;
create database giantdad;

drop owned by giantdad;
drop user giantdad;
create user giantdad with password 'giantdad';

grant all privileges on database giantdad to giantdad;

grant all on schema public to giantdad;

grant usage, create on schema public to giantdad;

alter database giantdad owner to giantdad;