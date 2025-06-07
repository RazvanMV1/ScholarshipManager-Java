DROP TABLE burse_acordate CASCADE CONSTRAINTS;
DROP TABLE criterii_medie CASCADE CONSTRAINTS;
DROP TABLE criterii_sociale CASCADE CONSTRAINTS;
DROP TABLE criterii CASCADE CONSTRAINTS;
DROP TABLE burse CASCADE CONSTRAINTS;
DROP TABLE studenti CASCADE CONSTRAINTS;
DROP TABLE specializari CASCADE CONSTRAINTS;
DROP TABLE facultati CASCADE CONSTRAINTS;
DROP TABLE semestre_universitare CASCADE CONSTRAINTS;
/

CREATE TABLE facultati (
    id NUMBER PRIMARY KEY,
    denumire VARCHAR2(100) NOT NULL,
    buget_burse NUMBER(10,2) DEFAULT 0
);

CREATE SEQUENCE facultati_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_facultati_id
BEFORE INSERT ON facultati
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT facultati_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE specializari (
    id NUMBER PRIMARY KEY,
    denumire VARCHAR2(100) NOT NULL,
    facultate_id NUMBER,
    numar_locuri_burse NUMBER DEFAULT 0,
    CONSTRAINT fk_specializari_facultate
        FOREIGN KEY (facultate_id) REFERENCES facultati(id)
);

CREATE SEQUENCE specializari_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_specializari_id
BEFORE INSERT ON specializari
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT specializari_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE studenti (
    id NUMBER PRIMARY KEY,
    nume VARCHAR2(50) NOT NULL,
    prenume VARCHAR2(50) NOT NULL,
    cnp VARCHAR2(13) UNIQUE,
    email VARCHAR2(100),
    telefon VARCHAR2(15),
    specializare_id NUMBER,
    an_studiu NUMBER NOT NULL,
    medie_semestru_anterior NUMBER(4,2),
    venit_lunar NUMBER(8,2),
    CONSTRAINT fk_studenti_specializare
        FOREIGN KEY (specializare_id) REFERENCES specializari(id)
);

CREATE SEQUENCE studenti_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_studenti_id
BEFORE INSERT ON studenti
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT studenti_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE semestre_universitare (
    id NUMBER PRIMARY KEY,
    an_universitar NUMBER NOT NULL,
    semestru NUMBER NOT NULL, -- 1 sau 2
    data_inceput DATE,
    data_sfarsit DATE
);

CREATE SEQUENCE semestre_universitare_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_semestre_universitare_id
BEFORE INSERT ON semestre_universitare
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT semestre_universitare_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE burse (
    id NUMBER PRIMARY KEY,
    denumire VARCHAR2(100) NOT NULL,
    tip_bursa VARCHAR2(20) NOT NULL, -- MERIT, STUDIU, SOCIALA, etc.
    valoare NUMBER(10,2) NOT NULL,
    semestru_id NUMBER,
    numar_burse_disponibile NUMBER,
    CONSTRAINT fk_burse_semestru
        FOREIGN KEY (semestru_id) REFERENCES semestre_universitare(id)
);

CREATE SEQUENCE burse_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_burse_id
BEFORE INSERT ON burse
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT burse_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE criterii (
    id NUMBER PRIMARY KEY,
    denumire VARCHAR2(100) NOT NULL,
    tip_criteriu VARCHAR2(50) NOT NULL, -- MEDIE, SOCIAL, etc.
    pondere NUMBER(4,2) NOT NULL,
    bursa_id NUMBER,
    CONSTRAINT fk_criterii_bursa
        FOREIGN KEY (bursa_id) REFERENCES burse(id)
);

CREATE SEQUENCE criterii_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_criterii_id
BEFORE INSERT ON criterii
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT criterii_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

-- Exemplu: criteriu medie
CREATE TABLE criterii_medie (
    id NUMBER PRIMARY KEY,
    medie_minima NUMBER(4,2) NOT NULL,
    CONSTRAINT fk_criterii_medie_criteriu FOREIGN KEY (id) REFERENCES criterii(id)
);

-- Exemplu: criteriu social
CREATE TABLE criterii_sociale (
    id NUMBER PRIMARY KEY,
    venit_maxim NUMBER(10,2) NOT NULL,
    CONSTRAINT fk_criterii_sociale_criteriu FOREIGN KEY (id) REFERENCES criterii(id)
);

CREATE TABLE burse_acordate (
    id NUMBER PRIMARY KEY,
    student_id NUMBER,
    bursa_id NUMBER,
    data_acordare DATE,
    punctaj_total NUMBER(6,2),
    este_activa NUMBER(1) DEFAULT 1,
    CONSTRAINT fk_burse_acordate_student
        FOREIGN KEY (student_id) REFERENCES studenti(id),
    CONSTRAINT fk_burse_acordate_bursa
        FOREIGN KEY (bursa_id) REFERENCES burse(id)
);

CREATE SEQUENCE burse_acordate_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_burse_acordate_id
BEFORE INSERT ON burse_acordate
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT burse_acordate_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/
