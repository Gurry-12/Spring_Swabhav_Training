# QA & Testing Guide for Recent Fixes

This guide provides step-by-step instructions on how to manually verify the recent backend fixes using Postman or a similar API testing tool.

## 1. Test Financial Precision (BigDecimal)
**Goal:** Verify that claims and payments process large/fractional numbers without rounding errors.
*   **Step 1:** As an Admin, create a `PolicyPlan` with a `premiumAmount` of `1000.50` and `coverageAmount` of `50000.99`.
*   **Step 2:** Purchase the policy.
*   **Step 3:** Pay the premium with exactly `1000.50`.
*   **Step 4:** Raise a claim for `10000.10`.
*   **Expected Result:** The `remainingClaimAmount` in the policy response should accurately reflect `40000.89` with no trailing nines (e.g., not `40000.889999999`).

## 2. Test Policy Cancellation Guard
**Goal:** Verify that a policy cannot be cancelled if there are pending claims.
*   **Step 1:** Purchase a policy and pay the premium to make it `ACTIVE`.
*   **Step 2:** Raise a claim against this policy (status becomes `SUBMITTED`).
*   **Step 3:** Call `PUT /api/policies/{policyId}/cancel`.
*   **Expected Result:** 400 Bad Request. Error: *"Policy cannot be cancelled while a claim is still pending or under review."*
*   **Step 4:** Have an admin approve or reject the claim.
*   **Step 5:** Call the cancel API again.
*   **Expected Result:** 200 OK. The policy successfully changes to `CANCELLED`.

## 3. Test Past Start Date Validation
**Goal:** Prevent purchasing a policy with a start date in the past.
*   **Step 1:** Call `POST /api/policies/purchase` with `"startDate": "2020-01-01"`.
*   **Expected Result:** 400 Bad Request. Error: *"Policy start date cannot be in the past."*

## 4. Test Overpayment Prevention
**Goal:** Verify a customer cannot pay more than the total required premium for the policy's duration.
*   **Step 1:** Purchase a policy with a plan that has `duration: 1` (year) and `premiumAmount: 1000`. Total required is 1000.
*   **Step 2:** Make a payment for `1000`. Policy becomes `ACTIVE`.
*   **Step 3:** Attempt to make another payment for `100` on the same policy.
*   **Expected Result:** 400 Bad Request. Error: *"Total payments would exceed the required premium for this policy."*

## 5. Test File Validation (Size and Type)
**Goal:** Ensure only safe files are uploaded for claims.
*   **Step 1:** Create a mock text file (`test.txt`) and try to upload it to `POST /api/claims/{claimId}/upload`.
*   **Expected Result:** 400 Bad Request. Error: *"Only JPEG, PNG, and PDF documents are accepted."*
*   **Step 2:** Find a PDF or image larger than 10MB and attempt to upload it.
*   **Expected Result:** 400 Bad Request. Error: *"Each document must not exceed 10 MB in size."*

## 6. Test OTP Security & Expiry
**Goal:** Ensure OTPs are hashed and cannot be reused/guessed.
*   **Step 1:** Register a new user. The DB will now store hashed strings in the `emailOtp` and `phoneOtp` columns of the `otp_verifications` table.
*   **Step 2:** Attempt to verify the account `POST /api/auth/verify-otp` using a wrong 6-digit number.
*   **Expected Result:** 400 Bad Request. Error: *"Invalid email OTP"* (the BCrypt comparison fails securely).

## 7. Test Performance / Pagination (EAGER Fetch Fix)
**Goal:** Ensure fetching plans doesn't trigger N+1 queries.
*   **Step 1:** Clear your application logs or watch your console.
*   **Step 2:** Call `GET /api/plans` (fetching multiple plans).
*   **Expected Result:** You should see exactly one SQL `SELECT` query executed by Hibernate for the plans, rather than 1 query for the plans and N additional queries fetching the `InsuranceProduct` for each plan.
