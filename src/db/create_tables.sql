CREATE TABLE "authors" (
  "author_id" int,
  "name" text,
  PRIMARY KEY ("author_id")
);

CREATE TABLE "readers" (
  "reader_id" int,
  "name" text,
  "address" text,
  "phone" text,
  "status" varchar(10),
  PRIMARY KEY ("reader_id")
);

CREATE TABLE "publisher" (
  "publisher_id" int,
  "name" text,
  PRIMARY KEY ("publisher_id")
);

CREATE TABLE "books" (
  "isbn" char(13),
  "name" text,
  "authors_ids" int[],
  "publisher_id" int,
  PRIMARY KEY ("isbn")
);

CREATE TABLE "book_instances" (
  "instance_id" int,
  "isbn" char(13),
  "status" varchar(10),
  PRIMARY KEY ("instance_id")
);

CREATE TABLE "rents" (
  "reader_id" int,
  "book_instance_id" int,
  "open_date" date,
  "close_date" date,
  "who_gave" text,
  "who_recieved" text
);

