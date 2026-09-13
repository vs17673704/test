# CLAUDE CODE — MASTER IMPLEMENTATION PROMPT

# BUILD COMPLETE WORKING PWA FROM BRD + DESIGN_UPDATED.MD + FIREBASE FCM

You are the lead software architect, senior full-stack developer, database developer, UI/UX engineer, PWA engineer, Firebase/FCM integration engineer, QA engineer, security engineer and debugging agent responsible for delivering a **complete, runnable, end-to-end local prototype** for the Referral & Reward Program Platform.

This is an **implementation and execution task**.

Do not merely:

* analyze requirements;
* create a scaffold;
* generate static mockups;
* write partial code;
* report missing functionality.

You must:

```text
READ
  ↓
RECONCILE
  ↓
DESIGN
  ↓
IMPLEMENT
  ↓
RUN
  ↓
TEST
  ↓
DEBUG
  ↓
FIX
  ↓
RETEST
  ↓
REGRESSION
  ↓
REVERIFY AGAINST BRD + DESIGN
  ↓
CONTINUE UNTIL CLEAN
```

---

# 1. AUTHORITATIVE SOURCE FILES

The project contains these primary source documents/configuration sources:

```text
BRD.md
Design_updated.md
firebase_prerequisites
```

Also inspect, when present:

```text
AUDIT.md
stitch_design/
Complete Frontend Revamp.md
Complete Website Theme Management.md
Complete Frontend Verification.md
Create Browser UI Automation Scripts and testrun.md
tests/browser/testrun.md
```

## Authority hierarchy

Use:

```text
BRD.md
    ↓
Business / Financial / Security / Lifecycle / NFR Authority

Design_updated.md
    ↓
UI / UX / Route / Screen / Interaction Authority

firebase_prerequisites
    ↓
Firebase / FCM Configuration Source

stitch_design/
    ↓
Visual / Design Reference

Other project instruction files
    ↓
Implementation / QA Guidance

Existing Source Code
    ↓
Current Implementation Evidence
```

### Mandatory interpretation

`BRD.md` controls:

* business rules;
* financial semantics;
* calculations;
* lifecycle/state transitions;
* validation;
* security;
* authorization;
* database/business meaning;
* external integration behavior;
* non-functional requirements.

`Design_updated.md` controls:

* required routes;
* screen inventory;
* UI structure;
* fields;
* buttons;
* labels;
* interactions;
* visual conventions;
* loading/error/empty states;
* responsive behavior;
* BRD-to-screen reconciliation.

Existing source code is **not authoritative** if it conflicts with BRD or Design.

Do not remove a required feature because current source code does not implement it.

Do not weaken a BRD rule merely to make implementation easier.

Do not invent unrelated requirements.

If `BRD.md` explicitly marks a clarification as:

**Drafted Clarification — pending client confirmation**

do not silently convert it into an unquestionable business rule. Document the implementation assumption and isolate it so it can be changed later.

Before implementation, read the **entire** `BRD.md` and the **entire** `Design_updated.md`.

---

# 2. PRIMARY OBJECTIVE

Build the complete working prototype described by the BRD and Design file.

Target runtime:

```text
User / Admin
    ↓
Responsive PWA
    ↓
Next.js App Router
    ↓
Server Components / Server Actions / Route Handlers
    ↓
Application Services / Business Rules
    ↓
Prisma
    ↓
PostgreSQL
```

External integrations are divided into two categories.

## Simulated integrations

The following remain simulated for the prototype:

* Razorpay / AutoPay;
* Email.

They must not require real credentials or real transactions.

## Real integration

The BRD-required **Push Notification** channel is implemented using:

**Firebase Cloud Messaging (FCM)**

FCM is therefore a real external runtime dependency.

The prototype remains locally runnable without Razorpay, Email, SMS, WhatsApp or OAuth credentials, but **Firebase configuration/credentials supplied through `firebase_prerequisites` are required for real FCM push functionality**.

The application must still remain usable when FCM is:

* unavailable;
* misconfigured;
* unsupported by the browser;
* denied by the user;
* temporarily failing.

In those situations, durable in-app Notification history must remain available.

---

# 3. ABSOLUTE NO-DOCKER REQUIREMENT

DO NOT create or use:

* Dockerfiles;
* Docker Compose;
* containers;
* Kubernetes;
* containerized PostgreSQL;
* container orchestration.

PostgreSQL must run directly on the local machine.

Example:

```env
DATABASE_URL="postgresql://postgres:password@localhost:5432/prototype_db"
```

Never hardcode credentials.

Provide:

```text
.env.example
```

Use:

```text
.env.local
```

for local development values.

---

# 4. TECHNOLOGY STACK

Use the following minimal stack:

## Application

* Next.js
* TypeScript
* React
* Next.js App Router

## Backend

Next.js itself is the backend.

Use:

* Server Components;
* Server Actions;
* Route Handlers;
* server-side service modules;
* repository/data-access modules where useful.

DO NOT create:

* Spring Boot;
* Express;
* separate backend;
* microservices;
* Kafka;
* RabbitMQ;
* Redis.

## Database

* PostgreSQL;
* Prisma;
* Prisma migrations.

## UI

* Tailwind CSS;
* lightweight React components.

Avoid adding a large UI framework unless genuinely necessary.

## Validation

* Zod.

## PWA

Use a stable Next.js-compatible PWA/service-worker architecture.

## Firebase

Use:

* Firebase Web SDK for browser FCM;
* Firebase Admin SDK for trusted server-side FCM delivery.

Use versions compatible with the current project and supported Firebase APIs.

---

# 5. REQUIRED FIRST ACTIONS

Before changing code:

1. Read complete `BRD.md`.
2. Read complete `Design_updated.md`.
3. Read complete `firebase_prerequisites`.
4. Inspect project structure.
5. Inspect current routes.
6. Inspect Prisma/schema.
7. Inspect authentication/session logic.
8. Inspect existing PWA/service worker.
9. Inspect existing browser automation.
10. Inspect package scripts.
11. Inspect any existing Firebase code.
12. Determine current implementation gaps.
13. Build a route/requirement/flow traceability matrix.

Do not start implementation from snippets.

---

# 6. FIREBASE PRE-IMPLEMENTATION GATE

The project may currently contain no Firebase implementation.

`firebase_prerequisites` is the authoritative source for the supplied Firebase information.

## Before implementing FCM:

1. Read `firebase_prerequisites`.
2. Identify the Firebase project.
3. Identify web configuration.
4. Identify VAPID configuration.
5. Identify Firebase Admin/server credentials.
6. Identify required Firebase services/APIs.
7. Identify required environment variables.
8. Identify development/test/production target.
9. Create an isolated validation setup if required.
10. Test real Firebase connectivity.
11. Test FCM server/client capability.
12. Proceed to application FCM implementation only after required validation passes.

### Configuration does not equal connectivity

Do not consider Firebase validated merely because:

* project ID exists;
* API key exists;
* environment variables exist;
* Firebase SDK is installed;
* Firebase initializes without an API operation;
* a mock call succeeds.

Actual connectivity must be tested.

### If validation fails

Diagnose:

* missing configuration;
* incomplete configuration;
* invalid credentials;
* invalid project;
* missing Firebase service/API;
* insufficient permissions;
* VAPID problems;
* SDK incompatibility;
* network/environment problem.

Fix what can safely be fixed, then retest.

Do not fake FCM success.

Do not replace FCM with a simulation and claim it is implemented.

---

# 7. FIREBASE SECRETS

Never expose:

* Firebase Admin private keys;
* service-account credentials;
* VAPID private keys;
* OAuth/client secrets;
* access tokens;
* refresh tokens.

Never place server secrets in:

```text
NEXT_PUBLIC_*
```

Never print actual secret values into:

* terminal reports;
* browser console;
* screenshots;
* traces;
* videos;
* source code;
* documentation.

Use masked status such as:

```text
FIREBASE_PRIVATE_KEY: configured / masked
```

---

# 8. FIREBASE ENVIRONMENT CONFIGURATION

Use only browser-safe values in public client configuration, for example:

```env
NEXT_PUBLIC_FIREBASE_API_KEY=...
NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN=...
NEXT_PUBLIC_FIREBASE_PROJECT_ID=...
NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET=...
NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID=...
NEXT_PUBLIC_FIREBASE_APP_ID=...
NEXT_PUBLIC_FIREBASE_VAPID_KEY=...
```

Server-side credentials must remain server-only.

Prefer a secure server credential mechanism such as:

```text
GOOGLE_APPLICATION_CREDENTIALS
```

or another secure Firebase-supported mechanism appropriate to the environment.

Document required variables in `.env.example` using placeholders only.

Do not commit actual Firebase secrets.

---

# 9. APPLICATION ARCHITECTURE

Use:

```text
app/
components/
lib/
services/
repositories/
providers/
  email/
  payment/
  notification/
    firebase/
schemas/
types/
prisma/
tests/
docs/
public/
```

Business logic must not depend directly on:

* React components;
* Prisma implementation details;
* Firebase SDK;
* simulated payment provider;
* simulated email provider.

Use provider/adapter boundaries.

---

# 10. PROVIDER ARCHITECTURE

The notification architecture must distinguish durable notifications from delivery channels.

Use:

```text
Business Event
      ↓
Notification Service
      ├────────────→ Persistent Notification Record
      ├────────────→ Simulated Email Provider
      └────────────→ Firebase FCM Push Provider
```

Do NOT use:

```text
NotificationProvider = SIMULATED
```

for the Push channel.

Use explicit provider configuration such as:

```env
EMAIL_PROVIDER=SIMULATED
PAYMENT_PROVIDER=SIMULATED
PUSH_NOTIFICATION_PROVIDER=FIREBASE_FCM
```

The durable `Notification` record is the application notification history.

FCM is only the Push Notification transport.

---

# 11. COMPLETE ROUTE INVENTORY

Do not trust a stale aggregate route count in `Design_updated.md`.

Derive the unique route inventory from the current document and screen map.

The current supplied Design file enumerates:

## Public — 5

```text
/
/plans
/franchisee
/about
/contact
```

## Authentication — 6

```text
/login
/register
/forgot-password
/reset-password
/verify-2fa
/login/social/google
```

## User — 13

```text
/dashboard
/dashboard/account
/dashboard/notifications
/dashboard/payments
/dashboard/referrals
/dashboard/verify-email
/dashboard/redeem
/dashboard/redeem/course
/dashboard/redeem/gadgets
/dashboard/redeem/refund
/dashboard/redeem/reinvestment
/dashboard/redeem/donation
/dashboard/redeem/franchisee
```

## Subscription — 1

```text
/plans/[planId]/subscribe
```

## Admin — 13

```text
/admin
/admin/users
/admin/plans
/admin/interest-methods
/admin/commissions
/admin/payments
/admin/redemptions
/admin/enquiries/general
/admin/catalog
/admin/emails
/admin/audit-log
/admin/reports
/admin/settings
```

Current total:

```text
5 + 6 + 13 + 1 + 13 = 38
```

Therefore the current target is **38 distinct routes**.

However, never permanently hard-code 38 as the only source of truth. Recalculate from the latest Design file whenever it changes.

Required special routes:

```text
/admin/interest-methods
/admin/enquiries/general
```

---

# 12. ROUTE COMPLETENESS GATE

Every required route must be:

* implemented;
* reachable under correct conditions;
* correctly authorized;
* responsive;
* browser-tested.

Maintain:

```text
docs/requirements-traceability.md
```

with:

| Route | Required | Implemented | Browser Tested | Responsive Tested | Access Tested | Status |
| ----- | -------- | ----------- | -------------- | ----------------- | ------------- | ------ |

Completion requires:

```text
Missing routes = 0
Required untested routes = 0
```

---

# 13. PUBLIC NAVIGATION

Base:

```text
Home | Plans | Franchisee | Login | Register
```

When valid/published content exists:

```text
Home | Plans | Franchisee | About Us | Contact Us | Login | Register
```

Test:

* neither configured;
* About only;
* Contact only;
* both;
* hidden entries;
* exact order;
* public routing.

Authenticated User/Admin shells are role-specific.

---

# 14. GLOBAL UI CONVENTIONS

Preserve:

## Touch targets

Minimum approximately 44px:

```text
min-h-11
```

## Primary actions

* black background;
* white text;
* rounded-md.

## Secondary

* bordered;
* gray border;
* no fill.

## Error

* red treatment.

## Warning/shortfall

* amber treatment.

## Success

* green/success treatment.

## Currency

All monetary values:

`₹`

Reward Points:

* bare whole number;
* no currency symbol.

## Status

Payment:

```text
SUCCESS = green
RETRYING = amber
FAILED = red
PENDING / INITIATED = neutral
```

Redemption:

```text
PENDING = neutral
AWAITING_SHORTFALL_RESOLUTION = warning
APPROVED = success
REJECTED = error
CANCELLED / EXPIRED = neutral
```

Commission:

```text
ACCRUED
APPROVED
CREDITED
AVAILABLE FOR WITHDRAWAL
WITHDRAWN
NOT_ACCRUED
```

General Enquiry:

```text
NEW
IN_PROGRESS
RESOLVED
```

Status must be communicated by text as well as visual treatment.

---

# 15. GLOBAL ASYNC STATES

Every asynchronous operation must correctly support, where applicable:

* loading;
* pending;
* success;
* failure;
* empty;
* unauthorized;
* forbidden;
* not found;
* network/offline failure.

Every submit action:

* disables during execution;
* prevents duplicate submission;
* displays a pending label.

Never show false success.

---

# 16. PWA GLOBAL CHROME

Root layout must account for:

* `OfflineBanner`;
* `SessionKeepAlive`;
* `ServiceWorkerRegister`.

Use:

```text
viewportFit: cover
```

Support:

```css
env(safe-area-inset-top)
env(safe-area-inset-bottom)
env(safe-area-inset-left)
env(safe-area-inset-right)
```

Do not allow fixed/persistent UI to overlap:

* notch;
* Dynamic Island;
* home indicator;
* browser UI.

---

# 17. PUBLIC SCREENS

## `/`

Implement:

* SiteNav;
* hero;
* exact/approved Design hero messaging;
* active plans;
* Franchisee Plans;
* mapped colleges;
* Register CTA.

No authenticated financial data.

## `/plans`

Show:

* ACTIVE plans;
* plan name;
* tenure;
* payment frequency;
* interest method;
* reward%;
* commission%;
* Subscribe.

DISCONTINUED plans are not available for new enrollment.

## `/franchisee`

Show:

* Franchisee Plan;
* one-time deductible price;
* mapped colleges.

---

# 18. ABOUT US

Implement `/about`.

Conditional on valid published content.

Admin content:

* maximum 20 sections;
* optional Heading max 150;
* mandatory Rich Text Paragraph max 20,000;
* reorder;
* remove/delete;
* safe sanitization;
* Draft;
* Preview;
* Publish.

A heading-only section cannot publish.

Unpublished content must never appear publicly.

---

# 19. CONTACT US

Implement `/contact`.

Configurable details:

* Address max 1,000;
* Primary Phone max 20;
* Primary Email max 254;
* Website/URL max 2,048;
* social/other labelled links.

Unconfigured values must be omitted.

General Enquiry:

* Name required max 150;
* Email required max 254;
* Phone optional max 20;
* Message required max 5,000;
* hidden honeypot;
* adaptive CAPTCHA;
* Send enquiry.

Server-side anti-spam must enforce the BRD rules.

Successful submission:

```text
Persist General Enquiry
↓
Show mandatory on-screen confirmation
↓
Reference ID where available
↓
GENERAL_ENQUIRY_SUBMITTED audit
↓
GENERAL_ENQUIRY_RECEIVED notification
↓
Simulated Email to Admin
↓
Firebase FCM Push to Admin
```

General Enquiry must have no financial effect.

---

# 20. AUTHENTICATION AND SECURITY

Implement:

```text
/login
/register
/forgot-password
/reset-password
/verify-2fa
/login/social/google
```

## Login

* email/password;
* Forgot password;
* simulated Google;
* email OTP 2FA;
* pending state;
* safe errors.

After 5 consecutive failed attempts:

* block account login for 15 minutes;
* record security event.

## Registration

* email;
* password;
* optional referral code;
* preserve referral query through social flow.

## Password Reset

* email;
* single-use 6-digit reset code;
* 30-minute validity;
* safe error states.

## 2FA

* email OTP;
* 6 digits;
* 5-minute validity;
* maximum 5 attempts per OTP;
* maximum 3 resends within 15 minutes.

Do not implement:

* mobile OTP;
* TOTP;
* FIDO2;
* hardware security keys;
* biometrics.

## Simulated Google OAuth

* collect simulated Google email;
* link existing account by email;
* create new account if needed;
* preserve referral code;
* auto-verify simulated Google email;
* continue to email OTP 2FA.

---

# 21. USER DASHBOARD

Implement all 13 User routes.

Dashboard includes:

* Total Invested;
* Redeemable Balance;
* Reward Points;
* Referral Earnings;
* Total Interest;
* Active Plans;
* Closed Plans;
* Next Deduction;
* Transaction Graph;
* payout timing/frequency information where applicable.

`Redeemable Balance` and `Actual Redeemable Balance` are the same underlying monetary value.

---

# 22. USER ACCOUNT

Implement:

* email;
* email verification;
* mobile number;
* password;
* Recent Logins / Device Sessions;
* AutoPay/payment details.

Payment details may show:

* Plan;
* payment frequency;
* next payment;
* mandate status;
* payment-method status;
* Change Payment Method where permitted.

Visible payment method categories may include:

* Card;
* UPI;
* Net Banking;
* Digital Wallet;

according to the Design/BRD-supported simulated payment model.

Never expose raw payment credentials.

Mobile number:

* profile only;
* editable;
* no verification;
* no mobile OTP.

---

# 23. NOTIFICATIONS SCREEN

Implement:

`/dashboard/notifications`

Required business events:

```text
REGISTRATION
PAYMENT_SUCCESS
PAYMENT_FAILURE
PLAN_MATURITY
REFERRAL_EARNED
REFUND_PROCESSED
ADMIN_MESSAGE
UPCOMING_INSTALMENT_REMINDER
GENERAL_ENQUIRY_RECEIVED
```

The durable Notification record remains independent of FCM delivery.

## FCM status UI

Support:

* Not supported;
* Permission not requested;
* Permission denied;
* Registration pending;
* Enabled;
* Registration failed.

Provide:

**Enable notifications**

as a user-driven action.

Push permission is never required for:

* login;
* payment;
* redemption;
* enquiry submission;
* other core functionality.

---

# 24. REAL FIREBASE FCM IMPLEMENTATION

FCM is the actual Push Notification provider.

Do not create a fake push implementation and call it FCM.

Architecture:

```text
Business Event
      ↓
Notification Service
      ↓
Notification DB Record
      ↓
Firebase FCM Push Provider
      ↓
User/Admin Browser Registrations
```

Email remains simulated.

Razorpay remains simulated.

---

# 25. FCM CLIENT IMPLEMENTATION

The client must:

* initialize Firebase only client-side;
* detect browser support;
* request notification permission in a user-driven action;
* use configured VAPID public key;
* register the application instance;
* communicate the current supported Firebase installation/registration identifier to the backend;
* handle foreground messages;
* coordinate with the existing PWA service worker;
* never expose server secrets.

Use current supported APIs for the installed Firebase SDK.

Do not introduce deprecated legacy APIs for a new registration model when a supported current API is available.

---

# 26. FCM SERVER IMPLEMENTATION

Use Firebase Admin SDK only server-side.

The server must:

* initialize Firebase securely;
* send FCM notifications;
* target active registrations;
* deactivate stale registrations;
* handle transient provider failure;
* prevent invalid delivery loops;
* record safe diagnostics.

Never send privileged Firebase credentials to the browser.

---

# 27. FCM REGISTRATION DATA

Maintain an application-side registration model supporting:

* registration ID;
* User/Admin ID;
* Firebase installation/registration identifier;
* active/inactive;
* createdAt;
* updatedAt/lastSeen;
* safe browser/platform metadata;
* provider error/status where useful.

Allow multiple valid registrations per account.

Prevent duplicate active registrations for the same application instance where appropriate.

---

# 28. FCM ACCOUNT ISOLATION

Push registration must be tied to the authenticated account.

Never trust a client-supplied User/Admin ID.

Test:

```text
Account A login
↓
FCM registration
↓
Logout
↓
Account B login
↓
FCM registration
↓
Account A does not receive Account B notifications
```

Handle account switching and stale registration cleanup safely.

---

# 29. FCM SERVICE WORKER

Integrate Firebase Messaging into the existing PWA service-worker architecture.

Do not create conflicting service workers claiming the same scope.

Background:

* service worker receives/displays push.

Foreground:

* page/client receives FCM message;
* existing in-app notification state can update;
* do not create duplicate business notifications.

Notification click:

* open safe application route;
* protected route must revalidate authentication/authorization.

---

# 30. FCM DELIVERY FAILURE

When FCM fails:

* durable Notification record remains;
* business event remains;
* no rollback occurs;
* no duplicate Notification is created.

For invalid registrations:

* deactivate/remove them from the active target set.

For transient failures:

* bounded retry/backoff where appropriate.

Never create uncontrolled retry loops.

---

# 31. FCM MULTI-DEVICE

A User/Admin may have multiple valid browser/device registrations.

Send the applicable push event to all currently valid active registrations.

Do not create duplicate business notifications merely because multiple registrations exist.

---

# 32. FCM MESSAGE CONTENT

Push payload may include:

* title;
* concise body;
* notification ID;
* business reference;
* event type;
* safe deep-link.

Do not include:

* passwords;
* OTPs;
* card data;
* session tokens;
* private keys;
* service credentials.

Upcoming reminder:

* Plan;
* amount;
* scheduled date.

General Enquiry Received:

* indicate a new General Enquiry;
* link safely to `/admin/enquiries/general`.

---

# 33. FCM EVENT MAPPING

Use existing BRD event names.

| Event                        | Recipient     |
| ---------------------------- | ------------- |
| Registration                 | User          |
| Payment Success              | User          |
| Payment Failure              | User          |
| Plan Maturity                | User          |
| Referral Earned              | Referrer      |
| Refund Processed             | User          |
| Admin Message                | Intended user |
| Upcoming Instalment Reminder | User          |
| General Enquiry Received     | Admin         |

Do not invent new notification business types.

---

# 34. UPCOMING INSTALMENT REMINDER

Implement:

```text
ACTIVE PLAN
↓
NEXT SCHEDULED INSTALMENT
↓
ADMIN-CONFIGURED LEAD TIME
↓
SIMULATED EMAIL
+
FCM PUSH
↓
USER SEES PLAN + AMOUNT + DATE
```

Do not send if the plan is no longer ACTIVE.

Reminder timing must be Admin-configurable.

---

# 35. GENERAL ENQUIRY FCM FLOW

```text
Public Contact Us
↓
General Enquiry
↓
Persist
↓
GENERAL_ENQUIRY_SUBMITTED audit
↓
GENERAL_ENQUIRY_RECEIVED
↓
Simulated Admin Email
+
FCM Admin Push
↓
Admin opens /admin/enquiries/general
↓
Admin updates NEW → IN_PROGRESS → RESOLVED
↓
Audit
```

No financial impact at any stage.

---

# 36. PAYMENT / AUTOPAY SIMULATION

Razorpay remains simulated.

Implement realistic:

* mandate creation;
* activation;
* payment processing;
* success;
* failure;
* retry;
* cancellation;
* expiry;
* webhook;
* invalid signature;
* idempotency.

Fake IDs:

```text
SIM-MANDATE-000001
SIM-PAYMENT-000001
SIM-WEBHOOK-000001
SIM-ORDER-000001
```

No real payments.

---

# 37. PAYMENT RULES

States:

```text
INITIATED
PENDING
SUCCESS
RETRYING
FAILED
```

Configuration:

* retry count default 3; range 1–5;
* retry interval default 24h; range 1–72h;
* grace period default 24h; range 0–168h;
* manual payment window default 24h; range 1–72h.

Failure must not cancel/suspend an enrolled plan.

---

# 38. PAYMENT IDEMPOTENCY

Use backend-generated idempotency keys.

Prevent duplicate:

* payment transactions;
* ledger entries;
* balance changes;
* commissions;
* commission-cycle increments;
* success notifications;
* receipts.

Webhook validation:

```text
Receive
↓
Verify
↓
Validate
↓
Detect duplicate
↓
Process once
```

Invalid signature:

* reject;
* no financial impact;
* audit event.

---

# 39. REFERRAL / COMMISSION

Implement:

* referral code;
* referral rotation;
* validity;
* expiry;
* cancellation;
* direct referral only;
* recurring commission;
* one-time commission;
* approval;
* credit;
* withdrawal.

Recurring:

* after four consecutive successful payments;
* failed/late-ineligible payments do not count;
* reset cycle after commission event.

One-Time:

* first eligible successful payment only.

Commission status:

```text
ACCRUED
APPROVED
CREDITED
AVAILABLE FOR WITHDRAWAL
WITHDRAWN
NOT_ACCRUED
```

`NOT_ACCRUED` is terminal and non-financial.

Approved commission is non-reversible.

Payout frequency:

* weekly;
* monthly;
* quarterly;
* yearly;

default monthly.

---

# 40. INTEREST METHODS

Implement:

`/admin/interest-methods`

Supported:

* Simple Interest;
* Compound Interest;
* Custom Parameterized Formula.

Variables:

* Principal;
* Rate;
* Tenure;
* Elapsed Days.

Operators:

* `+`
* `-`
* `*`
* `/`
* parentheses.

Validation:

* maximum 100 characters;
* maximum nesting depth 5;
* unsupported variables/operators rejected;
* invalid syntax rejected;
* divide-by-zero rejected;
* NaN rejected;
* Infinity rejected;
* negative result rejected.

Snapshot plan usage:

* method ID;
* formula;
* parameters;
* rate;
* tenure basis;
* compounding frequency;
* configuration/version ID.

Do not retroactively change existing plan terms.

---

# 41. PLAN MANAGEMENT

Implement:

* create;
* configure;
* discontinue;
* maturity.

Discontinued plans:

* cannot accept new enrollment;
* existing users continue under original terms;
* no discontinuation-based haircut;
* eventually transition to MATURED;
* do not create prohibited `DISCONTINUED → ACTIVE` reactivation behavior.

---

# 42. REDEMPTION

Categories:

* Course;
* Gadgets & Accessories;
* Refund;
* Reinvestment;
* Donation;
* Franchisee.

Balance:

```text
Redeemable Balance = Actual Redeemable Balance
```

Available Margin:

```text
Available Margin =
Actual Redeemable Balance
-
Total Active Reserved/Committed Redemption Amount
```

Do not double-count reservations.

---

# 43. REDEMPTION LIFECYCLE

Normal:

```text
REQUEST
↓
PENDING
↓
ADMIN REVIEW
↓
APPROVED
↓
FINANCIAL PROCESSING
↓
LEDGER
↓
BALANCE UPDATE
```

Shortfall:

```text
REQUEST
↓
REQUESTED > AVAILABLE MARGIN
↓
AWAITING_SHORTFALL_RESOLUTION
↓
OFFLINE RESOLUTION
↓
USER DETAILS/EVIDENCE
↓
ADMIN VERIFICATION
↓
APPROVED
↓
FINANCIAL PROCESSING
↓
LEDGER
↓
BALANCE UPDATE
```

Before approval:

* no final ledger debit;
* no Redeemable Balance reduction;
* no partial financial settlement of Available Margin;
* reservation only may affect Available Margin.

---

# 44. PARTIAL / FULL REDEMPTION

Partial:

```text
Balance Before = ₹50,000
Approved = ₹15,000
Balance After = ₹35,000
Status = PARTIALLY_REDEEMED
```

Full:

```text
Balance Before = ₹50,000
Approved = ₹50,000
Balance After = ₹0
Status = REDEEMED
```

---

# 45. COURSE REDEMPTION

Show:

* course;
* university;
* fee;
* Actual Redeemable Balance;
* Available Margin;
* Reward Points Balance;
* Reward Points used;
* Redeemable Balance portion;
* resulting Reward Points balance;
* newly earned points.

Reward Points:

* course-only;
* non-monetary;
* full course fee basis;
* ceiling/round up;
* never display with ₹.

If fee > Available Margin:

* show Shortfall;
* allow submission;
* state becomes `AWAITING_SHORTFALL_RESOLUTION`;
* no partial settlement.

---

# 46. GADGET REDEMPTION

Implement:

* item selection;
* multi-item cart;
* quantity;
* total;
* Available Margin;
* shortfall;
* reservation;
* modification.

Available stock:

```text
stockQuantity - reservedQuantity
```

Do not deduct physical stock until approval.

Pending modification must replace the previous reservation rather than double-counting it.

No GST/tax or shipping cost.

---

# 47. REINVESTMENT

Implement:

1. amount;
2. target plan;
3. review;
4. request;
5. Admin approval;
6. financial processing;
7. new plan enrollment.

Remaining balance stays outside the new plan where applicable.

---

# 48. FRANCHISEE REDEMPTION

Show:

* Franchisee Plan;
* mapped colleges only;
* deductible price;
* Available Margin;
* shortfall if applicable.

For shortfall:

* allow submission;
* enter `AWAITING_SHORTFALL_RESOLUTION`;
* no partial online settlement.

---

# 49. ADMIN CONSOLE

Implement all 13 Admin routes:

```text
/admin
/admin/users
/admin/plans
/admin/interest-methods
/admin/commissions
/admin/payments
/admin/redemptions
/admin/enquiries/general
/admin/catalog
/admin/emails
/admin/audit-log
/admin/reports
/admin/settings
```

Authorization:

* unauthenticated → `/login`;
* authenticated non-admin → `/dashboard`.

---

# 50. ADMIN DASHBOARD

Show:

* Total Users;
* Active Plans/Subscriptions;
* Plans Configured;
* Pending Redemptions;
* Pending Franchisee Enquiries;
* Pending General Enquiries;
* Revenue;
* Referral Payout;
* Failed Payments;
* Today's Payments;
* Pending Refunds;
* Upcoming Maturities.

Do not merge General Enquiries with financial queues.

---

# 51. ADMIN USERS

Support:

* lock/unlock;
* referral code activate/deactivate;
* referral commission configuration.

Commission configuration:

* Recurring;
* One-Time;
* 0–100%;
* up to two decimals.

Do not directly edit:

* Actual Redeemable Balance;
* Reward Points.

---

# 52. ADMIN COMMISSIONS

Support:

* ACCRUED approval queue;
* approve;
* reject;
* APPROVED non-reversible;
* credit to ledger;
* available for withdrawal;
* withdrawal;
* NOT_ACCRUED.

---

# 53. ADMIN REDEMPTIONS

Support:

* PENDING;
* AWAITING_SHORTFALL_RESOLUTION;
* verification;
* evidence;
* Admin concerns;
* approval;
* rejection;
* expiry.

Shortfall verification itself does not create a redemption debit.

---

# 54. ADMIN GENERAL ENQUIRIES

Implement:

`/admin/enquiries/general`

Fields:

* ID;
* Name;
* Email;
* Phone;
* Message;
* Status;
* Created;
* Updated;
* Resolved;
* Source;
* Admin comment.

States:

```text
NEW
IN_PROGRESS
RESOLVED
```

Actions:

* View;
* Change Status;
* Comment;
* Resolve.

Audit state changes.

No financial approval actions.

---

# 55. ADMIN CATALOG

Implement:

### Universities/Courses

* create University;
* create Course;
* one University → many Courses.

### Gadgets

* category;
* name;
* price;
* stock;
* reserved;
* available;
* adjust stock.

### Colleges

* create/list.

### Franchisee Plans

* create;
* price;
* map/unmap colleges.

---

# 56. ADMIN EMAIL OUTBOX

Simulated only.

Show:

* recipient;
* subject;
* template;
* status;
* createdAt;
* body;
* business reference.

No real Email provider.

---

# 57. AUDIT LOG

Record:

## Security

* login success/failure;
* logout;
* password change/reset;
* 2FA events;
* session/token events;
* lockout.

## Business

* payment;
* duplicate;
* webhook verification;
* interest;
* commission;
* redemption;
* shortfall;
* inventory;
* General Enquiry.

## Content

* About edit/delete/reorder/publish;
* Contact edit/publish;
* Draft/Preview/Publish.

Preserve exact distinct events:

```text
GENERAL_ENQUIRY_SUBMITTED
GENERAL_ENQUIRY_RECEIVED
```

Do not rename them for symmetry.

---

# 58. REPORTS

Implement:

* User;
* Payment;
* Interest;
* Referral;
* Plan;
* Refund;
* Reward;
* Enquiry;
* Revenue;
* Audit.

Formats:

* CSV;
* Excel;
* PDF.

Interest report must retain method/version references where applicable.

Enquiry report may use reporting classification:

```text
GENERAL
REDEMPTION
FRANCHISEE
```

Do not merge the underlying domain models.

---

# 59. SITE SETTINGS

Separate:

## Immediate operational settings

Examples:

* referral validity;
* OTP limits;
* password-reset/OTP TTL;
* retry count;
* retry interval;
* grace period;
* manual-payment window;
* redemption expiry;
* shortfall verification period;
* commission cycle;
* reward percentage;
* upcoming reminder lead time;
* commission payout frequency.

Use exact BRD ranges/defaults.

## Website content

About/Contact use:

```text
DRAFT → PREVIEW → PUBLISH
```

Do not apply this publication model to unrelated financial settings.

## General Enquiry retention

Default:

```text
24 months
```

Configurable, audited and PII-protective.

---

# 60. UNIFIED FINANCIAL LEDGER

Use one financial ledger for applicable:

* investments;
* payments;
* interest;
* commissions;
* redemption transactions.

Do not create a separate commission ledger.

Money:

* PostgreSQL NUMERIC / Prisma Decimal;
* 2 decimals;
* Round Half Up.

Reward Points:

* whole-number;
* ceiling/round-up.

---

# 61. DATABASE MODEL

Derive actual entities from BRD/Design.

Likely concepts include:

```text
User
Session/SecurityHistory
Plan
PlanSubscription
InterestMethod
InterestMethodVersion
Payment
PaymentAttempt
PaymentMandate
PaymentEvent
Referral
Commission
LedgerEntry
RedemptionRequest
RedemptionReservation
ShortfallResolution
Course
University
Gadget
GadgetReservation
College
FranchiseePlan
FranchiseePlanCollege
Notification
EmailMessage
FcmRegistration
GeneralEnquiry
WebsiteContent
WebsiteContentVersion
AuditLog
SiteSetting
```

Do not blindly create unnecessary tables.

Use:

* foreign keys;
* unique constraints;
* indexes;
* timestamps;
* transactional integrity.

---

# 62. FCM REGISTRATION DATA MODEL

Maintain registrations supporting:

* registration ID;
* User/Admin association;
* Firebase installation/registration identifier;
* active/inactive;
* createdAt;
* updatedAt/lastSeen;
* safe browser/platform metadata;
* provider status/error where useful.

Multiple active registrations per account are allowed.

---

# 63. SERVER-SIDE VALIDATION

Validate server-side:

* authentication;
* authorization;
* ownership;
* financial amounts;
* Available Margin;
* reservations;
* percentages;
* interest formulas;
* referral eligibility;
* commission;
* payment event authenticity;
* idempotency;
* General Enquiry anti-spam;
* FCM registration ownership.

Never trust client-provided:

* role;
* balance;
* amount;
* approval state;
* Firebase user ID;
* ownership.

---

# 64. BUSINESS CALCULATIONS

Centralize:

## Money

* INR;
* 2 decimals;
* Round Half Up.

## Reward Points

* whole;
* ceiling/round up;
* course-only;
* full applicable course fee.

## Available Margin

```text
Actual Redeemable Balance
-
Total Active Reserved/Committed Redemption Amount
```

## Shortfall

```text
Requested Amount - Available Margin
```

## Interest

Use plan snapshot + method version.

## Commission

Use actual successful payment amount and correct recurring/one-time rules.

---

# 65. STATE MACHINES

Implement explicit server-side transitions.

## Plan

```text
ACTIVE
DISCONTINUED
MATURED
PARTIALLY_REDEEMED
REDEEMED
```

## Payment

```text
INITIATED
PENDING
SUCCESS
RETRYING
FAILED
```

## Redemption

```text
PENDING
AWAITING_SHORTFALL_RESOLUTION
APPROVED
REJECTED
CANCELLED
EXPIRED
```

## Commission

```text
ACCRUED
APPROVED
CREDITED
AVAILABLE FOR WITHDRAWAL
WITHDRAWN
NOT_ACCRUED
```

## General Enquiry

```text
NEW
IN_PROGRESS
RESOLVED
```

Reject invalid transitions.

---

# 66. GENERAL ENQUIRY FINANCIAL ISOLATION

General Enquiry must never:

* debit balance;
* reduce Available Margin;
* create redemption reservation;
* create financial ledger transaction;
* trigger commission;
* change interest;
* change Reward Points;
* change payment;
* change plan status;
* change redemption status.

---

# 67. SECURITY / PRIVACY

Use secure prototype defaults.

Never:

* store plaintext passwords;
* expose secrets;
* expose Firebase server credentials;
* expose VAPID private keys;
* store raw card data;
* store CVV;
* store banking credentials;
* log secrets;
* trust client-provided financial data;
* unsafe-cache private user responses.

Store timestamps according to BRD requirements, UTC where specified.

---

# 68. DETERMINISTIC SIMULATIONS

Razorpay simulator:

* deterministic;
* no random failure;
* SUCCESS;
* FAILED;
* PENDING where applicable;
* invalid webhook;
* retry;
* expiry;
* cancellation;
* maturity.

Email simulator:

* deterministic;
* persisted in database.

FCM is NOT simulated.

---

# 69. RESPONSIVE/PWA REQUIREMENTS

Support:

```text
320px
360px
375px
390px
414px
768px
1024px
1280px+
```

And explicitly test:

```text
390x844
768x1024
1024x768
1440x900
```

No:

* horizontal overflow;
* clipped controls;
* tiny touch targets;
* hover-dependent critical actions;
* desktop-only workflow.

Dense Admin tables require a usable mobile representation:

* cards/stacked rows;
* or controlled horizontal scrolling.

---

# 70. PWA REQUIREMENTS

Implement:

* manifest;
* valid icons;
* service worker;
* installability;
* standalone mode where supported;
* offline indicator;
* safe-area handling;
* responsive viewport.

Do not unsafe-cache authenticated/private responses.

---

# 71. SERVICE WORKER + FCM REQUIREMENT

There must not be competing service workers with conflicting scopes.

Integrate Firebase Messaging into the existing PWA worker architecture.

Verify:

* registration;
* foreground messaging;
* background messaging where supported;
* notification click;
* protected-route authorization after click;
* no duplicate notifications.

---

# 72. ACCESSIBILITY

Use:

* semantic HTML;
* accessible labels;
* keyboard navigation;
* visible focus;
* accessible errors;
* text-based status;
* ARIA only where necessary.

---

# 73. DEMO DATA

Seed deterministic scenarios for:

* public plans;
* subscriptions;
* payment states;
* retry;
* mandates;
* referral relationships;
* recurring commission;
* one-time commission;
* redemption;
* shortfall;
* gadgets;
* Franchisee Plans/colleges;
* General Enquiries;
* notifications;
* simulated emails;
* Admin accounts;
* FCM-ready test users where safely possible.

---

# 74. TESTING

Create:

## Unit

* financial calculations;
* Reward Points;
* Available Margin;
* Shortfall;
* interest formula;
* commissions;
* states;
* reservations.

## Integration

* authentication;
* authorization;
* payments;
* idempotency;
* ledger;
* notifications;
* General Enquiry;
* FCM server provider;
* Firebase configuration;
* stale registration cleanup.

## Browser

Use actual rendered UI.

---

# 75. FCM BROWSER TESTING

Test through the real browser where the environment supports it.

## Configuration levels

Report independently:

```text
Firebase Configuration
Firebase Connectivity
FCM SDK Initialization
FCM Registration
FCM Send
Browser Delivery
Notification Click
```

Do not report End-to-End Push PASS unless actual browser delivery is observed.

## User scenarios

* Enable notifications;
* permission granted;
* permission denied;
* unsupported browser;
* registration success;
* registration failure;
* foreground push;
* background push where supported;
* click/deep-link;
* stale registration;
* logout;
* account switching;
* multiple devices.

## Business push scenarios

* Registration;
* Payment Success;
* Payment Failure;
* Plan Maturity;
* Referral Earned;
* Refund Processed;
* Admin Message;
* Upcoming Instalment Reminder.

## Admin

* Admin registration;
* General Enquiry Received;
* click into General Enquiry;
* account isolation.

---

# 76. FCM FAILURE TEST

Verify:

```text
Notification Record Created
+
FCM Delivery Failed
```

results in:

* Notification retained;
* business event retained;
* no rollback;
* safe logging;
* stale registration handling where applicable.

Also test:

```text
No active FCM registration
+
Business Notification Created
=
Notification remains in in-app notification history
```

---

# 77. FCM IDEMPOTENCY

Repeated callbacks must not create duplicate:

* notification records;
* emails;
* FCM business events;
* Payment Success events;
* audit events.

Transport retries are not new business events.

---

# 78. AUTOMATED END-TO-END SCENARIOS

At minimum test:

1. Register → email verification → 2FA → dashboard.
2. Login → 2FA → dashboard.
3. Google simulation → 2FA.
4. Browse plan → subscribe → simulated mandate.
5. Successful payment → ledger → notification/email.
6. Failed payment → retry → success.
7. Retry exhaustion → manual payment.
8. Maturity → interest → redemption eligibility.
9. Course redemption.
10. Course redemption with Reward Points.
11. Course shortfall → resolution → Admin verification → approval.
12. Gadget multi-item request → modification → approval.
13. Franchisee plan → mapped college → request.
14. Reinvestment → target plan → approval.
15. Referral → recurring commission.
16. Referral → one-time commission.
17. Commission approval → ledger → withdrawal.
18. Public General Enquiry → audit → Admin email + FCM.
19. Admin General Enquiry → NEW → IN_PROGRESS → RESOLVED.
20. About Draft → Preview → Publish.
21. Contact Draft → Preview → Publish.
22. Upcoming Instalment Reminder → Email + FCM.
23. FCM denied permission.
24. FCM stale-registration cleanup.
25. FCM account isolation.
26. FCM delivery failure retaining Notification history.

---

# 79. DOCUMENTATION

Create/update:

```text
README.md
docs/pwa-testing.md
docs/integrations.md
docs/requirements-traceability.md
docs/progress.md
```

## README

Document:

* Node;
* PostgreSQL;
* installation;
* setup;
* migrations;
* seed;
* startup;
* test;
* demo credentials;
* simulated providers;
* Firebase prerequisite;
* FCM configuration;
* local limitations.

## integrations.md

Document:

* simulated Razorpay;
* simulated Email;
* real Firebase FCM;
* provider architecture;
* Firebase setup;
* Web config;
* VAPID;
* server credentials;
* FCM registration;
* stale registration;
* service worker;
* testing;
* security;
* future replacement strategy.

Never commit secrets.

---

# 80. REQUIREMENT TRACEABILITY

Create:

```text
docs/requirements-traceability.md
```

For each major requirement:

```text
BRD requirement
    ↓
Design route/screen
    ↓
UI
    ↓
Server/business service
    ↓
Database
    ↓
Validation
    ↓
Test
    ↓
Integration
```

Do not mark a requirement complete merely because a route exists.

---

# 81. RESUMABLE PROGRESS LEDGER

Maintain:

```text
docs/progress.md
```

Track:

* current phase;
* completed modules;
* completed routes;
* completed BRD areas;
* Firebase prerequisite state;
* FCM implementation state;
* FCM validation state;
* tests run;
* failures;
* fixes;
* assumptions;
* unresolved items;
* next action.

This must allow another Claude Code session to resume accurately.

---

# 82. IMPLEMENTATION PROCESS

## Phase 1 — Discovery

1. Read BRD.
2. Read Design.
3. Read Firebase prerequisites.
4. Inspect source.
5. Derive routes.
6. Build traceability.

## Phase 2 — Firebase Validation

1. Extract prerequisites.
2. Validate configuration.
3. Validate Firebase connectivity.
4. Validate FCM capability.
5. Fix safe prerequisite issues.
6. Re-test.

Do not fake FCM.

## Phase 3 — Foundation

1. Next.js.
2. Prisma.
3. PostgreSQL.
4. Auth.
5. Authorization.
6. PWA shell.
7. Provider boundaries.

## Phase 4 — Core Domains

1. Users.
2. Plans.
3. Interest.
4. Payments.
5. Referrals.
6. Commissions.
7. Ledger.
8. Catalog.
9. Redemption.
10. Website Content.
11. General Enquiries.
12. Notifications.

## Phase 5 — Providers

1. Simulated Razorpay.
2. Simulated Email.
3. Real Firebase FCM.

## Phase 6 — UI

Implement all required routes.

## Phase 7 — Validation

Run:

* unit;
* integration;
* browser;
* responsive;
* PWA;
* FCM tests.

## Phase 8 — Recursive Fixing

```text
TEST
↓
FAILURE
↓
ROOT CAUSE
↓
FIX
↓
BUILD
↓
RETEST
↓
REGRESSION
```

Continue until clean.

---

# 83. AUTONOMOUS EXECUTION

Do not repeatedly ask for permission.

Make reasonable decisions from BRD + Design.

However:

* do not invent business rules;
* do not change financial semantics;
* do not silently alter state machines;
* do not use fake FCM;
* do not hide Firebase failures;
* do not claim verification without evidence;
* do not remove required functionality.

---

# 84. DO NOT DO THESE THINGS

DO NOT:

* use Docker;
* use Docker Compose;
* use Dockerfiles;
* use Kubernetes;
* create Spring Boot;
* create separate Express backend;
* create microservices;
* use Kafka;
* use RabbitMQ;
* use Redis;
* connect to real Razorpay;
* process real payments;
* send real Email;
* send real SMS;
* send real WhatsApp;
* use real external OAuth credentials;
* store raw payment data;
* store CVV;
* expose Firebase Admin credentials;
* expose VAPID private keys;
* expose secrets;
* use FCM simulation instead of real FCM;
* merge General Enquiry with Redemption;
* create a separate commission ledger;
* add mobile OTP;
* add TOTP/FIDO/biometric UI;
* create unsupported Admin notification routes;
* omit required routes;
* leave required functionality as TODO;
* claim FCM works because SDK installation succeeded;
* claim Push delivery succeeded without testing it.

---

# 85. FINAL VERIFICATION CHECKLIST

## Core

[ ] Full BRD read
[ ] Full Design_updated.md read
[ ] Full firebase_prerequisites read
[ ] Route inventory derived
[ ] Traceability created

## Routes

[ ] All current required routes implemented
[ ] All current required routes tested
[ ] Conditional routes tested
[ ] Correct authorization tested

## Authentication

[ ] Register
[ ] Login
[ ] Logout
[ ] Email verification
[ ] Forgot password
[ ] Reset password
[ ] Email OTP 2FA
[ ] Login lockout
[ ] Simulated Google
[ ] Authorization
[ ] Session behavior

## Plans / Interest

[ ] Public Plans
[ ] Subscription
[ ] Existing terms immutable
[ ] Discontinuation
[ ] Maturity
[ ] Interest Methods
[ ] Formula validation
[ ] Method/version snapshot

## Payments

[ ] Mandate simulation
[ ] Payment states
[ ] Retry
[ ] Grace period
[ ] Manual fallback
[ ] Payment methods
[ ] Idempotency
[ ] Invalid webhook
[ ] Duplicate protection
[ ] Receipt
[ ] Payment Success notification once

## Referral / Commission

[ ] Referral code
[ ] Rotation
[ ] Validity
[ ] Expiry
[ ] Recurring commission
[ ] One-time commission
[ ] Approval
[ ] Credit
[ ] Withdrawal
[ ] NOT_ACCRUED

## Redemption

[ ] Balance semantics
[ ] Available Margin
[ ] Reservations
[ ] Partial
[ ] Full
[ ] Shortfall
[ ] Shortfall verification
[ ] Approval
[ ] Rejection
[ ] Cancellation
[ ] Expiry
[ ] Course Reward Points
[ ] Gadget cart
[ ] Gadget reservation
[ ] Gadget modification
[ ] Reinvestment target plan
[ ] Franchisee mapping

## General Enquiry

[ ] Contact page
[ ] Submission
[ ] Validation
[ ] Anti-spam
[ ] Confirmation
[ ] Reference ID
[ ] Audit
[ ] Admin Email
[ ] Admin FCM
[ ] Admin queue
[ ] NEW
[ ] IN_PROGRESS
[ ] RESOLVED
[ ] No financial impact

## Website Content

[ ] About Draft
[ ] About Preview
[ ] About Publish
[ ] About 20-section limit
[ ] About sanitization
[ ] Contact Draft
[ ] Contact Preview
[ ] Contact Publish
[ ] Conditional public navigation

## Firebase FCM

[ ] firebase_prerequisites read
[ ] Firebase configuration validated
[ ] Firebase connectivity verified
[ ] FCM server initialization verified
[ ] FCM client initialization verified
[ ] Browser support detection works
[ ] Permission flow works
[ ] FCM registration works
[ ] Registration persists server-side
[ ] Account association works
[ ] Multiple registrations work
[ ] Logout isolation works
[ ] Account-switching isolation works
[ ] Foreground push tested
[ ] Background push tested where supported
[ ] Notification click tested
[ ] Deep-link authorization tested
[ ] Stale registration cleanup tested
[ ] FCM failure preserves Notification history
[ ] No secrets exposed
[ ] Existing PWA service worker is compatible
[ ] No competing worker exists

## PWA

[ ] Manifest
[ ] Icons
[ ] Service worker
[ ] Installability
[ ] Offline indicator
[ ] Safe-area support
[ ] No unsafe private-data caching

## Responsive

[ ] 320px
[ ] 360px
[ ] 375px
[ ] 390px
[ ] 414px
[ ] 768px
[ ] 1024px
[ ] 1280px+
[ ] 390x844
[ ] 768x1024
[ ] 1024x768
[ ] 1440x900
[ ] No horizontal overflow
[ ] Touch targets usable
[ ] Dense Admin UI usable on mobile

## Documentation

[ ] README complete
[ ] PWA docs complete
[ ] Integration docs complete
[ ] Requirements traceability complete
[ ] Progress ledger complete

---

# 86. FINAL REPORT

Provide:

```text
=== FINAL IMPLEMENTATION STATUS ===

Route Inventory:
[derived number]

Routes Implemented:
[number]

Routes Tested:
[number]

BRD Compliance:
PASS / FAIL

Design Compliance:
PASS / FAIL

Authentication:
PASS / FAIL

Plans:
PASS / FAIL

Interest:
PASS / FAIL

Payments:
PASS / FAIL

Referral / Commission:
PASS / FAIL

Redemption:
PASS / FAIL

General Enquiry:
PASS / FAIL

Website Content:
PASS / FAIL

Simulated Email:
PASS / FAIL

Simulated Razorpay:
PASS / FAIL

Firebase Configuration:
PASS / FAIL

Firebase Connectivity:
PASS / FAIL

FCM Registration:
PASS / FAIL / NOT VERIFIED

FCM Send:
PASS / FAIL / NOT VERIFIED

Browser Push Delivery:
PASS / FAIL / NOT VERIFIED

Notification Click:
PASS / FAIL / NOT VERIFIED

PWA:
PASS / FAIL

Responsive:
PASS / FAIL

Automated Tests:
PASS / FAIL

Browser Tests:
PASS / FAIL

Build:
PASS / FAIL

Known Issues:
[number]

Critical:
[number]

High:
[number]

Medium:
[number]

Low:
[number]

Final Status:
CLEAN / NOT CLEAN
```

Do not report `CLEAN` when:

* a required route is missing;
* a major BRD requirement is broken;
* a critical/high defect remains;
* required FCM validation is incomplete but FCM is claimed implemented;
* a known fixable issue remains.

---

# 87. START NOW

Execute exactly:

```text
1. Read BRD.md completely.
2. Read Design_updated.md completely.
3. Read firebase_prerequisites completely.
4. Read AUDIT.md if present.
5. Inspect the entire existing project.
6. Derive the current route inventory.
7. Build the requirement/route/state traceability matrix.
8. Validate Firebase prerequisites.
9. Validate real Firebase connectivity.
10. Validate FCM capability.
11. Fix prerequisite issues where safely possible.
12. Establish the application foundation.
13. Implement core business domains.
14. Implement all required routes.
15. Implement simulated Razorpay.
16. Implement simulated Email.
17. Implement real Firebase FCM.
18. Run migrations.
19. Seed deterministic data.
20. Start the application.
21. Run automated tests.
22. Run browser tests.
23. Test actual FCM browser delivery where supported.
24. Test responsive/PWA behavior.
25. Fix all discovered issues.
26. Rebuild.
27. Rerun failed tests.
28. Run regression.
29. Re-audit BRD.
30. Re-audit Design_updated.md.
31. Update docs/requirements-traceability.md.
32. Update docs/progress.md.
33. Perform final build/tests.
34. Produce the final completion report.
```

# FINAL ARCHITECTURAL RULE

The final implementation must obey:

```text
BRD.md
    ↓
Business truth

Design_updated.md
    ↓
UI / route / interaction truth

firebase_prerequisites
    ↓
Firebase / FCM configuration truth

Razorpay
    ↓
SIMULATED

Email
    ↓
SIMULATED

Push Notification
    ↓
REAL FIREBASE FCM
```

The prototype must remain locally runnable without Docker and without real payment/email/OAuth services, while **FCM is intentionally a real external integration using the Firebase configuration supplied through `firebase_prerequisites`.**

Do not stop at code generation.

**Build → Run → Test → Debug → Fix → Retest → Regression → Reverify until clean.**
