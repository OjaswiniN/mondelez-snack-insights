--
-- Name: roles;
--
CREATE TABLE IF NOT EXISTS roles  (
    id SMALLSERIAL NOT NULL,
    role character varying(21) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (role)
);

CREATE TABLE IF NOT EXISTS permissions  (
    id SMALLSERIAL NOT NULL,
    role_id smallint,
    permission text[],
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

--
-- Name: users;
--
CREATE TABLE IF NOT EXISTS users (
    id uuid NOT NULL,
    email character varying(64) NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(255) NOT NULL,
    location character varying(64) NOT NULL,
    dob date NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT (now() at time zone 'utc'),
    PRIMARY KEY (id),
    UNIQUE (email)
);

--
-- Name: user_roles;
--
CREATE TABLE IF NOT EXISTS user_roles (
    user_id uuid NOT NULL,
    role_id smallint NOT NULL,
    UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);