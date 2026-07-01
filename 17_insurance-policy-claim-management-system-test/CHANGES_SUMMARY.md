# Manual Change Migration Guide

Below are all the source code modifications made in this copy project. You can refer to the file names and the exact diffs (additions in green `+`, removals in red `-`) to apply them to your main project.

## File: `src/main/java/com/insurance/demo/controller/AuthController.java`
```diff
@@ -9,10 +9,14 @@ import org.springframework.web.bind.annotation.ResponseStatus;
 import org.springframework.web.bind.annotation.RestController;
 
 import com.insurance.demo.dto.request.LoginRequestDTO;
+import com.insurance.demo.dto.request.ResendOtpRequestDTO;
 import com.insurance.demo.dto.request.UserRequestDTO;
 import com.insurance.demo.dto.request.VerifyOtpRequest;
+import com.insurance.demo.dto.request.ForgotPasswordRequestDTO;
+import com.insurance.demo.dto.request.ResetPasswordRequestDTO;
 import com.insurance.demo.dto.response.ApiResponseDTO;
 import com.insurance.demo.dto.response.LoginResponseDTO;
+import com.insurance.demo.dto.response.ResendOtpResponseDTO;
 import com.insurance.demo.dto.response.UserResponseDTO;
 import com.insurance.demo.service.AuthService;
 
@@ -57,5 +61,23 @@ public class AuthController {
 	public ApiResponseDTO<UserResponseDTO> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
 		return authService.verifyOtp(request);
 	}
+	
+	@PostMapping("/resend-otp")
+	@Operation(summary = "Resend OTP", description = "Resend the OTP to user email and phone  to activate")
+	public ApiResponseDTO<ResendOtpResponseDTO> resendOtp(@Valid @RequestBody ResendOtpRequestDTO request){
+			return authService.resendOtp(request);
+	}
+
+	@PostMapping("/forgot-password")
+	@Operation(summary = "Forgot Password", description = "Sends an OTP to the user's registered email and phone number for password reset.")
+	public ApiResponseDTO<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
+		return authService.forgotPassword(request);
+	}
+
+	@PostMapping("/reset-password")
+	@Operation(summary = "Reset Password", description = "Resets the user's password using the OTPs sent to their email and phone.")
+	public ApiResponseDTO<String> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
+		return authService.resetPassword(request);
+	}
 
 }
```

## File: `src/main/java/com/insurance/demo/controller/InsuranceProductController.java`
```diff
@@ -91,7 +91,7 @@ public class InsuranceProductController {
 		return productService.getAllProductsWithPagination(pageNumber, pageSize, sortBy, sortDirection, productType, isActive);
 	}
 	
-	@PatchMapping("/{id}/active")
+	@PatchMapping("/{id}/activate")
 	@PreAuthorize("hasRole('ADMIN')")
 	@Operation(summary = "Activate Product", description = "Reactivates a previously deactivated insurance product.")
 	public ApiResponseDTO<ProductResponseDTO> activateProduct(@PathVariable Long id) {
```

## File: `src/main/java/com/insurance/demo/controller/PolicyController.java`
```diff
@@ -59,32 +59,32 @@ public class PolicyController {
 	@PreAuthorize("hasRole('CUSTOMER')")
 	@Operation(summary = "Get Policy of loged in customer", description = "Retrieves the details of a loged in customer's purchased policy.")
 	public PageResponseDTO<PolicyResponseDTO> getMyPolicies(Authentication authentication,
-			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
-			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
+			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize,
+			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String sortDirection) {
 
-		return policyService.getCustomerPolicies(authentication.getName(), page, size, sortBy, direction);
+		return policyService.getCustomerPolicies(authentication.getName(), pageNumber, pageSize, sortBy, sortDirection);
 	}
 
 	@GetMapping("/customer/{customerId}")
 	@PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
 	@Operation(summary = "Get Policy by customer ID", description = "Retrieves the details of a specific customer's purchased policy.")
 	public PageResponseDTO<PolicyResponseDTO> getPoliciesByCustomer(@PathVariable Long customerId,
-			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
-			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
+			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize,
+			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String sortDirection) {
 
-		return policyService.getPoliciesByCustomer(customerId, page, size, sortBy, direction);
+		return policyService.getPoliciesByCustomer(customerId, pageNumber, pageSize, sortBy, sortDirection);
 	}
 
 	@GetMapping
 	@PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
 	@Operation(summary = "Get All Policies", description = "Retrieves a paginated list of all policies. Supports filtering by customer ID, plan ID, and status.")
-	public PageResponseDTO<PolicyResponseDTO> getAllPolicies(@RequestParam(defaultValue = "0") int page,
-			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
-			@RequestParam(defaultValue = "asc") String direction,
+	public PageResponseDTO<PolicyResponseDTO> getAllPolicies(@RequestParam(defaultValue = "0") int pageNumber,
+			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy,
+			@RequestParam(defaultValue = "asc") String sortDirection,
 			@RequestParam(required = false) Long customerId,
 			@RequestParam(required = false) String status) {
 
-		return policyService.getAllPolicies(page, size, sortBy, direction, customerId, status);
+		return policyService.getAllPolicies(pageNumber, pageSize, sortBy, sortDirection, customerId, status);
 	}
 
 	@GetMapping("/{policyId}")
```

## File: `src/main/java/com/insurance/demo/controller/PremiumPaymentController.java`
```diff
@@ -46,13 +46,13 @@ public class PremiumPaymentController {
 
 	@GetMapping("/policy/{id}")
 	@PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
-@Operation(summary = "Get Payments for Policy", description = "Retrieves all payment records associated with a specific policy.")
+	@Operation(summary = "Get Payments for Policy", description = "Retrieves all payment records associated with a specific policy.")
 	public ApiResponseDTO<List<PaymentResponseDTO>> getPaymentsByPolicy(@PathVariable Long id) {
 		return paymentService.getPaymentsByPolicy(id);
 	}
 
 	@GetMapping("/{id}")
-	@PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
+	@PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
 	@Operation(summary = "Get Payment by ID", description = "Retrieves the details of a specific premium payment transaction.")
 	public ApiResponseDTO<PaymentResponseDTO> getPaymentById(@PathVariable(name = "id") Long paymentId) {
 		return paymentService.getPaymentById(paymentId);
@@ -78,8 +78,7 @@ public class PremiumPaymentController {
 
 	@GetMapping("/my-policies/{policyId}")
 	@PreAuthorize("hasRole('CUSTOMER')")
-
-@Operation(summary = "Get Payments for Policy", description = "Retrieves all payment records associated with a specific policy.")
+	@Operation(summary = "Get Payments for Policy", description = "Retrieves all payment records associated with a specific policy.")
 	public ApiResponseDTO<List<PaymentResponseDTO>> getPaymentsByMyPolicy(@PathVariable Long policyId) {
 		return paymentService.getPaymentsByMyPolicy(policyId);
 	}
```

## File: `src/main/java/com/insurance/demo/dto/request/ClaimRequestDTO.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.dto.request;
 
+import java.math.BigDecimal;
 import java.time.LocalDate;
 import java.util.List;
 
@@ -22,7 +23,7 @@ public class ClaimRequestDTO {
 
 	@NotNull(message = "Claim amount is required")
 	@Positive(message = "Claim amount must be greater than zero")
-	private Double claimAmount;
+	private BigDecimal claimAmount;
 
 	@NotBlank(message = "Claim reason is required")
 	private String claimReason;
```

## File: `src/main/java/com/insurance/demo/dto/request/CreateAgentRequestDTO.java`
```diff
@@ -2,8 +2,10 @@ package com.insurance.demo.dto.request;
 
 import jakarta.validation.constraints.Email;
 import jakarta.validation.constraints.NotBlank;
+import jakarta.validation.constraints.NotNull;
 import jakarta.validation.constraints.Pattern;
 import jakarta.validation.constraints.Size;
+import com.insurance.demo.enums.ProductType;
 import lombok.AllArgsConstructor;
 import lombok.Getter;
 import lombok.NoArgsConstructor;
@@ -31,4 +33,7 @@ public class CreateAgentRequestDTO {
 	@NotBlank(message = "Mobile number is required")
 	@Pattern(regexp = "^\\+[1-9]\\d{7,14}$", message = "Use international format, example: +919876543210")
 	private String mobileNumber;
+
+	@NotNull(message = "Product speciality is required")
+	private ProductType productSpeciality;
 }
\ No newline at end of file
```

## File: `src/main/java/com/insurance/demo/dto/request/ForgotPasswordRequestDTO.java`
```diff
new file mode 100644
@@ -0,0 +1,20 @@
+package com.insurance.demo.dto.request;
+
+import jakarta.validation.constraints.Email;
+import jakarta.validation.constraints.NotBlank;
+import lombok.AllArgsConstructor;
+import lombok.Getter;
+import lombok.NoArgsConstructor;
+import lombok.Setter;
+
+@Getter
+@Setter
+@NoArgsConstructor
+@AllArgsConstructor
+public class ForgotPasswordRequestDTO {
+
+	@Email(message = "enter a valid email")
+	@NotBlank(message = "email is required")
+	private String email;
+
+}
```

## File: `src/main/java/com/insurance/demo/dto/request/PaymentRequestDTO.java`
```diff
@@ -6,6 +6,7 @@ import com.insurance.demo.enums.PaymentStatus;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
 import jakarta.validation.constraints.Positive;
+import java.math.BigDecimal;
 import lombok.AllArgsConstructor;
 import lombok.Getter;
 import lombok.NoArgsConstructor;
@@ -21,7 +22,7 @@ public class PaymentRequestDTO {
 	private Long policyId;
 
 	@Positive(message = "Amount must be greater than zero")
-	private Double amount;
+	private BigDecimal amount;
 
 	@NotNull(message = "Payment mode is required")
 	private PaymentMode paymentMode;
```

## File: `src/main/java/com/insurance/demo/dto/request/PlanRequestDTO.java`
```diff
@@ -2,10 +2,12 @@ package com.insurance.demo.dto.request;
 
 import com.insurance.demo.enums.PremiumType;
 
+import jakarta.validation.constraints.Max;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
 import jakarta.validation.constraints.Pattern;
 import jakarta.validation.constraints.Positive;
+import java.math.BigDecimal;
 import lombok.AllArgsConstructor;
 import lombok.Data;
 import lombok.NoArgsConstructor;
@@ -23,15 +25,16 @@ public class PlanRequestDTO {
 	private String planName;
 
 	@Positive(message = "Coverage amount must be greater than zero")
-	private Double coverageAmount;
+	private BigDecimal coverageAmount;
 
 	@Positive(message = "Premium amount must be greater than zero")
-	private Double premiumAmount;
+	private BigDecimal premiumAmount;
 
 	@NotNull(message = "Premium type is required")
 	private PremiumType premiumType;
 
 	@Positive(message = "Duration must be greater than zero")
+	@Max(value = 40, message = "Duration cannot exceed 40 years")
 	private Integer duration;
 
 	@NotBlank(message = "Terms and conditions are required")
```

## File: `src/main/java/com/insurance/demo/dto/request/ResendOtpRequestDTO.java`
```diff
new file mode 100644
@@ -0,0 +1,22 @@
+package com.insurance.demo.dto.request;
+
+import jakarta.validation.constraints.Email;
+import jakarta.validation.constraints.NotBlank;
+import lombok.AllArgsConstructor;
+import lombok.Getter;
+import lombok.NoArgsConstructor;
+import lombok.Setter;
+
+@Getter
+@Setter
+@NoArgsConstructor
+@AllArgsConstructor
+public class ResendOtpRequestDTO {
+
+	@NotBlank
+	@Email
+	private String email;
+	
+	@NotBlank 
+	private String phone;
+}
```

## File: `src/main/java/com/insurance/demo/dto/request/ResetPasswordRequestDTO.java`
```diff
new file mode 100644
@@ -0,0 +1,31 @@
+package com.insurance.demo.dto.request;
+
+import jakarta.validation.constraints.Email;
+import jakarta.validation.constraints.NotBlank;
+import jakarta.validation.constraints.Size;
+import lombok.AllArgsConstructor;
+import lombok.Getter;
+import lombok.NoArgsConstructor;
+import lombok.Setter;
+
+@Getter
+@Setter
+@NoArgsConstructor
+@AllArgsConstructor
+public class ResetPasswordRequestDTO {
+
+	@Email(message = "enter a valid email")
+	@NotBlank(message = "email is required")
+	private String email;
+
+	@NotBlank(message = "email OTP is required")
+	private String emailOtp;
+
+	@NotBlank(message = "phone OTP is required")
+	private String phoneOtp;
+
+	@NotBlank(message = "new password is required")
+	@Size(min = 8, message = "password must be at least 8 characters long")
+	private String newPassword;
+
+}
```

## File: `src/main/java/com/insurance/demo/dto/response/ClaimResponseDTO.java`
```diff
@@ -1,8 +1,10 @@
 package com.insurance.demo.dto.response;
 
+import java.math.BigDecimal;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.util.List;
+import com.fasterxml.jackson.annotation.JsonInclude;
 
 import lombok.AllArgsConstructor;
 import lombok.Getter;
@@ -23,7 +25,7 @@ public class ClaimResponseDTO {
 
 	private String policyNumber;
 
-	private Double claimAmount;
+	private BigDecimal claimAmount;
 
 	private String claimReason;
 
@@ -42,4 +44,7 @@ public class ClaimResponseDTO {
 	private LocalDateTime updatedDate;
 	
 	private List<ClaimDocumentResponseDTO> documents;
+
+	@JsonInclude(JsonInclude.Include.NON_NULL)
+	private String assignedAgentName;
 }
\ No newline at end of file
```

## File: `src/main/java/com/insurance/demo/dto/response/PaymentResponseDTO.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.dto.response;
 
+import java.math.BigDecimal;
 import java.time.LocalDateTime;
 
 import lombok.AllArgsConstructor;
@@ -19,7 +20,7 @@ public class PaymentResponseDTO {
 
 	private String policyNumber;
 
-	private Double amount;
+	private BigDecimal amount;
 
 	private String paymentMode;
 
```

## File: `src/main/java/com/insurance/demo/dto/response/PlanResponseDTO.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.dto.response;
 
+import java.math.BigDecimal;
 import java.time.LocalDateTime;
 
 import lombok.AllArgsConstructor;
@@ -21,9 +22,9 @@ public class PlanResponseDTO {
 
 	private String planName;
 
-	private Double coverageAmount;
+	private BigDecimal coverageAmount;
 
-	private Double premiumAmount;
+	private BigDecimal premiumAmount;
 
 	private String premiumType;
 
```

## File: `src/main/java/com/insurance/demo/dto/response/PolicyResponseDTO.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.dto.response;
 
+import java.math.BigDecimal;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 
@@ -32,15 +33,17 @@ public class PolicyResponseDTO {
 
 	private String policyStatus;
 
-	private Double totalPremiumPaid;
+	private BigDecimal totalPremiumPaid;
 
 	private String productType;
 
-	private Double coverageAmount;
+	private BigDecimal coverageAmount;
 
-	private Double premiumAmount;
+	private BigDecimal premiumAmount;
 
 	private String premiumType;
 
 	private LocalDateTime createdDate;
+
+	private BigDecimal remainingClaimAmount;
 }
\ No newline at end of file
```

## File: `src/main/java/com/insurance/demo/dto/response/ResendOtpResponseDTO.java`
```diff
new file mode 100644
@@ -0,0 +1,18 @@
+package com.insurance.demo.dto.response;
+
+import lombok.AllArgsConstructor;
+import lombok.Getter;
+import lombok.NoArgsConstructor;
+import lombok.Setter;
+
+@Getter
+@Setter
+@AllArgsConstructor
+@NoArgsConstructor
+public class ResendOtpResponseDTO {
+
+	private String email;
+	private String phone;
+	
+	
+}
```

## File: `src/main/java/com/insurance/demo/dto/response/UserResponseDTO.java`
```diff
@@ -3,6 +3,8 @@ package com.insurance.demo.dto.response;
 import java.time.LocalDateTime;
 
 import com.insurance.demo.enums.Role;
+import com.insurance.demo.enums.ProductType;
+import com.fasterxml.jackson.annotation.JsonInclude;
 
 import lombok.AllArgsConstructor;
 import lombok.Getter;
@@ -34,4 +36,7 @@ public class UserResponseDTO {
 	private LocalDateTime createdDate;
 
 	private LocalDateTime updatedDate;
+
+	@JsonInclude(JsonInclude.Include.NON_NULL)
+	private ProductType productSpeciality;
 }
\ No newline at end of file
```

## File: `src/main/java/com/insurance/demo/model/AgentSpeciality.java`
```diff
new file mode 100644
@@ -0,0 +1,40 @@
+package com.insurance.demo.model;
+
+import com.insurance.demo.enums.ProductType;
+
+import jakarta.persistence.Column;
+import jakarta.persistence.Entity;
+import jakarta.persistence.EnumType;
+import jakarta.persistence.Enumerated;
+import jakarta.persistence.GeneratedValue;
+import jakarta.persistence.GenerationType;
+import jakarta.persistence.Id;
+import jakarta.persistence.JoinColumn;
+import jakarta.persistence.OneToOne;
+import jakarta.persistence.Table;
+import lombok.AllArgsConstructor;
+import lombok.Getter;
+import lombok.NoArgsConstructor;
+import lombok.Setter;
+
+@Entity
+@Table(name = "agent_specialities")
+@Getter
+@Setter
+@NoArgsConstructor
+@AllArgsConstructor
+public class AgentSpeciality {
+
+	@Id
+	@Column(name = "id")
+	@GeneratedValue(strategy = GenerationType.IDENTITY)
+	private Long id;
+
+	@OneToOne
+	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
+	private AppUser agent;
+
+	@Column(name = "product_speciality", nullable = false)
+	@Enumerated(EnumType.STRING)
+	private ProductType productSpeciality;
+}
```

## File: `src/main/java/com/insurance/demo/model/AppUser.java`
```diff
@@ -82,6 +82,9 @@ public class AppUser {
 	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
 	private Customer customer;
 
-	private boolean emailVerified;
-	private boolean phoneVerified;
+	@OneToOne(mappedBy = "agent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
+	private AgentSpeciality agentSpeciality;
+
+	private Boolean emailVerified = false;
+	private Boolean phoneVerified = false;
 }
```

## File: `src/main/java/com/insurance/demo/model/Claim.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.model;
 
+import java.math.BigDecimal;
 import java.time.LocalDateTime;
 import java.util.ArrayList;
 import java.util.List;
@@ -48,8 +49,8 @@ public class Claim {
 
     @Positive(message = "Claim amount must be greater than zero")
     @NotNull(message = "Claim amount is required")
-    @Column(nullable = false)
-    private Double claimAmount;
+    @Column(nullable = false, precision = 15, scale = 2)
+    private BigDecimal claimAmount;
 
     @NotBlank(message = "Claim reason is required")
     @Column(nullable = false)
@@ -83,6 +84,10 @@ public class Claim {
     @NotNull(message = "Policy is required")
     private Policy policy;
 
+    @ManyToOne(fetch = FetchType.LAZY)
+    @JoinColumn(name = "assigned_agent_id")
+    private AppUser assignedAgent;
+
     @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<ClaimDocument> claimDocuments = new ArrayList<>();
 
```

## File: `src/main/java/com/insurance/demo/model/Policy.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.model;
 
+import java.math.BigDecimal;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.util.ArrayList;
@@ -69,9 +70,9 @@ public class Policy {
 	@NotNull(message = "Policy status is required")
 	private PolicyStatus policyStatus;
 
-	@Column(name = "total_premium_paid", nullable = false)
+	@Column(name = "total_premium_paid", nullable = false, precision = 15, scale = 2)
 	@PositiveOrZero(message = "Total premium paid must be zero or positive")
-	private Double totalPremiumPaid = 0.0;
+	private BigDecimal totalPremiumPaid = BigDecimal.ZERO;
 
 	@CreationTimestamp
 	@Column(name = "created_date", updatable = false)
```

## File: `src/main/java/com/insurance/demo/model/PolicyPlan.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.model;
 
+import java.math.BigDecimal;
 import java.time.LocalDateTime;
 import java.util.List;
 
@@ -12,6 +13,7 @@ import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.EnumType;
 import jakarta.persistence.Enumerated;
+import jakarta.persistence.FetchType;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.GenerationType;
 import jakarta.persistence.Id;
@@ -21,7 +23,6 @@ import jakarta.persistence.OneToMany;
 import jakarta.persistence.Table;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
-import jakarta.validation.constraints.Pattern;
 import jakarta.validation.constraints.Positive;
 import jakarta.validation.constraints.Size;
 import lombok.AllArgsConstructor;
@@ -42,7 +43,7 @@ public class PolicyPlan {
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	private Long id;
 
-	@ManyToOne
+	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "product_id", nullable = false)
 	private InsuranceProduct insuranceProduct;
 
@@ -52,14 +53,14 @@ public class PolicyPlan {
 	private String planName;
 
 	@Positive(message = "amount should be positive")
-	@Column(name = "coverage_amount", nullable = false)
+	@Column(name = "coverage_amount", nullable = false, precision = 15, scale = 2)
 	@NotNull(message = "amount can't be null")
-	private Double coverageAmount;
+	private BigDecimal coverageAmount;
 
 	@Positive(message = "amount should be positive")
-	@Column(name = "premium_amount", nullable = false)
+	@Column(name = "premium_amount", nullable = false, precision = 15, scale = 2)
 	@NotNull(message = "premium amount can't be null")
-	private Double premiumAmount;
+	private BigDecimal premiumAmount;
 
 	@Enumerated(EnumType.STRING)
 	@NotNull(message = "premium type can't be null")
```

## File: `src/main/java/com/insurance/demo/model/PremiumPayment.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.model;
 
+import java.math.BigDecimal;
 import java.time.LocalDateTime;
 
 import org.hibernate.annotations.CreationTimestamp;
@@ -44,11 +45,11 @@ public class PremiumPayment {
 
 	@Positive(message = "amount should be positive")
 	@NotNull(message = "amount can't be null")
-	@Column(name = "amount", nullable = false)
-	private Double amount;
+	@Column(name = "amount", nullable = false, precision = 15, scale = 2)
+	private BigDecimal amount;
 
 	@Column(name = "payment_date", nullable = false)
-	private LocalDateTime paymentDate = LocalDateTime.now();
+	private LocalDateTime paymentDate;
 
 	@Enumerated(EnumType.STRING)
 	@NotNull(message = "payment mode can't be null")
```

## File: `src/main/java/com/insurance/demo/repository/AgentSpecialityRepository.java`
```diff
new file mode 100644
@@ -0,0 +1,12 @@
+package com.insurance.demo.repository;
+
+import org.springframework.data.jpa.repository.JpaRepository;
+import org.springframework.stereotype.Repository;
+
+import com.insurance.demo.model.AgentSpeciality;
+import com.insurance.demo.model.AppUser;
+
+@Repository
+public interface AgentSpecialityRepository extends JpaRepository<AgentSpeciality, Long> {
+	AgentSpeciality findByAgent(AppUser agent);
+}
```

## File: `src/main/java/com/insurance/demo/repository/AppUserRepository.java`
```diff
@@ -32,4 +32,5 @@ public interface AppUserRepository extends JpaRepository<AppUser, Long> {
 
 	Page<AppUser> findByIsActive(Boolean isActive, Pageable pageable);
 
+	Optional<AppUser> findByEmailAndMobileNumber(String email, String mobileNumber);
 }
```

## File: `src/main/java/com/insurance/demo/repository/ClaimRepository.java`
```diff
@@ -1,14 +1,18 @@
 package com.insurance.demo.repository;
 
+import java.math.BigDecimal;
 import java.util.List;
 import java.util.Optional;
 
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.jpa.repository.JpaRepository;
+import org.springframework.data.jpa.repository.Query;
+import org.springframework.data.repository.query.Param;
 import org.springframework.stereotype.Repository;
 
 import com.insurance.demo.enums.ClaimStatus;
+import com.insurance.demo.enums.ProductType;
 import com.insurance.demo.model.Claim;
 
 @Repository
@@ -27,4 +31,10 @@ public interface ClaimRepository extends JpaRepository<Claim, Long>{
 
 	Page<Claim> findByPolicyCustomerIdAndClaimStatus(Long customerId, ClaimStatus status, Pageable pageable);
 
+    Page<Claim> findByPolicyPolicyPlanInsuranceProductProductType(ProductType productType, Pageable pageable);
+    Page<Claim> findByPolicyPolicyPlanInsuranceProductProductTypeAndClaimStatus(ProductType productType, ClaimStatus status, Pageable pageable);
+
+    @Query("SELECT COALESCE(SUM(c.claimAmount), 0) FROM Claim c WHERE c.policy.id = :policyId AND c.claimStatus != :status")
+    BigDecimal sumActiveClaimsByPolicyId(@Param("policyId") Long policyId, @Param("status") ClaimStatus status);
+
 }
```

## File: `src/main/java/com/insurance/demo/repository/InsurenceProductRepository.java`
```diff
deleted file mode 100644
@@ -1,24 +0,0 @@
-package com.insurance.demo.repository;
-
-import java.util.List;
-import java.util.Optional;
-
-import org.springframework.data.jpa.repository.JpaRepository;
-import org.springframework.stereotype.Repository;
-
-import com.insurance.demo.enums.Role;
-import com.insurance.demo.model.AppUser;
-import com.insurance.demo.model.InsuranceProduct;
-
-@Repository
-public interface InsurenceProductRepository extends JpaRepository<InsuranceProduct, Long>{
-
-	boolean existsByProductNameIgnoreCase(String productName);
-
-	Optional<InsuranceProduct> findByProductNameIgnoreCase(String productName);
-
-
-	List<InsuranceProduct> findByIsActiveTrue();
-	
-
-}
```

## File: `src/main/java/com/insurance/demo/repository/PremiumPaymentRepository.java`
```diff
@@ -1,6 +1,7 @@
 package com.insurance.demo.repository;
 
 import java.util.List;
+import java.util.Optional;
 
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
@@ -26,5 +27,18 @@ public interface PremiumPaymentRepository extends JpaRepository<PremiumPayment,
 	Page<PremiumPayment> findByPolicyId(Long policyId, Pageable pageable);
 
 	Page<PremiumPayment> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);
+	
+	
+	Optional<PremiumPayment> findTopByPolicyIdAndPaymentStatusOrderByPaymentDateDesc(
+	        Long policyId,
+	        PaymentStatus paymentStatus);
+
+	long countByPolicyIdAndPaymentStatus(
+	        Long policyId,
+	        PaymentStatus paymentStatus);
+
+	boolean existsByPolicyIdAndPaymentStatus(
+	        Long policyId,
+	        PaymentStatus paymentStatus);
 
 }
```

## File: `src/main/java/com/insurance/demo/security/JwtService.java`
```diff
@@ -24,15 +24,15 @@ public class JwtService {
 	@Value("${app.jwt.expiration-ms}")
 	private long jwtExpirationMs;
 
-	public String generateToken(UserDetails userDetails) {
+	public String generateToken(UserDetails userDetails, String fullName) {
 
 		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
 
 		Date issuedAt = new Date();
 		Date expiryDate = new Date(issuedAt.getTime() + jwtExpirationMs);
 
-		return Jwts.builder().subject(userDetails.getUsername()).claim("roles", roles).issuedAt(issuedAt)
-				.expiration(expiryDate).signWith(getSigningKey()).compact();
+		return Jwts.builder().subject(userDetails.getUsername()).claim("roles", roles).claim("fullName", fullName)
+				.issuedAt(issuedAt).expiration(expiryDate).signWith(getSigningKey()).compact();
 	}
 
 	public String extractUsername(String token) {
```

## File: `src/main/java/com/insurance/demo/service/AuthService.java`
```diff
@@ -1,11 +1,15 @@
 package com.insurance.demo.service;
 
 import com.insurance.demo.dto.request.LoginRequestDTO;
+import com.insurance.demo.dto.request.ResendOtpRequestDTO;
 import com.insurance.demo.dto.request.UserRequestDTO;
 import com.insurance.demo.dto.request.VerifyOtpRequest;
 import com.insurance.demo.dto.response.ApiResponseDTO;
 import com.insurance.demo.dto.response.LoginResponseDTO;
+import com.insurance.demo.dto.response.ResendOtpResponseDTO;
 import com.insurance.demo.dto.response.UserResponseDTO;
+import com.insurance.demo.dto.request.ForgotPasswordRequestDTO;
+import com.insurance.demo.dto.request.ResetPasswordRequestDTO;
 
 import jakarta.validation.Valid;
 
@@ -16,5 +20,11 @@ public interface AuthService {
 	ApiResponseDTO<UserResponseDTO> registerUser(UserRequestDTO dto);
 
 	ApiResponseDTO<UserResponseDTO> verifyOtp(@Valid VerifyOtpRequest request);
+	
+	ApiResponseDTO<ResendOtpResponseDTO> resendOtp(ResendOtpRequestDTO request);
+
+	ApiResponseDTO<String> forgotPassword(ForgotPasswordRequestDTO request);
+
+	ApiResponseDTO<String> resetPassword(ResetPasswordRequestDTO request);
 
 }
```

## File: `src/main/java/com/insurance/demo/serviceimpl/AuthServiceImpl.java`
```diff
@@ -12,10 +12,14 @@ import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 import com.insurance.demo.dto.request.LoginRequestDTO;
+import com.insurance.demo.dto.request.ResendOtpRequestDTO;
 import com.insurance.demo.dto.request.UserRequestDTO;
 import com.insurance.demo.dto.request.VerifyOtpRequest;
+import com.insurance.demo.dto.request.ForgotPasswordRequestDTO;
+import com.insurance.demo.dto.request.ResetPasswordRequestDTO;
 import com.insurance.demo.dto.response.ApiResponseDTO;
 import com.insurance.demo.dto.response.LoginResponseDTO;
+import com.insurance.demo.dto.response.ResendOtpResponseDTO;
 import com.insurance.demo.dto.response.UserResponseDTO;
 import com.insurance.demo.enums.Role;
 import com.insurance.demo.exception.BadRequestException;
@@ -55,12 +59,12 @@ public class AuthServiceImpl implements AuthService {
 			throw new BadRequestException("Invalid email or password");
 		});
 
-		if (!appUser.isEmailVerified()) {
+		if (!appUser.getEmailVerified()) {
 			log.warn("Login blocked. Email not verified. UserId={}", appUser.getId());
 			throw new BadRequestException("Please verify email before logging in.");
 		}
 		
-		if (!appUser.isPhoneVerified()) {
+		if (!appUser.getPhoneVerified()) {
 			log.warn("Login blocked. Phone not verified. UserId={}", appUser.getId());
 			throw new BadRequestException("Please verify phone before logging in.");
 		}
@@ -75,7 +79,7 @@ public class AuthServiceImpl implements AuthService {
 
 		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 
-		String token = jwtService.generateToken(userDetails);
+		String token = jwtService.generateToken(userDetails, appUser.getFullName());
 
 		UserResponseDTO dto = userService.findByEmail(userDetails.getUsername());
 
@@ -126,14 +130,65 @@ public class AuthServiceImpl implements AuthService {
 
 		otpService.verifyOtp(user, request.getEmailOtp(), request.getPhoneOtp());
 
-		user.setEmailVerified(true);
-        user.setPhoneVerified(true);
-		user.setIsActive(true);
+		user.setEmailVerified(Boolean.TRUE);
+        user.setPhoneVerified(Boolean.TRUE);
+		user.setIsActive(Boolean.TRUE);
 
 		AppUser saved = userRepository.save(user);
 
 		return new ApiResponseDTO<>("User account activated successfully.", true, modelMapper.map(saved, UserResponseDTO.class),
 				LocalDateTime.now());
 	}
+	
+	@Override
+	public ApiResponseDTO<ResendOtpResponseDTO> resendOtp(ResendOtpRequestDTO request) {
+
+		AppUser user = userRepository.findByEmailAndMobileNumber(request.getEmail(), request.getPhone())
+				.orElseThrow(() -> new ResourceNotFoundException("User not found with the provided details."));
+
+		if (Boolean.TRUE.equals(user.getIsActive())) {
+			throw new BadRequestException("user is already verified");
+		}
+
+		if (Boolean.FALSE.equals(otpService.invalidateLastOtp(user))) {
+			throw new BadRequestException("Otp is Still active please verify your email and phone");
+		}
+
+		otpService.createAndSendOtp(user);
+
+		ResendOtpResponseDTO dto = new ResendOtpResponseDTO(request.getEmail(), request.getPhone());
+
+		return new ApiResponseDTO<>("Otp are sent again on your email and password.", true, dto, LocalDateTime.now());
+
+	}
+
+	@Override
+	public ApiResponseDTO<String> forgotPassword(ForgotPasswordRequestDTO request) {
+		AppUser user = userRepository.findByEmail(request.getEmail().toLowerCase())
+				.orElseThrow(() -> new ResourceNotFoundException("User not found with the provided details."));
+
+		otpService.createAndSendOtp(user);
+		return new ApiResponseDTO<>("OTP sent to your registered email and phone number.", true, null, LocalDateTime.now());
+	}
+
+	@Override
+	@Transactional
+	public ApiResponseDTO<String> resetPassword(ResetPasswordRequestDTO request) {
+		AppUser user = userRepository.findByEmail(request.getEmail().toLowerCase())
+				.orElseThrow(() -> new ResourceNotFoundException("User not found with the provided details."));
+
+		otpService.verifyOtp(user, request.getEmailOtp(), request.getPhoneOtp());
+
+		if (Boolean.FALSE.equals(user.getIsActive())) {
+			user.setEmailVerified(Boolean.TRUE);
+			user.setPhoneVerified(Boolean.TRUE);
+			user.setIsActive(Boolean.TRUE);
+		}
+
+		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
+		userRepository.save(user);
+
+		return new ApiResponseDTO<>("Password has been reset successfully.", true, null, LocalDateTime.now());
+	}
 
 }
```

## File: `src/main/java/com/insurance/demo/serviceimpl/ClaimDocumentServiceImpl.java`
```diff
@@ -54,9 +54,21 @@ public class ClaimDocumentServiceImpl implements ClaimDocumentService {
 			}
 
 			if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
-
 				throw new BadRequestException("Uploaded document must have a valid file name.");
 			}
+
+			// File type validation: only images and PDFs allowed
+			String contentType = file.getContentType();
+			if (contentType == null || !java.util.Set.of(
+					"image/jpeg", "image/png", "image/jpg", "application/pdf"
+			).contains(contentType)) {
+				throw new BadRequestException("Only JPEG, PNG, and PDF documents are accepted.");
+			}
+
+			// File size validation: max 10 MB per file
+			if (file.getSize() > 10 * 1024 * 1024) {
+				throw new BadRequestException("Each document must not exceed 10 MB in size.");
+			}
 		}
 
 		String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
```

## File: `src/main/java/com/insurance/demo/serviceimpl/ClaimServiceImpl.java`
```diff
@@ -1,6 +1,7 @@
 package com.insurance.demo.serviceimpl;
 
 import java.io.IOException;
+import java.math.BigDecimal;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.util.ArrayList;
@@ -26,6 +27,7 @@ import com.insurance.demo.dto.response.ClaimHistoryResponseDTO;
 import com.insurance.demo.dto.response.ClaimResponseDTO;
 import com.insurance.demo.dto.response.PageResponseDTO;
 import com.insurance.demo.enums.ClaimStatus;
+import com.insurance.demo.enums.ProductType;
 import com.insurance.demo.enums.PolicyStatus;
 import com.insurance.demo.exception.BadRequestException;
 import com.insurance.demo.exception.ResourceNotFoundException;
@@ -64,7 +66,7 @@ public class ClaimServiceImpl implements ClaimService {
 			throws IOException {// Customer only
 
 		if (files == null || files.isEmpty()) {
-			throw new ResourceNotFoundException("At least one supporting document must be provided.");
+			throw new BadRequestException("At least one supporting document must be provided.");
 		}
 
 		for (MultipartFile file : files) {
@@ -91,18 +93,25 @@ public class ClaimServiceImpl implements ClaimService {
 			throw new BadRequestException("Claims can only be filed against your own active policies.");
 		}
 
-		if (!PolicyStatus.ACTIVE.equals(policy.getPolicyStatus())) {
-			throw new BadRequestException("Claim can only be raised against Active policies");
+		if (!List.of(PolicyStatus.ACTIVE, PolicyStatus.EXPIRED, PolicyStatus.CANCELLED).contains(policy.getPolicyStatus())) {
+			throw new BadRequestException("Claim can only be raised against Active, Expired, or Cancelled policies");
 		}
 
-		if (dto.getClaimAmount() > policy.getPolicyPlan().getCoverageAmount()) {
-			throw new BadRequestException("The requested claim amount exceeds the maximum coverage of your policy.");
+		BigDecimal activeClaimsSum = claimRepository.sumActiveClaimsByPolicyId(policy.getId(), ClaimStatus.REJECTED);
+		BigDecimal remainingCoverage = policy.getPolicyPlan().getCoverageAmount().subtract(activeClaimsSum);
+
+		if (dto.getClaimAmount().compareTo(remainingCoverage) > 0) {
+			throw new BadRequestException("The requested claim amount exceeds your remaining policy coverage of " + remainingCoverage);
 		}
 
 		if (dto.getIncidentDate().isAfter(LocalDate.now())) {
 			throw new BadRequestException("Incident date cannot be in the future");
 		}
 
+		if(dto.getIncidentDate().isBefore(policy.getStartDate()) || dto.getIncidentDate().isAfter(policy.getEndDate())) {
+			throw new BadRequestException("Incident date should be between the policy period");
+		}
+		
 		// Create Claim
 		Claim claim = new Claim();
 		claim.setPolicy(policy);
@@ -148,6 +157,13 @@ public class ClaimServiceImpl implements ClaimService {
 		if (claim.getClaimStatus() != ClaimStatus.UNDER_REVIEW) {
 			throw new BadRequestException("The claim must be under review before a recommendation can be made.");
 		}
+		
+		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
+		AppUser currentUser = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
+		
+		if (claim.getAssignedAgent() == null || !claim.getAssignedAgent().getId().equals(currentUser.getId())) {
+			throw new AccessDeniedException("You are not authorized to review this claim. It is assigned to another agent.");
+		}
 
 		ClaimStatus previous = claim.getClaimStatus();
 
@@ -219,8 +235,12 @@ public class ClaimServiceImpl implements ClaimService {
 				|| !claim.getPolicy().getCustomer().getUser().getEmail().equals(loggedInEmail))) {
 			throw new AccessDeniedException("You do not have permission to view this claim.");
 		}
-
+		
+		List<ClaimDocumentResponseDTO> documents = claimDocumentRepository.findByClaimId(claim.getId()).stream()
+				.map(document -> modelMapper.map(document, ClaimDocumentResponseDTO.class)).toList();
+		
 		ClaimResponseDTO response = convertToResponseDTO(claim);
+		response.setDocuments(documents);
 		return new ApiResponseDTO<>("Claim details retrieved successfully.", true, response, LocalDateTime.now());
 	}
 
@@ -274,15 +294,34 @@ public class ClaimServiceImpl implements ClaimService {
 			}
 		}
 
+		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
+		String email = auth.getName();
+		AppUser currentUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
+		
+		boolean isAgent = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"));
+		
+		ProductType agentSpeciality = null;
+		if (isAgent && currentUser.getAgentSpeciality() != null) {
+			agentSpeciality = currentUser.getAgentSpeciality().getProductSpeciality();
+		}
+
 		Page<Claim> claimPage;
-		if (customerId != null && claimStatus != null) {
-			claimPage = claimRepository.findByPolicyCustomerIdAndClaimStatus(customerId, claimStatus, pageable);
-		} else if (customerId != null) {
-			claimPage = claimRepository.findByPolicyCustomerId(customerId, pageable);
-		} else if (claimStatus != null) {
-			claimPage = claimRepository.findByClaimStatus(claimStatus, pageable);
+		if (isAgent && agentSpeciality != null) {
+			if (claimStatus != null) {
+				claimPage = claimRepository.findByPolicyPolicyPlanInsuranceProductProductTypeAndClaimStatus(agentSpeciality, claimStatus, pageable);
+			} else {
+				claimPage = claimRepository.findByPolicyPolicyPlanInsuranceProductProductType(agentSpeciality, pageable);
+			}
 		} else {
-			claimPage = claimRepository.findAll(pageable);
+			if (customerId != null && claimStatus != null) {
+				claimPage = claimRepository.findByPolicyCustomerIdAndClaimStatus(customerId, claimStatus, pageable);
+			} else if (customerId != null) {
+				claimPage = claimRepository.findByPolicyCustomerId(customerId, pageable);
+			} else if (claimStatus != null) {
+				claimPage = claimRepository.findByClaimStatus(claimStatus, pageable);
+			} else {
+				claimPage = claimRepository.findAll(pageable);
+			}
 		}
 
 		List<ClaimResponseDTO> content = claimPage.getContent().stream().map(this::convertToResponseDTO).toList();
@@ -354,11 +393,19 @@ public class ClaimServiceImpl implements ClaimService {
 			throw new BadRequestException("Only newly submitted claims can be moved to the under review status.");
 		}
 
+		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
+		AppUser currentUser = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
+		
+		if (claim.getAssignedAgent() != null) {
+			throw new BadRequestException("This claim is already under review by another agent.");
+		}
+
 		ClaimStatus previous = claim.getClaimStatus();
 
 		// Agent recommends
 		claim.setClaimStatus(ClaimStatus.UNDER_REVIEW);
 		claim.setAgentRemarks("Claim under review");
+		claim.setAssignedAgent(currentUser);
 
 		Claim updated = claimRepository.save(claim);
 
@@ -392,6 +439,11 @@ public class ClaimServiceImpl implements ClaimService {
 		}
 		response.setAgentRemarks(claim.getAgentRemarks());
 		response.setAdminRemarks(claim.getAdminRemarks());
+		
+		if (claim.getAssignedAgent() != null) {
+			response.setAssignedAgentName(claim.getAssignedAgent().getFullName());
+		}
+		
 		response.setCreatedDate(claim.getCreatedDate());
 		response.setUpdatedDate(claim.getUpdatedDate());
 		return response;
```

## File: `src/main/java/com/insurance/demo/serviceimpl/CustomerServiceImpl.java`
```diff
@@ -5,8 +5,6 @@ import java.time.LocalDateTime;
 import java.util.List;
 
 import org.modelmapper.ModelMapper;
-import org.slf4j.Logger;
-import org.slf4j.LoggerFactory;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
@@ -30,13 +28,14 @@ import com.insurance.demo.repository.CustomerRepository;
 import com.insurance.demo.service.CustomerService;
 
 import lombok.RequiredArgsConstructor;
+import lombok.extern.slf4j.Slf4j;
 
+@Slf4j
 @Service
 @RequiredArgsConstructor
 @Transactional
 public class CustomerServiceImpl implements CustomerService {
 
-	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
 
 	private final CustomerRepository customerRepository;
 	private final AppUserRepository appUserRepository;
@@ -45,7 +44,7 @@ public class CustomerServiceImpl implements CustomerService {
 	@Override
 	public ApiResponseDTO<CustomerResponseDTO> createCustomer(Long userId, CustomerRequestDTO requestDTO) {
 
-		logger.info("Creating customer profile for userId: {}", userId);
+		log.info("Creating customer profile for userId: {}", userId);
 
 		if (requestDTO.getDateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
 
@@ -82,7 +81,7 @@ public class CustomerServiceImpl implements CustomerService {
 
 		CustomerResponseDTO dto = convertToResponseDTO(savedCustomer);
 
-		logger.info("Customer profile created successfully with id: {}", savedCustomer.getId());
+		log.info("Customer profile created successfully with id: {}", savedCustomer.getId());
 
 		return new ApiResponseDTO<>("Customer profile Created Successfully", true, dto, LocalDateTime.now());
 	}
@@ -91,7 +90,7 @@ public class CustomerServiceImpl implements CustomerService {
 	@Transactional(readOnly = true)
 	public ApiResponseDTO<CustomerResponseDTO> getCustomerById(Long customerId) {
 
-		logger.info("Fetching customer with id: {}", customerId);
+		log.info("Fetching customer with id: {}", customerId);
 
 		Customer customer = findCustomerById(customerId);
 
@@ -106,7 +105,7 @@ public class CustomerServiceImpl implements CustomerService {
 	@Transactional(readOnly = true)
 	public ApiResponseDTO<List<CustomerResponseDTO>> getAllCustomers() {
 
-		logger.info("Fetching all customers");
+		log.info("Fetching all customers");
 
 		List<CustomerResponseDTO> customers = customerRepository.findAll().stream().map(this::convertToResponseDTO)
 				.toList();
@@ -117,7 +116,7 @@ public class CustomerServiceImpl implements CustomerService {
 	@Override
 	public ApiResponseDTO<CustomerResponseDTO> updateCustomer(Long customerId, CustomerRequestDTO requestDTO) {
 
-		logger.info("Updating customer with id: {}", customerId);
+		log.info("Updating customer with id: {}", customerId);
 
 		Customer customer = findCustomerById(customerId);
 
@@ -129,7 +128,7 @@ public class CustomerServiceImpl implements CustomerService {
 
 		CustomerResponseDTO dto = convertToResponseDTO(updatedCustomer);
 
-		logger.info("Customer updated successfully with id: {}", customerId);
+		log.info("Customer updated successfully with id: {}", customerId);
 
 		return new ApiResponseDTO<>("Customer profile Updated Successfully", true, dto, LocalDateTime.now());
 	}
@@ -139,7 +138,7 @@ public class CustomerServiceImpl implements CustomerService {
 	public PageResponseDTO<CustomerResponseDTO> getAllCustomersWithPagination(int pageNumber, int pageSize,
 			String sortBy, String sortDirection, String city, String state) {
 
-		logger.info(
+		log.info(
 				"Fetching customers with pagination. pageNumber: {}, pageSize: {}, sortBy: {}, sortDirection: {}, city: {}, state: {}",
 				pageNumber, pageSize, sortBy, sortDirection, city, state);
 
@@ -258,4 +257,4 @@ public class CustomerServiceImpl implements CustomerService {
 		return new ApiResponseDTO<>("CCustomer details retrieved successfully.", true, dto, LocalDateTime.now());
 	}
 
-}
\ No newline at end of file
+}
```

## File: `src/main/java/com/insurance/demo/serviceimpl/PolicyPlanServiceImpl.java`
```diff
@@ -43,7 +43,7 @@ public class PolicyPlanServiceImpl implements PolicyPlanService {
 
 		log.info("Creating policy plan: {}", dto.getPlanName());
 
-		if (dto.getCoverageAmount() <= dto.getPremiumAmount()) {
+		if (dto.getCoverageAmount().compareTo(dto.getPremiumAmount()) <= 0) {
 			throw new BadRequestException("The policy coverage amount must strictly exceed the required premium amount.");
 		}
 
@@ -86,8 +86,8 @@ public class PolicyPlanServiceImpl implements PolicyPlanService {
 
 		log.info("Updating policy plan with id: {}", planId);
 
-		if (dto.getCoverageAmount() <= dto.getPremiumAmount()) {
-			throw new BadRequestException("Cannot create a policy plan under an inactive insurance product.");
+		if (dto.getCoverageAmount().compareTo(dto.getPremiumAmount()) <= 0) {
+			throw new BadRequestException("Coverage amount must strictly exceed the premium amount.");
 		}
 
 		PolicyPlan existingPlan = policyPlanRepository.findById(planId)
```

## File: `src/main/java/com/insurance/demo/serviceimpl/PolicyServiceImpl.java`
```diff
@@ -1,5 +1,6 @@
 package com.insurance.demo.serviceimpl;
 
+import java.math.BigDecimal;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.util.List;
@@ -21,6 +22,7 @@ import com.insurance.demo.dto.response.ApiResponseDTO;
 import com.insurance.demo.dto.response.PageResponseDTO;
 import com.insurance.demo.dto.response.PolicyResponseDTO;
 import com.insurance.demo.enums.PolicyStatus;
+import com.insurance.demo.enums.ProductType;
 import com.insurance.demo.exception.BadRequestException;
 import com.insurance.demo.exception.DuplicateResourceException;
 import com.insurance.demo.exception.PlanNotActiveException;
@@ -32,10 +34,10 @@ import com.insurance.demo.model.PolicyPlan;
 import com.insurance.demo.repository.CustomerRepository;
 import com.insurance.demo.repository.PolicyPlanRepository;
 import com.insurance.demo.repository.PolicyRepository;
+import com.insurance.demo.repository.ClaimRepository;
+import com.insurance.demo.enums.ClaimStatus;
 import com.insurance.demo.service.PolicyService;
 import com.insurance.demo.util.PolicyNumberGenerator;
-import com.sun.jdi.request.DuplicateRequestException;
-
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 
@@ -47,6 +49,7 @@ public class PolicyServiceImpl implements PolicyService {
 	private final PolicyRepository policyRepository;
 	private final PolicyPlanRepository policyPlanRepository;
 	private final CustomerRepository customerRepository;
+	private final ClaimRepository claimRepository;
 	private final ModelMapper modelMapper;
 
 	@Override
@@ -61,11 +64,33 @@ public class PolicyServiceImpl implements PolicyService {
 		PolicyPlan plan = policyPlanRepository.findByIdAndIsActiveTrue(requestDTO.getPlanId())
 				.orElseThrow(PlanNotActiveException::new);
 
-		if (policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(customer.getId(), plan.getId(),
-				List.of(PolicyStatus.ACTIVE, PolicyStatus.PENDING_PAYMENT))) {
-			throw new DuplicateResourceException("This policy is already active or pending payment.");
+//		if (policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(customer.getId(), plan.getId(),
+//				List.of(PolicyStatus.ACTIVE, PolicyStatus.PENDING_PAYMENT))) {
+//			throw new DuplicateResourceException("This policy is already active or pending payment.");
+//		}
+		
+		ProductType productType = plan.getInsuranceProduct().getProductType();
+
+		if (productType == ProductType.HEALTH) {
+
+			boolean exists = policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(customer.getId(),
+					plan.getId(), List.of(PolicyStatus.ACTIVE, PolicyStatus.PENDING_PAYMENT));
+
+			if (exists) {
+				throw new DuplicateResourceException("This health policy is already active or pending payment.");
+			}
+
+		} else {
+
+			boolean pendingExists = policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(
+					customer.getId(), plan.getId(), List.of(PolicyStatus.PENDING_PAYMENT));
+
+			if (pendingExists) {
+				throw new DuplicateResourceException("This policy is already pending payment.");
+			}
 		}
 
+
 		Policy policy = new Policy();
 
 		policy.setCustomer(customer);
@@ -73,13 +98,17 @@ public class PolicyServiceImpl implements PolicyService {
 
 		policy.setPolicyNumber(PolicyNumberGenerator.generatePolicyNumber());
 
+		if (requestDTO.getStartDate().isBefore(LocalDate.now())) {
+			throw new BadRequestException("Policy start date cannot be in the past.");
+		}
+
 		policy.setStartDate(requestDTO.getStartDate());
 
 		policy.setEndDate(requestDTO.getStartDate().plusYears(plan.getDuration()));
 
 		policy.setPolicyStatus(PolicyStatus.PENDING_PAYMENT);
 
-		policy.setTotalPremiumPaid(0.0);
+		policy.setTotalPremiumPaid(BigDecimal.ZERO);
 
 		Policy savedPolicy = policyRepository.save(policy);
 
@@ -92,16 +121,38 @@ public class PolicyServiceImpl implements PolicyService {
 	public ApiResponseDTO<PolicyResponseDTO> issuePolicy(PolicyIssueRequestDTO requestDTO) {
 
 		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
-				.orElseThrow(() -> new RuntimeException("Customer not found"));
+				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + requestDTO.getCustomerId()));
 
 		PolicyPlan plan = policyPlanRepository.findByIdAndIsActiveTrue(requestDTO.getPlanId())
 				.orElseThrow(PlanNotActiveException::new);
 
-		if (policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(customer.getId(), plan.getId(),
-				List.of(PolicyStatus.ACTIVE, PolicyStatus.PENDING_PAYMENT))) {
-			throw new DuplicateResourceException("This policy is already active or pending payment.");
+//		if (policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(customer.getId(), plan.getId(),
+//				List.of(PolicyStatus.ACTIVE, PolicyStatus.PENDING_PAYMENT))) {
+//			throw new DuplicateResourceException("This policy is already active or pending payment.");
+//		}
+		
+		ProductType productType = plan.getInsuranceProduct().getProductType();
+
+		if (productType == ProductType.HEALTH) {
+
+			boolean exists = policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(customer.getId(),
+					plan.getId(), List.of(PolicyStatus.ACTIVE, PolicyStatus.PENDING_PAYMENT));
+
+			if (exists) {
+				throw new DuplicateResourceException("This health policy is already active or pending payment.");
+			}
+
+		} else {
+
+			boolean pendingExists = policyRepository.existsByCustomerIdAndPolicyPlanIdAndPolicyStatusIn(
+					customer.getId(), plan.getId(), List.of(PolicyStatus.PENDING_PAYMENT));
+
+			if (pendingExists) {
+				throw new DuplicateResourceException("This policy is already pending payment.");
+			}
 		}
 
+
 		Policy policy = new Policy();
 
 		policy.setCustomer(customer);
@@ -109,13 +160,17 @@ public class PolicyServiceImpl implements PolicyService {
 
 		policy.setPolicyNumber(PolicyNumberGenerator.generatePolicyNumber());
 
+		if (requestDTO.getStartDate().isBefore(LocalDate.now())) {
+			throw new BadRequestException("Policy start date cannot be in the past.");
+		}
+
 		policy.setStartDate(requestDTO.getStartDate());
 
 		policy.setEndDate(requestDTO.getStartDate().plusYears(plan.getDuration()));
 
 		policy.setPolicyStatus(PolicyStatus.PENDING_PAYMENT);
 
-		policy.setTotalPremiumPaid(0.0);
+		policy.setTotalPremiumPaid(BigDecimal.ZERO);
 
 		Policy savedPolicy = policyRepository.save(policy);
 
@@ -182,7 +237,7 @@ public class PolicyServiceImpl implements PolicyService {
 			String direction) {
 
 		Customer customer = customerRepository.findByUserEmail(email)
-				.orElseThrow(() -> new RuntimeException("Customer not found"));
+				.orElseThrow(() -> new ResourceNotFoundException("Customer profile not found for this account."));
 
 		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
 
@@ -217,6 +272,14 @@ public class PolicyServiceImpl implements PolicyService {
 
 		Policy policy = policyRepository.findById(policyId).orElseThrow(() -> new PolicyNotFoundException(policyId));
 
+		// Block cancellation if any claim is still open
+		List<ClaimStatus> openStatuses = List.of(ClaimStatus.SUBMITTED, ClaimStatus.UNDER_REVIEW, ClaimStatus.RECOMMENDED_FOR_APPROVAL);
+		boolean hasOpenClaims = policy.getClaims().stream()
+				.anyMatch(c -> openStatuses.contains(c.getClaimStatus()));
+		if (hasOpenClaims) {
+			throw new BadRequestException("Policy cannot be cancelled while a claim is still pending or under review.");
+		}
+
 		policy.setPolicyStatus(PolicyStatus.CANCELLED);
 
 		Policy updatedPolicy = policyRepository.save(policy);
@@ -247,6 +310,10 @@ public class PolicyServiceImpl implements PolicyService {
 		dto.setPremiumAmount(policy.getPolicyPlan().getPremiumAmount());
 		dto.setPremiumType(policy.getPolicyPlan().getPremiumType().name());
 
+		BigDecimal activeClaimsSum = claimRepository.sumActiveClaimsByPolicyId(policy.getId(), ClaimStatus.REJECTED);
+		BigDecimal remaining = policy.getPolicyPlan().getCoverageAmount().subtract(activeClaimsSum);
+		dto.setRemainingClaimAmount(remaining);
+
 		return dto;
 	}
 
```

## File: `src/main/java/com/insurance/demo/serviceimpl/PremiumPaymentServiceImpl.java`
```diff
@@ -1,10 +1,11 @@
 package com.insurance.demo.serviceimpl;
 
+import java.math.BigDecimal;
 import java.time.LocalDateTime;
 import java.util.List;
+import java.util.Optional;
 
 import org.modelmapper.ModelMapper;
-import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
@@ -22,6 +23,7 @@ import com.insurance.demo.dto.response.PageResponseDTO;
 import com.insurance.demo.dto.response.PaymentResponseDTO;
 import com.insurance.demo.enums.PaymentStatus;
 import com.insurance.demo.enums.PolicyStatus;
+import com.insurance.demo.enums.PremiumType;
 import com.insurance.demo.exception.BadRequestException;
 import com.insurance.demo.exception.DuplicateResourceException;
 import com.insurance.demo.exception.ResourceNotFoundException;
@@ -34,7 +36,6 @@ import com.insurance.demo.repository.PremiumPaymentRepository;
 import com.insurance.demo.service.PremiumPaymentService;
 import com.insurance.demo.util.TransactionReferenceGenerator;
 
-import jakarta.validation.Valid;
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 
@@ -52,74 +53,115 @@ public class PremiumPaymentServiceImpl implements PremiumPaymentService {
 	@Transactional
 	public ApiResponseDTO<PaymentResponseDTO> recordPayment(PaymentRequestDTO dto) {
 
-	    log.info("Recording payment for policy id: {}", dto.getPolicyId());
-
-	    Policy policy = policyRepository.findById(dto.getPolicyId())
-	            .orElseThrow(() ->
-	                    new ResourceNotFoundException("Policy not found with id: " + dto.getPolicyId()));
-
-	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
-
-	    boolean isCustomer = SecurityContextHolder.getContext()
-	            .getAuthentication()
-	            .getAuthorities()
-	            .stream()
-	            .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
-
-	    if (isCustomer && !policy.getCustomer().getUser().getEmail().equals(email)) {
-	        throw new AccessDeniedException("You are not allowed to record payment for another customer's policy");
-	    }
-
-	    if (policy.getPolicyPlan().getPremiumAmount().compareTo(dto.getAmount()) != 0) {
-	        throw new BadRequestException("Payment amount must match premium amount");
-	    }
-	    
-	    if(PolicyStatus.CANCELLED.equals(policy.getPolicyStatus())) {
-	    	throw new BadRequestException("you are restricted to make payment for a cancelled policy");
-	    }
-	    
-	    if(PolicyStatus.EXPIRED.equals(policy.getPolicyStatus())) {
-	    	throw new BadRequestException("you are restricted to make payment for a expired policy");
-	    }
-
-	    String transactionReferance = TransactionReferenceGenerator.generateTransactionReference();
-	    
-	    if (paymentRepository.existsByTransactionReference(transactionReferance)) {
-	        throw new DuplicateResourceException("Transaction reference already exists");
-	    }
-	    
-	    if(policy.getPolicyPlan().getCoverageAmount() <= (policy.getTotalPremiumPaid() + dto.getAmount())) {
-	    	throw new BadRequestException("Required premium already paid. Policy is active.");
-	    }
-
-	    PremiumPayment payment = new PremiumPayment();
-	    payment.setAmount(dto.getAmount());
-	    payment.setPaymentMode(dto.getPaymentMode());
-	    payment.setTransactionReference(transactionReferance);
-	    payment.setPolicy(policy);
-	    payment.setPaymentDate(LocalDateTime.now());
-
-	    if(PaymentStatus.SUCCESS.equals(dto.getPaymentStatus())) {
-	    payment.setPaymentStatus(PaymentStatus.SUCCESS);
-	    }
-	    
-	    if(PaymentStatus.FAILED.equals(dto.getPaymentStatus())) {
-		    payment.setPaymentStatus(PaymentStatus.FAILED);
-		    }
-
-	    PremiumPayment savedPayment = paymentRepository.save(payment);
-
-	    if(PaymentStatus.SUCCESS.equals(dto.getPaymentStatus())) {
-	    	policy.setTotalPremiumPaid(policy.getTotalPremiumPaid() + dto.getAmount());
-		    policy.setPolicyStatus(PolicyStatus.ACTIVE);
-		    }
-	    
-	    policyRepository.save(policy);
-
-	    PaymentResponseDTO responseDTO = modelMapper.map(savedPayment, PaymentResponseDTO.class);
-	    responseDTO.setPolicyNumber(policy.getPolicyNumber());
-
-	    return new ApiResponseDTO<>("Payment recorded successfully", true, responseDTO, LocalDateTime.now());
+		log.info("Recording payment for policy id: {}", dto.getPolicyId());
+
+		Policy policy = policyRepository.findById(dto.getPolicyId())
+				.orElseThrow(() -> new ResourceNotFoundException("Policy not found with id: " + dto.getPolicyId()));
+
+		String email = SecurityContextHolder.getContext().getAuthentication().getName();
+
+		boolean isCustomer = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
+				.anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
+
+		if (isCustomer && !policy.getCustomer().getUser().getEmail().equals(email)) {
+			throw new AccessDeniedException("You are not allowed to record payment for another customer's policy");
+		}
+
+		if (dto.getPaymentStatus() == null) {
+			throw new BadRequestException("Payment status is required.");
+		}
+
+		if (policy.getPolicyPlan().getPremiumAmount().compareTo(dto.getAmount()) != 0) {
+			throw new BadRequestException("Payment amount must match premium amount");
+		}
+
+		if (PolicyStatus.CANCELLED.equals(policy.getPolicyStatus())) {
+			throw new BadRequestException("you are restricted to make payment for a cancelled policy");
+		}
+
+		if (PolicyStatus.EXPIRED.equals(policy.getPolicyStatus())) {
+			throw new BadRequestException("you are restricted to make payment for a expired policy");
+		}
+
+		
+		// one time payment
+		if (policy.getPolicyPlan().getPremiumType().equals(PremiumType.ONE_TIME)) {
+			// verify any existing payment for this policy -
+			if (paymentRepository.existsByPolicyIdAndPaymentStatus(policy.getId(), PaymentStatus.SUCCESS)) {
+
+				throw new BadRequestException("Premium has already been paid for this ONE_TIME plan.");
+			}
+
+		}
+
+		// annual payment
+		if (policy.getPolicyPlan().getPremiumType().equals(PremiumType.ANNUAL)) {
+
+			Optional<PremiumPayment> payment = paymentRepository
+					.findTopByPolicyIdAndPaymentStatusOrderByPaymentDateDesc(policy.getId(), PaymentStatus.SUCCESS);
+
+			if (payment.isPresent()) {
+
+				PremiumPayment latestPayment = payment.get();
+
+				LocalDateTime nextEligibleDate = latestPayment.getPaymentDate().plusYears(1);
+				LocalDateTime paymentWindowStart = nextEligibleDate.minusDays(15);
+
+				if (LocalDateTime.now().isBefore(paymentWindowStart)) {
+					throw new BadRequestException(
+							"Next annual premium can be paid only after " + paymentWindowStart.toLocalDate() + " (includes 15-day early payment window)");
+				}
+			}
+
+			long successfulPayments = paymentRepository.countByPolicyIdAndPaymentStatus(policy.getId(),
+					PaymentStatus.SUCCESS);
+
+			if (successfulPayments >= policy.getPolicyPlan().getDuration()) {
+				throw new BadRequestException("All annual premiums for this policy have already been paid.");
+			}
+		}
+
+		String transactionReferance = TransactionReferenceGenerator.generateTransactionReference();
+
+		if (paymentRepository.existsByTransactionReference(transactionReferance)) {
+			throw new DuplicateResourceException("Transaction reference already exists");
+		}
+
+		// Fix: compare against total required premium (premiumAmount * duration), not coverage amount
+		BigDecimal totalRequiredPremium = policy.getPolicyPlan().getPremiumAmount()
+				.multiply(BigDecimal.valueOf(policy.getPolicyPlan().getDuration()));
+		if (policy.getTotalPremiumPaid().add(dto.getAmount()).compareTo(totalRequiredPremium) > 0) {
+			throw new BadRequestException("Total payments would exceed the required premium for this policy.");
+		}
+
+		PremiumPayment payment = new PremiumPayment();
+		payment.setAmount(dto.getAmount());
+		payment.setPaymentMode(dto.getPaymentMode());
+		payment.setTransactionReference(transactionReferance);
+		payment.setPolicy(policy);
+		payment.setPaymentDate(LocalDateTime.now());
+
+		if (PaymentStatus.SUCCESS.equals(dto.getPaymentStatus())) {
+			payment.setPaymentStatus(PaymentStatus.SUCCESS);
+		}
+
+		if (PaymentStatus.FAILED.equals(dto.getPaymentStatus())) {
+			payment.setPaymentStatus(PaymentStatus.FAILED);
+		}
+
+		PremiumPayment savedPayment = paymentRepository.save(payment);
+
+		if (PaymentStatus.SUCCESS.equals(dto.getPaymentStatus())) {
+			policy.setTotalPremiumPaid(policy.getTotalPremiumPaid().add(dto.getAmount()));
+			policy.setPolicyStatus(PolicyStatus.ACTIVE);
+		}
+
+		policyRepository.save(policy);
+
+		PaymentResponseDTO responseDTO = modelMapper.map(savedPayment, PaymentResponseDTO.class);
+		responseDTO.setPolicyNumber(policy.getPolicyNumber());
+
+		return new ApiResponseDTO<>("Payment recorded successfully", true, responseDTO, LocalDateTime.now());
 	}
 
 	@Override
@@ -129,12 +171,11 @@ public class PremiumPaymentServiceImpl implements PremiumPaymentService {
 
 		List<PremiumPayment> list = paymentRepository.findByPolicyId(id);
 
-		List<PaymentResponseDTO> responseList = list.stream()
-				.map(payment -> {
-					PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
-					dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
-					return dto;
-				}).toList();
+		List<PaymentResponseDTO> responseList = list.stream().map(payment -> {
+			PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
+			dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
+			return dto;
+		}).toList();
 
 		return new ApiResponseDTO<>("Payments fetched successfully", true, responseList, LocalDateTime.now());
 	}
@@ -149,13 +190,12 @@ public class PremiumPaymentServiceImpl implements PremiumPaymentService {
 
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 		String email = authentication.getName();
-		boolean isCustomer = authentication.getAuthorities()
-				.stream()
+		boolean isCustomer = authentication.getAuthorities().stream()
 				.anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
 
-		if (isCustomer && (payment.getPolicy() == null || payment.getPolicy().getCustomer() == null ||
-				payment.getPolicy().getCustomer().getUser() == null ||
-				!payment.getPolicy().getCustomer().getUser().getEmail().equals(email))) {
+		if (isCustomer && (payment.getPolicy() == null || payment.getPolicy().getCustomer() == null
+				|| payment.getPolicy().getCustomer().getUser() == null
+				|| !payment.getPolicy().getCustomer().getUser().getEmail().equals(email))) {
 			throw new AccessDeniedException("You are not allowed to view this payment");
 		}
 
@@ -170,7 +210,8 @@ public class PremiumPaymentServiceImpl implements PremiumPaymentService {
 	public PageResponseDTO<PaymentResponseDTO> getAllPaymentsWithPagination(int pageNumber, int pageSize, String sortBy,
 			String sortDirection, Long policyId, String paymentStatus) {
 
-		log.info("Fetching Payments with pagination. pageNumber: {}, pageSize: {}, sortBy: {}, sortDirection: {}, policyId: {}, status: {}",
+		log.info(
+				"Fetching Payments with pagination. pageNumber: {}, pageSize: {}, sortBy: {}, sortDirection: {}, policyId: {}, status: {}",
 				pageNumber, pageSize, sortBy, sortDirection, policyId, paymentStatus);
 		validatePagination(pageNumber, pageSize);
 		validatePaymentSortField(sortBy);
@@ -196,17 +237,15 @@ public class PremiumPaymentServiceImpl implements PremiumPaymentService {
 			paymentPage = paymentRepository.findAll(pageable);
 		}
 
-		List<PaymentResponseDTO> content = paymentPage.getContent().stream()
-				.map(payment -> {
-					PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
-					dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
-					return dto;
-				}).toList();
+		List<PaymentResponseDTO> content = paymentPage.getContent().stream().map(payment -> {
+			PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
+			dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
+			return dto;
+		}).toList();
 		return new PageResponseDTO<>(content, paymentPage.getNumber(), paymentPage.getSize(),
 				paymentPage.getTotalElements(), paymentPage.getTotalPages(), paymentPage.isLast(), sortDirection);
 	}
 
-
 	private Direction getSortDirection(String sortDirection) {
 		if (sortDirection == null || sortDirection.equalsIgnoreCase("asc"))
 			return Sort.Direction.ASC;
@@ -241,12 +280,11 @@ public class PremiumPaymentServiceImpl implements PremiumPaymentService {
 		log.info("Fetching payment history for customer email: {}", email);
 		List<PremiumPayment> payments = paymentRepository.findByPolicyCustomerUserId(user.getId());
 
-		List<PaymentResponseDTO> responseList = payments.stream()
-				.map(payment -> {
-					PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
-					dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
-					return dto;
-				}).toList();
+		List<PaymentResponseDTO> responseList = payments.stream().map(payment -> {
+			PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
+			dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
+			return dto;
+		}).toList();
 
 		return new ApiResponseDTO<>("Payment history fetched successfully", true, responseList, LocalDateTime.now());
 	}
@@ -262,14 +300,14 @@ public class PremiumPaymentServiceImpl implements PremiumPaymentService {
 		log.info("Fetching payments for policy ID: {} by customer email: {}", policyId, email);
 		List<PremiumPayment> payments = paymentRepository.findByPolicyIdAndPolicyCustomerUserId(policyId, user.getId());
 
-		List<PaymentResponseDTO> responseList = payments.stream()
-				.map(payment -> {
-					PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
-					dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
-					return dto;
-				}).toList();
+		List<PaymentResponseDTO> responseList = payments.stream().map(payment -> {
+			PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
+			dto.setPolicyNumber(payment.getPolicy().getPolicyNumber());
+			return dto;
+		}).toList();
 
-		return new ApiResponseDTO<>("Payments for policy fetched successfully", true, responseList, LocalDateTime.now());
+		return new ApiResponseDTO<>("Payments for policy fetched successfully", true, responseList,
+				LocalDateTime.now());
 	}
 
 }
```

## File: `src/main/java/com/insurance/demo/serviceimpl/UserServiceImpl.java`
```diff
@@ -23,6 +23,7 @@ import com.insurance.demo.enums.Role;
 import com.insurance.demo.exception.BadRequestException;
 import com.insurance.demo.exception.DuplicateResourceException;
 import com.insurance.demo.exception.ResourceNotFoundException;
+import com.insurance.demo.model.AgentSpeciality;
 import com.insurance.demo.model.AppUser;
 import com.insurance.demo.repository.AppUserRepository;
 import com.insurance.demo.service.UserService;
@@ -49,7 +50,7 @@ public class UserServiceImpl implements UserService {
 		List<AppUser> users = userRepository.findAll();
 
 		List<UserResponseDTO> userResponseDTOs = users.stream()
-				.map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();
+				.map(this::mapToUserResponseDTO).toList();
 
 		ApiResponseDTO<List<UserResponseDTO>> apiResponseDTO = new ApiResponseDTO<>();
 
@@ -71,14 +72,14 @@ public class UserServiceImpl implements UserService {
 
 		AppUser user = getById(userId);
 
-		if (Boolean.FALSE.equals(user.isEmailVerified())) {
-			UserResponseDTO responseDto = modelMapper.map(user, UserResponseDTO.class);
+		if (Boolean.FALSE.equals(user.getEmailVerified())) {
+			UserResponseDTO responseDto = mapToUserResponseDTO(user);
 			log.info("User is not verified by id: {}", userId);
 			return new ApiResponseDTO<>("User is not verified", false, responseDto, LocalDateTime.now());
 		}
 
 		if (Boolean.TRUE.equals(user.getIsActive())) {
-			UserResponseDTO responseDto = modelMapper.map(user, UserResponseDTO.class);
+			UserResponseDTO responseDto = mapToUserResponseDTO(user);
 			log.info("user already active with id {} ", userId);
 			return new ApiResponseDTO<>("User Already Active", false, responseDto, LocalDateTime.now());
 		}
@@ -87,7 +88,7 @@ public class UserServiceImpl implements UserService {
 
 		AppUser retrivedUser = userRepository.save(user);
 
-		UserResponseDTO responseDto = modelMapper.map(retrivedUser, UserResponseDTO.class);
+		UserResponseDTO responseDto = mapToUserResponseDTO(retrivedUser);
 		return new ApiResponseDTO<>("User Activated successfully", true, responseDto, LocalDateTime.now());
 	}
 
@@ -111,14 +112,14 @@ public class UserServiceImpl implements UserService {
 
 		AppUser user = getById(userId);
 
-		if (Boolean.FALSE.equals(user.isEmailVerified())) {
-			UserResponseDTO responseDto = modelMapper.map(user, UserResponseDTO.class);
+		if (Boolean.FALSE.equals(user.getEmailVerified())) {
+			UserResponseDTO responseDto = mapToUserResponseDTO(user);
 			log.info("User is not verified by id: {}", userId);
 			return new ApiResponseDTO<>("User is not verified", false, responseDto, LocalDateTime.now());
 		}
 
 		if (Boolean.FALSE.equals(user.getIsActive())) {
-			UserResponseDTO responseDto = modelMapper.map(user, UserResponseDTO.class);
+			UserResponseDTO responseDto = mapToUserResponseDTO(user);
 			log.info("Already deactivated user by id: {}", userId);
 			return new ApiResponseDTO<>("User Already Deactivated", false, responseDto, LocalDateTime.now());
 		}
@@ -127,7 +128,7 @@ public class UserServiceImpl implements UserService {
 
 		AppUser retrivedUser = userRepository.save(user);
 
-		UserResponseDTO responseDto = modelMapper.map(retrivedUser, UserResponseDTO.class);
+		UserResponseDTO responseDto = mapToUserResponseDTO(retrivedUser);
 		return new ApiResponseDTO<>("User Deactivated successfully", true, responseDto, LocalDateTime.now());
 	}
 
@@ -152,11 +153,17 @@ public class UserServiceImpl implements UserService {
 		user.setRole(Role.ROLE_AGENT);
 		user.setIsActive(false);
 		user.setEmailVerified(false);
+		
+		AgentSpeciality agentSpeciality = new AgentSpeciality();
+		agentSpeciality.setProductSpeciality(agentRequestDTO.getProductSpeciality());
+		agentSpeciality.setAgent(user);
+		user.setAgentSpeciality(agentSpeciality);
+
 		AppUser retrivedUser = userRepository.save(user);
 
 		otpService.createAndSendOtp(retrivedUser);
 
-		UserResponseDTO dto = modelMapper.map(retrivedUser, UserResponseDTO.class);
+		UserResponseDTO dto = mapToUserResponseDTO(retrivedUser);
 		return new ApiResponseDTO<>("Account created. An OTP has been sent to the email to complete registration.",
 				true, dto, LocalDateTime.now());
 	}
@@ -193,7 +200,7 @@ public class UserServiceImpl implements UserService {
 			userPage = userRepository.findAll(pageable);
 		}
 		List<UserResponseDTO> content = userPage.getContent().stream()
-				.map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();
+				.map(this::mapToUserResponseDTO).toList();
 		return new PageResponseDTO<>(content, userPage.getNumber(), userPage.getSize(), userPage.getTotalElements(),
 				userPage.getTotalPages(), userPage.isLast(), sortDirection);
 	}
@@ -204,7 +211,7 @@ public class UserServiceImpl implements UserService {
 		AppUser user = userRepository.findByEmail(username)
 				.orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + username));
 
-		return modelMapper.map(user, UserResponseDTO.class);
+		return mapToUserResponseDTO(user);
 	}
 
 	@Override
@@ -213,7 +220,7 @@ public class UserServiceImpl implements UserService {
 		log.info("Fetching User with id - {} ", id);
 		AppUser appUser = getById(id);
 
-		UserResponseDTO dto = modelMapper.map(appUser, UserResponseDTO.class);
+		UserResponseDTO dto = mapToUserResponseDTO(appUser);
 
 		return new ApiResponseDTO<>("User found", true, dto, LocalDateTime.now());
 	}
@@ -234,7 +241,7 @@ public class UserServiceImpl implements UserService {
 
 	private void validateUserSortField(String sortBy) {
 		if (!List.of("id", "fullName", "email", "mobileNumber", "role", "isActive").contains(sortBy)) {
-			throw new BadRequestException("Invalid sort field for course: " + sortBy);
+			throw new BadRequestException("Invalid sort field for users: " + sortBy);
 		}
 	}
 
@@ -246,4 +253,12 @@ public class UserServiceImpl implements UserService {
 		throw new BadRequestException("Sort direction must be asc or desc.");
 	}
 
+	private UserResponseDTO mapToUserResponseDTO(AppUser user) {
+		UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
+		if (user.getAgentSpeciality() != null) {
+			dto.setProductSpeciality(user.getAgentSpeciality().getProductSpeciality());
+		}
+		return dto;
+	}
+
 }
```

## File: `src/main/java/com/insurance/demo/util/PaginationValidator.java`
```diff
new file mode 100644
@@ -0,0 +1,34 @@
+package com.insurance.demo.util;
+
+import com.insurance.demo.exception.BadRequestException;
+import java.util.Set;
+
+/**
+ * Shared pagination validation utility.
+ * Centralises page/size/sortField validation that was duplicated across all service classes.
+ */
+public class PaginationValidator {
+
+    private static final int MAX_PAGE_SIZE = 100;
+
+    private PaginationValidator() {
+    }
+
+    public static void validate(int page, int size) {
+        if (page < 0) {
+            throw new BadRequestException("Page number cannot be negative.");
+        }
+        if (size <= 0) {
+            throw new BadRequestException("Page size must be greater than zero.");
+        }
+        if (size > MAX_PAGE_SIZE) {
+            throw new BadRequestException("Page size cannot exceed " + MAX_PAGE_SIZE + ".");
+        }
+    }
+
+    public static void validateSortField(String sortBy, Set<String> allowedFields) {
+        if (!allowedFields.contains(sortBy)) {
+            throw new BadRequestException("Invalid sort field: '" + sortBy + "'. Allowed fields: " + allowedFields);
+        }
+    }
+}
```

## File: `src/main/java/com/insurance/demo/verification/OtpService.java`
```diff
@@ -5,6 +5,7 @@ import java.security.SecureRandom;
 import java.time.LocalDateTime;
 
 import org.springframework.beans.factory.annotation.Value;
+import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
@@ -22,6 +23,7 @@ public class OtpService {
     private final OtpVerificationRepository otpRepository;
     private final EmailService emailService;
     private final SmsService smsService;
+    private final PasswordEncoder passwordEncoder;
     private final SecureRandom secureRandom = new SecureRandom();
 
     @Value("${app.otp.expiry-minutes}")
@@ -34,8 +36,8 @@ public class OtpService {
 
         OtpVerification otpVerification = OtpVerification.builder()
                 .user(user)
-                .emailOtp(emailOtp)
-                .phoneOtp(phoneOtp)
+                .emailOtp(passwordEncoder.encode(emailOtp))
+                .phoneOtp(passwordEncoder.encode(phoneOtp))
                 .expiresAt(LocalDateTime.now().plusMinutes(expiryMinutes))
                 .used(false)
                 .build();
@@ -54,13 +56,13 @@ public class OtpService {
             throw new BadRequestException("OTP expired. Please register again to get a new OTP.");
         }
 
-        if (!latestOtp.getEmailOtp().equals(emailOtp)) {
+        if (!passwordEncoder.matches(emailOtp, latestOtp.getEmailOtp())) {
             throw new BadRequestException("Invalid email OTP");
         }
 
-//        if (!latestOtp.getPhoneOtp().equals(phoneOtp)) {
-//            throw new BadRequestException("Invalid phone OTP");
-//        }
+        if (!passwordEncoder.matches(phoneOtp, latestOtp.getPhoneOtp())) {
+            throw new BadRequestException("Invalid phone OTP");
+        }
 
         latestOtp.setUsed(true);
         otpRepository.save(latestOtp);
@@ -70,4 +72,17 @@ public class OtpService {
         int number = secureRandom.nextInt(900000) + 100000;
         return String.valueOf(number);
     }
+    
+    public boolean invalidateLastOtp(AppUser user) {
+
+		OtpVerification latestOtp = otpRepository.findTopByUserAndUsedFalseOrderByCreatedAtDesc(user)
+				.orElseThrow(() -> new BadRequestException("No active OTP found. Please register again."));
+
+		if (latestOtp.getExpiresAt().isAfter(LocalDateTime.now())) {
+			return false;
+
+		}
+
+		return true;
+	}
 }
```

## File: `src/main/resources/application.properties`
```diff
@@ -23,7 +23,7 @@ logging.level.com.insurance.demo = DEBUG
 # JWT configuration
 app.jwt.secret=${JWT_KEY}
 
-app.jwt.expiration-ms=300000
+app.jwt.expiration-ms=6000000
 
 
 cloudinary.cloud-name=${CLOUDINARY_CLOUD_NAME}
```
