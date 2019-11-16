CREATE TABLE IF NOT EXISTS Task(
  id IDENTITY PRIMARY KEY,
  name VARCHAR,
  document VARCHAR,
  resolver VARCHAR,
  status INTEGER
);
DELETE FROM Task;
INSERT INTO Task VALUES(1, 'task1', '/text/text', 'Who', 0);
INSERT INTO Task VALUES(2, 'task2', '/text/text', 'Who', 0);
INSERT INTO Task VALUES(3, 'task3', '/text/text', 'Who', 0);

