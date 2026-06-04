-- =============================================================
--  hotel-manager · testovacie dáta
--  Súbor: backend/src/main/resources/data.sql
--  Spúšťa sa automaticky Springom PO tom, ako Hibernate vytvorí
--  tabuľky (ddl-auto=update + defer-datasource-initialization=true)
--
--  Prihlasovacie údaje (heslo: "heslo"):
--    admin@gmail.com    – ADMIN
--    manager1@gmail.com – MANAGER  (spravuje hotel 1 a 3)
--    manager2@gmail.com – MANAGER  (spravuje hotel 2)
--    user1@gmail.com    – USER
--    user2@gmail.com    – USER
-- =============================================================

-- -------------------------------------------------------------
-- USERS  (hotel_id = NULL, priradenie cez hotels.manager_id)
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

(4, 'user1@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Ján Sloboda', 'USER', NULL, NOW(), NULL),

(5, 'user2@gmail.com',
 '$2a$10$yOAh1lzwcGcgbx3OBAQFiecPX.VrAHXvSO5GiTtogEHncqPe5Jaom',
 'Mária Horáková', 'USER', NULL, NOW(), NULL);

-- -------------------------------------------------------------
-- HOTELS  (manager_id → users.id)
-- manager1 (id=2) spravuje hotel 1 a 3
-- manager2 (id=3) spravuje hotel 2
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
 NOW(), 2);

-- -------------------------------------------------------------
-- ROOMS
-- RoomType:   APARTMENT | THREE_BED | TWO_BED
-- RoomStatus: AVAILABLE | OCCUPIED  | OUT_OF_SERVICE
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

-- -------------------------------------------------------------
-- BOOKINGS
-- user1 (id=4) a user2 (id=5) majú vlastné rezervácie
-- manager1 (id=2) a manager2 (id=3) tiež majú pár rezervácií
-- BookingStatus: PENDING | CONFIRMED | CANCELLED
-- total_price = počet nocí × price_per_night
-- -------------------------------------------------------------
INSERT IGNORE INTO bookings (id, from_date, to_date, status, created_at, user_id, guest_name, room_id, total_price, note) VALUES

-- user1 rezervácie
(1, '2026-06-10', '2026-06-13', 'CONFIRMED', NOW(), 4, 'Ján Sloboda',    2,   267.00, 'Prosím tichú izbu'),
(2, '2026-06-15', '2026-06-20', 'CONFIRMED', NOW(), 4, 'Ján Sloboda',    9,   550.00, NULL),
(3, '2026-07-10', '2026-07-12', 'PENDING',   NOW(), 4, 'Ján Sloboda',    5,   390.00, NULL),
(4, '2026-05-01', '2026-05-03', 'CONFIRMED', NOW(), 4, 'Ján Sloboda',    1,   178.00, NULL),
(5, '2026-04-15', '2026-04-17', 'CANCELLED', NOW(), 4, 'Ján Sloboda',    7,   150.00, 'Zrušené'),

-- user2 rezervácie
(6, '2026-06-18', '2026-06-21', 'PENDING',   NOW(), 5, 'Mária Horáková', 13,  315.00, 'Neskorý príchod po 22:00'),
(7, '2026-07-01', '2026-07-07', 'CONFIRMED', NOW(), 5, 'Mária Horáková', 10, 1080.00, 'Výhľad na hory'),
(8, '2026-04-20', '2026-04-23', 'CONFIRMED', NOW(), 5, 'Mária Horáková', 12,  315.00, NULL),
(9, '2026-05-10', '2026-05-14', 'CANCELLED', NOW(), 5, 'Mária Horáková', 7,   300.00, 'Zrušené zo zdravotných dôvodov'),

-- manager1 rezervácie (id=2)
(10, '2026-06-20', '2026-06-23', 'CONFIRMED', NOW(), 2, 'Peter Novák',   3,   360.00, NULL),
(11, '2026-07-05', '2026-07-08', 'PENDING',   NOW(), 2, 'Peter Novák',   14,  435.00, NULL),

-- manager2 rezervácie (id=3)
(12, '2026-06-25', '2026-06-28', 'CONFIRMED', NOW(), 3, 'Jana Kováčová', 8,   225.00, NULL);