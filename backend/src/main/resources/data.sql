insert into project(id, name, created_by, creation_date, last_modification_by, last_modification_date)
values ('12c1526f-9916-4bc8-8bed-78e66267f652', 'Test Project', 'amoreno', '2022-05-25 14:15:00', 'amoreno', '2022-05-25 14:15:00');

insert into user_story (id, title, description, user_story_status, project_id,
                        created_by, creation_date, last_modification_by, last_modification_date)
values ('664535bf-8e8d-45b2-b17c-549a4017ac44', 'Develop controllers', 'As a user I want to have a controller', 'READY', '12c1526f-9916-4bc8-8bed-78e66267f652',
        'amoreno', '2022-05-25 14:15:00', 'amoreno', '2022-05-25 14:15:00');
