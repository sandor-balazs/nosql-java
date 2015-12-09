USE ontime;

CREATE TABLE IF NOT EXISTS allocation (
    id uuid,
    fte float,
    year int,
    month int,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS country (
    id uuid,
    code text,
    name text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS department (
    id uuid,
    name text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS employee (
    id uuid,
    firstName text,
    lastName text,
    userId text,
    phone text,
    email text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS employment (
    id uuid,
    fte float,
    startDate timestamp,
    endDate timestamp,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS funding (
    id uuid,
    name text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS location (
    id uuid,
    name text,
    address text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS milestone (
    id uuid,
    name text,
    appOrder int,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS projectCountry (
    id uuid,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS projectCountryMilestone (
    id uuid,
    planned timestamp,
    actual timestamp,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS project (
    id uuid,
    code text,
    title text,
    description text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS projectMilestone (
    id uuid,
    planned timestamp,
    actual timestamp,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS projectRegion (
    id uuid,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS projectRegionMilestone (
    id uuid,
    planned timestamp,
    actual timestamp,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS region (
    id uuid,
    code text,
    name text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS skill (
    id uuid,
    name text,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS status (
    id uuid,
    name text,
    appOrder int,
    PRIMARY KEY(id)
);
