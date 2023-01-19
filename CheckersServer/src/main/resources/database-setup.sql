CREATE DATABASE Checkers;

CREATE USER 'checkers_manager'@'localhost' IDENTIFIED BY 'checkers_manager_pass';
GRANT ALL PRIVILEGES ON Checkers.* TO checkers_manager@localhost;

CREATE TABLE GameType(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(30),

    PRIMARY KEY (id)
);

INSERT INTO GameType (name) VALUES ('INTERNATIONAL');
INSERT INTO GameType (name) VALUES ('BRAZILIAN');
INSERT INTO GameType (name) VALUES ('THAI');

CREATE TABLE Game(
  id INT NOT NULL AUTO_INCREMENT,
  game_type INT NOT NULL,
  save_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(id),
  FOREIGN KEY(game_type) REFERENCES GameType(id)
);

CREATE TABLE Turn(
    id INT NOT NULL AUTO_INCREMENT,
    game_id INT NOT NULL,
    checker_color ENUM('white', 'black'),

    PRIMARY KEY(id),
    FOREIGN KEY(game_id) REFERENCES Game(id)
);

CREATE TABLE Move(
    id INT NOT NULL AUTO_INCREMENT,
    turn_id INT NOT NULL,
    move_number_in_turn INT NOT NULL,
    move_type ENUM('normal', 'beat'),
    start_x INT NOT NULL,
    start_y INT NOT NULL,
    end_x INT NOT NULL,
    end_y INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY(turn_id) REFERENCES Turn(id)
);