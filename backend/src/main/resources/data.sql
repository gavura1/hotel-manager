-- =============================================================
--  hotel-manager · testovacie dáta
--  Súbor: backend/src/main/resources/data.sql
--
--  Prihlasovacie údaje (heslo: "heslo"):
--    admin@gmail.com    – ADMIN
--    manager1@gmail.com – MANAGER  (spravuje hotel 1 a 3)
--    manager2@gmail.com – MANAGER  (spravuje hotel 2)
--    manager3@gmail.com – MANAGER  (spravuje hotel 4)
--    user1@gmail.com    – USER
--    user2@gmail.com    – USER
--    user3@gmail.com    – USER
--    user4@gmail.com    – USER
--    user5@gmail.com    – USER
-- =============================================================

-- -------------------------------------------------------------
-- USERS
-- -------------------------------------------------------------
INSERT IGNORE INTO users (id, email, password, name, role, google_id, created_at, hotel_id) VALUES
(1, 'admin@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Admin Systému', 'ADMIN', NULL, NOW(), NULL),

(2, 'manager1@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Peter Novák', 'MANAGER', NULL, NOW(), NULL),

(3, 'manager2@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Jana Kováčová', 'MANAGER', NULL, NOW(), NULL),

(4, 'manager3@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Tomáš Horváth', 'MANAGER', NULL, NOW(), NULL),

(5, 'user1@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Ján Sloboda', 'USER', NULL, NOW(), NULL),

(6, 'user2@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Mária Horáková', 'USER', NULL, NOW(), NULL),

(7, 'user3@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Lucia Procházková', 'USER', NULL, NOW(), NULL),

(8, 'user4@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Martin Krajčí', 'USER', NULL, NOW(), NULL),

(9, 'user5@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Zuzana Benková', 'USER', NULL, NOW(), NULL);

-- -------------------------------------------------------------
-- HOTELS
-- manager1 (id=2) → hotel 1, 3
-- manager2 (id=3) → hotel 2
-- manager3 (id=4) → hotel 4
-- -------------------------------------------------------------
INSERT IGNORE INTO hotels (id, name, address, description, created_at, manager_id) VALUES
(1, 'Grand Hotel Banská Bystrica',
 'Námestie SNP 1, 974 01 Banská Bystrica',
 'Luxusný štvorhviezdičkový hotel v centre mesta s výhľadom na historické námestie.',
 NOW(), 2),

(2, 'Alpine Resort Jasná',
 'Demänovská Dolina 72, 031 01 Liptovský Mikuláš',
 'Horský resort priamo pri lyžiarskych tratiach Jasnej. Ideálny pre zimnú aj letnú dovolenku.',
 NOW(), 3),

(3, 'City Boutique Hotel Bratislava',
 'Obchodná 15, 811 06 Bratislava',
 'Moderný butikový hotel v srdci Bratislavy, kúsok od Starého mesta.',
 NOW(), 2),

(4, 'Thermal Hotel Piešťany',
 'Winterova 41, 921 29 Piešťany',
 'Relaxačný hotel s termálnymi kúpeľmi priamo na Kúpeľnom ostrove.',
 NOW(), 4);

-- -------------------------------------------------------------
-- ROOMS
-- -------------------------------------------------------------

-- Grand Hotel Banská Bystrica (hotel_id = 1)
INSERT IGNORE INTO rooms (id, room_number, room_type, room_status, price_per_night, capacity, hotel_id) VALUES
(1,  '101', 'TWO_BED',   'AVAILABLE',        89.00, 2, 1),
(2,  '102', 'TWO_BED',   'OCCUPIED',         89.00, 2, 1),
(3,  '103', 'THREE_BED', 'AVAILABLE',       120.00, 3, 1),
(4,  '201', 'THREE_BED', 'AVAILABLE',       120.00, 3, 1),
(5,  '202', 'APARTMENT', 'AVAILABLE',       195.00, 4, 1),
(6,  '203', 'APARTMENT', 'OUT_OF_SERVICE',  195.00, 4, 1);

-- Alpine Resort Jasná (hotel_id = 2)
INSERT IGNORE INTO rooms (id, room_number, room_type, room_status, price_per_night, capacity, hotel_id) VALUES
(7,  '101', 'TWO_BED',   'AVAILABLE',        75.00, 2, 2),
(8,  '102', 'TWO_BED',   'AVAILABLE',        75.00, 2, 2),
(9,  '103', 'THREE_BED', 'OCCUPIED',        110.00, 3, 2),
(10, '201', 'APARTMENT', 'AVAILABLE',       180.00, 5, 2),
(11, '202', 'APARTMENT', 'AVAILABLE',       180.00, 5, 2);

-- City Boutique Hotel Bratislava (hotel_id = 3)
INSERT IGNORE INTO rooms (id, room_number, room_type, room_status, price_per_night, capacity, hotel_id) VALUES
(12, '101', 'TWO_BED',   'AVAILABLE',       105.00, 2, 3),
(13, '102', 'TWO_BED',   'OCCUPIED',        105.00, 2, 3),
(14, '201', 'THREE_BED', 'AVAILABLE',       145.00, 3, 3),
(15, '202', 'APARTMENT', 'AVAILABLE',       220.00, 4, 3);

-- Thermal Hotel Piešťany (hotel_id = 4)
INSERT IGNORE INTO rooms (id, room_number, room_type, room_status, price_per_night, capacity, hotel_id) VALUES
(16, '101', 'TWO_BED',   'AVAILABLE',        95.00, 2, 4),
(17, '102', 'TWO_BED',   'AVAILABLE',        95.00, 2, 4),
(18, '103', 'TWO_BED',   'OCCUPIED',         95.00, 2, 4),
(19, '201', 'THREE_BED', 'AVAILABLE',       135.00, 3, 4),
(20, '202', 'APARTMENT', 'AVAILABLE',       210.00, 4, 4),
(21, '203', 'APARTMENT', 'OUT_OF_SERVICE',  210.00, 4, 4);

-- -------------------------------------------------------------
-- BOOKINGS — iba useri (id 5-9)
-- -------------------------------------------------------------
INSERT IGNORE INTO bookings (id, from_date, to_date, status, created_at, user_id, guest_name, room_id, total_price, note) VALUES

-- user1 (Ján Sloboda) — rezervácie v rôznych hoteloch
(1,  '2026-06-10', '2026-06-13', 'CONFIRMED', NOW(), 5, 'Ján Sloboda',      2,   267.00, 'Prosím tichú izbu'),
(2,  '2026-06-15', '2026-06-20', 'CONFIRMED', NOW(), 5, 'Ján Sloboda',      9,   550.00, NULL),
(3,  '2026-07-10', '2026-07-12', 'PENDING',   NOW(), 5, 'Ján Sloboda',      5,   390.00, NULL),
(4,  '2026-08-01', '2026-08-05', 'CONFIRMED', NOW(), 5, 'Ján Sloboda',     16,   380.00, 'Termálne kúpele'),
(5,  '2026-05-01', '2026-05-03', 'CONFIRMED', NOW(), 5, 'Ján Sloboda',      1,   178.00, NULL),
(6,  '2026-03-10', '2026-03-12', 'CANCELLED', NOW(), 5, 'Ján Sloboda',      7,   150.00, 'Zrušené — pracovné dôvody'),
(7,  '2025-12-26', '2025-12-30', 'CONFIRMED', NOW(), 5, 'Ján Sloboda',     10,   720.00, 'Silvester v horách'),

-- user2 (Mária Horáková) — rezervácie v rôznych hoteloch
(8,  '2026-06-18', '2026-06-21', 'PENDING',   NOW(), 6, 'Mária Horáková',  13,   315.00, 'Neskorý príchod po 22:00'),
(9,  '2026-07-01', '2026-07-07', 'CONFIRMED', NOW(), 6, 'Mária Horáková',  10,  1080.00, 'Výhľad na hory'),
(10, '2026-09-15', '2026-09-18', 'CONFIRMED', NOW(), 6, 'Mária Horáková',  20,   630.00, 'Relaxačný pobyt'),
(11, '2026-04-20', '2026-04-23', 'CONFIRMED', NOW(), 6, 'Mária Horáková',  12,   315.00, NULL),
(12, '2026-05-10', '2026-05-14', 'CANCELLED', NOW(), 6, 'Mária Horáková',   7,   300.00, 'Zrušené zo zdravotných dôvodov'),
(13, '2025-11-20', '2025-11-23', 'CONFIRMED', NOW(), 6, 'Mária Horáková',  14,   435.00, NULL),

-- user3 (Lucia Procházková)
(14, '2026-06-25', '2026-06-28', 'CONFIRMED', NOW(), 7, 'Lucia Procházková', 3,  360.00, NULL),
(15, '2026-07-15', '2026-07-20', 'PENDING',   NOW(), 7, 'Lucia Procházková', 17, 475.00, 'Kúpeľný pobyt'),
(16, '2026-04-05', '2026-04-07', 'CONFIRMED', NOW(), 7, 'Lucia Procházková', 8,  150.00, NULL),
(17, '2026-02-14', '2026-02-16', 'CANCELLED', NOW(), 7, 'Lucia Procházková', 12, 210.00, 'Valentínsky pobyt — zrušený'),

-- user4 (Martin Krajčí)
(18, '2026-07-20', '2026-07-25', 'CONFIRMED', NOW(), 8, 'Martin Krajčí',    5,  975.00, 'Rodinná dovolenka'),
(19, '2026-08-10', '2026-08-13', 'PENDING',   NOW(), 8, 'Martin Krajčí',   19,  405.00, NULL),
(20, '2026-05-20', '2026-05-22', 'CONFIRMED', NOW(), 8, 'Martin Krajčí',    7,  150.00, NULL),
(21, '2026-03-01', '2026-03-03', 'CANCELLED', NOW(), 8, 'Martin Krajčí',   13,  210.00, 'Zrušené'),
(22, '2025-10-10', '2025-10-14', 'CONFIRMED', NOW(), 8, 'Martin Krajčí',   11,  720.00, 'Jesenná dovolenka'),

-- user5 (Zuzana Benková)
(23, '2026-06-28', '2026-07-02', 'CONFIRMED', NOW(), 9, 'Zuzana Benková',   4,  480.00, NULL),
(24, '2026-08-20', '2026-08-24', 'PENDING',   NOW(), 9, 'Zuzana Benková',  20,  840.00, 'Kúpeľný pobyt'),
(25, '2026-04-12', '2026-04-15', 'CONFIRMED', NOW(), 9, 'Zuzana Benková',  14,  435.00, NULL),
(26, '2026-01-05', '2026-01-08', 'CANCELLED', NOW(), 9, 'Zuzana Benková',   3,  360.00, 'Zrušené — choroba'),
(27, '2025-09-15', '2025-09-19', 'CONFIRMED', NOW(), 9, 'Zuzana Benková',   8,  300.00, 'Letná dovolenka');