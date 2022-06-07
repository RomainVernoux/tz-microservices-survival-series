insert into user_story (id, title, description, user_story_status, project_id)
values ('664535bf-8e8d-45b2-b17c-549a4017ac44', 'Develop controllers', 'As a user I want to have a controller', 'TODO', '12c1526f-9916-4bc8-8bed-78e66267f652');

insert into workflow_rule(id, max_number_of_user_stories, project_id, user_story_status)
values ('12c1526f-9916-4bc8-8bed-78e66267f652', 3, '12c1526f-9916-4bc8-8bed-78e66267f652', 'IN_PROGRESS')