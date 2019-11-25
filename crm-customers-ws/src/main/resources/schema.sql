--CREATE TABLE tasks(
--   id             INTEGER NOT NULL,
--   template_id    INTEGER NOT NULL,
--   title          VARCHAR(32) NOT NULL,
--   description    VARCHAR(64) NOT NULL,
--   text           TEXT NOT NULL
--);

--CREATE TABLE tasks_state(
--   id             INTEGER NOT NULL PRIMARY KEY,
--   graph_id             INTEGER NOT NULL,
--   title VARCHAR(32) NOT NULL,
--   previous_department VARCHAR(64) NOT NULL,
--   current_department    VARCHAR(64) NOT NULL
--);

--CREATE TABLE graphs(
--   id             INTEGER NOT NULL,
--   graph_nodes    TEXT NOT NULL,
--   graph_edges    TEXT NOT NULL
--);

--INSERT INTO graphs VALUES (1, '{"nodes":[{"id":"Отдел клиентов"}, {"id":"Отдел аналитиков"}, {"id":"Отдел разработчиков"}]}', '{"edges":[{"source":"Отдел клиентов", "target":"Отдел аналитиков"}, {"source":"Отдел аналитиков", "target":"Отдел разработчиков"}, {"source":"Отдел разработчиков", "target":"Отдел аналитиков"}, {"source":"Отдел разработчиков", "target":"Отдел клиентов"}]}');
--INSERT INTO graphs VALUES (2, '{"nodes":[{"id":"Отдел клиентов"}, {"id":"Отдел аналитиков"}, {"id":"Отдел разработчиков"}]}', '{"edges":[{"source":"Отдел клиентов", "target":"Отдел разработчиков"}, {"source":"Отдел разработчиков", "target":"Отдел аналитиков"}, {"source":"Отдел аналитиков", "target":"Отдел клиентов"}]}');