# Student Verification App

A simple Spring Boot REST API where a student registers with name, email, phone and password. The app sends OTP to email and phone, verifies both OTPs, then allows login using JWT. After login, the student can upload documents to Cloudinary.

## Main Flow

1. Register student: `POST /api/auth/register`
2. Verify email OTP and phone OTP: `POST /api/auth/verify-otp`
3. Login: `POST /api/auth/login`
4. Upload document with JWT token: `POST /api/students/documents/upload`
5. View uploaded documents: `GET /api/students/documents`

## Tech Used

- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- H2 Database
- JavaMailSender for email OTP
- Twilio for phone OTP
- Cloudinary for document upload

## Required Environment Variables

For real email/SMS/Cloudinary testing, configure these:

```bash
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_gmail_app_password
TWILIO_ACCOUNT_SID=your_twilio_sid
TWILIO_AUTH_TOKEN=your_twilio_auth_token
TWILIO_FROM_PHONE=+1234567890
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
JWT_SECRET=change-this-secret-key-minimum-32-characters
```

If mail or Twilio values are empty, OTP will be printed in application logs so you can test locally.

## Run

```bash
mvn spring-boot:run
```

H2 Console:

```text
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:studentdb
Username: sa
Password: empty
```

## Postman Testing

### 1. Register

`POST http://localhost:8080/api/auth/register`

```json
{
  "name": "Amit Kumar",
  "email": "amit@example.com",
  "phoneNumber": "+919876543210",
  "password": "password123"
}
```

### 2. Verify OTP

`POST http://localhost:8080/api/auth/verify-otp`

```json
{
  "email": "amit@example.com",
  "emailOtp": "123456",
  "phoneOtp": "654321"
}
```

Use the OTPs received through email/SMS, or check application logs in local mode.

### 3. Login

`POST http://localhost:8080/api/auth/login`

```json
{
  "email": "amit@example.com",
  "password": "password123"
}
```

Copy the token from response.

### 4. Upload Document

`POST http://localhost:8080/api/students/documents/upload`

Headers:

```text
Authorization: Bearer YOUR_TOKEN_HERE
```

Body → form-data:

```text
key: file
value: select PDF / Word / image file
```

### 5. Get My Documents

`GET http://localhost:8080/api/students/documents`

Headers:

```text
Authorization: Bearer YOUR_TOKEN_HERE
```
