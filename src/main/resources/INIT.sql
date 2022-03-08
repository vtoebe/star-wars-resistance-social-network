DROP SCHEMA IF EXISTS stwars CASCADE;

CREATE SCHEMA IF NOT EXISTS stwars;

CREATE TABLE stwars.person (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(250) NOT NULL,
                               birth TIMESTAMP NOT NULL,
                               genre VARCHAR(30) NOT NULL,
                               faction VARCHAR(10) NOT NULL,
                               created_at TIMESTAMP
);

CREATE TABLE stwars.base (
                             name VARCHAR(90) PRIMARY KEY,
                             faction VARCHAR(10)
);

SELECT * FROM stwars.base b ;

CREATE TABLE stwars.locale (
                               id INT PRIMARY KEY REFERENCES stwars.person(id),
                               longitude VARCHAR(60),
                               latitude VARCHAR(60),
                               base VARCHAR(90) REFERENCES stwars.base(name),
                               updated_at TIMESTAMP
);

CREATE TABLE stwars.items (
                              id SERIAL PRIMARY KEY,
                              weapons INT,
                              ammunitions INT,
                              waters INT,
                              foods INT,
                              updated_at TIMESTAMP
);


CREATE TABLE stwars.inventory (
                                  id INT PRIMARY KEY REFERENCES stwars.person(id),
                                  items INT REFERENCES stwars.items(id),
                                  updated_at TIMESTAMP
);

CREATE TABLE stwars.rebellion (
                                  person_id INT REFERENCES stwars.person(id),
                                  report_by_person_id INT REFERENCES stwars.person(id),
                                  description VARCHAR(250),
                                  registered_at TIMESTAMP,
                                  PRIMARY KEY (person_id, report_by_person_id)
);

-- DROP TABLE stwars.hist_localization;

CREATE TABLE stwars.hist_transaction (
                                         id SERIAL PRIMARY KEY,
                                         requester_person INT REFERENCES stwars.person(id),
                                         receiver_person INT REFERENCES stwars.person(id),
                                         transfer VARCHAR(250),
                                         transfer_at TIMESTAMP
);

CREATE TABLE stwars.marketplace (
                                    id SERIAL PRIMARY KEY,
                                    offered_by INT REFERENCES stwars.person(id),
                                    offer INT REFERENCES stwars.items(id),
                                    receive INT REFERENCES stwars.items(id),
                                    base VARCHAR(90) REFERENCES stwars.base(name),
                                    points INT,
                                    created_at TIMESTAMP
);


CREATE TABLE stwars.hist_localization (
                                          id SERIAL PRIMARY KEY,
                                          person_id INT REFERENCES stwars.person(id),
                                          longitude VARCHAR(60),
                                          latitude VARCHAR(60),
                                          base VARCHAR(90) REFERENCES stwars.base(name),
                                          created_at TIMESTAMP
);