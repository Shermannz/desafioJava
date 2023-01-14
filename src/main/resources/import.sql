INSERT INTO tb_pessoa (name,birth_Date) VALUES ('Alex', '1990-05-05T20:00:00Z');
INSERT INTO tb_pessoa (name,birth_Date) VALUES ('Bob', '1996-12-25T03:50:00Z');
INSERT INTO tb_pessoa (name,birth_Date) VALUES ('Simon', '1899-01-01T13:20:00Z');

INSERT INTO tb_endereco (place,cep,num,city,pessoa_id,current) VALUES ('Rua Alex1',551567,50,'Alex1City',1L,true);
INSERT INTO tb_endereco (place,cep,num,city,pessoa_id,current) VALUES ('Rua Alex2',811250,102,'Alex2City',1L,false);

INSERT INTO tb_endereco (place,cep,num,city,pessoa_id,current) VALUES ('Rua Bob1',322116,132,'Bob1City',2L,true);
INSERT INTO tb_endereco (place,cep,num,city,pessoa_id,current) VALUES ('Rua Bob2',226341,22,'Bob2City',2L,false);

INSERT INTO tb_endereco (place,cep,num,city,pessoa_id,current) VALUES ('Rua Simon1',543216,777,'Simon1City',3L,false);
INSERT INTO tb_endereco (place,cep,num,city,pessoa_id,current) VALUES ('Rua Simon2',811250,102,'Simon2City',3L,true);