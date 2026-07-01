# Testing Data Guide

This document outlines the testing data available for the Insurance Policy & Claim Management System. You can populate this data by executing the `db/dummy_data.sql` script in your MySQL database.

## 1. Test Users & Credentials

All dummy users are pre-configured with verified emails and phone numbers. They use standard passwords based on their roles.

### Customers
**Password for all customers:** `Customer@123`

| Name | Email | Mobile |
| :--- | :--- | :--- |
| Rahul Sharma | rahul.sharma01@example.com | +919876540001 |
| Priya Verma | priya.verma01@example.com | +919876540002 |
| Amit Kumar | amit.kumar01@example.com | +919876540003 |
| Sneha Gupta | sneha.gupta01@example.com | +919876540004 |
| Karan Mehta | karan.mehta01@example.com | +919876540005 |
| Ananya Singh | ananya.singh01@example.com | +919876540006 |
| Vikram Patel | vikram.patel01@example.com | +919876540007 |
| Isha Kapoor | isha.kapoor01@example.com | +919876540008 |
| Rohit Arora | rohit.arora01@example.com | +919876540009 |
| Meera Joshi | meera.joshi01@example.com | +919876540010 |

### Internal Staff (Formerly Agents)
**Password for all staff:** `Agent@123`

| Name | Email | Mobile | Speciality |
| :--- | :--- | :--- | :--- |
| Health Staff | health.staff@example.com | +919876540101 | HEALTH |
| Life Staff | life.staff@example.com | +919676540102 | LIFE |
| Motor Staff | motor.staff@example.com | +919876540103 | MOTOR |
| Travel Staff | travel.staff@example.com | +919876540104 | TRAVEL |
| Insurance Staff| insurance.staff@example.com | +919876540105 | INSURANCE |

---

## 2. Insurance Products & Plans

Currently configured products and their respective plans available in the dummy database:

1. **Health Shield Pro** (`HEALTH`)
   - **Gold Health Plan**: ₹500,000 Coverage | ₹15,000 Premium (Annual) | 12 months

2. **Drive Safe Motor Insurance** (`MOTOR`)
   - **Comprehensive Auto Plan**: ₹1,000,000 Coverage | ₹25,000 Premium (Annual) | 1 year

3. **Life Term Premium** (`LIFE`)
   - **Secure Future Term**: ₹10,000,000 Coverage | ₹100,000 Premium (One-Time) | 30 years

---

## 3. How to Use Postman

1. **Set up Variables:** Ensure the `{{baseUrl}}` variable is set to `http://localhost:8080`.
2. **Login:** Call the `Login as Customer` or `Login as Agent` endpoints in the `1. Auth` folder. The tests script in the Postman collection will automatically capture the returned JWT and save it to the `{{token}}` variable.
3. **Execute Endpoints:** Other endpoints are configured to inherit auth, so they will use the `{{token}}` seamlessly.
