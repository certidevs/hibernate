-- CTE

/*
WITH cte_name (column_list) AS (
	SELECT * FROM .....
)
statement;
*/

-- ejemplo 1
WITH numbers AS (
    SELECT * FROM generate_series(1,10)  AS id
)
SELECT * FROM numbers;

-- ejemplo 2
WITH numbers AS (
    SELECT * FROM generate_series(
                          '2021-01-01 00:00'::timestamp,
                          '2021-09-22 00:00',
                          '6 hours'
                      )  AS creation_date
)
SELECT * FROM numbers;


-- ejemplo 3
select * from ticket_flights;


WITH cte_price AS (
    SELECT flight_id, fare_conditions, amount, (
        CASE
            WHEN amount < 10000 THEN 'CHEAP'
            WHEN amount < 30000 THEN 'MEDIUM'
            ELSE 'EXPENSIVE'
            END
        ) AS flight_price FROM ticket_flights
)
SELECT * FROM cte_price WHERE flight_price = 'CHEAP' OR flight_price = 'EXPENSIVE';


-- ejemplo 4: borrar datos de una tabla original hacia una tabla histórico
select * from seats;
select * from seats_archive;

drop table if exists seats_archive;

CREATE TABLE seats_archive AS SELECT * FROM seats LIMIT 0;

WITH cte_seats_archive_one AS (
DELETE FROM seats
WHERE aircraft_code = '319' and seat_no = '2C'
    RETURNING *
)
INSERT INTO seats_archive
SELECT * FROM cte_seats_archive_one;

WITH cte_seats_archive_all AS (
DELETE FROM seats
                RETURNING *
)
INSERT INTO seats_archive
SELECT * FROM cte_seats_archive_all;
