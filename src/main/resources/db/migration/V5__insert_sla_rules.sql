INSERT INTO sla_rules (category_id, priority, response_time_minutes, execution_time_minutes) VALUES
    (1, 'LOW', 120, 720),
    (1, 'MEDIUM', 60, 240),
    (1, 'HIGH', 20, 120),
    (1, 'CRITICAL', 10, 60),

    (2, 'LOW', 240, 1440),
    (2, 'MEDIUM', 120, 720),
    (2, 'HIGH', 60, 240),
    (2, 'CRITICAL', 30, 120),

    (3, 'LOW', 240, 1440),
    (3, 'MEDIUM', 120, 480),
    (3, 'HIGH', 60, 240),
    (3, 'CRITICAL', 30, 120),

    (4, 'LOW', 480, 2880 ),
    (4, 'MEDIUM', 240, 1440 ),
    (4, 'HIGH', 120, 720 ),
    (4, 'CRITICAL', 60, 240 ),

    (5, 'LOW', 720, 2880),
    (5, 'MEDIUM', 480, 1440),
    (5, 'HIGH', 240, 720),
    (5, 'CRITICAL', 120, 360),

    (6, 'LOW', 180, 1440),
    (6, 'MEDIUM', 120, 720),
    (6, 'HIGH', 60, 240),
    (6, 'CRITICAL', 30, 120),

    (7, 'LOW', 60, 240),
    (7, 'MEDIUM', 30, 120),
    (7, 'HIGH', 10, 60),
    (7, 'CRITICAL', 5, 30),

    (8, 'LOW', 60, 360),
    (8, 'MEDIUM', 30, 180),
    (8, 'HIGH', 10, 60),
    (8, 'CRITICAL', 5, 30);