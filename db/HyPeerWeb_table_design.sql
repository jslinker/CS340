CREATE TABLE IF NOT EXISTS nodes
(
	web_id INTEGER UNIQUE NOT NULL,
	fold INTEGER,
	surrogate_fold INTEGER,
	inverse_surrogate_fold INTEGER,
	FOREIGN KEY (fold) REFERENCES nodes(web_id),
	FOREIGN KEY (surrogate_fold) REFERENCES nodes(web_id),
	FOREIGN KEY (inverse_surrogate_fold) REFERENCES nodes(web_id)
);

CREATE TABLE IF NOT EXISTS neighbors
(
	node INTEGER NOT NULL,
	neighbor INTEGER NOT NULL,
	FOREIGN KEY (node) REFERENCES nodes(web_id),
	FOREIGN KEY (neighbor) REFERENCES nodes(web_id),
	UNIQUE (node, neighbor)
);

--up pointers are the same as edge nodes
CREATE TABLE IF NOT EXISTS up_pointers
(
	node INTEGER NOT NULL,
	edge_node INTEGER NOT NULL,
	FOREIGN KEY (node) REFERENCES nodes(web_id),
	FOREIGN KEY (edge_node) REFERENCES nodes(web_id),
	UNIQUE (node, edge_node)
);

--down pointers are the same as surrogate neighbors
CREATE TABLE IF NOT EXISTS down_pointers
(
	node INTEGER NOT NULL,
	surrogate_neighbor INTEGER NOT NULL,
	FOREIGN KEY (node) REFERENCES nodes(web_id),
	FOREIGN KEY (surrogate_neighbor) REFERENCES nodes(web_id),
	UNIQUE (node, surrogate_neighbor)
);
