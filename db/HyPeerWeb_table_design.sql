CREATE TABLE IF NOT EXISTS nodes
(
	node_key INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	web_id INTEGER UNIQUE NOT NULL,
	fold INTEGER,
	surrogate_fold INTEGER,
	FOREIGN KEY (fold) REFERENCES nodes(node_key),
	FOREIGN KEY (surrogate_fold) REFERENCES nodes(node_key),
);