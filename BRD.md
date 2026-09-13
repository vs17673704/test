# Business Requirements Document (BRD)
## Referral & Reward Program Platform

> This document is a **complete, logically reorganized** copy of `Requirement.md` (itself a verified, gap-free transcription of the client's requirements PDF). Every Rule (I–XXXVIII) and every Part B section/sub-section has been preserved **verbatim** — nothing has been summarized, paraphrased, or omitted. Content has only been *relocated* into thematic sections so that related rules, modules, and flows sit together. Where a single source paragraph list (e.g. "Points to remember") contained items belonging to different themes, each item was moved to its matching theme in full and labelled `*(Points to remember)*` for traceability. Numbered flow diagrams retain their original numbers (e.g. "Flow Numbering 17") so they remain cross-referenceable against `Requirement.md`.
>
> A subsequent requirements-quality audit identified five clarification gaps, all of which have now been amended directly into this document: (1) **Redeemable Balance = Actual Redeemable Balance** as one underlying financial balance; (2) Reward Points rounding as an explicit exception to monetary Round Half Up rounding (Rule XXXIII); (3) Reward calculation when previously earned Reward Points are used for a course; (4) measurable Performance/PWA/Scalability NFR wording; and (5) the previously empty Referral Cancellation Flow Numbering 18 cross-reference.
>
> A second-pass audit of this fully-amended document identified four further clarification gaps. Since `BRD_Updated.md` does not yet contain client-approved language for these, the following are **original draft clarifications proposed for review — none has been confirmed by the client**: (6) Rule XIX (Referral Validity Period) expiry accrual behavior, mirrored from Rule XX; (7) precedence between the global referral validity period and a referrer-specific validity period; (8) "authorized Admin"/"authorized administrator" refers to the single defined Admin role, not a separate permission tier; and (9) the worked-example label "Actual Balance" is the same balance as Redeemable Balance / Actual Redeemable Balance. Each is marked inline with **"Drafted Clarification — pending client confirmation."**
>
> A third-pass audit found two further issues, and the client has since **explicitly confirmed** how to resolve both — these are settled requirement changes, not pending drafts, and are marked inline as **"Client-Requested Deviation."** (10) Reward Points shall never be displayed or labeled with the ₹ (or any other currency) symbol, since they are a non-monetary whole-number unit — all such labels in Appendix A have been corrected accordingly. (11) Reward Points rounding direction has been changed from round-down (floor) to **round-up (ceiling)** — this explicitly overrides the original source-stated floor-rounding rule and its worked example in Rule XIII/Rule XXXIII and in Appendix A's "Reward Points Rounding" example, which are preserved verbatim but annotated as superseded. (12) Mobile number verification is **not required** — the original source sentence requiring a "verified" status via a configured verification process is preserved verbatim but annotated as superseded.
>
> A new requirement (13) was added by the client after the original PDF was finalized: a public **Landing Page** displaying available Plans and Franchisee options to visitors before registration/login. This did not exist in the original source PDF or in `Requirement.md`'s verbatim transcription; it is recorded there under a separate **"Client Addendum — Not in Original PDF"** section, and reflected here in **1. Platform Overview**, marked **"Client-Requested Deviation"** for traceability.
>
> Two verbatim, differently-worded "Commission States:" listings exist in the source (one inside the Commission Lifecycle flow, one inside Final Referral Rules) — both are preserved in full, in their original locations, rather than merged, since they are not identical.
>
> The "Worked Financial Examples" block is kept intact as a single appendix (rather than split across themes) to preserve its internal narrative flow and cross-references between examples; each theme above links to it via a "Worked examples: see Appendix A" note. This block ends mid-word ("shall remai...") in the source document itself — this is preserved as-is rather than "fixed," since altering source wording would itself introduce a discrepancy against `Requirement.md`.
>
> A fourth-pass cross-reference audit of Rule XXXIX (Website Content Pages & General Enquiry) found 26 gaps against the original client requirement for that Rule, plus 3 further cross-reference findings identified once Rule XXXIX was compared against the rest of this document. All 26 original gaps have been resolved directly in Rule XXXIX and its related sections. Of those, five previously carried only a "Drafted Clarification — pending client confirmation" placeholder with no numeric value; those five now carry a concrete proposed default, marked inline as **"RESOLVED — PROPOSED DEFAULT — CLIENT CONFIRMATION RECOMMENDED"** (About Us section cap: 20; field length limits; anti-spam thresholds; General Enquiry retention: 24 months; public navigation menu order) — these apply immediately as working defaults but remain open for client confirmation/override, distinct from the settled "Client-Requested Deviation" items elsewhere in this document. Of the 3 further findings: (27) § 1 Platform Overview's navigation sentence has been corrected from stating a fixed 5-item menu to forward-referencing Rule XXXIX.11's conditional extension, removing a contradiction; (28) Rule XXXIX.6's notification-channel cross-reference has been corrected from the incorrect "Rule XXXVIII and §7" to the correct "§7 Notification System" (Rule XXXVIII governs only the unrelated Upcoming Instalment Reminder); (29) the differing audit/notification event names `GENERAL_ENQUIRY_SUBMITTED` and `GENERAL_ENQUIRY_RECEIVED` were confirmed to be intentionally distinct (audit of the act vs. notification of receipt) and were left unchanged.

## Table of Contents

1. Platform Overview
2. Plans, Tenure & Interest Engine
3. Payments & Payment Processing
4. Referral & Commission System
5. Redemption, Available Margin & Money Mechanics
6. Admin, User Management & Roles
7. Notifications
8. Dashboards & Reports
9. Security
10. APIs & Database Design
11. Non-Functional Requirements
- Appendix A: Worked Financial Examples
- Appendix B: Rule Number Locator
---

# 1. Platform Overview
Client requires website with following functionalities:
I. Website Admin will make Plans of certain tenures like 6 months, 1 year, 2 years, 3 years etc. that consists
of benefits provided and the gain attained upon maturity from which registered user can opt any one, select
the payment amount contribution (either monthly or Lum sum payment) and make payment through
payment gateway integration.
II. When user select the plan and payment amount, then he would be able to see:
● Invested or Contributed amount.
● Amount which can be redeemable after the completion of selected Plan tenure and thirdly the
amount which user can redeem if they do any educational course from the institute or choose any
other things from the list provided by the institute. Amount which user can redeem will be calculated
according to annual/monthly interest defined by the web admin for that plan.
● Amount which is shown to user for opting any course/Item provided by institute, it would be again
calculated according to annual/monthly interest defined by the web admin for that plan.

**Client-Requested Deviation — new requirement, not in original PDF:** The website shall provide a public
**Landing Page**, accessible without login, that displays:
- The available Plans (as configured by the Admin), so a visitor can browse Plan options before registering.
- The available Franchisee options (Franchisee Plans and their associated Colleges), so a visitor can browse
  Franchisee options before registering.
- A navigation menu with the base options `Home | Plans | Franchisee | Login | Register`. This menu
  is extended by Rule XXXIX.11 when the optional About Us and/or Contact Us pages are configured
  and enabled; see Rule XXXIX.11 for the resulting menu order and visibility rules.

This is a new client-requested requirement added after the original PDF was finalized. See `Requirement.md`'s
"Client Addendum — Not in Original PDF" section for the corresponding source record.

---


# 2. Plans, Tenure & Interest Engine


## Rule III — Plan Discontinuation

III. Plan Discontinuation: When an Admin discontinues a plan, existing users enrolled in that plan shall
continue to receive the value applicable at maturity.
- User cannot redeem the money back before the maturity of the selected plan tenure even if
they stop paying the scheduled instalment irrespective of whether the plan gets discontinued
by the admin.
- Discontinued Plan Availability: Once a Plan is discontinued, it shall no longer be available
for display, selection, or enrolment by new users.
- Existing Enrolled Users: Discontinuation of a Plan shall not terminate or discontinue the
Plan for users who were already enrolled before the discontinuation. Such users shall
continue under the applicable Plan terms until the Plan reaches its maturity date.
- Discontinued Plan Maturity: For an existing enrolled user, the discontinued Plan shall
remain active until its configured maturity date. Upon reaching the maturity date, the Plan
shall transition to MATURED and become eligible for the normal redemption flow.
- Discontinuation Financial Treatment: No haircut, deduction, or penalty shall be applied
solely because the Plan was discontinued. The applicable plan value shall be calculated and
settled according to the applicable Plan terms, including any applicable pro-rated value. Here
pro-rated value is used for the users who were unable to submit all the instalments till the
maturity period. So, whatever amount had been paid by the users, the final settlement will be
done on the basis of the amount deposited by the user.
- Plan cancellation isn’t required in this project.
- When an Admin discontinues a Plan, the Plan shall move to DISCONTINUED status
for existing enrolled users. The Plan shall remain DISCONTINUED until its
configured maturity date. Existing enrolled users shall continue under the applicable
Plan terms, and upon maturity the Plan shall transition from DISCONTINUED →
## MATURED.
- A DISCONTINUED Plan shall not be available for new user enrolment.

## Rule IV — Final Redeemable Amount at Maturity

IV. Final redeemable amount will be calculated according to the amount in the user account at the time of
plan maturity.

## Rule V — Partial Redemption & Reinvestment

V. User can redeem partial amount after the plan maturity and can re-invest pending amount by choosing
any new plan. There is no limit to partial redemptions.

## Rule XIV — Plan View, Inputs & Autopay Setup

XIV. Plans when opted, a view will appear with plan detail as set by the admin and inputs that includes the
list of preset amounts and list of payment frequency set by the admin. These inputs of amount and payment
frequency will be used for auto pay feature. Plan created by user talks about the benefits and gains during
until the plan maturity whereas the amount selected by the user is based on the minimum investment
required for the plan and the payment frequency. When user submit the plan, then auto-debit will be
effective upon auto-payment is setup by the user for the plan. Earlier the selected duration is the frequency
of Autopay that will take place until the maturity of the plan. User can have multiple plans available to opt

and will be active as per the plan maturity period. So basically, a plan in this project means a screen where
plan detail set by admin are available along with the inputs on the basis of which Autopay will work until the
plan reaches the maturity.

## Rule XVI — Plan Configuration Changes Do Not Affect Existing Plans

XVI. If admin changes the plan details like interest, etc then existing plans shouldn’t be affected by the
change. If a Plan is discontinued, the existing enrolled Plan shall remain DISCONTINUED until its originally
configured maturity date and shall transition to MATURED only upon reaching that maturity date.

## Plan Management (Admin)

Plan Management (Admin):
- Investment Tenure
- Monthly/Lumpsum
- Interest
- Reward - a percentage configured by the Admin that shall be applied to the applicable course fee for
an approved course redemption to calculate the reward points/amount. Reward Amount =
`Applicable Course Fee × Configured Reward Percentage`
- Plan active/inactive
- List of the amount to be invested
- Payment Frequency
- Plan Configuration Update: Any administrative modification to an existing plan's configurable
terms, including interest rate, tenure, reward percentage, commission percentage, or other
applicable plan parameters.
- Existing Plan Terms: When an Admin updates the configuration of a plan, the updated
configuration shall apply only to new plan purchases created after the effective configuration
change. Existing plans shall continue to use the terms captured at the time of their purchase unless
an explicitly defined migration operation is performed.
- **Reward Percentage Scope:**
The Admin shall be able to configure the Reward Percentage either for each plan separately or as
a common Reward Percentage applicable across all plans.
The Reward Percentage applicable to a course redemption shall be determined from the
configuration applicable to the user's plan at the time of the relevant plan purchase/redemption,
consistent with the existing Plan Configuration and Existing Plan Terms rules.
- Recommended flow

```
PLAN CONFIGURATION
|
+---- Existing Plans
|        |
|        +--> RETAIN ORIGINAL TERMS
|
+---- New Purchases
|
+--> USE UPDATED TERMS
```



## Interest Engine

## Interest Engine:
The system shall provide an Interest Engine through which the Admin can configure and assign supported
Interest Calculation Methods to plans. The detailed formula types, parameters, validation, versioning,
snapshot/locking behaviour, and ledger requirements shall be governed by Rule XXVI – Interest
Calculation Formula Configuration.

**Simplified Interest Configuration Flow:**

```
ADMIN CONFIGURES INTEREST METHOD
↓
SELECT FORMULA TYPE
↓
SIMPLE / COMPOUND / CUSTOM
↓
CONFIGURE REQUIRED PARAMETERS
↓
SYSTEM VALIDATES CONFIGURATION
↓
VALID?
↓
NO → REJECT CONFIGURATION
↓
YES
↓
SELECT PLANS
↓
APPLY INTEREST METHOD
↓
SNAPSHOT FORMULA + PARAMETERS
↓
LOCK PLAN-SPECIFIC CONFIGURATION
↓
CALCULATE INTEREST
↓
RECORD CALCULATION IN LEDGER
↓
STORE METHOD / VERSION REFERENCE
```

Important clarification:

```
Plan A
Interest Rate = 10%
Method Version = V1
↓
Interest accrued using V1
↓
Admin changes configuration to 12%
↓
New Method Version = V2
↓
Plan A continues using V1
↓
V2 applies only when assigned to eligible/new plans
```





## Rule XXVI — Interest Calculation Formula Configuration

XXVI. Interest Calculation Formula Configuration:
a) The Admin shall configure and manage an Interest Calculation Method by selecting one of the
following formula types and specifying its required parameters:
- Simple Interest
- Compound Interest, with configurable compounding frequency
- Custom Parameterized Formula
b) For Simple Interest and Compound Interest, the Admin shall configure the applicable interest rate,
tenure basis, and interest period, including predefined periods such as weekly, monthly, quarterly, and
annually, with a custom period option where permitted. For Compound Interest, the Admin shall
additionally specify the compounding frequency.
c) A Custom Parameterized Formula shall use only the following system-approved variables and predefined
mathematical operators:
- Principal
- Rate
- Tenure
- Elapsed Days
Custom Formula Validation: Custom formulas shall use only the approved variables Principal, Rate,
Tenure, and Elapsed Days, together with predefined mathematical operators supported by the system. The
system shall validate syntax, operator usage, nesting depth, expression length, unsupported
variables/operators, division by zero and non-finite results before saving or applying the formula. Invalid
formulas shall be rejected.
- +
- -
- *
- /
- parentheses
- maximum expression length
- maximum nesting depth
- division-by-zero → reject
- NaN / Infinity → reject
- negative result → reject
d) The Admin shall be able to apply an Interest Calculation Method to selected plans, including newly
created or existing plans. Once applied, the method and its parameters shall be snapshotted and locked
for that plan. The snapshot shall include the formula type, formula/parameters, interest rate,
tenure/interest-period basis, compounding frequency where applicable, and configuration version.
e) Any subsequent modification to an Interest Calculation Method shall apply only to plans created or
assigned after the modification and shall not retroactively change or recalculate interest already
accrued under an existing plan.
f) Every interest calculation shall be recorded in the ledger with a reference to the exact Interest Calculation
Method and configuration/parameter version used, enabling audit and reproducibility.
Custom Formula Constraints: Maximum expression length shall be 100 characters and maximum nesting
depth shall be 5.

## Plan Discontinuation Flow (Diagram)

## 19. Plan Discontinuation:

```
ACTIVE PLAN
↓
PLAN DISCONTINUATION
↓
PLAN = DISCONTINUED
↓
PLAN NOT AVAILABLE TO NEW USERS
↓
EXISTING ENROLLED USERS
↓
USER'S ENROLLED PLAN REMAINS ACTIVE
↓
CONTINUE UNTIL ORIGINAL MATURITY DATE
↓
PLAN REACHES MATURITY DATE
↓
USER'S PLAN = MATURED
↓
CALCULATE APPLICABLE PLAN VALUE
↓
NO DISCONTINUATION-BASED HAIRCUT,
DEDUCTION OR PENALTY
↓
SETTLE APPLICABLE VALUE
↓
UPDATE BALANCE / LEDGER
↓
NORMAL REDEMPTION FLOW
```

- Plan status: DISCONTINUED — no longer available to new users.
- Existing user's enrolment: remains ACTIVE until its original maturity date.
- At maturity: the enrolled user's plan becomes MATURED.
- No early settlement merely because the plan was discontinued.


*(Points to remember)* Interest Calculation Method

- Interest Calculation Method: Admin can create multiple interests out of simple/compound interest or
interest calculated by some configurable formula. These interests will be created once and applied to other
places wherever required.

*(Points to remember)* Interest on Plan

- Interest on Plan: Applicable only at plan maturity.

*(Points to remember)* Educational Benefit Calculation

- Educational Benefit Calculation: Redeemable amount is calculated using one of the Interest Calculation
as created by the admin.

> Worked example: see Appendix A — "One Illustrative Interest Example".


---


# 3. Payments & Payment Processing


## Rule VI — Auto-Payment Receipts

VI. Autopayment receipt is generated and sent to user email when their payment is deducted. An option to
download will be available to download the receipts, reports and transactions.

## Rule VII — Auto-Debit Payment Methods

VII. Option to deduct auto payment from user attached credit/debit card, UPI, Net banking or digital wallet
as per the payment frequency selected by the user while opting the plan.

## Rule XXVII — Failed Payment and Retry Management

XXVII. Failed Payment and Retry Management:
- The system shall support automatic retry of failed recurring Plan payments through the configured
payment gateway. The Admin shall be able to configure the maximum number of retry attempts,
retry interval, and applicable grace period.
- When a scheduled Plan payment fails, the system shall record the failed payment attempt and notify
the user. The system shall automatically retry the payment according to the configured retry policy
until the payment succeeds, the maximum retry attempts are exhausted, or the configured grace
period expires, whichever occurs first.

- Failure to successfully complete a scheduled payment, including exhaustion of all configured retries
and expiry of the applicable grace period, shall not result in cancellation or suspension of the
enrolled Plan.
- The system shall continue to maintain the Plan until its applicable maturity date. At maturity, the
system shall calculate the amount payable to the user based on the actual amount and applicable
duration for which payments were successfully received, using the defined pro-rata maturity
calculation. The resulting amount shall be recorded in the ledger and made payable to the user
through the applicable redemption/maturity process.
- All successful payments, failed attempts, retries, and applicable adjustments to the maturity
calculation shall be recorded in the ledger for auditability.
- Grace Period: The Admin shall configure the payment grace period in hours. The value shall be a
positive whole number within the configured system-supported range. The configured grace period
shall start from the scheduled payment due date/time and shall determine the period during which a
failed payment may be successfully recovered. 24 hours default, allow 0–168h (up to 7 days)
configurable.
- Retry Configuration: The Admin shall configure the maximum number of automatic retry attempts
and the retry interval. The system shall retry only failures identified as retriable by the payment
gateway. Non-retriable or terminal failures shall not be retried automatically. Retry attempts shall
stop when payment succeeds, the maximum retry count is reached, the grace period expires, or the
gateway identifies the failure as non-retriable. User will then get an option to pay using other modes
of payments like UPI, Credit/Debit cards, Net banking, etc, which are manual payment modes.
These modes will appear when retry limit and grace period had reached their limits. Retry intervals
shall follow a configurable retry schedule; where no schedule is configured, a fixed configured
interval shall be used. Retry count/interval — default 3 retries, interval 24h between attempts (so a
3-retry cycle spans ~3 days), configurable range 1–5 retries / 1–72h interval.
- Manual Payment Fallback: After the configured retry attempts are exhausted or the grace period
expires, the system shall provide the user with the available manual payment options. The Admin
shall configure a manual payment window during which the user may complete the missed
scheduled payment manually. The default manual payment window shall be 24 hours, with a
configurable range of 1 to 72 hours. The manual payment window shall be measured from the point
at which automatic recovery ends. The manual payment window shall apply only after automatic
recovery ends and shall not extend or modify the configured retry count or grace period.
A manual payment successfully received within this configured window shall be treated as a
successful payment for the corresponding scheduled instalment and shall be recorded in the Unified
Financial Ledger. A manual payment received after the configured manual payment window shall
not be treated as a successful payment for that scheduled instalment.

## Rule XXX — Payment Method Change

XXX. Payment Method: The user may change the payment method associated with an ACTIVE or
DISCONTINUED Plan, subject to applicable payment gateway validation and authorization requirements.
Changing the payment method shall not modify the Plan's payment amount, payment frequency, principal
already paid, or accrued interest. For a DISCONTINUED Plan, the payment method may be changed only
for scheduled payments that remain due before the Plan's maturity date.

## Rule XXXVI — Duplicate Payment Handling and Idempotency

XXXVI. Duplicate Payment Handling and Idempotency
The system shall prevent the same payment from being credited, recorded, or processed more than once,
including where duplicate gateway callbacks/webhooks, user retries, network retries, scheduled-payment
retries, or delayed gateway responses are received.
**1. Idempotency Key**
Every payment initiation request shall have a unique Idempotency Key generated by the backend.
The Idempotency Key shall be associated with the intended payment transaction and shall remain
unchanged when the same payment request is retried.
The system shall persist the Idempotency Key and shall enforce uniqueness at the database level.
If the same Idempotency Key is received again:
- the system shall not create a new payment transaction;
- the system shall not create another investment/ledger entry;
- the system shall not generate another commission;
- the system shall not send another successful-payment business event;
- the system shall return/reference the result of the original transaction.
**2. Gateway Transaction Reference**
For payments processed through Razorpay, the system shall store the gateway-provided unique payment
identifiers, including the applicable Order ID, Payment ID, and other relevant gateway reference/receipt
identifiers.
A successful gateway transaction identifier shall be unique within the payment records.
A webhook/callback for an already processed gateway Payment ID shall be treated as a duplicate event
and shall not result in another financial transaction.
- Definition of a Duplicate Payment
A payment shall be considered a duplicate when any of the following is true:
a. The same Idempotency Key has already been processed.
b. The same gateway Payment ID has already been successfully processed.
c. The same gateway Order ID is associated with a payment that has already been successfully settled and
the incoming event attempts to create another settlement for that same payment.
d. The same payment event/webhook is delivered more than once by the payment gateway.
e. The client retries the same payment operation after a timeout where the original payment request has
already succeeded.
f. An automatic retry and a previous payment attempt result in the same underlying gateway payment being
reported as successful more than once.

**4. Business Transaction Uniqueness**
A successful payment shall result in exactly one applicable financial posting for the corresponding payment
event.

For each successful payment, the system shall create the required unified ledger transaction only once.
The payment record and ledger transaction shall be linked using a unique internal Payment Transaction ID.
No duplicate payment shall:
- increase the user's invested/contributed amount twice;
- increase redeemable or plan balances twice;
- trigger interest-related processing twice;
- generate referral commission twice;
- count twice toward the recurring commission cycle;
- generate duplicate receipts or duplicate payment-success business events.
Successful payments and related transactions must be recorded in the unified ledger and that commission
is calculated from successfully received payments.
**5. Razorpay Webhook Signature Verification**
All Razorpay webhook requests shall be authenticated before any webhook business processing is
performed.
The system shall verify the Razorpay webhook signature using the configured Razorpay webhook secret
and the exact raw webhook request payload received from Razorpay.
If signature verification fails, the system shall:
- reject the webhook request;
- not create or update any financial transaction;
- not update the user's balance;
- not generate commission;
- record the failed verification event in the audit log.
Signature verification shall be completed before duplicate detection or any other financial/business
processing of the webhook.
The webhook secret shall be stored securely and shall not be exposed in application logs, API responses,
or user-facing interfaces.
**6. Webhook Idempotency**
Razorpay webhook processing shall be idempotent.
The system shall maintain a record of processed webhook/event identifiers where available. If the same
webhook/event is received again, the system shall acknowledge it without repeating the associated
business processing.
Webhook receipt and webhook business processing shall be treated as separate steps so that a temporary
internal failure does not cause financial duplication when the gateway retries the webhook.
**7. Concurrent Request Protection**
Duplicate protection shall work under concurrent execution.
If two identical payment requests or webhook events are received simultaneously, database
constraints/transactions shall ensure that only one request can create the successful financial posting.
The system shall use an appropriate database unique constraint and transactional locking/concurrency
mechanism rather than relying only on application-level checks.
**8. Payment State Handling**
The system shall maintain the following payment states:

`INITIATED → PENDING → SUCCESS / FAILED → RETRYING → SUCCESS / FAILED`
RETRYING shall indicate that the payment has failed an attempt but remains eligible for automatic recovery
under the configured retry/grace-period policy. Each retry attempt shall be recorded separately, while the
payment obligation remains associated with the same scheduled instalment.
A payment shall transition from RETRYING to SUCCESS when a retry succeeds. It shall transition to
FAILED when the retry limit is exhausted, the grace period expires, or the gateway identifies the payment
as non-retriable.
A payment in RETRYING shall not be treated as a successfully received payment and shall not trigger
balance updates, interest-related successful-payment processing, commission eligibility, or other success-
based financial processing until it reaches SUCCESS.
A duplicate request referring to a payment in PENDING or RETRYING state shall return the existing
payment status and shall not create another payment transaction or retry cycle.
Attempt vs. Payment Status: Each gateway payment attempt shall have its own attempt result
(SUCCESS/FAILED), while the overall scheduled payment shall remain in RETRYING while further
automatic attempts are permitted. The overall payment shall become SUCCESS only upon a successful
attempt and FAILED only when no further automatic recovery is permitted.
- Amount and Context Validation

Where a gateway callback is received, the system shall validate that the gateway transaction belongs to the
expected:
- user;
- plan;
- payment order/reference;
- amount;
- currency;
- payment purpose.
A gateway callback that does not match the original payment context shall be marked for exception/review
and shall not automatically credit the user's account.
- Failed Payment and Retry Interaction
A failed payment attempt shall not prevent a later legitimate retry from succeeding.
However, each retry shall remain linked to the same scheduled payment obligation while each actual
gateway transaction shall retain its own gateway reference.
Only an actually successful and unique payment shall be credited.
This shall work consistently with the BRD's configured retry count, retry interval and grace-period rules.
**11. Audit Trail**
For every duplicate-detection event, the system shall record:
- User ID;
- Plan ID, where applicable;
- Internal Payment Transaction ID;
- Idempotency Key;
- Gateway Order ID;
- Gateway Payment ID;
- webhook/event identifier, where available;
- received timestamp;
- original transaction timestamp;
- duplicate detection reason;
- processing outcome.
Duplicate events shall remain auditable but shall not create additional financial ledger entries.
- Receipt and Notification Rule
A successful payment receipt and Payment Success notification shall be generated only for the first
successfully processed financial transaction.
A duplicate callback/retry shall not generate another receipt, commission, balance update, or Payment
Success notification.
**13. Database Constraints**
At minimum, the database shall enforce uniqueness for:
- Idempotency Key;
- Gateway Payment ID;
- applicable gateway Order ID + successful settlement context;
- processed webhook/event ID, where provided.
The exact uniqueness constraints shall be designed so that legitimate separate instalments/payments for
the same Plan are not incorrectly classified as duplicates.

Required acceptance criteria
- Retrying the same API request with the same Idempotency Key produces one payment transaction
only.
- Receiving the same successful Razorpay webhook multiple times produces one financial posting
only.
- Two concurrent requests for the same payment cannot both credit the user's account.
- A duplicate cannot generate duplicate referral commission.
- A duplicate cannot increment the successful-payment count used for commission eligibility more
than once.
- A legitimate subsequent instalment for the same Plan is accepted as a new payment.
- Every duplicate event is auditable.
- No duplicate payment can cause more than one unified-ledger financial impact.
- A Razorpay webhook with an invalid or missing signature is rejected and produces no financial or
balance impact.




## Payment Module

## Payment Module:
- Payment Gateway
- Auto debit via credit or debit card, UPI, Net banking, digital wallet. Payment method can be changed
after creation of an ACTIVE or DISCONTINUED Plan, subject to the applicable payment gateway
validation and authorization requirements.
- Receipts
- Failed payment retries
● Grace period
● Maximum retries
● Card expiry handling
● Change payment method
● Redeemable Balance - Display the user's redeemable balance as a monetary amount in INR (₹).
The Redeemable Balance shall represent the actual monetary value available for eligible
redemption. It shall remain distinct from Reward Points, which are maintained separately as a
course-only benefit. Redeemable Balance ≠ Reward Points: Redeemable Balance is a monetary
INR value and is used for eligible financial redemptions. Reward Points are separate non-cash
points used only for eligible future course-fee adjustment and cannot be withdrawn.
● Payment history
● Duplicate payment handling

*(Points to remember)* Payment Gateway Tokenization

**6. Payment Gateway Tokenization:**
- Only Razorpay shall be used as the payment gateway. Razorpay shall support secure recurring-
payment authorization for automatic deductions through tokenized card payments, UPI
AutoPay, and eMandate, as applicable.
- The system shall not store raw card or other sensitive payment credentials. Instead, tokenized
payment credentials and/or authorized UPI AutoPay/eMandate mandates shall be used for
subsequent recurring deductions.
- The backend shall be capable of initiating authorized automatic deductions through Razorpay's
recurring-payment APIs, subject to applicable payment-network, RBI, NPCI, and mandate-specific
limits and conditions.

*(Points to remember)* AutoPay/eMandate Limit Handling

- AutoPay/eMandate Limit Handling:
- If an automatic deduction cannot be executed because of a payment-network, RBI, NPCI, mandate-
specific, bank or gateway limit, the system shall treat the automatic payment attempt as
unsuccessful and shall not mark the scheduled payment as successfully received.
- The payment shall then follow the configured Failed Payment and Retry Management rules,
including the applicable retry schedule and grace period.
- The user shall be notified that the automatic deduction could not be completed and shall be
provided the applicable manual payment option where automatic recovery is no longer possible or
permitted.
- No interest, commission, ledger credit, balance update, or successful-payment count shall be
generated from an unsuccessful AutoPay/eMandate attempt.

## Cross-Reference: Failed Payment and Retry Management (Flow Numbering 26)

- Failed Payment and Retry Management

---


# 4. Referral & Commission System


## Rule VIII — Referral Commission Records and Views

VIII. Referral Commission Records and Views
The system shall maintain commission transaction records within the Unified Financial Ledger for each
Referrer to record commission transactions generated from eligible payments received from their directly
Referred Users, irrespective of the number of plans opted for by each Referred User. The Commission
Records shall provide the following views:
- Per-Referred-User Commission View
o Display commission details for each individual referred user.
o Show commission earned from all plans associated with the referred user.
o Include commission generated from both active and expired plans.

o Display upcoming commission expected from currently active plans based on eligible
future payments.
- Referrer-Level Commission Summary
o Provide an aggregated view of commission across all referred users.
o Display total commission earned, including commission generated from active and expired
plans.
o Display the total upcoming commission associated with currently active plans.
- Plan-Level Commission Details
o For each referred user, the ledger shall identify the plans against which commission was
earned or is expected.
o Commission shall be calculated and recorded based on the actual payment amount
received from the referred user and the applicable commission rules.
- Commission Transaction Records: Commission transactions shall be recorded in the unified
financial ledger with the applicable commission reference, Referrer User ID, Referred User ID,
commission amount, status and transaction timestamp.
- Commission Records/View: The system shall provide a consolidated financial view of a Referrer's
commission at both the individual referred-user level and the overall referrer level, while
maintaining the commission history and upcoming commission associated with each plan. These
records shall be derived from the unified financial ledger.

## Rule IX — Commission Frequency

IX. Commission Frequency: If the Admin configures a Referrer for Recurring Commission, the Referrer
shall continue to receive commission for every four consecutive successful payments received from the
directly Referred User throughout the tenure of the applicable plan. No commission shall be generated for
failed or unsuccessful payments. Failed payments shall not terminate the Referrer’s recurring commission
eligibility; commission eligibility shall remain applicable for the remaining tenure of the plan and shall cease
when the plan reaches its maturity/expiry. If the Admin configures a Referrer for One-Time Commission,
the Referrer shall receive commission only once in accordance with the applicable commission rules,
regardless of subsequent eligible payments.

## Rule X — Commission Crediting

X. Commission Crediting: Eligible commission shall be credited to the Referrer's redeemable balance
through the Unified Financial Ledger.

## Rule XI — Referrer Record View

XI. Referrer will be able to view a record of the referred users and their respective commission received.
Eligible Commission → Commission Transaction → Unified Financial Ledger → Commission Credited →
Redeemable Balance → Available for Redemption

## Rule XII — Referral Code and Relationship Creation

XII. Referral Code and Relationship Creation: Each registered user shall have one active unique referral
code that may be shared with prospective users. A user may regenerate/rotate their referral code when
required. Upon regeneration, the previous code shall become inactive for new registrations and the newly
generated code shall become the user's active referral code. Existing referral relationships shall remain
associated with the original referring user and shall not be affected by code regeneration. A referral
relationship shall be created only when a new user uses an existing user's referral code during registration
and successfully completes the applicable registration requirements. The user whose referral code is used
shall become the Referrer, and the newly registered user shall become the Referred User.
New User Registration → Uses Referral Code → Registration Successfully Completed → Referral
Relationship Created

## Rule XV — Direct Referral Commission

XV. Direct Referral Commission: The Referrer shall be eligible to receive commission only from eligible
payments made by users directly referred by that Referrer. Commission shall be calculated independently
for each directly referred user and their eligible plans. A Referrer shall not receive commission from any
user referred by their directly Referred User or from any other downstream or indirect referral relationship.

## Rule XVII — Grace Period, Notifications & Recurring Commission

XVII. User with get notifications and messages regarding grace period provided for failed payments. This
grace period is set by admin. If payment is received within the grace period the referrer will receive
commission otherwise for skipped payments no commission will be generated. In short for every four
consecutive successful payments commission will be generated as long as user have active plan(s) if
referrer is allowed to receive recurring commission by the admin. The late payments won’t attract any
penalties. Commission already withdrawn by the referrer shall likewise never be returned or recovered.

## Rule XVIII — Single-Level Referral Relationship

XVIII. Single-Level Referral Relationship: The system shall maintain only direct, single-level Referrer →
Referred User relationships. A Referrer may refer one or more users by having each new user use the
Referrer's referral code during registration. A Referred User may subsequently become a Referrer and refer
one or more newly registering users using their own referral code. Each subsequent referral shall be treated
as an independent direct referral relationship and shall not create any multi-level, hierarchical, or indirect
commission relationship.  Let’s say, A → B, A → C, B → D. It does not mean: A → B → D, which is a multi-
level commission relationship. Instead, there are two independent relationships: A → B, B → D.

## Rule XIX — Referral Validity Period

XIX. Referral Validity Period – The Admin shall configure a positive referral validity period in whole days.
The system shall validate the configured value against the supported minimum and maximum range. Each
referral relationship shall receive an expiry date calculated from its creation date/time using the validity
period effective at the time of relationship creation. Referral validity can be applicable to either selected
referrers or all referrers.
Proposed default 30–1095 days (min 30 to prevent instant expiry, max 3 years).

**Drafted Clarification — pending client confirmation:** Upon expiry of a referral relationship's validity
period, future referral commission accrual associated with that relationship shall stop, in the same manner
as Rule XX (Cancellation):
- No new referral commission shall accrue from the expiry date/time.
- Any commission that was already accrued before expiry shall continue through the normal commission
approval process.
- Existing commission shall not be marked CANCELLED or reversed solely because the referral relationship
subsequently expired.
- The expiry date/time shall be recorded in the audit log.
This mirrors the existing indirect statement under Final Referral Rules ("Existing Commission") but is
restated here as an explicit rule so expiry behavior does not need to be inferred. **This paragraph is a
proposed clarification drafted for review — it has not yet been confirmed by the client.**

## Rule XX — Referral Commission Discontinuation (Cancellation)

XX. Referral Commission Discontinuation – Cancellation shall prevent future referral commission
accrual associated with the referrer from the effective cancellation time. An authorized administrator may
cancel an active referral relationship for a user stating a reason for cancellation as written by the admin in
cancellation comments.
When a referral is cancelled:
- No new referral commission shall accrue from the cancellation date/time.
- Any commission that was already accrued before the cancellation shall continue through the normal
commission approval process.
- Existing commission shall not be marked CANCELLED solely because the referral relationship was
subsequently cancelled.
- The cancellation reason, effective date/time, and Admin details shall be recorded in the audit log.
- Refer to business rule XXI for the non-reversal treatment of approved commission.

## Rule XXI — Non-Reversible Commission

XXI. Referral commission becomes non-reversible once it reaches APPROVED status. Approved, credited,
available, or withdrawn commission shall not be clawed back, reversed, cancelled, or reduced due to
subsequent referral cancellation, Referral Validity Period, payment failure, plan discontinuation, or other
subsequent events. Commission already withdrawn by the referrer shall not be returned or recovered under
any circumstances.

## Rule XXXII — Commission Calculation

XXXII. Commission Calculation: The Admin shall configure the commission type for each Referrer as
either Recurring Commission or One-Time Commission, along with the applicable commission
percentage.
a) Recurring Commission: Recurring Commission shall be generated after every four consecutive
successful payments received under the plan. Once four consecutive successful payments have been
received, the commission cycle shall restart and the next four consecutive successful payments shall
become eligible for commission. This cycle shall continue repeatedly until the Plan Maturity Date. Failed
payments shall not count toward the four-payment cycle.
b) Failed Payment: A failed payment shall not generate commission and shall not count as a successful
payment toward the four-payment commission limit.
c) Late Payment within Grace Period: If a scheduled payment initially fails but is subsequently received
successfully within the configured grace period, the payment shall be treated as a successful payment for
commission calculation, and commission shall be calculated on the successfully received amount.
d) Failed Payment after Grace Period: If the payment is not successfully received within the applicable
grace period, no commission shall be generated for that payment. Future eligible successful payments shall
remain subject to the applicable commission rules.
e) One-Time Commission: If the Admin configures the Referrer for One-Time Commission, the Referrer
shall receive commission at the configured percentage on the first successful payment made by each
directly Referred User under the applicable plan. No further commission shall be generated for subsequent
payments under that plan.
f) Commission Calculation Basis: Commission shall be calculated on the actual payment amount
successfully received from the directly Referred User.
g) Commission Payout Timing: The commission payout shall be processed according to the payout
frequency configured by the Admin (weekly, monthly, quarterly or yearly). The default payout frequency
shall be monthly unless the Admin configures a different frequency. Payout frequency shall not change the
commission calculation or eligibility rules.

## Referral Module

## Referral Module:
- Referral Relationship Management
- Referral Validity Period Management

● Referral Code Management: Each user shall have one active referral code at a time. The user shall
be able to regenerate/rotate their referral code when required, such as when the code is
compromised or unintentionally shared. Regeneration shall deactivate the previous code for all new
referral registrations and generate a new unique code. Existing referral relationships created using
the previous code shall remain unchanged and valid. A deactivated referral code shall not create
any new referral relationship. Referral codes shall be exactly 8 characters long and use only
uppercase letters (A–Z) and digits (0–9). Each active referral code shall be unique across all users.
Code generation shall use secure random generation, with database-level uniqueness enforcement.
- Referral Cancellation Management
- Commission reports

*(Points to remember)* Referral Commission Basis

- Referral Commission Basis: Commission shall be calculated according to the commission type
configured by Admin. For Recurring Commission, commission shall be generated after every four
consecutive successful payments. For One-Time Commission, commission shall be generated once on the
first successful payment under the applicable Plan.

## Commission Lifecycle (Flow Numbering 17)

## 17. Commission Lifecycle
**Commission Lifecycle:**

`ACCRUED → APPROVED → CREDITED → AVAILABLE FOR WITHDRAWAL → WITHDRAWN`

Non-Accrual Outcome: Payment/Eligibility Evaluation → NOT_ACCRUED
NOT_ACCRUED shall be used only when an eligible commission opportunity is evaluated but no
commission is generated, such as when:
- the underlying payment fails;
- the payment is skipped and no commission is earned;
- the payment does not satisfy the configured commission eligibility criteria; or
- the referral relationship is no longer eligible for future commission accrual.
NOT_ACCRUED is a terminal non-financial state and shall not represent a commission balance, payable
amount, or ledger credit.
A NOT_ACCRUED record shall have a commission amount of ₹0 and shall not progress to APPROVED,
CREDITED, AVAILABLE FOR WITHDRAWAL, or WITHDRAWN.
A failed payment shall therefore result in no commission accrual rather than a failed commission
transaction.
**Commission States:**
- ACCRUED: Commission has been calculated and recorded but has not yet been approved.
- APPROVED: Commission has passed the applicable business validation/approval stage.
- CREDITED: Commission has been added to the Referrer's commission balance.
- AVAILABLE FOR WITHDRAWAL: Commission is available for withdrawal by the Referrer.
- WITHDRAWN: Commission has been withdrawn by the Referrer.
- NOT_ACCRUED: It shall be used when a commission opportunity is evaluated but commission is
not generated because one or more of the following conditions apply:
o The payment is failed or unsuccessful.
o The payment is not received within the applicable grace period.
o The payment does not qualify under the configured commission type or commission cycle.
o The Referrer is not eligible for commission for the applicable plan/payment.
o The referral relationship is no longer eligible for future commission accrual.
o The commission has already been generated where the applicable commission rule is One-
Time Commission.
NOT_ACCRUED shall have a commission amount of ₹0 and shall not create a financial credit or
ledger balance. It shall not progress to APPROVED, CREDITED, AVAILABLE FOR WITHDRAWAL,
or WITHDRAWN.


## Cross-Reference: Referral Cancellation (Flow Numbering 18)

## 18. Referral Cancellation:

**Referral Cancellation Flow:**

`ACTIVE REFERRAL RELATIONSHIP`  
↓  
`ADMIN INITIATES CANCELLATION`  
↓  
`ENTER CANCELLATION REASON / COMMENTS`  
↓  
`REFERRAL RELATIONSHIP = CANCELLED`  
↓  
`NO NEW REFERRAL COMMISSION ACCRUES FROM EFFECTIVE CANCELLATION DATE/TIME`  
↓  
`PREVIOUSLY ACCRUED COMMISSION CONTINUES THROUGH NORMAL APPROVAL PROCESS`  
↓  
`APPROVED COMMISSION REMAINS NON-REVERSIBLE`  
↓  
`AUDIT LOG UPDATED`

**Reference:** Rule XX — Referral Commission Discontinuation (Cancellation) and Rule XXI — Non-Reversible Commission.


## Dashboard Commission Metric Definitions

## Dashboard Commission Metric Definitions
Dashboard commission metrics shall be derived from the Unified Financial Ledger and shall use the
Commission Status/Lifecycle defined in this BRD.
- Referral Earnings – Student/User Dashboard
“Referral Earnings” shall represent the total commission that has been financially credited to the Referrer's
commission balance or has already been withdrawn.
Referral Earnings = Sum of Commission Amounts where Commission Status is:
- CREDITED
- AVAILABLE FOR WITHDRAWAL
- WITHDRAWN
Commission in ACCRUED or APPROVED status shall not be included in Referral Earnings because it has
not yet been credited to the Referrer's commission balance.
The same commission transaction shall not be counted more than once across these statuses.
**2. Referral Payout – Admin Dashboard**
“Referral Payout” shall represent the total commission amount that has actually been withdrawn/paid out to
Referrers.
Referral Payout = Sum of Commission Amounts where Commission Status = WITHDRAWN
Commission in ACCRUED, APPROVED, CREDITED, or AVAILABLE FOR WITHDRAWAL status shall not
be included in Referral Payout.
- Source of Calculation
These metrics shall be calculated from the Unified Financial Ledger/Commission Transactions and shall not
be maintained as separate manually updated financial balances.
The calculation shall be reproducible from the underlying commission transaction records using the
Commission Status, Commission Amount and applicable transaction identifiers.
**4. Lifecycle Reference**
The commission lifecycle used for these dashboard calculations is:

`ACCRUED → APPROVED → CREDITED → AVAILABLE FOR WITHDRAWAL → WITHDRAWN`

The metric definitions above shall remain aligned with this lifecycle.













**STUDENT METRICS**
- Total Invested
- Total Interest
- Redeemable Balance
- Reward Points
- Active Plans
- Closed Plans
- Next Deduction
- Referral Earnings
- Transaction Graph

**ADMIN METRICS**
- Total Users
- Active Plans
- Today's Payments
- Pending Refunds
- Pending Enquiries (existing Redemption/Franchisee enquiries)
- Pending General Enquiries (Rule XXXIX; additive, separate from the above — see §8)
- Referral Payout
- Revenue
- Failed Payments
- Upcoming Maturities

**DATA PRINCIPLE**
Dashboard metrics should be derived from transactional and ledger data rather than independently
maintaining conflicting financial values.




## Definition of Commission Reporting

Definition of Commission reporting:
Admin shall be able to view referral commission reports by:
- Referrer
- Referred User
- Plan
- Payment/transaction
- Commission amount
- Commission status
- Accrual date
- Approval date

- Credit date
- Withdrawal date
- Referral status


## Final Referral Rules

## Final Referral Rules:
Self-Referral Prevention: The system shall prevent a user from registering or being associated as a
Referred User using their own referral code. The system shall reject any referral relationship where the
Referrer User ID is the same as the Referred User ID.
{Referrer User ID == Referred User ID} → REJECT REFERRAL
Single-Level Referral: The system shall support only direct Referrer → Referred User relationships. A
Referrer may have one or more directly Referred Users. A Referred User may subsequently become a
Referrer and refer newly registering users using their own referral code. Such subsequent referral
relationships shall remain independent direct relationships and shall not create any multi-level, hierarchical,
or indirect commission entitlement.
Direct Commission Eligibility: A Referrer shall receive commission only from eligible payments made by
their directly Referred Users. Commission generated from a downstream referral shall be payable only to
the Referrer directly associated with that downstream Referred User. No upstream Referrer shall receive
commission from downstream or indirect referrals.
Referral Validity Period Configuration: The Admin shall configure a global referral validity period,
expressed in days, applicable to newly created referral relationships.
Configuration Changes: Changes to the global referral validity period shall apply only to referral
relationships created after the configuration change. Existing referral relationships shall retain their
originally calculated expiry date.
**Drafted Clarification — pending client confirmation:** Rule XIX permits a referral validity period to be
applied to either "selected referrers" or "all referrers." Where a referrer-specific validity period is
configured for a given referrer, that referrer-specific period shall take precedence over the global
referral validity period for referral relationships created by that referrer; the global period shall apply
to all other referrers. A referral relationship's expiry date shall be fixed at creation time using whichever
period (referrer-specific or global) was effective for that referrer at that time, and shall not change if
the applicable period is later reconfigured. **This paragraph is a proposed clarification drafted for
review — it has not yet been confirmed by the client.**
Referral Cancellation: Refer business rule XX.
Existing Commission: Commission that was eligible and accrued before Referral Validity Period or
cancellation shall continue through the normal commission approval process.
Referral Code Rotation: Only the active referral code shall be accepted for new referral registrations. When
a code is regenerated, the old code shall immediately become inactive for new registrations. Code
regeneration shall not alter, cancel, or reassign any existing Referrer → Referred User relationship or
previously earned commission.
Referral Code Audit: The system shall record referral-code generation, regeneration, activation/deactivation
timestamp, and the user responsible for the action in the audit log.


Referral relationships:

```
A → B
A → C
B → D
```

Commission relationships:

```
B payment
↓
Commission → A

C payment
↓
Commission → A

D payment
↓
Commission → B
```

There is no:-

```
D payment
↓
Commission → A
```

**Commission States:**
ACCRUED: Commission has been calculated but not yet approved.

APPROVED: Commission has passed the business validation/approval stage.
CREDITED: Commission has been added to the referrer's commission balance.
AVAILABLE FOR WITHDRAWAL: Referrer can request withdrawal.
WITHDRAWN: Commission has been withdrawn by the referrer.

Summary metrics:
- Total Referral Commission Accrued
- Total Commission Approved
- Total Commission Credited
- Total Commission Withdrawn
- Total Pending Commission











> Worked examples: see Appendix A — "Successful Four Consecutive Payments + Referral Commission" and "Failed Payment + Referral Commission".


---


# 5. Redemption, Available Margin & Money Mechanics


## Rule XIII — Reward Points on Course Redemption

XIII. If student redeems balance for course, then certain reward points will be available to the student for its
next course which cannot be redeemed in any other manner. If reward points are available, then the course
fees will be adjusted accordingly. Reward points cannot be withdrawn.
Reward Percentage Calculation: The reward percentage shall be calculated on the course fee amount
applicable to the approved course redemption. The reward points/amount shall be calculated as the
applicable course fee multiplied by the configured reward percentage. The course fee shall be the
authoritative calculation basis for course redemption rewards. The Reward Percentage used for calculation
shall be the Reward Percentage configured for the applicable plan, or the common Reward Percentage
configured by Admin where the system is using a common reward configuration.

**Reward Calculation When Existing Reward Points Are Used:** Where an eligible course redemption is
paid using a combination of Redeemable Balance and previously earned Reward Points, the **full approved
course fee remains the Applicable Course Fee** for calculating newly earned Reward Points. The use of
previously earned Reward Points shall not reduce the course fee used as the reward-calculation basis.
Therefore:

`Reward = Full Applicable Course Fee × Configured Reward Percentage`

Example: If the course fee is ₹15,000, ₹13,500 is paid using Redeemable Balance and ₹1,500 is paid using
existing Reward Points, and the configured reward percentage is 10%, the newly earned Reward Points shall
be calculated as `₹15,000 × 10% = 1,500 Reward Points`.

## Definitions

****User => Anyone including students who is registered with the platform****  
****Referrer => Any registered user****

****Redeemable Balance / Actual Redeemable Balance => The same underlying monetary INR balance belonging to the user. These terms shall not represent two separate balances or database fields.****

- **Redeemable Balance** is the user-facing terminology used when displaying the balance.
- **Actual Redeemable Balance** is the terminology used in formulas, rules and worked examples to distinguish the actual balance from Available Margin.
- An active redemption reservation shall reduce Available Margin only and shall not reduce the Redeemable Balance / Actual Redeemable Balance.
- The Redeemable Balance / Actual Redeemable Balance shall be reduced only when the applicable redemption is financially processed and the corresponding Unified Financial Ledger transaction is created.
- `Redeemable Balance = Actual Redeemable Balance`

**Drafted Clarification — pending client confirmation:** Where worked examples in this document use the
label "Actual Balance" (e.g., in Appendix A), that label refers to this same Redeemable Balance / Actual
Redeemable Balance and is not a third, separate balance. `Actual Balance = Redeemable Balance = Actual
Redeemable Balance`. **This paragraph is a proposed clarification drafted for review — it has not yet been
confirmed by the client.**

## Redeem Options

Redeem Options: (Any payment made to the user will be offline which admin will update manually)
To Redeem a student can opt for courses, refund, re-invest, donate now or “Gadgets and Accessories”.
- Course: If the course fee exceeds the user's Available Margin, the system shall calculate the
Shortfall Amount and place the redemption request into AWAITING_SHORTFALL_RESOLUTION.
`Shortfall Amount = Course Fee − Available Margin`
The complete course redemption request shall remain financially unprocessed until the shortfall is
resolved offline and the required evidence/details are submitted by the user and verified by an
authorized Admin.

The course redemption shortfall shall follow the same Offline Shortfall Verification procedure
defined under Rule XXXIV – Money Mechanics. The Admin shall record the applicable payment
reference/receipt number, payment date, amount, payment method and verification comments, and
supporting proof may be uploaded where required.
The shortfall shall not be considered resolved until it has been verified by an authorized Admin in
accordance with Rule XXXIV.
The course redemption request shall remain subject to the system-wide Redemption Request
Expiry rules. A request in PENDING or AWAITING_SHORTFALL_RESOLUTION shall expire after
the configured maximum period if the required action has not been completed. Upon expiry, the
request shall move to EXPIRED and any associated financial and inventory reservation shall be
released in accordance with the applicable expiry rules.
The expiry period shall be the same Admin-configured period applicable to other redemption
requests and shall not use a separate course-specific timeout.
After successful Admin verification and approval, the system shall process the course redemption in
accordance with the applicable redemption and ledger rules.
If the shortfall is not resolved before the applicable expiry period, the course redemption request
shall expire/cancel and no redemption ledger debit shall be created.
- Reinvestment: Reinvesting the amount redeemable balance will be updated accordingly. Before
maturity only redeemable balance should be available.
- Donate: Donation of an either complete or partial amount, an enquiry form will be presented to set
amount and fill other relevant details.
- Refund: An enquiry will be generated for either partial or complete refund which will be addressed
by the admin.
- Accessories and Gadgets: User can use a partial or complete amount of their redeemable balance to
raise an enquiry for available gadgets and accessories. A view shall display the available items and
their respective prices. No tax/GST or shipping cost required for this fueature. The list should be simply
displaying the price for each gadget.
When the user selects one or more items, the system shall calculate the Total Requested Redemption
Amount and compare it with the user's Available Margin.
- If Total Requested Redemption Amount ≤ Available Margin, the user may submit the
redemption request normally.
- If Total Requested Redemption Amount > Available Margin, the system shall display:
o Actual Redeemable Balance
o Available Margin
o Total Requested Redemption Amount
o Shortfall Amount
`Shortfall Amount = Total Requested Redemption Amount − Available Margin`
An amount exceeding the Available Margin shall not prevent the user from submitting the request.
The user may submit the request with the shortfall.
The request shall then move to AWAITING_SHORTFALL_RESOLUTION.
While the shortfall remains unresolved:
- the full redemption request shall remain financially unprocessed;
- no portion of the request shall be processed separately;
- no redemption ledger debit shall be created;
- the Actual Redeemable Balance shall not be reduced due to the redemption;
- the user shall be required to resolve the shortfall offline;
- Admin shall verify the submitted shortfall resolution before approval.
Only after successful Admin verification and approval shall the redemption proceed to financial
processing in accordance with the applicable redemption rules.
**Example:**

| Item | Amount |
|---|---|
| Actual Redeemable Balance | ₹10,000 |
| Available Margin | ₹10,000 |
| Selected Gadgets | ₹12,000 |
| Shortfall | ₹2,000 |

The system shall allow submission, display the ₹2,000 shortfall, and move the request to
AWAITING_SHORTFALL_RESOLUTION. It shall not debit ₹10,000 separately.

Any pending gadget request that is subsequently modified shall be replaced by the revised total
requested amount. The previous pending reservation shall not be counted again when recalculating
Available Margin or Shortfall.
- Franchisee: Admin shall be able to create and manage Franchisee Plans. Each Franchisee Plan shall
contain a Plan Name and a One-Time Deductible Price. The User shall be able to view the available
Franchisee Plans and select a Franchisee Plan.
After selecting a Franchisee Plan, the system shall display the Colleges mapped to that Franchisee
Plan. The User shall select a College and submit a Franchisee redemption enquiry to Admin for offline
processing.
The system shall compare the One-Time Deductible Price with the User's Available Margin.
- If the One-Time Deductible Price ≤ Available Margin, the User may submit the enquiry normally for
Admin review.
- If the One-Time Deductible Price > Available Margin, the system shall calculate and display the
Shortfall Amount.
`Shortfall Amount = One-Time Deductible Price − Available Margin`
- The User shall still be allowed to submit the enquiry with the shortfall. The enquiry shall move to
`AWAITING_SHORTFALL_RESOLUTION`.
- While the shortfall remains unresolved, no portion of the Franchisee redemption shall be financially
processed and no redemption ledger debit shall be created.
- The shortfall shall be resolved offline and the required payment details/evidence shall be submitted for
Admin verification.
- Only after Admin verification and approval shall the Franchisee redemption proceed to financial
processing in accordance with the applicable redemption and Unified Financial Ledger rules.

After every redemption balance available to redeem will be shown as margin which is the available balance
left to request something. Whereas actually redeemable balance wouldn't be affected by the raised
enquires.
For example, if ₹2000 as redeemable balance and opt for gadgets with price 200, then the available margin
will be 1800 whereas the redeemable balance will remain same unless admin approves the enquiry. Once
approved the redeemable balance gets reduced by 200 and updated accordingly.
As for enquiry admin can raise concerns if there are any and share then in form of messages against the
raised enquiry.
The Admin shall not directly edit the user's Reward Points or Actual Redeemable Balance. Upon approval
of an eligible redemption, the system shall automatically calculate and apply the applicable financial
deduction through the Unified Financial Ledger and redeemable balance would get updated accordingly.
These balances won't have expiry.
Note: Any enquiry generated by the user when approved will result in deduction of the redeemable balance.
Inventory should update once request for accessories and gadgets raised in enquiry gets approved by the
admin.

## Rule XXII — Redemption Shortfall Resolution

XXII. Redemption Shortfall Resolution: If the requested redemption amount exceeds the user's Available
Margin, the redemption request shall be placed in AWAITING_SHORTFALL_RESOLUTION status and
the system shall calculate and display the Shortfall Amount.
The entire redemption request shall remain financially unprocessed until the shortfall is resolved
outside the system, the user submits the required resolution details/evidence, and the Admin verifies and
approves the resolution.

No partial or full redemption settlement, final redemption ledger debit, or reduction of the Redeemable
Balance attributable to the redemption shall occur while the request is awaiting shortfall resolution or Admin
approval.


The system shall not process the Available Margin portion separately from the Shortfall Amount. A
redemption request having a shortfall shall be treated as one redemption request for financial
processing purposes.

Resolution of the shortfall outside the system shall not itself create a redemption ledger debit. Any
external/offline amount used to resolve the shortfall shall be recorded as shortfall-resolution
information/evidence and shall not be treated as a deduction from the user's Redeemable Balance unless a
separate, explicitly defined financial transaction applies.
The user must either:
- Resolve the shortfall outside the system and submit the required resolution details/evidence for
Admin verification; or
- Cancel the redemption request.
If the user cancels the request, the redemption shall be marked CANCELLED and any associated
reservation shall be released.
After successful Admin approval, the redemption shall become eligible for financial processing. The system
shall then process the redemption in accordance with the applicable settlement mechanism and shall create
the corresponding financial ledger transaction(s).
The resulting ledger transaction(s) shall be linked to the relevant Redemption Request ID and shall
accurately represent the amount and nature of the actual financial transaction. The system shall not create
a ledger debit merely to represent the offline shortfall unless such debit represents an actual, separately
defined financial transaction.
`Shortfall Amount = Requested Redemption Amount − Available Margin`
Submission vs Financial Processing Clarification:
The existence of a Shortfall shall not prevent the user from submitting the redemption request.
A redemption request may be submitted even when:
`Requested Redemption Amount > Available Margin`
In such a case, the system shall accept the request and place it in:
`AWAITING_SHORTFALL_RESOLUTION`
The Shortfall shall prevent approval and financial processing, but shall not prevent request submission.
No partial redemption of the Available Margin portion shall occur while the Shortfall remains unresolved.

## Rule XXIII — No Financial Impact Before Approval

XXIII. No Financial Impact Before Approval: A redemption request with a shortfall shall not reduce the
user's Redeemable Balance or create a final ledger debit while it is in
AWAITING_SHORTFALL_RESOLUTION or awaiting Admin approval.
Any reservation associated with the request shall remain separately tracked from financial transactions
and shall not constitute a ledger debit or financial settlement.
If the request is cancelled, any associated reservation shall be released in accordance with the applicable
reservation rules.

## Rule XXIV — Admin Approval

XXIV. Admin Approval: Only after the Admin verifies that the shortfall has been resolved may the
redemption move to APPROVED and proceed to financial processing.
A redemption request with a shortfall shall be treated as a single redemption request for financial
processing. The system shall not financially process or create a ledger debit for the Available Margin
portion separately while the shortfall remains unresolved or awaiting Admin approval.
After Admin approval, the system shall proceed with financial processing in accordance with the applicable
redemption and settlement rules. The resulting ledger transaction(s) shall represent only the actual
financial transaction(s) that occur as part of the approved redemption.
Shortfall → Offline Resolution → Admin Verification → Approval → Financial Processing → Ledger
Impact → Balance Update

## Rule XXV — Available Margin Formula

XXV. Available Margin
Available Margin already reflects amounts reserved by pending redemptions; therefore, a pending
redemption amount must not be deducted from Available Margin again when calculating the shortfall.
**Available Margin Formula:**

`Available Margin = Actual Redeemable Balance − Total Active Reserved/Committed Redemption Amount`

The system shall calculate a user's Available Margin as the portion of the Actual Redeemable Balance that
is not committed to existing pending redemption requests or other active reservations.
The system shall consider all applicable active reservations across redemption categories when calculating
Available Margin.

An active reservation may reduce Available Margin but shall not constitute a financial ledger debit or a
separate reduction of the Actual Redeemable Balance.
A redemption ledger debit shall occur only when the redemption is financially processed in accordance with
the applicable redemption and settlement rules.

**Terminology Clarification:** For this rule and all subsequent Available Margin calculations, **Actual
Redeemable Balance means the same underlying balance defined as Redeemable Balance in Section 5 →
Definitions**. It is not a separate financial balance.

## Interest Calculation Reference
For any ledger entry generated by an interest calculation, the entry shall include the Interest Calculation
Method ID and configuration/version ID used for that calculation.

## Rule XXVIII — Concurrent Reservations Across Categories

XXVIII. Concurrent reservations across categories:
Concurrent Redemption Reservations: The system shall maintain a consolidated reservation amount
across all active, pending redemption requests, irrespective of redemption category. When calculating
Available Margin for a new redemption request, the system shall include the reserved amounts of all other
active redemption requests for the same user.
A new redemption request shall be evaluated against the remaining Available Margin after accounting for all
existing active reservations. The system shall not allow concurrent redemption requests to collectively
exceed the user's Actual Redeemable Balance.
**Example:**

| Item | Amount |
|---|---|
| Actual Redeemable Balance | ₹10,000 |
| Pending Course Reservation | ₹4,000 |
| Pending Gadget Reservation | ₹3,000 |
| Pending Franchisee Reservation | ₹2,000 |
| Total Reserved | ₹9,000 |
| Available Margin | ₹10,000 − ₹9,000 = ₹1,000 |

`SUBMITTED → ADMIN REVIEW → AWAITING_SHORTFALL_RESOLUTION`


## Rule XXIX — Redemption Reservation and Available Margin

XXIX. Redemption Reservation and Available Margin: The system shall maintain a reservation against
each active redemption request that has committed or reserved an amount against the user's Actual
Redeemable Balance. Available Margin shall be calculated as:
Available Margin = Actual Redeemable Balance − Total Active Reserved Redemption Amount

The Total Active Reserved Redemption Amount shall include reservations from all active redemption
requests across all redemption categories for the same user. The system shall recalculate Available Margin
whenever a reservation is created, modified, approved, rejected, cancelled, or released. A new redemption
request shall not be permitted to cause the combined active reservations to exceed the Actual Redeemable
Balance.


## Rule XXXI — Unified Ledger Model

XXXI. Unified Ledger Model: The system shall maintain a single unified financial ledger for all applicable
financial transactions, including plan transactions, interest transactions, commission transactions and
redemption transactions. Commission transactions shall be recorded in the unified ledger and shall
contribute to the user's applicable redeemable/available balance in accordance with the commission rules.
A separate Commission Ledger shall not be maintained as an independent financial ledger.

## Rule XXXIII — Percentage Configuration and Rounding

XXXIII. Percentage Configuration and Rounding: Interest, reward and commission percentages shall be
configured as decimal percentages from 0.00% to 100.00%, inclusive, with up to two decimal places.
Values outside this range shall be rejected by the system. Monetary calculations shall be rounded to two
decimal places using the Round Half Up method. Where the third decimal place is 5 or greater, the second
decimal place shall be increased by one; where it is less than 5, the second decimal place shall remain
unchanged.

**Reward Points Rounding Exception:** Reward Points are non-monetary whole-number units and are
therefore subject to the separate Reward Points rounding rule defined in this BRD. The general monetary
Round Half Up rule shall **not** be used to round Reward Points. Reward Points shall be calculated from:

`Reward Points = Applicable Course Fee × Configured Reward Percentage`

**Client-Requested Deviation:** The resulting Reward Points value shall then be **rounded up (ceiling) to the
nearest whole Reward Point**. This overrides the floor-rounding example shown later in this document under
"Reward Points Rounding" in Appendix A (Worked Financial Examples), which was the original source-stated
rounding direction; the client has explicitly directed that Reward Points always round up instead. Any
fractional portion shall be rounded up to the next whole Reward Point, and Reward Points shall never be
displayed or labeled with the ₹ (or any other currency) symbol, since they are a non-monetary whole-number
unit. The Reward Points calculation shall not first be converted to a two-decimal monetary value using Round
Half Up.

**For ₹:**
- Percentage: up to 2 decimal places
- Money: 2 decimal places
Percentage Range: Interest, Reward and Commission percentages shall be configured between 0.00%
and 100.00%, inclusive. Values below 0.00% or above 100.00% shall not be accepted by the system. The
percentage configuration shall support up to two decimal places.
**Validation Examples:**
- 0.00% → Allowed
- 10.00% → Allowed
- 100.00% → Allowed
- 100.01% → Rejected
- Negative percentage → Rejected



## Rule XXXIV — Money Mechanics

XXXIV. Money Mechanics:
- Currency and Monetary Precision: All monetary amounts shall be denominated in Indian Rupees
(INR). Monetary values shall be stored and calculated with a precision of two decimal places
(paise). All monetary calculations shall be rounded to two decimal places using the Round Half Up
method defined in Rule XXXIII. The same rounding method shall apply consistently to interest,
commission, redemption, balance, payment, and ledger calculations.
- Interest Day-Count Convention: Where interest or pro-rata maturity calculations are based on
elapsed days, the system shall use the Actual/365 day-count convention, unless a plan-specific
calculation method explicitly defines another supported convention.
- Interest Calculation Rounding and Compounding: Interest shall be calculated using the
applicable Interest Calculation Method and configured compounding frequency. For compound
interest, the calculated interest shall be added to the applicable principal/base amount at each
configured compounding interval. Intermediate calculations shall retain system-supported precision,
and the final interest amount credited or recorded in the ledger shall be rounded to two decimal
places using the Round Half Up method defined in Rule XXXIII. No additional rounding shall be
applied unless specifically defined by the selected Interest Calculation Method.
- Offline Shortfall Verification: For an offline shortfall payment, the Admin shall record the payment
reference/receipt number, payment date, amount, payment method and verification comments
before marking the shortfall as verified. Supporting proof may be uploaded where required. An
offline shortfall shall not be treated as resolved until verified by an authorized Admin. If a previously
verified payment is subsequently determined to be invalid, the system shall record the
reversal/exception through an audit-controlled adjustment process.
- Donation Processing: The Admin shall configure or select the eligible donation recipient/entity for
each donation request. Approved donations shall be processed by Admin offline after obtaining the
user’s consent. Admin approval shall trigger the applicable financial processing through the Unified
Financial Ledger, and the approved donation amount shall be debited from the user’s Redeemable
Balance. The Available Margin shall be recalculated after the balance update. The system shall
record the recipient, approved donation amount, transaction reference, processing status,
consent/approval details and relevant dates. Where required, the applicable donation
receipt/document shall be generated or recorded.
- This Offline Shortfall Verification procedure is the **system-wide verification procedure** and shall
apply to all redemption categories involving an offline shortfall, including Course, Gadgets &
Accessories, and Franchisee redemption.
- Currency Display Convention: All monetary amounts shown anywhere in the system, including
plans, payments, interest, commission, redemption amounts, balances, ledger entries, dashboards,
notifications, reports, and examples, shall be displayed with the INR currency symbol (₹) or the
explicit INR designation. Monetary values shall not be displayed as standalone numbers where the
context could be ambiguous.

## Rule XXXV — Concurrency / Race Conditions, Inventory Reservation, Redemption Expiry & Status Enums

XXXV. Concurrency / race conditions:
Concurrent Redemption Control: Available Margin validation and reservation creation shall be performed
atomically. The system shall prevent two concurrent redemption requests from reserving the same available
balance. Appropriate database transaction isolation, row-level locking or optimistic locking shall be used to
ensure that the combined active reservations never exceed the Actual Redeemable Balance.
Inventory Reservation: When a gadget/accessory redemption request is submitted, the system shall
reserve the required inventory quantity in addition to the financial reservation. Reserved inventory shall not
be available for another redemption request. The reservation shall be released when the request is
cancelled/rejected/expired and converted to a stock deduction when the request is approved.
Redemption Request Expiry: The Admin shall configure the maximum period for which a redemption
request may remain in PENDING or AWAITING_SHORTFALL_RESOLUTION.
- Default expiry period: 7 calendar days.
- Configurable range: 1 to 30 calendar days.
- The expiry period shall be measured from the time the request enters the applicable status.
- When the configured period expires without the required action, the request shall automatically
move to EXPIRED status and all associated financial and inventory reservations shall be released.

- The expiry configuration shall apply uniformly to all applicable redemption categories, including
Course, Gadgets & Accessories, and Franchisee redemption.
- Any change to the expiry configuration shall apply to requests created or entering the applicable
status after the configuration change and shall not retroactively alter an already-running expiry
period.


**Redemption Request Status:**
- PENDING
- AWAITING_SHORTFALL_RESOLUTION
- APPROVED
- REJECTED
- CANCELLED
- EXPIRED

**Plan Status:**
- ACTIVE
- MATURED
- DISCONTINUED
- PARTIALLY_REDEEMED
- REDEEMED


## Rule XXXVII — Franchisee Plan and College Redemption

XXXVII. Franchisee Plan and College Redemption:
The system shall support a Franchisee redemption option under the existing User flow. Admin shall be able
to create and manage Franchisee Plans, with each Franchisee Plan containing a Plan Name and a One-
Time Deductible Price.
The User shall be able to view the available Franchisee Plans and select a Franchisee Plan. Upon
selection, the system shall display the list of Colleges mapped to that Franchisee Plan. The User shall
select a College and submit a Franchisee redemption enquiry to Admin for offline processing.
The system shall compare the One-Time Deductible Price with the User's Available Margin.
- If One-Time Deductible Price ≤ Available Margin, the User may submit the Franchisee
redemption enquiry normally for Admin review.
- If One-Time Deductible Price > Available Margin, the system shall calculate and display the
Shortfall Amount.
`Shortfall Amount = One-Time Deductible Price − Available Margin`
- The User shall still be allowed to submit the enquiry with the shortfall, and the enquiry shall move to
`AWAITING_SHORTFALL_RESOLUTION`.
- While the shortfall remains unresolved, the complete Franchisee redemption enquiry shall remain
financially unprocessed. No portion shall be processed separately and no redemption ledger debit
shall be created.
- The shortfall shall be resolved through the applicable offline process, and the required payment
details/evidence shall be submitted for Admin verification.
- The shortfall shall be considered resolved only after successful Admin verification.
- After successful verification and approval, the Franchisee redemption shall proceed to financial
processing in accordance with the applicable redemption and Unified Financial Ledger rules.
- Upon approval, the applicable deductible amount shall be reflected in the user's redemption records
and the Actual Redeemable Balance shall be updated in accordance with the approved
redemption.
- The selected Franchisee Plan and College shall remain linked to the submitted enquiry and its
related financial and audit records.
- Franchisee redemption shall not be subject to the investment Plan Status values or any maturity
period. Each approved Franchisee redemption shall be treated as a one-time redemption
transaction based on the selected Franchisee Plan and College.
For a Franchisee redemption with a shortfall, the system-side financial settlement shall be limited to the
amount available through the user's Redeemable Balance/Available Margin, while the resolved offline
shortfall shall be recorded as verification information/evidence. The offline shortfall shall not create a
redemption ledger debit unless a separate, explicitly defined financial transaction applies.

*(Points to remember)* Handling Missed Instalments

- Handling Missed Instalments: A missed instalment shall reduce only the remaining unpaid principal
amount of the Plan by the amount of the missed instalment. The system shall not reduce previously paid
principal amounts. Any interest already accrued shall not be treated as principal for the purpose of this
adjustment. Future interest calculations shall use the resulting remaining unpaid principal as the applicable
principal balance.

| Item | Amount |
|---|---|
| Original Principal | ₹10,000 |
| Paid Principal | ₹2,000 |
| Remaining Unpaid Principal | ₹8,000 |
| Missed Instalment | ₹1,000 |
| Adjusted Unpaid Principal | ₹7,000 |

Missed installment → reduce remaining unpaid principal, not the original principal and not previously paid
principal.

Boundary Handling: The remaining unpaid principal shall never become negative. When a missed
instalment is equal to or greater than the current remaining unpaid principal, the adjusted remaining unpaid
principal shall be set to ₹0.00. Any excess amount beyond the remaining unpaid principal shall not create a
negative principal balance and shall not be treated as additional missed principal. For cumulative missed
instalments, the same rule shall apply after each missed instalment, and once the remaining unpaid
principal reaches ₹0.00, no further reduction shall be applied.
**Edge Case Example:**

| Item | Amount |
|---|---|
| Remaining Unpaid Principal | ₹800 |
| Missed Instalment | ₹1,000 |
| Adjusted Unpaid Principal | ₹0 |

Excess ₹200 is not added as negative principal.
Interest Impact: Future interest calculations shall use ₹0.00 as the applicable remaining unpaid principal
once the remaining unpaid principal reaches ₹0.00. No negative interest-bearing principal shall be created.

## Donation Financial Settlement

Donation Financial Settlement: A Donation request shall remain an enquiry/request until Admin
approval. Before approval, the Actual Redeemable Balance shall remain unchanged and no
redemption ledger debit shall be created. After approval, the approved donation amount shall be
posted as a redemption transaction in the Unified Financial Ledger and deducted from the user’s
Redeemable Balance. For a partial donation, only the approved donation amount shall be deducted.


## Franchisee Plan Management (Admin)

Franchisee Plan Management (Admin):
- Franchisee Plan Name
- One-Time Deductible Price

● College Mapping to Franchisee Plan


## Accessories / Gadget Module

## Accessories / Gadget Module
- Gadget & Accessories Management: Admin can manage categories, items, prices, stock and
inventory.
- Catalogue: User can view available gadgets/accessories and their prices. No GST/tax or shipping
cost is added.
- Selection: User can select one or more items and raise a redemption enquiry using partial or
complete redeemable balance.
- Balance Check: System calculates Total Requested Redemption Amount and compares it with
Available Margin.
- Normal Request: Where the requested amount is within Available Margin, the enquiry can be
submitted for Admin review.
- Shortfall Request: Where the requested amount exceeds Available Margin, the system displays
the Shortfall Amount and allows submission in AWAITING_SHORTFALL_RESOLUTION.
- Offline Resolution: Any shortfall/payment is resolved offline and the required details/evidence are
submitted for Admin verification.
- Approval: Only after Admin verification and approval does the enquiry proceed to financial
processing.
- Financial Update: On approval, the system updates the Unified Financial Ledger and reduces the
Actual Redeemable Balance.
- Inventory: Inventory is reserved for the enquiry and converted to a stock deduction only after
approval; reservations are released on cancellation, rejection or expiry.
- Admin Communication: Admin can raise concerns and communicate messages against the
enquiry.
- Modification: A modified pending enquiry replaces the previous requested amount/reservation and
recalculates Available Margin and Shortfall.
- Expiry: Pending enquiries remain subject to the system-wide redemption expiry rules.


## Franchisee Module

**Franchisee Module:**
o Franchisee Plan Management
o One-Time Deductible Price Management
o College Mapping
o Franchisee Plan Selection
o College Selection
o Franchisee Redemption Enquiry
o Available Margin Check
o Shortfall Calculation
o Offline Shortfall Resolution
o Admin Verification and Approval
o Redemption Financial Processing
o Enquiry Status and History



## Flow Diagrams — Preamble

REWARD POINTS: Course-only benefit
REFERRAL COMMISSION: Commission Records / View

Sequence of below diagrams are not related to the sequence of business requirement rules.
























































Here course fee comparison is made my admin by referring to the available redeemable balance and then
manually adjusting redeemable balance after approving the course issuance.












## Reinvestment Flow (Flow Numbering 9)

## 9. REINVESTMENT FLOW




RULE: Reinvestment is optional. Partial reinvestment leaves the remaining balance available.
















RULE: The remaining balance may be kept for future eligible redemption or optionally reinvested.
Reinvestment is not mandatory.


## Financial Ledger Flow (Flow Numbering 13)

## 13. Financial Ledger Flow:

Ledger data:
- Ledger Entry ID
- User ID / Plan ID reference
- Transaction Type
- Redemption Type, where applicable
- Requested Amount, where applicable
- Approved Amount, where applicable
- Transaction Amount
- Transaction Status
- Balance Before Transaction
- Balance After Transaction
- Interest Calculation Method ID, where applicable
- Interest Configuration/Parameter Version, where applicable
- Referrer User ID, where applicable
- Commission Reference, where applicable
- Approver User ID, where applicable
- Approval Timestamp (UTC), where applicable
- Transaction Timestamp (UTC)
- Description/Reason, where applicable

Ledger Data Type, Precision and Timezone
- Ledger Entry ID / User ID / Plan ID / Reference IDs: Stored as unique system identifiers.
- Transaction Type / Redemption Type / Transaction Status: Stored as controlled system-defined
values.
- Requested Amount / Approved Amount / Transaction Amount / Balance Before Transaction /
Balance After Transaction: Stored and calculated in INR with two decimal places (paise).
- Percentage/rate values used in ledger calculations: Stored with up to two decimal places.
- Transaction Timestamp / Approval Timestamp: Stored as date-time values with timezone
information. The system shall use UTC for storage and display timestamps to users/admins in the
configured application timezone.

- Commission and interest amounts: Follow the same INR two-decimal monetary precision and
rounding rules defined in Rule XXXIII and Rule XXXIV.



## End-to-End Business Sequence

## END-TO-END BUSINESS SEQUENCE :-
- Plan reaches maturity.
- User initiates an eligible redemption.
- System validates the request and Available Margin.
- If the request exceeds Available Margin, it enters AWAITING_SHORTFALL_RESOLUTION.
- The user resolves the shortfall offline or cancels the request.
- Admin verifies the offline resolution before approval.
- Only an approved request proceeds to financial processing.
- Ledger and balance are updated after approval.
- The resulting redemption status and metrics are recorded.



## Gadget Redemption Calculation (Flow Numbering 20)

## 20. Gadget Redemption Calculation:-























For the shortfall branch:
AWAITING_SHORTFALL_RESOLUTION → Offline Resolution → Admin Verification → Approval →
Financial Processing → Ledger Transaction
There shall be no ledger debit, no partial redemption, and no financial settlement between the
shortfall identification and Admin approval stages.




## Gadget Redemption — Shortfall Scenario
A user selects gadgets with a total requested redemption value of ₹12,000.
At the time of submitting the redemption request:

| Parameter | Amount |
|---|---|
| Actual Redeemable Balance | ₹10,000 |
| Existing Active Reservations | ₹0 |
| Available Margin | ₹10,000 |
| Requested Redemption Amount | ₹12,000 |
| Shortfall Amount | ₹2,000 |

**Shortfall Calculation**

`Available Margin = Actual Redeemable Balance − Total Active Reserved/Committed Redemption Amount`

`Available Margin = ₹10,000 − ₹0 = ₹10,000`
`Shortfall = Requested Redemption Amount − Available Margin`
`Shortfall = ₹12,000 − ₹10,000 = ₹2,000`

Since the requested redemption amount of ₹12,000 exceeds the Available Margin of ₹10,000, the
redemption request shall move to:

`AWAITING_SHORTFALL_RESOLUTION`

**While the Shortfall Is Pending**
The system shall:
- Display the Requested Redemption Amount as ₹12,000.
- Display the Available Margin as ₹10,000.
- Display the Shortfall Amount as ₹2,000.
- Keep the entire redemption request financially unprocessed.
- Not process the ₹10,000 Available Margin portion separately.
- Not create a redemption ledger debit.
- Not reduce the Redeemable Balance as a result of redemption settlement.
- Maintain any applicable reservation required by the redemption lifecycle.
- Require the user to resolve the ₹2,000 shortfall outside the system.
**Therefore:**

`Redemption Ledger Debit = ₹0`
`Redemption Financial Settlement = ₹0`

**Offline Shortfall Resolution**
The user resolves the ₹2,000 shortfall outside the system through the prescribed offline process and
submits the required details/evidence.
The system records the shortfall-resolution information against the same Redemption Request ID.
The offline resolution itself does not create a redemption ledger debit.

**Admin Verification**
Admin verifies the submitted shortfall resolution.
If Admin rejects the resolution, the redemption follows the applicable rejection/failure handling and no
redemption ledger debit is created.
If Admin approves the resolution, the redemption becomes eligible for financial processing.
Admin Shortfall Verification SLA:
After the user submits the required shortfall-resolution details/evidence, the authorized Admin shall review
and verify the submission within the configured shortfall verification period.
- Default verification period: 24 hours.
- Configurable range: 1 to 72 hours.
- The verification period shall start from the timestamp at which the user submits the required
shortfall-resolution details/evidence.
- The system shall record the submission timestamp and verification timestamp.
- If verification is not completed within the configured verification period, the redemption request shall
remain subject to the applicable system-wide Redemption Request Expiry rules.
- The system-wide Redemption Request Expiry shall take precedence if the redemption request
reaches its expiry before Admin verification is completed.
**Financial Processing After Approval**
After Admin approval, the system processes the redemption using the defined financial settlement
mechanism.

The system shall record the actual financial transaction(s) resulting from that settlement and link them to
the same Redemption Request ID.
The system shall not:
- debit ₹10,000 first and ₹2,000 later as two independent redemption transactions;
- create a fictitious ₹12,000 debit against a ₹10,000 Redeemable Balance; or
- create a ledger debit merely to represent the offline shortfall.
The ledger shall record only the actual financial transaction(s) that occur as part of the approved
settlement.
**Cancellation**
If the user cancels the redemption before financial settlement:

| Item | Result |
|---|---|
| Redemption Status | CANCELLED |
| Financial Settlement | ₹0 |
| Redemption Ledger Debit | ₹0 |
| Reservation | Released |

**Important Accounting Principle**
In this example, the ₹2,000 shortfall is a prerequisite for approving the ₹12,000 redemption request; it
is not automatically a ₹2,000 ledger transaction.

The redemption therefore remains one redemption request, with no partial online settlement while the
shortfall is unresolved.















## Financial Impact — Before and After Approval (Flow Numbering 24)

- Financial Impact - Before and After Approval:-



**After Approval:**
- The approved redemption amount shall be processed through the Unified Financial Ledger.
- The applicable redemption amount shall be debited from the user's Redeemable Balance.
- The Available Margin shall be recalculated after the balance update.

- The approval-to-financial-settlement flow shall apply consistently to Course, Refund, Reinvestment,
Donation, Gadgets & Accessories, and Franchisee redemptions, subject to the specific settlement
mechanics defined for each category.
**Before Approval:**
- The Actual Redeemable Balance shall remain unchanged.
- Any applicable redemption reservation shall affect only the Available Margin.
- No redemption ledger debit or financial settlement shall occur before Admin approval.
- For a shortfall request, the full redemption shall remain financially unprocessed until the shortfall is
resolved and verified by Admin.
- If the request is cancelled, the associated reservation shall be released.



## Balance and Ledger Impact (Flow Numbering 25)

- Balance and Ledger Impact:-

Balance and Ledger Impact:
- On submission, the system shall perform the applicable balance/Available Margin check.
- A sufficient-balance request shall proceed to PENDING and may create a reservation.
- A shortfall request shall move to AWAITING_SHORTFALL_RESOLUTION and shall not create a
financial ledger debit.
- Resolution of an offline shortfall shall be recorded as verification information/evidence and shall not
itself create a redemption ledger debit.

- After Admin approval, the approved redemption shall create the applicable Unified Financial Ledger
entry and update the user's Redeemable Balance.
- Available Margin shall then be recalculated based on the updated balance and active reservations.
- On cancellation, rejection, or expiry, associated reservations shall be released without creating a
redemption debit.

## Franchisee Redemption Flow (Flow Numbering 27)

## 27. Franchisee Redemption Flow:

```
ADMIN CREATES FRANCHISEE PLAN
↓
ENTER PLAN NAME + ONE-TIME DEDUCTIBLE PRICE
↓
MAP COLLEGES
↓
USER OPENS REDEEM → FRANCHISEE
↓
VIEW FRANCHISEE PLANS
↓
SELECT PLAN
↓
VIEW MAPPED COLLEGES
↓
SELECT COLLEGE
↓
DISPLAY ONE-TIME DEDUCTIBLE PRICE
↓
CHECK AVAILABLE MARGIN
↓
PRICE ≤ AVAILABLE MARGIN?
YES → Submit Enquiry → Admin Review → Approve → Financial Processing → Ledger Update →
Balance Update
NO → Calculate Shortfall → Submit Enquiry → AWAITING_SHORTFALL_RESOLUTION → Offline
Resolution → Admin Verification → Approval → Financial Processing → Ledger Update → Balance Update
```



> Worked examples: see Appendix A for all redemption/ledger worked scenarios (Course, Refund, Reinvestment, Donation, Gadgets & Accessories, Full vs Partial, Shortfall, Franchisee).


---


# 6. Admin, User Management & Roles


## Admin Area

**Admin Area:**
Admin user should be able to view and manage calculations plans, users, enquiries an accessories.
Admin will be able to access and generate all kinds of reports related to payment, referrals, enquiries,
payments made to user, accessories and gadget inventory, etc.
Admin shall also be able to manage public website content (About Us and Contact Us, including
Draft/Preview/Publish — Rule XXXIX) and process General Enquiries submitted through the public
Contact Us page. "Enquiries" and "General Enquiries" remain distinct concepts throughout this
document: the pre-existing "enquiries" referenced above (and elsewhere in this Admin Area
description) denote Redemption/Franchisee enquiries under the existing redemption model, while
"General Enquiry" denotes only the Rule XXXIX public-contact-form record; the two are never merged
into a single entity or a single status model.

## User Management

**User Management:**
- Registration process
- Login
- OTP verification using email
- Email verification
- Forgot Password
- Change Password
- Social Login: Users shall be able to register and log in using administrator-configured social identity
providers (e.g., Google and/or other approved OAuth 2.0/OpenID Connect providers). The system
shall use the provider’s authorization flow and shall not store the user’s social-provider password.
On successful authentication, the system shall create or link the user account using the verified
provider identity/email. A user shall not be assigned duplicate accounts when the same verified
email is already registered. Social-login users may set a local password later through the applicable
account/password flow.

● Mobile Number: The user's mobile number shall be stored as part of the user's profile/contact
information and shall be available to Admin in User Management. The mobile number shall be
associated with the user's account and shall be editable by the user subject to system validation.
The mobile number shall not be used for login, 2FA, password reset, or other authentication OTP
purposes. A mobile number shall be marked as verified only after successful completion of the
configured verification process. *(Superseded — see Client-Requested Deviation below.)*

**Client-Requested Deviation — supersedes the sentence above:** The client has since directed that mobile
number verification is **not required**. The preceding sentence (originally requiring the mobile number to
be marked "verified" via a configured verification process) is preserved verbatim above per this document's
no-omission policy but is superseded: the mobile number shall be stored and editable as described, with no
verification step, verified/unverified status field, or verification process of any kind.

## Rule XXXIX — Website Content Pages (About Us & Contact Us) and General Enquiry

XXXIX. The public site menu shall offer two optional content pages, **About Us** and **Contact
Us**, each independently configurable by Admin under Site Settings, and a **General Enquiry**
submission channel reachable from the Contact Us page. This Rule defines the content model,
publication model, visibility rules, validation, security, workflow, notification, audit, and retention
requirements for all three. General Enquiry is a communications/business-support record and is a
functionally and financially distinct domain from the existing Redemption Request / Franchisee
enquiry model defined elsewhere in this document (Rule XXII, Rule XXXVII, and related redemption
rules) — see **XXXIX.10** below. Nothing in this Rule alters the Redemption Request lifecycle
(`PENDING`, `AWAITING_SHORTFALL_RESOLUTION`, `APPROVED`, `REJECTED`, `CANCELLED`,
`EXPIRED`) or the Plan lifecycle (`ACTIVE`, `MATURED`, `DISCONTINUED`, `PARTIALLY_REDEEMED`,
`REDEEMED`).

### XXXIX.1 — About Us: Content Model

- Admin may add one or more content sections. Each section consists of an optional **Heading**
  and a mandatory **paragraph in Rich Text format** (see **XXXIX.3**). Heading is never mandatory
  when adding a section — a section may consist of the paragraph alone.
- A section with a Heading but no Paragraph is **invalid** and shall be rejected at save time; it shall
  not be created and shall not be published.
- A section with neither Heading nor Paragraph (i.e., not configured) shall not be shown on the
  About Us page.
- If Admin has configured no valid sections at all, the About Us menu entry shall not appear and the
  page shall not be reachable.
- Admin shall be able to **add**, **edit**, **delete**, and **reorder** sections. Display order on the
  public page shall follow the Admin-controlled order. Deletion of a section shall require Admin
  confirmation and shall be recorded in the Audit Log (see **XXXIX.6**).
- **RESOLVED — PROPOSED DEFAULT — CLIENT CONFIRMATION RECOMMENDED:** the maximum number
  of About Us sections shall be **20**. This limit shall be enforced **server-side** and shall apply to
  both the **draft** and **published** versions of the About Us content. An attempt to create or save a
  21st section shall be **rejected with a clear error message** indicating the maximum has been
  reached; the 21st section shall not be created, saved as a draft, or published. This numeric limit is
  a proposed default pending explicit client confirmation and is not a placeholder — it applies
  immediately unless and until the client specifies a different value.

### XXXIX.2 — Contact Us: Content Model & Visibility

- Admin may configure the following generic contact detail fields, each independently optional:
  **Address**, **Primary Phone**, **Primary Email**, **Website / URL**, and **Social Links** (one or
  more labelled links, e.g. Facebook, Instagram, LinkedIn) / **Other Contact Links**.
- Any individual field Admin has not provided shall not be shown to users on the Contact Us page
  (missing fields are omitted, not shown blank).
- Visibility of the Contact Us page and of the General Enquiry channel are governed by two
  independent conditions rather than a single combined one:
  - **Contact details visible** — shown when Admin has configured at least one contact detail field
    above.
  - **General Enquiry channel available** — the enquiry form defined in **XXXIX.4** is a fixed
    platform feature and is not itself individually togglable by Admin; it is available whenever the
    Contact Us page is reachable.
  - The Contact Us page (and its menu entry) is reachable, and therefore the General Enquiry form
    is available, when **either** at least one contact detail is configured **or** the General Enquiry
    channel is otherwise enabled for the site. Under the currently confirmed requirement, the
    General Enquiry channel does not have its own independent enable/disable switch, so in
    practice: at least one contact detail configured ⇒ page visible with contact details **and** the
    enquiry form; no contact details configured ⇒ page and menu entry hidden. **Drafted
    Clarification — pending client confirmation:** if the client wishes the General Enquiry form to be
    reachable even when zero contact details are configured, Admin shall be given a separate
    "Enable General Enquiry form" toggle independent of the contact-detail fields; this toggle is not
    yet a confirmed requirement and is not implemented until specified.
  - If Admin has configured no contact details at all (and the General Enquiry channel has no
    independent toggle enabled per the clarification above), the Contact Us menu entry shall not
    appear and the page shall not be reachable.

### XXXIX.3 — Rich Text: Permitted Format & Sanitization

- "Rich Text" for the About Us paragraph field means **approved text formatting only**: bold,
  italic, underline, ordered lists, unordered lists, hyperlinks, and supported text sub-headings
  within the paragraph.
- Rich Text content shall **not** permit scripts, inline JavaScript, event-handler attributes,
  other executable content, arbitrary/unsafe raw HTML, or arbitrary iframe/embed content.
- All Rich Text content shall be **sanitized server-side** against an approved formatting allow-list
  before storage and before rendering, with safe handling of any embedded links (e.g. no
  `javascript:` URIs). This requirement applies to any Rich Text rendered on a public,
  unauthenticated page. No specific third-party rich-text editor is mandated by this BRD.

### XXXIX.4 — Content & Submission Validation

- Heading, Paragraph, and all Contact Us fields shall be validated server-side in addition to any
  client-side validation used for UX; server-side validation is mandatory regardless of client-side
  checks.
- **Email** fields (Contact Us Primary Email and the General Enquiry submitter email) shall be
  validated for email format. **Phone** fields shall be validated for phone-number format. **URL /
  Website / Social Link** fields shall be validated for URL format.
- **RESOLVED — PROPOSED DEFAULT — CLIENT CONFIRMATION RECOMMENDED:** the following
  maximum lengths (in characters) shall be enforced **server-side** for the fields below. These are
  proposed defaults pending explicit client confirmation and are not placeholders — they apply
  immediately unless and until the client specifies different values.

  | Field | Maximum Length (characters) |
  |---|---|
  | About Us — Heading | 150 |
  | About Us — Paragraph / Rich Text | 20,000 |
  | Contact Us — Email | 254 |
  | Contact Us — Phone | 20 |
  | Contact Us — URL (Website / Social Links) | 2,048 |
  | Contact Us — Address | 1,000 |
  | General Enquiry — Name | 150 |
  | General Enquiry — Email | 254 |
  | General Enquiry — Phone | 20 |
  | General Enquiry — Message | 5,000 |

### XXXIX.5 — General Enquiry: Submission

- The Contact Us page shall include a **General Enquiry** action allowing a user to submit an
  enquiry with the following fields:
  - **Name** (mandatory)
  - **Email** (mandatory)
  - **Phone number** (optional)
  - **Content** — the enquiry message (mandatory)
- A General Enquiry may be submitted **anonymously**; registration or authentication shall not be
  required to submit one, and the system shall not create a user account merely because an
  enquiry was submitted.
- For a **logged-in** user submitting a General Enquiry: the Name and Email fields shall be
  pre-filled from the user's profile where available, and the resulting enquiry record shall be
  associated with that User ID (the User ID is the authoritative user relationship identifier for the
  enquiry). The user may still edit the pre-filled Name/Email before submitting.
- For an **anonymous** submitter: the enquiry's User ID shall be `NULL`, and the submitted
  Name, Email, and Phone (if provided) shall be stored directly on the enquiry record.

### XXXIX.6 — General Enquiry: Lifecycle, Admin Workflow, Audit & Notification

- A General Enquiry shall follow its own lifecycle, independent of and never merged with the
  Redemption Request lifecycle or the Franchisee redemption enquiry lifecycle defined elsewhere in
  this document:
  ```
  NEW → IN_PROGRESS → RESOLVED
  ```
  A `RESOLVED` enquiry may be reopened back to `IN_PROGRESS` by Admin if further action is
  needed.
- Admin shall be able to: view a queue/list of General Enquiries; search and filter the list (e.g. by
  status); open an enquiry's details; change its status; record a resolution note / admin comment;
  and see the submission timestamp, submitter information, and associated User ID where
  applicable.
- **Audit:** on submission, an Audit Log entry shall be created (using the existing Audit Log model —
  no separate audit subsystem is introduced) capturing at minimum: Enquiry ID, action
  (`GENERAL_ENQUIRY_SUBMITTED`), timestamp, source, the authenticated User ID where
  applicable, and initial status. Subsequent Admin status changes and resolution actions shall
  likewise be audit-logged.
- **Notification:** on successful submission, the system shall: (1) persist the General Enquiry, (2)
  write the Audit Log event, then (3) trigger a `GENERAL_ENQUIRY_RECEIVED` Admin notification
  using the existing Notification System and its existing configured channels (Email and Push
  Notification — see §7 Notification System); no new notification infrastructure is introduced for this
  purpose. This event is additive to, and does not replace, any of the existing notification events
  listed in §7.
- **Submitter confirmation:** after successful submission, the submitter shall see an on-screen
  confirmation, which should include an enquiry reference/ID where practical. A confirmation email
  may additionally be sent where configured, but on-screen confirmation shall not depend on email
  delivery and email confirmation shall not be the only confirmation mechanism.

### XXXIX.7 — General Enquiry: Anti-Spam & Abuse Prevention

- Because the General Enquiry form is reachable without authentication, the submission endpoint
  shall apply server-side validation, rate limiting, and spam/abuse prevention, with safe error
  handling that does not leak internal system details.
- **RESOLVED — PROPOSED DEFAULT — CLIENT CONFIRMATION RECOMMENDED:** the following
  anti-spam thresholds shall be enforced **server-side**. These are proposed defaults pending
  explicit client confirmation and are not placeholders — they apply immediately unless and until the
  client specifies different values.
  - **IP-based throttling:** maximum **5 submissions per IP address per 15-minute window**.
  - **Email-based throttling:** maximum **3 submissions per submitter email address per 1-hour
    window**.
  - **Duplicate suppression:** a submission with the same normalized Name + Email + Message
    combination from the same IP address shall be suppressed (not created as a duplicate record)
    within a **10-minute window** of the prior identical submission.
  - **Honeypot field:** a hidden honeypot field is **mandatory** on the submission form; a
    submission that populates the honeypot field shall be silently rejected (treated as spam) without
    revealing the detection mechanism to the submitter.
  - **CAPTCHA:** CAPTCHA is **optional and adaptive** — it may be triggered based on risk signals
    (e.g. rate-limit proximity, suspicious patterns) rather than shown unconditionally on every
    submission. No specific CAPTCHA vendor is mandated by this BRD.

### XXXIX.8 — General Enquiry: Data Retention & Privacy

- Submitted General Enquiries (including submitter PII: Name, Email, Phone) shall be retained
  subject to a retention policy and shall be handled under this document's existing security/privacy
  controls (§9). Admin export of enquiry data may be supported through the reporting mechanism
  (§8). Deletion, if supported, shall be a controlled, audited action.
- **RESOLVED — PROPOSED DEFAULT — CLIENT CONFIRMATION RECOMMENDED:** the default
  retention period for General Enquiry records (including submitter PII) shall be **24 months** from
  the enquiry's creation date. This retention period is a proposed default pending explicit client
  confirmation and is not a placeholder — it applies immediately unless and until the client specifies
  a different value. The retention period shall be **configurable by Admin**. Deletion or anonymization
  of records that reach the end of the configured retention period shall be a **controlled, audited
  action** (recorded in the Audit Log), shall be **PII-protective** (removing or irreversibly anonymizing
  Name/Email/Phone), and shall be **reporting-aware** — the system shall account for the effect of
  retention-driven deletion/anonymization on historical Enquiry Report data (e.g. by retaining
  aggregate/statistical counts or a redacted record where reporting continuity is required) rather than
  silently breaking prior report totals.

### XXXIX.9 — Publication Model: Draft / Preview / Publish

- For **About Us** and **Contact Us content only** (not for the General Enquiry submissions
  themselves, which have no draft state), Admin changes follow a **Draft → Preview → Publish**
  model, distinct from the instant-apply model used elsewhere in Site Settings and distinct from the
  Theme/Website Appearance draft→publish→archive workflow (this Rule does not alter either of
  those existing workflows):
  - Saving a draft does **not** change what is publicly visible.
  - Admin may preview draft content before publishing; preview does not publish.
  - Only published content is publicly visible on the About Us / Contact Us pages.
  - Publishing a new version replaces the active public version. Version/audit history shall be
    maintained where supported by the existing Audit Log model.
  - This Draft/Preview/Publish model applies **only** to About Us and Contact Us content and shall
    not change the save/publish semantics of Theme/Website Appearance, other Site Settings,
    Plans, Interest, Payments, Referral configuration, Notifications, or any other financial
    configuration.

### XXXIX.10 — Financial Isolation (General Enquiry ≠ Financial Transaction)

- A General Enquiry shall have **no financial impact** and is not a financial transaction. Submitting,
  viewing, or resolving a General Enquiry shall **not**: debit the Redeemable Balance; change
  Available Margin; create or modify a redemption reservation; create or modify a Unified Financial
  Ledger entry; trigger or affect referral commission or commission eligibility; affect interest; affect
  Reward Points; affect payment status; or affect Plan maturity/status. The existing Redemption
  Request lifecycle (`PENDING`, `AWAITING_SHORTFALL_RESOLUTION`, `APPROVED`, `REJECTED`,
  `CANCELLED`, `EXPIRED`) and Plan lifecycle remain wholly authoritative and unaffected by this
  Rule; the General Enquiry `NEW`/`IN_PROGRESS`/`RESOLVED` lifecycle applies only to General
  Enquiry records and shall never be merged with, substituted for, or used to reinterpret those
  existing states.

### XXXIX.11 — Public Navigation

- The public site menu (base options `Home | Plans | Franchisee | Login | Register`, per §1 Platform
  Overview) is extended, subject to the visibility rules above, to the order **Home | Plans |
  Franchisee | About Us | Contact Us | Login | Register**. The existing Home, Plans, Franchisee,
  Login, and Register entries and their behavior are unchanged.
- **RESOLVED — PROPOSED DEFAULT — CLIENT CONFIRMATION RECOMMENDED:** the menu order
  above (`Home | Plans | Franchisee | About Us | Contact Us | Login | Register`) is the proposed
  default menu order and is not a placeholder — it applies immediately unless already client-approved
  or unless the client specifies a different order.
- About Us and Contact Us are **public-site pages** reachable via the public `SiteNav`. Logged-in
  User and Admin shells use their own existing, separate navigation and do not automatically
  inherit these public entries; adding these public pages shall not alter authenticated User or Admin
  navigation.

## Roles

**Roles:**
- Admin: Can manage the webapp features. The admin should be able to handle payout time which
will be only an information shared with user in the form of notification as well as in their dashboard.

**Drafted Clarification — pending client confirmation:** This BRD defines a single Admin role (above).
Phrases used elsewhere in this document such as "authorized Admin" and "authorized administrator" refer to
any user holding this Admin role, and do not denote a separate, more-privileged administrative sub-role or
permission tier. No admin sub-roles or tiered admin permissions are defined in this BRD unless/until the
client specifies them. **This paragraph is a proposed clarification drafted for review — it has not yet been
confirmed by the client.**
**Responsibilities:**
  - Dashboard
  - User Management
  - Plan Management
  - Interest/Reward configuration
  - Payment monitoring
  - Referral commission and code configuration
  - Reward point configuration
  - University management
  - Course management
  - Gadget & Accessories management
  - Redemption request management
  - Enquiry management
  - Notifications
  - Reports
  - Audit logs
  - Site settings
  - Website Content Management:
    - About Us (sections, Draft/Preview/Publish)
    - Contact Us (contact details, Draft/Preview/Publish)
    - Content version/audit history
  - General Enquiry management:
    - View/search/filter the General Enquiry queue
    - Change General Enquiry status (`NEW`/`IN_PROGRESS`/`RESOLVED`)
    - Record resolution notes / admin comments
    - Enquiry reporting (Rule XXXIX; distinct from Redemption Request Management and the
      existing Enquiry Management responsibility above, which continues to cover
      Franchisee/redemption enquiries)
- User: Can only create a profile and perform user related activities as per the documentation.

**Responsibilities:**
  - Register/Login
  - View available plans
  - Subscribe to one or multiple plans
  - Make payments
  - Manage auto-debit mandate
  - View investment summary
  - View redeemable balance
  - Redeem for (all of them will result in an enquiry to the admin)
    - Courses
    - Refund
    - Reinvestment
    - Donation
    - Franchisee
    - Gadgets & Accessories
  - View reward points
  - Generate and share referral code
  - View referred users
  - View referral earnings
  - Download payment receipts
  - Receive notifications
  - Update profile and payment details
  - View published About Us page (Rule XXXIX)
  - View published Contact Us page (Rule XXXIX)
  - Submit a General Enquiry, with or without being logged in (Rule XXXIX)
  - Receive on-screen (and, where configured, email) confirmation of General Enquiry submission
    (Rule XXXIX)

---


# 7. Notifications


## Rule XXXVIII — Upcoming Instalment Reminder

XXXVIII. Upcoming Instalment Reminder: The system shall send an Email and Push Notification to the
User before the scheduled upcoming instalment payment of an ACTIVE Plan. The reminder shall include
the applicable Plan, scheduled payment amount, and scheduled payment date. The reminder timing shall
be configurable by Admin.


## Notification System

**Need:**
- Email
- Push Notification

**Events:**
- Registration
- Payment Success
- Payment Failure
- Plan Maturity
- Referral Earned
- Refund Processed
- Admin Messages
- Upcoming Instalment Reminder: The system shall send an Email and Push Notification to the User
before the scheduled upcoming instalment payment of an ACTIVE Plan. The reminder shall include
the applicable Plan, scheduled payment amount, and scheduled payment date. The reminder timing
shall be configurable by Admin.
- General Enquiry Received (`GENERAL_ENQUIRY_RECEIVED`, Rule XXXIX): The system shall trigger
an Admin notification, via the existing Email and Push Notification channels, whenever a user
submits a General Enquiry through the public Contact Us page. This event is additive to the events
above; none of the existing events are removed, renamed, or altered by this addition.

---


# 8. Dashboards & Reports


## Student Dashboard

- Total invested
- Total interest
- Redeemable balance
- Reward points
- Active plans
- Closed plans
- Next deduction
- Referral earnings
- Transaction graph

## Admin Dashboard

- Total users
- Active plans
- Today's payments
- Pending refunds
- Pending enquiries — this stat continues to reflect existing Redemption/Franchisee enquiries
  under their existing status model and is not redefined by Rule XXXIX
- Pending General Enquiries (Rule XXXIX) — a separate stat counting General Enquiry records in
  `NEW` or `IN_PROGRESS` status; this is additive and does not merge with, or change the
  definition of, "Pending enquiries" above
- Referral payout
- Revenue
- Failed payments
- Upcoming maturities

## Reports

- Payment Report
- Interest Report
- Referral Report
- Plan Report
- Refund Report
- Reward Report
- Enquiry Report — includes existing Redemption/Franchisee enquiries and is extended to include
  General Enquiries (Rule XXXIX), distinguished within the report by an `Enquiry Type`
  classification (e.g. `GENERAL`, `REDEMPTION`, `FRANCHISEE`); `Enquiry Type` is a
  reporting/filtering classification only and does not merge the underlying domain-specific records
  or their distinct status models into one entity
- Revenue Report
- Audit Report
- User Report
- **Export:**
  - Excel
  - PDF
  - CSV

---


# 9. Security


## Security (Overview)

- Password encryption
- JWT/OAuth
- Role Based Access
- Device login
- 2FA
- Login history
- Audit logging

## Security Configuration / Technical Security Requirements

| Control | Requirement |
|---|---|
| Password Hashing | Passwords shall be stored only as securely salted password hashes using Bcrypt. Plain-text or reversibly encrypted passwords shall not be stored. |
| Password Complexity | Minimum 8 characters including at least one uppercase letter, one lowercase letter, one number, and one special character. |
| 2FA | Two-factor authentication shall use OTP delivered through the registered email address for applicable authentication flows. |
| 2FA OTP | OTP shall be 6 digits, valid for 5 minutes, and limited to 5 verification attempts per OTP. A maximum of 3 OTP resend requests within 15 minutes shall be permitted. |
| JWT Access Token | Access token shall expire after 30 minutes. |
| Refresh Token | Refresh token shall expire after 30 days and shall be invalidated on logout, password change, or account security reset. |
| Device / Session Tracking | The system shall track authenticated device/session information, including login time, logout time, and session status. |
| Login History | The system shall record successful and failed login attempts with timestamp and relevant security/audit information. |
| Failed Login Protection | After 5 consecutive failed login attempts, further login attempts for the account shall be temporarily blocked for 15 minutes. |
| Audit Logging | Security-sensitive events, including login success/failure, logout, password change/reset, 2FA events, token/session events, and account lockout shall be audit logged. |
| Password Reset | Password-reset tokens shall be single-use and shall expire after 30 minutes. |
| Transport Security | Authentication credentials, tokens, OTPs, and other sensitive information shall be transmitted only over HTTPS/TLS. |
| Social Login / OAuth | Social authentication shall use OAuth 2.0 and/or OpenID Connect authorization flows with administrator-approved providers. The system shall validate the provider-issued authorization result/token, verify the identity claims required for account login, and reject invalid, expired, or unverifiable authentication responses. Provider credentials/secrets shall be stored securely and shall not be exposed to users. |

**JWT Access Token:** Access tokens shall use RS256 (RSA SHA-256) signing. Access tokens shall expire after 30 minutes. The
signing private key shall be securely stored and shall not be exposed to clients.

**Refresh Token:** Refresh tokens shall expire after 30 days and shall be invalidated on logout, password change, or account
security reset. Refresh tokens shall be single-use and rotated upon successful refresh. The previously
used refresh token shall be invalidated immediately after rotation. Reuse of an invalidated refresh token
shall be rejected and the associated session shall be revoked.

**Social Login and 2FA:** Where 2FA is enabled for the user/account, successful social authentication shall
not bypass the applicable 2FA requirement. The system shall initiate the configured email OTP 2FA step
after successful social authentication. Users authenticating through social login shall use the registered
email address for applicable 2FA, consistent with the existing 2FA rules.

**Account Linking:** If a social-login provider returns an email address that matches an existing verified user
account, the system shall link the provider identity to the existing account only after the applicable account-
verification rules are satisfied. The system shall not create a second account for the same verified email

**Website Content / General Enquiry Security (Rule XXXIX):**
- **Rich Text:** all About Us Rich Text content shall be sanitized server-side against an approved
  formatting allow-list before storage and rendering, with safe link handling (no `javascript:` URIs
  or other unsafe schemes) and XSS prevention, applied to any Rich Text rendered on a public,
  unauthenticated page.
- **Public General Enquiry submission:** the endpoint shall apply server-side validation, rate
  limiting, and anti-spam/abuse-prevention measures, with safe error handling that avoids leaking
  internal system details, consistent with **XXXIX.7**.
- **Audit:** General Enquiry submission, About Us/Contact Us content modifications and
  publication, and General Enquiry status changes/resolutions shall each be captured using the
  existing Audit Logging control above; no separate audit subsystem is introduced.
- These requirements are additive to, and do not duplicate or replace, the existing authentication
  and session-security requirements above.

---


# 10. APIs & Database Design


## APIs

- Login
- Register
- Plans
- Payments
- Referral
- Rewards
- Redeem / Franchisee
- Reports
- Dashboard
- Notifications
- Website Content & General Enquiry (Rule XXXIX) — logical capabilities, endpoint naming to be
  adapted to existing conventions:
  - **Public:** `GET /content/about`, `GET /content/contact`, `POST /enquiries/general`
  - **Admin Content:** `GET/PUT /admin/site-content/about` (draft), `POST
    /admin/site-content/about/publish`; `GET/PUT /admin/site-content/contact` (draft), `POST
    /admin/site-content/contact/publish`
  - **Admin General Enquiry:** `GET /admin/enquiries/general`, `GET
    /admin/enquiries/general/{id}`, `PATCH /admin/enquiries/general/{id}/status`, `POST
    /admin/enquiries/general/{id}/comment`
  - These are additive; they do not rename or merge any existing financial API (e.g. Redeem /
    Franchisee) group.

## Database Design

- **Master**
  - Users
  - Plans (or Plan Configurations)
  - Interest
  - Inventory
  - Franchisee Plans
  - Colleges
  - Franchisee Plan–College Mapping
  - Site Content / Page Configuration (Rule XXXIX) — About Us Sections, Contact Details
- **Transactional**
  - Investments
  - Financial Transactions / Unified Ledger Redemptions
  - Redemptions
  - Notifications
  - Audit Logs
  - General Enquiry (Rule XXXIX) — conceptually: `id`, `user_id` (nullable), `name`, `email`,
    `phone` (nullable), `message`, `status`, `admin_comment` (nullable), `created_at`,
    `updated_at`, `resolved_at` (nullable), `resolved_by` (nullable), `source`. General Enquiry is
    not merged with the Redemptions entity or the Franchisee Redemption Enquiry data merely
    because both may appear together in the Enquiry Report; it carries no ledger, balance,
    reservation, or margin fields (see **XXXIX.10**).

Commission-specific fields can exist within the unified transaction structure.
```
Transaction Type = COMMISSION
Referrer User ID
Referred User ID
Commission Amount
Commission Status
Accrual Date
Approval Date
Credit Date
```

## University–Course Relationship

Each Course shall be associated with one University, and one University may have multiple Courses.
**Relationship: University 1 → Many Courses**
The Course record shall contain a mandatory University ID reference to its associated University.
A Course cannot be created or maintained without an associated University. When displaying or managing
Courses, the system shall identify the associated University.
A University may have zero, one, or multiple Courses.



---


# 11. Non-Functional Requirements

## Performance
- At least **95% of standard synchronous user-facing API requests shall complete within 2.0 seconds** under the agreed normal operating load, measured at the application/API boundary.
- For purposes of this NFR, standard synchronous user-facing APIs include the applicable **Login, Register, Plans, Payments, Referral, Rewards, Redeem / Franchisee, Dashboard, normal Notifications operations, and the public About Us / Contact Us content reads and General Enquiry submission (Rule XXXIX)**. Reports or other bulk/long-running operations shall be measured separately where applicable.
- Asynchronous processing triggered by a General Enquiry submission (e.g. the `GENERAL_ENQUIRY_RECEIVED` Admin notification dispatch) is background/asynchronous and is not included in the synchronous 2-second measurement for the submission endpoint, consistent with the asynchronous-processing exclusion above.
- Third-party payment-gateway processing time, asynchronous/background processing, bulk report generation, large file generation/downloads, and other explicitly asynchronous operations shall not be included in the standard 2-second response-time measurement unless specifically agreed in the performance test specification.
- "Normal operating load" shall be defined in the performance test specification using an agreed profile covering concurrent authenticated users, average request rate, peak request rate, and representative transaction volume.
- **Client confirmation required:** the numerical normal-load profile shall be finalized before performance acceptance testing.
- The PWA should be optimized for fast loading and efficient mobile performance, with measurable acceptance targets defined in the performance test specification.

## Availability
- The production system shall provide 99.9% monthly uptime.
- Planned maintenance shall be excluded from uptime calculation.

## PWA & Responsiveness
- The application shall be responsive across mobile, tablet and desktop devices.
- The PWA shall support installation on supported devices.
- Service-worker caching shall be used where appropriate to improve loading and repeat access.
- PWA performance shall be evaluated using an agreed mobile-device and network profile and shall include measurable targets for initial load, repeat navigation/load, and key user journeys. **Client confirmation required:** the final metric thresholds and test profile shall be documented before performance acceptance testing.

## Security
- All application communication shall use HTTPS.
- Authentication, authorization and payment-related data shall be handled securely.

## Scalability
- The system architecture shall support increasing users and transaction volume without major application redesign.
- The production architecture shall support an agreed capacity profile covering registered users, monthly active users, concurrent active users, peak API request rate, and monthly financial transactions.
- The application tier shall support horizontal scaling by adding application instances without requiring redesign of the core business logic or database model, subject to the agreed capacity limits.
- **Client confirmation required:** the numerical scalability/capacity targets shall be finalized before scalability acceptance testing.
- Scalability testing shall verify that the agreed capacity can be supported while maintaining the applicable response-time and availability requirements.


---


# Appendix A: Worked Financial Examples

Worked Financial Examples – Redemption and Ledger Calculations:
Illustrative examples only. Actual interest rates, reward percentages, and commission percentages are
configurable by Admin.
## 1. Course Redemption
Scenario A: Redeeming amount for course for the first time.

**Example:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹20,000 |
| Course Fee | ₹15,000 |
| Reward Points Balance | 0 |
| Reward Percentage | 10% |
| Reward Points Earned | 1,500 |
| Balance After Approval | ₹5,000 |
| Reward Points Balance | 1,500 |

**Flow:**
```
Redeemable Balance
₹20,000
↓
Reward Points Balance
0
↓
Course Redemption
₹15,000
↓
Balance After Redemption
₹5,000
↓
Reward = ₹15,000 × 10%
↓
Reward Points Balance
1,500
↓
Usable Only for Next Course
```

Scenario B: Redeeming amount for course for the second time.

**Example:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹20,000 |
| Course Fee | ₹15,000 |
| Reward Points Balance Before Redemption | 1,500 |
| Reward Percentage | 10% |
| Redeemable Balance Used | ₹13,500 |
| Reward Points Used | 1,500 |
| Balance After Approval | ₹6,500 |
| Reward Points Earned | 1,500 |

**Normative Clarification:** Scenario B illustrates the Rule XIII requirement that newly earned Reward Points
are calculated using the **full approved course fee**, even when part of that fee is settled using previously
earned Reward Points.

**Flow:**
```
Redeemable Balance
₹20,000
↓
Reward Points Balance
1,500
↓
Course Redemption
₹15,000 (₹13,500 Redeemable Balance + 1,500 Reward Points)
↓
Redeemable Balance After Redemption
₹6,500
↓
Reward = ₹15,000 × 10%
↓
Reward Points Earned = 1,500
```
Note: Reward Points do not increase the redeemable balance and cannot be withdrawn or used for refund,
donation, reinvestment, or gadgets.

## Reward Points Rounding

**Client-Requested Deviation — supersedes the paragraph below:** The client has since directed that Reward
Points always round **up (ceiling)**, not down. See the Reward Points Rounding Exception under Rule XXXIII
for the current rule. The paragraph and worked example immediately below are preserved verbatim as the
original source text (per this document's no-omission policy) but are **superseded** and no longer describe
the implemented rounding direction.

Reward Points shall be calculated using:
`Reward Points = Applicable Course Fee × Configured Reward Percentage`
Where the calculation produces a fractional value, the system shall round down (floor) the calculated
Reward Points to the nearest whole Reward Point.

**Example (superseded — see Client-Requested Deviation above):**

| Item | Value |
|---|---|
| Applicable Course Fee | ₹12,345 |
| Reward Percentage | 10% |
| Calculated Reward | 1,234.50 Reward Points |
| Reward Points Credited (original, floor) | 1,234 |
| Reward Points Credited (current rule, ceiling) | 1,235 |

The system shall not round the Reward Points upward. *(Superseded — see above.)*
Any fractional amount discarded through rounding shall not be credited to the user's Reward Points
balance.

**Reward Points Balance and Redemption Rules:**

- Data Type: Reward Points shall be stored as non-negative whole numbers. Fractional Reward
Points shall not be stored or maintained.
- Expiry: Reward Points shall not expire unless an expiry period is subsequently configured by the
Admin.
- Maximum Balance: No maximum cap shall apply to the user's Reward Points balance unless a cap
is subsequently configured by the Admin.
- Redemption: When Reward Points are used for an eligible redemption, the system shall deduct
only the number of Reward Points actually used and retain the remaining balance.
- Partial Redemption: Partial use of Reward Points shall be permitted. The system shall calculate
and record the exact whole-number Reward Points deducted and the resulting remaining Reward
Points balance.
- Insufficient Points: A user shall not be permitted to redeem more Reward Points than the available
Reward Points balance.
- No Negative Balance: The Reward Points balance shall never become negative.


## 2. Refund — Partial

**Example:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹20,000 |
| Requested Refund | ₹8,000 |
| Available Margin | ₹12,000 |

**Before Admin Approval:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹20,000 |
| Available Margin | ₹12,000 |

**After Approval:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹12,000 |
| Available Margin | ₹12,000 |

## 3. Reinvestment — Partial

**Example:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹30,000 |
| Reinvestment Amount | ₹20,000 |
| Remaining Balance | ₹10,000 |

```
Old Plan
|
+---- ₹20,000 ---> New Plan
|
+---- ₹10,000 ---> Remaining Redeemable Balance
```
Reinvestment is optional; the remaining ₹10,000 does not have to be reinvested.

## 4. Donation — Partial

**Example:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹25,000 |
| Donation Requested | ₹5,000 |
| Remaining Balance | ₹20,000 |

**Before approval:**

| Item | Amount |
|---|---|
| Actual Balance | ₹25,000 |
| Available Margin | ₹20,000 |

**After approval:**

| Item | Amount |
|---|---|
| Actual Balance | ₹20,000 |
| Available Margin | ₹20,000 |

**After Approval:**
- Approved Donation ₹5,000
- Unified Financial Ledger → Redemption/DONATION Debit ₹5,000
- Redeemable Balance Before ₹25,000
- Redeemable Balance After ₹20,000

Available Margin recalculated based on the updated balance and active reservations.


Before Approval: No financial ledger debit and no reduction of Actual Redeemable Balance.

## 5. Gadgets & Accessories — Multiple Items

**Initial State:**

| Description | Amount |
|---|---|
| Redeemable Balance | ₹10,000 |
| Available Margin | ₹10,000 |

User selects gadgets:

| Description | Amount |
|---|---|
| Selected Gadget A | ₹2,500 |
| Selected Gadget B | ₹1,500 |
| Total Requested | ₹4,000 |

**Before Admin Approval:**

| Description | Amount |
|---|---|
| Actual Redeemable Balance | ₹10,000 |
| Pending/Reserved Redemption | ₹4,000 |
| Available Margin | ₹6,000 |

The ₹4,000 redemption amount is pending approval and is therefore reserved. The Actual Redeemable
Balance remains ₹10,000 until the redemption is approved.
The Available Margin is calculated as:
`₹10,000 − ₹4,000 = ₹6,000`

**Scenario A — User Adds a ₹7,000 Gadget After the Original Redemption Is Approved**
The original ₹4,000 redemption has already been approved and processed.

| Description | Amount |
|---|---|
| Current Actual Redeemable Balance | ₹6,000 |
| Additional Gadget | ₹7,000 |
| Shortfall | ₹1,000 |

Calculation: ₹7,000 − ₹6,000 = ₹1,000 shortfall

**Therefore:**

| Description | Amount |
|---|---|
| Available Margin | ₹6,000 |
| Requested Additional Amount | ₹7,000 |
| Shortfall | ₹1,000 |

The additional redemption cannot be approved while the ₹1,000 shortfall remains unresolved. The user
must resolve the shortfall offline or cancel the redemption request.

**Scenario B — User Adds a ₹7,000 Gadget Before the Original Redemption Is Approved**

In this scenario, the original ₹4,000 redemption is still pending approval. Therefore, the ₹4,000 has not
yet been debited from the Actual Redeemable Balance.

The user modifies the pending gadget cart by adding another ₹7,000 gadget.

| Description | Amount |
|---|---|
| Existing Selection | ₹4,000 |
| Additional Gadget | ₹7,000 |
| Revised Total Requested | ₹11,000 |
| Actual Redeemable Balance | ₹10,000 |
| Shortfall | ₹1,000 |

**Calculation:**
```
₹4,000 + ₹7,000 = ₹11,000
₹11,000 − ₹10,000 = ₹1,000 (shortfall)
```

**Revised Pending State:**

| Description | Amount |
|---|---|
| Actual Redeemable Balance | ₹10,000 |
| Revised Pending Redemption Amount | ₹11,000 |
| Available Margin | ₹0 |
| Shortfall | ₹1,000 |
| Ledger Debit | ₹0 |

The original ₹4,000 pending reservation is replaced by the revised ₹11,000 pending request. It must not
be added to the revised request again.
Because the revised request exceeds the Actual Redeemable Balance by ₹1,000, the request cannot be
approved while the shortfall remains unresolved.
The user must either:
- Resolve the ₹1,000 shortfall offline, after which the Admin may verify and approve the request; or
- Cancel the redemption, in which case no ledger debit is made.
When a pending gadget redemption cart is modified, the previous pending request shall be replaced by the
revised total requested amount. The system shall recalculate the Available Margin and Shortfall using the
revised request and shall not double-count the previous pending reservation.

When a pending redemption request is modified, the revised total requested amount shall replace the
previous pending request amount for the purpose of calculating Available Margin and Shortfall. The
previous pending amount shall not be counted separately.

Depiction of example as flow diagram:



**Full vs Partial Redemption:**

```
Partial
Balance Before         ₹50,000
Approved Redemption   ₹15,000
----------
Balance After          ₹35,000
```
Plan Status: MATURED → PARTIALLY REDEEMED
```
Full
Balance Before         ₹50,000
Approved Redemption   ₹50,000
-----------
Balance After                ₹0
```
Plan Status: MATURED → REDEEMED
This will make PARTIALLY_REDEEMED state mathematically unambiguous.

## Partial Redemption Shortfall Example

| Item | Value |
|---|---|
| REDEEMABLE BALANCE | INR 50,000 |
| REQUESTED REDEMPTION AMOUNT | INR 70,000 |
| AVAILABLE MARGIN | INR 50,000 |
| SHORTFALL | INR 20,000 |

**Shortfall Calculation:**
Shortfall = Requested Redemption Amount − Available Margin = ₹70,000 − ₹50,000 = ₹20,000

**Before Shortfall Resolution:**
- Redemption Ledger Debit: ₹0
- Redeemable Balance: ₹50,000

After the user resolves the ₹20,000 shortfall offline and Admin approves the resolution, the redemption
becomes eligible for financial processing in accordance with the applicable settlement mechanism.
Redeemable Amount Available for System-Side Settlement: ₹50,000
Redeemable Balance After System-Side Settlement: ₹0
- Offline Shortfall Resolution: ₹20,000
- Total Redemption Value: ₹70,000

Course Redemption example:

**If:**

| Item | Amount |
|---|---|
| Redeemable Balance | ₹1,00,000 |
| Course Fee | ₹80,000 |
| Shortfall | ₹0 |

**Then:**

| Item | Amount |
|---|---|
| Redeemable Balance Deduction | ₹80,000 |
| Offline Payment | ₹0 |
| Remaining Balance | ₹20,000 |
| Ledger Debit | ₹80,000 |

## Course Redemption Shortfall Example

**Suppose:**

| Item | Amount |
|---|---|
| Course Fee | ₹80,000 |
| Redeemable Balance | ₹60,000 |
| Shortfall | ₹20,000 |

The financial treatment is:
```
COURSE FEE
₹80,000
↓
├── Redeemable Balance Used
│   ₹60,000
│
└── Offline Shortfall Payment
₹20,000
```
After successful offline payment and Admin approval:
```
Initial Redeemable Balance:  ₹60,000
Redeemable Amount Used:  -₹60,000
------------
Remaining Redeemable Balance:   ₹0
```

| Item | Amount |
|---|---|
| Offline Shortfall Payment | ₹20,000 |
| Total Course Fee | ₹80,000 |

**One Illustrative Interest Example:**
Illustrative only — not a fixed business rate.
For example:

| Item | Value |
|---|---|
| Principal | ₹10,000 |
| Illustrative annual rate | 12% |
| Tenure | 1 year |
| Interest method | Simple Interest |

```
Interest = ₹10,000 × 12% × 1 = ₹1,200
Illustrative maturity amount = ₹11,200
```

**Successful Four Consecutive Payments + Referral Commission:**
Configured recurring commission = ₹250 per completed four-payment cycle.
1st payment → Successful → Commission cycle: 1/4 → NOT_ACCRUED
2nd payment → Successful → Commission cycle: 2/4 → NOT_ACCRUED
3rd payment → Successful → Commission cycle: 3/4 → NOT_ACCRUED
4th payment → Successful → Commission cycle completed: 4/4 → ₹250 commission → ACCRUED
5th payment → Successful → New commission cycle: 1/4 → NOT_ACCRUED
6th payment → Successful → New commission cycle: 2/4 → NOT_ACCRUED
After the fourth consecutive successful payment, the ₹250 recurring commission is generated and the
commission cycle restarts from 1/4 for subsequent payments.


**Failed Payment + Referral Commission:**
1st payment → Successful → Commission cycle: 1/4 → NOT_ACCRUED
2nd payment → Successful → Commission cycle: 2/4 → NOT_ACCRUED
3rd payment → Failed → ₹0 commission → NOT_ACCRUED
3rd payment → Later successful → Commission cycle: 3/4 → NOT_ACCRUED
4th payment → Successful → Commission cycle completes: 4/4 → ₹250 commission → ACCRUED
Illustrative example: Configured recurring commission = ₹250 per completed four-payment cycle.

A failed payment shall not count toward the four-payment commission cycle. If the failed payment is
subsequently received successfully within the configured grace period, it shall count as a successful
payment toward the same four-payment cycle. Once four consecutive successful payments are completed,
the applicable recurring commission shall be generated and the commission cycle shall restart.

**Franchisee Example**

Normal case:
```
One-Time Deductible Price = ₹40,000
Available Margin = ₹50,000
```
₹40,000 ≤ ₹50,000, so the User submits the Franchisee enquiry normally. Before approval, the
Redeemable Balance remains unchanged, consistent with the existing enquiry model.

Shortfall case:
```
One-Time Deductible Price = ₹80,000
Available Margin = ₹50,000
Shortfall = ₹80,000 − ₹50,000 = ₹30,000
```

Before Admin approval:
- Redeemable Balance remains unchanged.
- Redemption Ledger Debit = ₹0.

After offline shortfall resolution and Admin approval:
System-side settlement shall be processed only in accordance with the applicable redemption settlement
rules.
The ₹30,000 offline shortfall shall remai



---

# Appendix B: Rule Number Locator

For readers who already know a rule by its original numeral, this maps every Rule to the section it now lives in.

| Rule | Now in |
|---|---|
| I | 1. Platform Overview |
| II | 1. Platform Overview |
| III | 2. Plans, Tenure & Interest Engine |
| IV | 2. Plans, Tenure & Interest Engine |
| V | 2. Plans, Tenure & Interest Engine |
| VI | 3. Payments & Payment Processing |
| VII | 3. Payments & Payment Processing |
| VIII | 4. Referral & Commission System |
| IX | 4. Referral & Commission System |
| X | 4. Referral & Commission System |
| XI | 4. Referral & Commission System |
| XII | 4. Referral & Commission System |
| XIII | 5. Redemption, Available Margin & Money Mechanics |
| XIV | 2. Plans, Tenure & Interest Engine |
| XV | 4. Referral & Commission System |
| XVI | 2. Plans, Tenure & Interest Engine |
| XVII | 4. Referral & Commission System |
| XVIII | 4. Referral & Commission System |
| XIX | 4. Referral & Commission System |
| XX | 4. Referral & Commission System |
| XXI | 4. Referral & Commission System |
| XXII | 5. Redemption, Available Margin & Money Mechanics |
| XXIII | 5. Redemption, Available Margin & Money Mechanics |
| XXIV | 5. Redemption, Available Margin & Money Mechanics |
| XXV | 5. Redemption, Available Margin & Money Mechanics |
| XXVI | 2. Plans, Tenure & Interest Engine |
| XXVII | 3. Payments & Payment Processing |
| XXVIII | 5. Redemption, Available Margin & Money Mechanics |
| XXIX | 5. Redemption, Available Margin & Money Mechanics |
| XXX | 3. Payments & Payment Processing |
| XXXI | 5. Redemption, Available Margin & Money Mechanics |
| XXXII | 4. Referral & Commission System |
| XXXIII | 5. Redemption, Available Margin & Money Mechanics |
| XXXIV | 5. Redemption, Available Margin & Money Mechanics |
| XXXV | 5. Redemption, Available Margin & Money Mechanics |
| XXXVI | 3. Payments & Payment Processing |
| XXXVII | 5. Redemption, Available Margin & Money Mechanics |
| XXXVIII | 7. Notifications |
| XXXIX | 6. Admin, User Management & Roles |
