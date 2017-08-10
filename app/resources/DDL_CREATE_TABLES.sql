DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS tip;

CREATE TABLE tip (
   id SERIAL,
   message text NOT NULL,
   submitted_by VARCHAR(50) NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
   updated_at TIMESTAMP NULL,
   _version INT NOT NULL DEFAULT 0,
   PRIMARY KEY (id));

CREATE TABLE comment (
   id SERIAL,
   tip_id INT NOT NULL,
   message text NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (tip_id) REFERENCES tip(id) ON DELETE CASCADE);

CREATE INDEX comment_tip_id_index ON public.comment (tip_id);