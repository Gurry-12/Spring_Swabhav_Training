# Frontend Integration Guide: Recent API Changes

This document outlines the recent backend changes and how they impact the frontend application. Please review these updates to ensure seamless integration.

## 1. DTO Field Changes (Monetary Values)
All monetary fields across the application have been migrated from `Double` to `BigDecimal` on the backend to prevent rounding errors.

**Impact on Frontend:**
- For JSON serialization, these fields will still arrive as `Number` types (e.g., `1000.50`), but they will no longer suffer from floating-point anomalies (e.g., `1000.499999999`).
- Ensure any client-side calculations (like remaining premium or claim amounts) use robust decimal math libraries (like `decimal.js` or `big.js`) or round to 2 decimal places to match the backend's strict scale.

**Affected Response DTOs:**
- `ClaimResponseDTO`: `claimAmount`
- `PlanResponseDTO`: `coverageAmount`, `premiumAmount`
- `PaymentResponseDTO`: `amount`
- `PolicyResponseDTO`: `coverageAmount`, `premiumAmount`, `totalPremiumPaid`, `remainingClaimAmount`

## 2. New Data in Responses
**API:** `GET /api/policies/{id}` and `GET /api/policies/customer`
- The `PolicyResponseDTO` now includes a new field: `remainingClaimAmount`.
- **Usage:** You can now display the remaining coverage available to the customer directly on the policy details page without calculating it manually on the frontend.
```json
{
  "policyId": 12,
  "coverageAmount": 500000.00,
  "remainingClaimAmount": 450000.00
}
```

## 3. New Validation Errors to Handle
The frontend should gracefully handle the following new `400 Bad Request` exceptions by displaying appropriate toast notifications or field errors to the user.

### Policy Purchase & Issuance
**APIs:** `POST /api/policies/purchase` and `POST /api/policies/issue`
- **Start Date:** The user cannot select a start date in the past.
  - *Error Message:* `"Policy start date cannot be in the past."*

### Policy Cancellation
**API:** `PUT /api/policies/{id}/cancel`
- **Active Claims Check:** Customers/Admins can no longer cancel a policy if there is an active, unresolved claim (`SUBMITTED`, `UNDER_REVIEW`, `RECOMMENDED_FOR_APPROVAL`).
  - *Error Message:* `"Policy cannot be cancelled while a claim is still pending or under review."*

### Premium Payment
**API:** `POST /api/payments`
- **Overpayment Guard:** The backend now strictly validates that payments do not exceed the total required premium for the full duration of the policy.
  - *Error Message:* `"Total payments would exceed the required premium for this policy."*
- **Status Requirement:** `paymentStatus` is now strictly validated and cannot be null.

### Claim Document Upload
**API:** `POST /api/claims/{claimId}/upload`
- **File Type Restrictions:** Only JPEG, PNG, and PDF files are accepted.
  - *Error Message:* `"Only JPEG, PNG, and PDF documents are accepted."*
  - **Frontend Action:** Please update your file picker `<input type="file" accept="image/jpeg, image/png, application/pdf">` to match this requirement.
- **File Size Restrictions:** Files cannot exceed 10 MB.
  - *Error Message:* `"Each document must not exceed 10 MB in size."*
  - **Frontend Action:** Add client-side validation to block files > 10MB before sending the request.

## 4. Policy Plan Updates (Admin Portal)
**API:** `PUT /api/admin/plans/{planId}`
- **Coverage vs Premium:** The backend now strictly enforces that `coverageAmount` must be strictly greater than `premiumAmount`.
  - *Error Message:* `"Coverage amount must strictly exceed the premium amount."*
- **Duration Cap:** Policy plans are now capped at a maximum duration of 40 years.
  - *Error Message:* `"Duration cannot exceed 40 years"*
