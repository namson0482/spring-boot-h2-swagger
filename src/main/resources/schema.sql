
create table device(
  deviceid     varchar(20),
  latitude     decimal(7,4),
  longitude     decimal(7,4),
  constraint pk_device primary key (deviceid)
);

create table weather(
  id    bigint auto_increment,
  deviceid     varchar(20),
  humidity     varchar(20),
  created DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  constraint pk_weather primary key (id)
);

create table temperature(
  id    bigint auto_increment,
  weatherid     bigint,
  unit     varchar(20),
  value     decimal(7,4),
  constraint pk_temperature primary key (id)
);