DROP TABLE IF EXISTS t_Attachment;
DROP TABLE IF EXISTS t_Post;

CREATE TABLE t_Post(
    id int PRIMARY KEY AUTO_INCREMENT, 
    title varchar(255) NOT NULL,
    date_time date, 
    last_modified date,
    message varchar(65535),
    hashtags varchar(255),
    username varchar(255),
    sys_group varchar(255)
)ENGINE=InnoDB;

CREATE TABLE t_Attachment(

    id int PRIMARY KEY AUTO_INCREMENT, 
    file_name varchar(255),
    file_size varchar(255),
    media_type varchar(255), 
    media LONGBLOB NOT NULL,
    post_id int,
    FOREIGN KEY (post_id) REFERENCES t_Post(id) ON DELETE CASCADE
)ENGINE=InnoDB;