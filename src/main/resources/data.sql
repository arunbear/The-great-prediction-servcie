-- Add a default user
insert into user_ (id) values (1);

-- Add a default match
insert into match (id, start_time) values (1, DATEADD('DAY', 1, CURRENT_TIMESTAMP));

-- Add a match for which predictions should not be updated
-- Assume a match does not last more than one day
insert into match (id, start_time) values (2, DATEADD('DAY', -2, CURRENT_TIMESTAMP));
