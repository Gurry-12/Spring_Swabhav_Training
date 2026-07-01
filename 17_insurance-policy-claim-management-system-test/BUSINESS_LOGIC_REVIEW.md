# Business Logic Review

Below is a comprehensive review of the business logic within the core service implementations (`ClaimServiceImpl`, `PolicyServiceImpl`, `PremiumPaymentServiceImpl`). The review focuses on missing validations, logical flaws, concurrency issues, and standard insurance domain violations.

## 1. ClaimServiceImpl

### Missing Validations & Edge Cases
* **Negative Claim Amounts**: In `raiseClaim()`, there is no validation to ensure that the `claimAmount` is greater than `0`. A user could submit a negative claim amount, which would artificially increase their remaining coverage when subtracted from the total.
* **Date Validations**: While the incident date is checked to be within the policy period, there is no validation on how far back a claim can be submitted (e.g., standard insurance often requires claims to be filed within 30-90 days of the incident).

### Concurrency Issues
* **Coverage Limit Bypass**: When calculating `remainingCoverage` to verify if the claim exceeds the limit (`dto.getClaimAmount().compareTo(remainingCoverage) > 0`), there is no locking mechanism (such as `@Lock(LockModeType.PESSIMISTIC_WRITE)`). If a customer submits two claims simultaneously, both transactions could read the same `remainingCoverage`, allowing the total claimed amount to exceed the maximum coverage limit.

---

## 2. PolicyServiceImpl

### Logical Flaws & Inconsistencies
* **Policy Cancellation Constraints**: In `cancelPolicy(Long policyId)`, there is no check to see if the policy is already `CANCELLED` or `EXPIRED`. This allows redundant status updates.
* **Missing Ownership Verification**: The `cancelPolicy` method lacks authorization checks to ensure that the user canceling the policy actually owns it, or that they have the required ADMIN/AGENT role. (Even if handled at the controller level, defense-in-depth is recommended).
* **No Refund Logic**: Canceling an active policy does not trigger any refund calculations or partial premium returns, which is a standard requirement in insurance platforms.
* **Duplicate Policies**: `purchasePolicy` prevents multiple active HEALTH plans but explicitly allows a user to purchase the same non-HEALTH plan multiple times. Depending on domain requirements, this might be a logical flaw.

---

## 3. PremiumPaymentServiceImpl

### Business Rule Violations
* **Annual Premium Due Dates**: In `recordPayment()` for `ANNUAL` plans, the next eligible payment date is calculated as `latestPayment.getPaymentDate().plusYears(1)`. This is a significant business flaw. Premium due dates should be anchored to the **policy start date's anniversary**, not the date the last payment was made. If a customer pays 2 months late, their next premium due date is permanently shifted forward by 2 months, giving them free coverage time.

### Concurrency & Race Conditions
* **Total Premium Calculation**: When recording a successful payment, the policy's total premium is updated via:
  `policy.setTotalPremiumPaid(policy.getTotalPremiumPaid().add(dto.getAmount()));`
  Without database-level locking, concurrent payment requests (e.g., a double-click on the frontend) will read the same initial `totalPremiumPaid` value, leading to a lost update. 
* **Overpayment Validation**: The check `policy.getTotalPremiumPaid().add(dto.getAmount()).compareTo(totalRequiredPremium) > 0` is vulnerable to the same race condition, potentially allowing a user to overpay.

---

## Recommendations

1. **Implement Pessimistic Locking**: Use `@Lock(LockModeType.PESSIMISTIC_WRITE)` on repository queries fetching `Policy` entities during payments and claim submissions to prevent race conditions.
2. **Anchor Due Dates**: Refactor `PremiumPaymentServiceImpl` to calculate next due dates using `policy.getStartDate().plusYears(successfulPayments)`.
3. **Add strict DTO Validations**: Enforce `@Positive` annotations on `claimAmount` and `amount` fields in the respective DTOs.
4. **Enforce Ownership**: Consistently verify user ownership in action-oriented methods like `cancelPolicy()`.
