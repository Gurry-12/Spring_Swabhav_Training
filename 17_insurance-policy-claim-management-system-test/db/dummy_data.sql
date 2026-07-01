-- dummy_data.sql
-- Contains sample data for testing, including your provided customers and staff. 
-- The default password for customers is 'Customer@123'
-- The default password for internal staff (agents) is 'Agent@123'

-- Create database if you haven't already:
-- CREATE DATABASE IF NOT EXISTS insurance_test_db;
-- USE insurance_test_db;

-- ==========================================
-- 1. INSERT USERS (Customers and Staff)
-- ==========================================

-- Customers (Password: Customer@123)
INSERT INTO users (email_verified, is_active, phone_verified, created_date, updated_date, full_name, email, mobile_number, password, role)
VALUES
(1, 1, 1, NOW(), NOW(), 'Rahul Sharma', 'rahul.sharma01@example.com', '+919876540001', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Priya Verma', 'priya.verma01@example.com', '+919876540002', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Amit Kumar', 'amit.kumar01@example.com', '+919876540003', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Sneha Gupta', 'sneha.gupta01@example.com', '+919876540004', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Karan Mehta', 'karan.mehta01@example.com', '+919876540005', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Ananya Singh', 'ananya.singh01@example.com', '+919876540006', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Vikram Patel', 'vikram.patel01@example.com', '+919876540007', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Isha Kapoor', 'isha.kapoor01@example.com', '+919876540008', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Rohit Arora', 'rohit.arora01@example.com', '+919876540009', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER'),
(1, 1, 1, NOW(), NOW(), 'Meera Joshi', 'meera.joshi01@example.com', '+919876540010', '$2a$10$2svs5HyjgPpKo/VO.5HIh.SHmYLNTqdsniBreMinKCN1HhZP1vwu2', 'ROLE_CUSTOMER');

-- Internal Staff / Agents (Password: Agent@123)
INSERT INTO users (email_verified, is_active, phone_verified, created_date, updated_date, full_name, email, mobile_number, password, role)
VALUES
(1, 1, 1, NOW(), NOW(), 'Health Staff', 'health.staff@example.com', '+919876540101', '$2a$10$sWoTTbjF80Shszc0NQrlEOATAIaGO5ZCmzm/4/zpjMreHry4WwCaS', 'ROLE_INTERNAL_STAFF'),
(1, 1, 1, NOW(), NOW(), 'Life Staff', 'life.staff@example.com', '+919676540102', '$2a$10$sWoTTbjF80Shszc0NQrlEOATAIaGO5ZCmzm/4/zpjMreHry4WwCaS', 'ROLE_INTERNAL_STAFF'),
(1, 1, 1, NOW(), NOW(), 'Motor Staff', 'motor.staff@example.com', '+919876540103', '$2a$10$sWoTTbjF80Shszc0NQrlEOATAIaGO5ZCmzm/4/zpjMreHry4WwCaS', 'ROLE_INTERNAL_STAFF'),
(1, 1, 1, NOW(), NOW(), 'Travel Staff', 'travel.staff@example.com', '+919876540104', '$2a$10$sWoTTbjF80Shszc0NQrlEOATAIaGO5ZCmzm/4/zpjMreHry4WwCaS', 'ROLE_INTERNAL_STAFF'),
(1, 1, 1, NOW(), NOW(), 'Insurance Staff', 'insurance.staff@example.com', '+919876540105', '$2a$10$sWoTTbjF80Shszc0NQrlEOATAIaGO5ZCmzm/4/zpjMreHry4WwCaS', 'ROLE_INTERNAL_STAFF');


-- ==========================================
-- 2. INSERT CUSTOMERS
-- ==========================================
INSERT INTO customers (created_date, updated_date, date_of_birth, user_id, address, city, state, pin_code, nominee_name, nominee_relation)
VALUES
(NOW(), NOW(), '1995-03-15', (SELECT id FROM users WHERE email='rahul.sharma01@example.com'), '12 MG Road', 'Jaipur', 'Rajasthan', '302001', 'Pooja Sharma', 'Sister'),
(NOW(), NOW(), '1992-07-21', (SELECT id FROM users WHERE email='priya.verma01@example.com'), '45 Civil Lines', 'Lucknow', 'Uttar Pradesh', '226001', 'Rajesh Verma', 'Father'),
(NOW(), NOW(), '1998-11-09', (SELECT id FROM users WHERE email='amit.kumar01@example.com'), '78 Park Street', 'Kolkata', 'West Bengal', '700016', 'Sunita Kumar', 'Mother'),
(NOW(), NOW(), '1994-01-18', (SELECT id FROM users WHERE email='sneha.gupta01@example.com'), '22 FC Road', 'Pune', 'Maharashtra', '411004', 'Ankit Gupta', 'Brother'),
(NOW(), NOW(), '1997-09-30', (SELECT id FROM users WHERE email='karan.mehta01@example.com'), '91 Ring Road', 'Ahmedabad', 'Gujarat', '380015', 'Neha Mehta', 'Wife'),
(NOW(), NOW(), '1996-12-12', (SELECT id FROM users WHERE email='ananya.singh01@example.com'), '10 Sector 15', 'Chandigarh', 'Chandigarh', '160015', 'Harpreet Singh', 'Father'),
(NOW(), NOW(), '1993-05-26', (SELECT id FROM users WHERE email='vikram.patel01@example.com'), '5 Banjara Hills', 'Hyderabad', 'Telangana', '500034', 'Ritu Patel', 'Spouse'),
(NOW(), NOW(), '1999-08-14', (SELECT id FROM users WHERE email='isha.kapoor01@example.com'), '61 Indiranagar', 'Bengaluru', 'Karnataka', '560038', 'Sanjay Kapoor', 'Father'),
(NOW(), NOW(), '1991-04-07', (SELECT id FROM users WHERE email='rohit.arora01@example.com'), '14 Connaught Place', 'New Delhi', 'Delhi', '110001', 'Asha Arora', 'Mother'),
(NOW(), NOW(), '1998-10-22', (SELECT id FROM users WHERE email='meera.joshi01@example.com'), '33 Race Course Road', 'Indore', 'Madhya Pradesh', '452001', 'Mohit Joshi', 'Brother');


-- ==========================================
-- 3. INSERT STAFF SPECIALITIES 
-- (Replaces old agent_specialities)
-- ==========================================
INSERT INTO staff_specialities (product_speciality, user_id)
VALUES
('HEALTH', (SELECT id FROM users WHERE email='health.staff@example.com')),
('LIFE', (SELECT id FROM users WHERE email='life.staff@example.com')),
('MOTOR', (SELECT id FROM users WHERE email='motor.staff@example.com')),
('TRAVEL', (SELECT id FROM users WHERE email='travel.staff@example.com')),
('INSURANCE', (SELECT id FROM users WHERE email='insurance.staff@example.com'));


-- ==========================================
-- 4. UTILITY UPDATES
-- ==========================================
-- Assign a staff member to claims that do not have one assigned yet
UPDATE claims
SET assigned_staff_id = (SELECT id FROM users WHERE email='health.staff@example.com')
WHERE assigned_staff_id IS NULL;
