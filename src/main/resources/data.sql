-- Insert sample trains if they don't exist (avoids duplicates in tests/restarts)
INSERT INTO train (route, departure_time, available_seats)
SELECT 'NYC to Boston', TIMESTAMP '2023-10-01 10:00:00', 100
WHERE NOT EXISTS (SELECT 1 FROM train WHERE route = 'NYC to Boston');

INSERT INTO train (route, departure_time, available_seats)
SELECT 'Boston to NYC', TIMESTAMP '2023-10-02 14:00:00', 50
WHERE NOT EXISTS (SELECT 1 FROM train WHERE route = 'Boston to NYC');
