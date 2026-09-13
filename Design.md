# Design.md — BRD-Aligned UI/UX Screen Inventory with Firebase FCM Push Notifications

This document is the **BRD-aligned design and screen inventory** for the referral & reward platform. It also defines the Firebase Cloud Messaging (FCM) implementation of the BRD-required Push Notification channel. It preserves the existing functional screen inventory where it is already defined and adds/corrects UI requirements required by the current `BRD.md`.

**Source hierarchy for design decisions**
1. `BRD.md` is authoritative for business rules, lifecycle/status behavior, validation, financial mechanics, security, and non-functional requirements.
2. The existing screen/component details in this file are retained where they are already implemented in the current prototype.
3. A screen or control marked **BRD-required / gap** is required by the BRD but was not represented fully in the previous Design inventory; it must be implemented/reconciled without inventing unrelated requirements.
4. Where the BRD explicitly says a clarification is still pending client confirmation, the UI must not silently turn that draft clarification into a settled business rule.

---

## Conventions used across the whole app

- **Touch target**: every interactive element uses `min-h-11` (44px) — preserve as the minimum touch target on mobile.
- **Primary action button**: black background, white text, rounded-md.
- **Secondary/tertiary action**: bordered button (`border-gray-300`), no fill.
- **Error text**: red-600.
- **Warning / shortfall text**: amber-600.
- **Secondary/meta text**: zinc-500.
- **Success state**: use green success treatment.
- **Status color coding**:
  - Payment: `SUCCESS` = success/green; `RETRYING` = warning/amber; `FAILED` = error/red; `PENDING` / `INITIATED` = neutral/zinc.
  - Redemption: `PENDING` = neutral; `AWAITING_SHORTFALL_RESOLUTION` = warning; `APPROVED` = success; `REJECTED` = error; `CANCELLED` / `EXPIRED` = neutral/closed.
  - Plan: `ACTIVE`, `MATURED`, `DISCONTINUED`, `PARTIALLY_REDEEMED`, `REDEEMED` must remain distinguishable.
  - Commission: `ACCRUED`, `APPROVED`, `CREDITED`, `AVAILABLE FOR WITHDRAWAL`, `WITHDRAWN`, `NOT_ACCRUED`.
  - General Enquiry: `NEW`, `IN_PROGRESS`, `RESOLVED`.
- **Currency display**: all monetary values use INR / `₹` consistently. Reward Points are a **bare whole number** and are never shown with `₹` or any other currency symbol.
- **Balance terminology**:
  - `Redeemable Balance` and `Actual Redeemable Balance` are the **same underlying monetary INR balance**.
  - They are not two balances and must not appear as separate financial totals.
  - An active redemption reservation reduces **Available Margin only**; it does not reduce the Redeemable/Actual Redeemable Balance until financial processing after approval.
- **Available Margin**:
  - `Available Margin = Actual Redeemable Balance − Total Active Reserved/Committed Redemption Amount`
  - Reservations across all redemption categories must be reflected once only.
  - Do not deduct a pending redemption amount twice.
- **Reward Points**:
  - Non-monetary whole-number unit.
  - Course-only benefit; cannot be withdrawn and cannot be used for refund, donation, reinvestment, or gadgets.
  - Calculated from the **full applicable course fee**, even where previously earned Reward Points are used to settle part of the course fee.
  - Current client-requested rounding rule: **ceiling / round up** to the next whole point.
- **Monetary rounding**: monetary calculations use two decimal places and Round Half Up as defined by the BRD.
- **Forms**: preserve the existing Next.js Server Action + `useActionState` pattern. Every submit action must expose a pending label and disable duplicate submission.
- **Loading / success / failure / empty states**: every async list, form, approval, simulation, and calculation surface needs explicit loading, success, failure, and empty states where applicable.
- **No financial effect before redemption approval**: a pending/shortfall redemption may reserve margin, but must not create a redemption ledger debit or reduce Redeemable Balance until the approved financial processing step.
- **PWA/global chrome**: root layout mounts `OfflineBanner`, `SessionKeepAlive`, and `ServiceWorkerRegister`. The shell must reserve space for the persistent offline indicator and support PWA installation.
- **Safe-area**: viewport uses `viewportFit: cover`; fixed/persistent UI must respect `env(safe-area-inset-*)`.
- **Responsive layout**: all screens must work across mobile, tablet and desktop without horizontal overflow; dense admin tables must provide a usable responsive alternative (stacked rows/cards or controlled horizontal scrolling).
- **Accessibility**: every form field has an associated label, validation is visible without relying only on color, and status badges have text as well as visual treatment.
- **Public navigation**: base menu is `Home | Plans | Franchisee | Login | Register`; when content pages are configured/published, the proposed BRD default order is `Home | Plans | Franchisee | About Us | Contact Us | Login | Register`.
- **Authenticated shells do not inherit the public navigation automatically**. User/Admin navigation remains role-specific.
- **Push Notification channel**: the BRD-required Push Notification channel is implemented using **Firebase Cloud Messaging (FCM)** for supported web browsers. FCM is a delivery mechanism for existing business notification events, not a new business notification type.
- **Push notification persistence**: every applicable business notification continues to create the durable application `Notification` record independently of FCM delivery. FCM delivery failure, denied permission, unsupported browser capability, or absent device registration must not erase the in-app notification history.
- **FCM/PWA service-worker coordination**: the FCM messaging service-worker behavior must be integrated with the existing PWA service-worker architecture without competing workers claiming the same scope.
- **Firebase secrets**: browser-visible Firebase configuration may contain only the public client configuration required by the Firebase Web SDK. Firebase Admin SDK credentials, service-account private keys, VAPID private material and other server secrets are server-side only.

---

## 1. Public / Marketing Screens

### 1.1 Landing Page — `/`
**Access**: public.

- `SiteNav`.
- Hero: heading **"Invest, refer, and earn rewards"** and explanatory copy.
- `#plans` section: available ACTIVE plans; show plan name, tenure, payment frequency and relevant benefit/interest information.
- `#franchisee` section: available Franchisee Plans, one-time deductible price and mapped colleges.
- Closing CTA: **Register now** → `/register`.
- Public users must be able to browse plans and franchisee options before authentication.
- No financial or account-specific data may be exposed on this page.

### 1.2 Plans — `/plans`
**Access**: public.

- `SiteNav`.
- Card/list of all available `ACTIVE` plans.
- Per plan: name, tenure, payment frequency, interest calculation method summary, reward percentage where applicable, commission percentage where applicable.
- Action: **Subscribe** → `/plans/{planId}/subscribe`.
- Discontinued plans are not displayed as available for new enrolment.
- Empty state: **"No plans are available yet."**
- [BRD-required] Preserve a clear distinction between public plan configuration and an already enrolled user's frozen plan terms.

### 1.3 Franchisee — `/franchisee`
**Access**: public.

- `SiteNav`.
- List of available Franchisee Plans.
- Per plan: name, one-time deductible price, mapped colleges.
- No authenticated balance or redemption information is shown.
- Empty state where no Franchisee Plans are configured.

### 1.4 About Us — `/about` *(BRD-required / currently planned)*
**Access**: public and conditional.

- Route/menu entry exists only when valid/published About Us content is available.
- `SiteNav`.
- Render published sections in Admin-controlled order.
- Each section:
  - Optional **Heading**, max 150 characters.
  - Mandatory **Paragraph** in sanitized Rich Text, max 20,000 characters.
- Heading-only section is invalid and must never publish.
- Empty/unconfigured section is not rendered.
- Maximum 20 sections; server-side enforcement.
- Rich Text must be safely sanitized before public rendering.
- Public users see **published content only**, never an unpublished draft.

### 1.5 Contact Us — `/contact` *(BRD-required / currently planned)*
**Access**: public; submission can be anonymous or associated with an authenticated user.

- `SiteNav`.
- Show only contact-detail values actually configured by Admin; never render blank placeholders for missing values.
- Supported configured details include:
  - Address — max 1,000 chars.
  - Primary Phone — max 20 chars.
  - Primary Email — max 254 chars.
  - Website / URL — max 2,048 chars.
  - Social / other labelled links.
- General Enquiry form:
  - **Name** — required, max 150.
  - **Email** — required, max 254.
  - **Phone number** — optional, max 20.
  - **Message / content** — required, max 5,000.
  - Hidden **honeypot** field.
  - Adaptive CAPTCHA only when risk signals require it.
  - Submit: **Send enquiry**.
- Anti-spam behavior is server-side; do not expose throttling/suppression mechanics as a separate user workflow.
- On successful submission:
  - Mandatory on-screen confirmation.
  - Show enquiry reference/ID where available.
  - Optional submitter email confirmation may also be sent.
- Submission triggers:
  - persisted General Enquiry record;
  - audit event `GENERAL_ENQUIRY_SUBMITTED`;
  - Admin notification event `GENERAL_ENQUIRY_RECEIVED` through Email + Push channels.
- General Enquiry is **not** a redemption request and has **no financial impact**.

### 1.6 Public Content / Enquiry States
Use these visual states wherever applicable:

- Loading content.
- No published content / page unavailable because the page is not configured.
- General Enquiry validation error.
- CAPTCHA/risk challenge shown adaptively.
- Submit pending.
- Submission success with reference.
- Safe generic submission failure.
- Never leak internal exception details.

---

## 2. Authentication Screens

### 2.1 Login — `/login`
- Fields: **Email**, **Password**.
- Link: **Forgot password?** → `/forgot-password`.
- Submit: **Log in** → pending **"Logging in..."**.
- Error state displayed near form.
- Divider + **Continue with Google** → `/login/social/google`.
- Footer: **Register** → `/register`.
- [BRD-required] After 5 consecutive failed login attempts for an account, login is blocked for 15 minutes; show a clear but non-sensitive lockout message.
- [BRD-required] Successful and failed login attempts are recorded for security/login history.

### 2.2 Register — `/register`
- Reads `?ref=` query parameter and pre-fills referral code.
- Fields:
  - **Email**.
  - **Password** — minimum 8 chars with upper, lower, digit and special character.
  - **Referral code** — optional; when supplied must conform to the 8-character uppercase A–Z / 0–9 referral-code format.
- Submit: **Register** → pending **"Creating account..."**.
- Google registration preserves `?ref=`.
- Footer: **Already have an account? Log in**.
- [BRD-required] Mobile number is not an authentication field and is not required for verification.

### 2.3 Forgot Password — `/forgot-password`
- Field: **Email**.
- Submit: **Send reset code** → pending **"Sending..."**.
- Success/info and error states.
- Link after a code has been issued: **Reset your password** → `/reset-password`.
- Password-reset token/code is single-use and valid for 30 minutes.

### 2.4 Reset Password — `/reset-password`
- Fields:
  - **Email**.
  - **Reset code** — numeric, max 6 digits, one-time-code autocomplete.
  - **New password** — same complexity requirement.
- Submit: **Reset password** → pending **"Resetting..."**.
- States: invalid/expired code, max-attempt handling if applicable, success, generic failure.
- Footer: **Back to log in**.

### 2.5 Verify 2FA — `/verify-2fa`
**Access**: requires a pending 2FA session; otherwise redirect to `/login`.

- Heading: **"Verify your identity"**.
- Subtext explains that the 6-digit code was sent to the registered email.
- Verification form:
  - **Verification code** — 6 digits.
  - **Verify** → pending **"Verifying..."**.
- Resend action:
  - **Resend code** → pending **"Sending..."**.
- Security behavior represented in UI:
  - OTP validity: 5 minutes.
  - Maximum 5 verification attempts per OTP.
  - Maximum 3 resend requests within 15 minutes.
- [BRD requirement] Email OTP is the 2FA mechanism. Do not add hardware key, biometric, TOTP-app, FIDO2/YubiKey, or mobile-OTP authentication UI.

### 2.6 Simulated Google OAuth Consent — `/login/social/google`
- Heading: **"Sign in with Google"**.
- Subtext explains this is a simulated consent screen for the prototype.
- Field: **Google account email**.
- Hidden referral code preserved from `?ref=`.
- Submit: **Authorize** → pending **"Authorizing..."**.
- **Cancel** → `/login`.
- Existing matching account is linked instead of creating a duplicate account.
- New account is auto-verified by the simulated provider flow.
- Successful social authentication still goes through the applicable email-OTP 2FA gate.

---

## 3. User Dashboard

### 3.1 Dashboard Home — `/dashboard`
**Access**: authenticated.

Header navigation:
- **Browse Plans**
- **Notifications** (unread count where > 0)
- **Referrals**
- **Redeem**
- **Payments**
- **Account**
- **Log out**

Security/profile banner:
- If email is unverified: **"Your email is not verified yet."** + **Verify now** → `/dashboard/verify-email`.

Identity:
- `Logged in as {email} · Referral code: {activeCode}`.

Core metrics:
- **Total Invested**
- **Redeemable Balance**
- **Reward Points**
- **Referral Earnings**
- **Total Interest**
- **Active Plans**
- **Closed Plans**

Additional BRD metrics:
- **Next Deduction** — next scheduled instalment amount/date.
- **Transaction Graph** — visual transaction history/summary.

Plan summary:
- **Your Plans** list.
- Per plan show enough information to understand:
  - plan name;
  - status;
  - contributed/invested amount;
  - relevant maturity information;
  - scheduled payment information when applicable.
- Empty state: **"You haven't subscribed to any plan yet. Browse plans."**

Payout information:
- [BRD-required] Show the Admin-configured payout timing/frequency as informational content.
- Do not imply that the user is changing payout configuration here.
- Corresponding payout-time information may also be delivered through notifications.

### 3.2 Account — `/dashboard/account`
- Header **Account** + **Back to dashboard**.
- Account information:
  - Email.
  - Email verification status.
- **Mobile number** section:
  - Editable user profile/contact field.
  - Optional.
  - Validation: 7–15 digits with optional leading `+`.
  - Helper: "Not used for login, 2FA, or password reset — leave blank to remove."
  - Submit: **Save mobile number** → **"Saving..."**.
  - No mobile verification UI, no verified/unverified field, and no mobile OTP.
- **Change password**:
  - Current password.
  - New password.
  - Submit **Change password** → **"Updating..."**.
  - Google-only users see a no-local-password informational state.
- **AutoPay & Payment Details** [BRD-required / gap]:
  - Show payment/mandate details associated with ACTIVE or DISCONTINUED plans.
  - Show associated Plan, payment frequency, next scheduled payment, mandate/payment-method status.
  - Provide **Change payment method** where permitted.
  - Payment-method change must not alter plan amount, frequency, already paid principal, or accrued interest.
  - For a DISCONTINUED plan, payment-method change applies only to scheduled payments still due before maturity.
  - Supported payment methods visible in the product include card, UPI, Net banking and digital wallet where applicable.
  - For recurring card setup, present only a tokenized Razorpay mandate representation; never expose/store raw card credentials.
- **Recent Logins / Device Sessions**:
  - Recent successful and failed login attempts.
  - Login timestamp, IP where available, device/user-agent, result/status.
  - Authenticated session entries show login time, logout time where available, and session state.
  - Empty state: **"No login history yet."**

### 3.3 Notifications — `/dashboard/notifications`
- Header **Notifications** + **Back to dashboard**.
- Latest notifications list; unread state shown clearly.
- Notification types/events required by BRD:
  - `REGISTRATION`
  - `PAYMENT_SUCCESS`
  - `PAYMENT_FAILURE`
  - `PLAN_MATURITY`
  - `REFERRAL_EARNED`
  - `REFUND_PROCESSED`
  - `ADMIN_MESSAGE`
  - `UPCOMING_INSTALMENT_REMINDER`
  - Admin-only `GENERAL_ENQUIRY_RECEIVED`
- Upcoming instalment reminder content:
  - Plan
  - scheduled payment amount
  - scheduled payment date
  - reminder timing is Admin-configurable
- Notification channels remain the BRD-defined **Email** and **Push Notification** channels.
- **Push Notification is implemented with Firebase Cloud Messaging (FCM)** for supported web browsers.
- Persistent in-app Notification history is independent of FCM transport delivery.
- `GENERAL_ENQUIRY_RECEIVED` is an Admin-facing notification event and must not be exposed as a normal User notification event.
- Include a compact **Push Notifications** status area within this screen or the existing authenticated notification UI:
  - `Not supported` — current browser does not support the required Push/FCM capability.
  - `Permission not requested` — show **Enable notifications**.
  - `Permission denied` — explain that browser/device permission must be changed in browser settings; do not claim the application can override it.
  - `Permission granted / registration pending` — show registration/setup pending state.
  - `Enabled` — show that this browser/device is registered for Push Notifications.
  - `Registration failed` — show a non-blocking error with retry/recovery.
- Do not expose Firebase project secrets, service-account data, VAPID private material, or raw provider credentials.
- Notification click/deep-link targets must respect authentication and authorization.
- Empty state: **"You have no notifications yet."**

### 3.4 Payment History — `/dashboard/payments`
- Header **Payment History** + **Back to dashboard**.
- For each scheduled payment:
  - plan;
  - amount;
  - scheduled date;
  - current overall payment status;
  - retry information where applicable.
- Overall payment lifecycle:
  - `INITIATED → PENDING → SUCCESS / FAILED → RETRYING → SUCCESS / FAILED`
- `RETRYING`:
  - show next retry time;
  - show applicable grace-period end;
  - explain that the payment has not succeeded yet.
- `FAILED` within manual-payment window:
  - show **Pay now**;
  - manual payment flow must allow applicable manual methods.
- `FAILED` after manual-payment window:
  - **"Manual payment window has closed."**
- A failed recurring payment does **not** cancel or suspend the enrolled plan.
- At maturity, the user-facing maturity amount reflects actual successful payments and applicable pro-rata calculation.
- Successful payment receipt can be downloaded.
- Payment Success receipt/notification must be generated once per successful financial transaction; duplicate callbacks/retries must not create duplicates.
- Empty state: **"No payments yet."**

### 3.5 Referrals — `/dashboard/referrals`
- Header **Referrals** + **Back to dashboard**.
- Referral code card:
  - current active referral code;
  - shareable referral link;
  - **Regenerate / Rotate referral code** action.
- Regeneration UI must warn that:
  - old code becomes inactive for new registrations;
  - existing referral relationships remain unchanged.
- Referral code display format: exactly 8 uppercase letters/digits.
- Summary metrics:
  - Total Referral Commission Accrued.
  - Total Commission Approved.
  - Total Commission Credited.
  - Total Commission Withdrawn.
  - Total Pending Commission / applicable pending value.
  - Total commission earned.
  - Total upcoming commission from active plans where available.
- **People you referred**:
  - referred user;
  - referral status;
  - expiry date where applicable;
  - active/expired/cancelled relationship state.
- **Per-Referred-User Commission View**:
  - commission history across all plans for that referred user;
  - active and expired plan history;
  - upcoming eligible commission where available.
- **Plan-Level Commission Details**:
  - plan;
  - eligible successful-payment progress;
  - commission status;
  - commission amount.
- **Available for withdrawal**:
  - commission item and amount;
  - **Withdraw** action.
- Commission must be presented as part of the Unified Financial Ledger model; do not present a separate independent financial "commission ledger".
- Empty state: **"You haven't referred anyone yet. Share your referral code above."**

### 3.6 Verify Email — `/dashboard/verify-email`
**Access**: redirects to `/dashboard` if already verified.

- Heading **"Verify your email"**.
- Send-code action.
- 6-digit verification code field.
- **Verify email** → pending **"Verifying..."**.
- **Send verification code** → pending **"Sending..."**.
- Expired/invalid-code, success and error states.

### 3.7 Redeem Hub — `/dashboard/redeem`
- Header **Redeem** + **Back to dashboard**.
- Stat cards:
  - **Actual Redeemable Balance**
  - **Available Margin**
- Use these exact concepts; they are not separate monetary balances.
- `Available Margin` must reflect all active reservations across redemption categories.
- Redeem Options:
  1. Course → `/dashboard/redeem/course`
  2. Gadgets & Accessories → `/dashboard/redeem/gadgets`
  3. Refund → `/dashboard/redeem/refund`
  4. Reinvestment → `/dashboard/redeem/reinvestment`
  5. Donation → `/dashboard/redeem/donation`
  6. Franchisee → `/dashboard/redeem/franchisee`
- All redemption options ultimately create a request/enquiry for Admin handling.
- **Your Redemption Requests**:
  - show category/type;
  - requested amount;
  - reservation amount where applicable;
  - status;
  - shortfall where > 0;
  - created/updated context as available.
- Required lifecycle states:
  - `PENDING`
  - `AWAITING_SHORTFALL_RESOLUTION`
  - `APPROVED`
  - `REJECTED`
  - `CANCELLED`
  - `EXPIRED`
- **Cancel** is available only while the lifecycle permits cancellation (including PENDING/AWAITING_SHORTFALL_RESOLUTION in the existing flow).
- [BRD-required] Expanded request detail should show:
  - status timeline;
  - available margin/reservation context;
  - shortfall details;
  - Admin concern/message where one was raised;
  - offline shortfall-resolution evidence/status where submitted.
- Empty state: **"No redemption requests yet."**

### 3.8 Redeem — Course — `/dashboard/redeem/course`
- Header **Redeem for a Course** + **Back**.
- Show **Actual Redeemable Balance** and **Available Margin**.
- Course list:
  - course name;
  - university;
  - fee.
- For each course, show a calculation summary before submission:
  - Applicable Course Fee;
  - Reward Points Balance available;
  - Reward Points the user intends/automatically uses where applicable;
  - Redeemable Balance portion;
  - resulting post-redemption Reward Points Balance;
  - newly earned Reward Points based on the **full approved course fee**.
- If fee > Available Margin:
  - show **Shortfall Amount**;
  - explain that the request can still be submitted;
  - state that it will remain `AWAITING_SHORTFALL_RESOLUTION`.
- While shortfall is pending:
  - no partial financial settlement;
  - no redemption ledger debit;
  - no Redeemable Balance reduction attributable to the redemption.
- Request action: **Request**; pending state and inline error.
- Reward Points are never displayed as currency.

### 3.9 Redeem — Donation — `/dashboard/redeem/donation`
- Header **Donate** + **Back**.
- Show Actual Redeemable Balance and Available Margin.
- Fields:
  - **Amount** — ₹, number, min 1, step 0.01, required.
  - **Comments** — optional.
- Support partial or complete donation request.
- Submit: **Submit request**.
- Before approval: no balance deduction/ledger debit.
- After approval: Admin-selected/eligible donation recipient and applicable offline processing are recorded on the Admin side; financial settlement is then posted through the Unified Financial Ledger.

### 3.10 Redeem — Refund — `/dashboard/redeem/refund`
- Header **Request a Refund** + **Back**.
- Show Actual Redeemable Balance and Available Margin.
- Fields:
  - **Amount** — ₹, number, min 1, step 0.01, required.
  - **Comments** — optional.
- Support partial or complete refund requests.
- Submit: **Submit request**.
- Pending/shortfall/approved/rejected/cancelled/expired states must follow the common redemption lifecycle.

### 3.11 Redeem — Reinvestment — `/dashboard/redeem/reinvestment`
- Header **Reinvest Redeemable Balance** + **Back**.
- Show Actual Redeemable Balance and Available Margin.
- Fields:
  - **Amount** — ₹, number, min 1, step 0.01, required.
  - **Comments** — optional.
- [BRD-required] Reinvestment flow must include selecting a **new plan** for the amount being reinvested.
- Flow:
  - enter amount;
  - select eligible new plan;
  - review amount + target plan;
  - submit request;
  - Admin approval/financial processing;
  - applicable balance/ledger update;
  - new plan enrolment using the approved reinvestment amount.
- Reinvestment is optional; any remaining Redeemable Balance may remain outside the new plan.

### 3.12 Redeem — Franchisee Enquiry — `/dashboard/redeem/franchisee`
- Header **Franchisee Enquiry** + **Back**.
- Show Actual Redeemable Balance and Available Margin.
- List Franchisee Plans:
  - Plan Name;
  - One-Time Deductible Price.
- After selecting a plan, show only the colleges mapped to that Franchisee Plan.
- **Select a college** dropdown is required.
- Compare price against Available Margin.
- If price ≤ Available Margin: normal `PENDING` request.
- If price > Available Margin:
  - display Shortfall Amount;
  - allow submission;
  - move to `AWAITING_SHORTFALL_RESOLUTION`.
- No partial online settlement while shortfall is unresolved.
- Pending request remains linked to selected Franchisee Plan and College.
- Submit: **Submit enquiry**.
- Disabled/error state when no mapped college exists.

### 3.13 Redeem — Gadgets & Accessories — `/dashboard/redeem/gadgets`
- Header **Redeem for Gadgets & Accessories** + **Back**.
- Show Available Margin and note: **No GST or shipping cost applies**.
- Catalogue:
  - category;
  - item name;
  - price;
  - available stock = stock quantity − reserved quantity.
- Multi-select/cart behavior is required.
- Cart summary:
  - selected items;
  - quantity;
  - total requested amount;
  - Available Margin;
  - Shortfall Amount when applicable.
- If total ≤ Available Margin: submit normally.
- If total > Available Margin:
  - display Actual Redeemable Balance;
  - Available Margin;
  - Total Requested Redemption Amount;
  - Shortfall Amount;
  - allow submission into `AWAITING_SHORTFALL_RESOLUTION`.
- Inventory reservation:
  - reserve requested inventory while the request is active;
  - do not deduct stock until approval;
  - release reservation on cancellation/rejection/expiry.
- [BRD-required] Pending gadget request modification:
  - edited cart replaces the previous requested amount/reservation;
  - Available Margin and Shortfall are recalculated from the revised amount;
  - previous pending reservation must not be double-counted.
- Request action is disabled where item stock is unavailable; inline validation/error state is required.

---

## 4. Plan Subscription Flow

### 4.1 Subscribe to a Plan — `/plans/[planId]/subscribe`
**Access**: authenticated; inactive/missing plan is unavailable.

Selected-plan summary:
- Plan name.
- Tenure.
- Payment frequency.
- Configured interest method summary.
- Configured reward percentage where applicable.
- Any plan benefit/gain information supported by the BRD.
- Show contribution/investment amount and the relevant plan calculation context.

Contribution selection:
- **Choose an instalment amount** — one radio per preset amount configured by Admin.
- Payment frequency is driven by the plan configuration.

AutoPay setup:
- **Confirm & Set Up AutoPay** → pending **"Setting up AutoPay..."**.
- Prototype explicitly communicates that the Razorpay integration is simulated and no real charge occurs.
- Razorpay is the only gateway.
- Recurring card flow uses tokenized payment authorization; no raw card details are stored.
- The product must be able to represent Cards, UPI AutoPay and eMandate mandate states.
- After setup, show the resulting mandate/payment-method state and the next scheduled payment.

---

## 5. Admin Console

All Admin pages:
- require an authenticated ADMIN session;
- redirect unauthenticated users to `/login`;
- redirect non-ADMIN users to `/dashboard`.

Admin navigation should expose the BRD responsibilities without merging distinct domains:
- Users
- Plans
- Interest Methods
- Commissions
- AutoPay Simulator
- Redemptions
- General Enquiries
- Catalog
- Emails
- Audit Log
- Reports
- Settings
- Log out

### 5.1 Admin Dashboard — `/admin`
Stat cards / summaries:
- Total Users.
- Active Plans / Active Subscriptions as supported by the current dashboard model.
- Plans Configured.
- Pending Redemptions.
- Pending Franchisee Enquiries.
- **Pending General Enquiries** — separate from redemption/franchisee enquiries; count `NEW` + `IN_PROGRESS`.
- Revenue (successful Plan Payments).
- Referral Payout (Withdrawn).
- Failed Payments.
- Today's Payments (count + ₹ total).
- Pending Refunds.
- Upcoming Maturities.

Admin information/actions:
- Notification responsibility and notification event visibility.
- Push Notification delivery uses Firebase FCM for supported registered Admin browser/device instances.
- Admin notification records remain available through the application Notification model and existing Admin notification/audit surfaces even if FCM delivery fails.
- Payout information/configuration context.
- Links to detailed management screens.
- Do not merge General Enquiries into the financial redemption queue.

### 5.2 User Management — `/admin/users`
- Header **User Management** + **Back to admin**.
- Table:
  - Email
  - Mobile
  - Role
  - Referral Code
  - Referral Code state
  - Reward Points
  - Status (Locked/Active)
  - Joined
  - Actions
- Row actions:
  - **Lock / Unlock**.
  - **Activate code / Deactivate code** for USER role where supported by existing source.
  - **Configure referral commission** [BRD-required / gap] for applicable Referrers.
- Referral commission configuration:
  - `Recurring Commission` or `One-Time Commission`.
  - applicable commission percentage within 0.00–100.00%, up to two decimals.
- User detail/modal should not provide direct edit of Redeemable Balance or Reward Points, because the BRD says Admin shall not directly edit those financial/point balances.

### 5.3 Plan Management — `/admin/plans`
- Header **Plan Management** + **Back to admin**.
- Table:
  - Name
  - Tenure
  - Frequency
  - Interest Method
  - Reward %
  - Commission %
  - Subscribers
  - Status
  - Actions
- Create Plan:
  - **Plan name**
  - **Tenure (months)**
  - **Payment frequency**: Monthly / Lumpsum
  - **Preset amounts**
  - **Interest calculation method**
  - **Reward %** where plan-specific
  - **Referral commission %**
  - **Create plan**
- Configuration validation:
  - percentages 0.00–100.00%, up to two decimals;
  - monetary inputs displayed as INR where monetary.
- Existing Plan Terms rule:
  - changing plan configuration applies to new purchases/assignments only;
  - existing plans retain their captured terms.
- Plan discontinuation:
  - **Discontinue** is available for applicable active plans.
  - Discontinued plans are not available to new users.
  - Existing enrolled users continue under the original plan terms until original maturity.
  - At maturity: `DISCONTINUED → MATURED`.
  - No discontinuation-based haircut/penalty.
  - **Do not provide a Reactivate action that reverses DISCONTINUED back to ACTIVE for the lifecycle above.**

### 5.4 Interest Methods — `/admin/interest-methods` *(BRD-required / gap)*
Admin can create/manage supported Interest Calculation Methods.

Supported formula types:
- Simple Interest.
- Compound Interest.
- Custom Parameterized Formula.

Configuration UI:
- Formula type.
- Applicable interest rate.
- Tenure basis.
- Interest period (weekly/monthly/quarterly/annual and custom where permitted).
- Compound frequency for Compound Interest.
- Custom formula using only:
  - Principal
  - Rate
  - Tenure
  - Elapsed Days
  - `+`, `-`, `*`, `/`, parentheses.
- Custom formula validation:
  - max 100 characters;
  - max nesting depth 5;
  - unsupported variables/operators rejected;
  - division by zero rejected;
  - NaN/Infinity rejected;
  - negative result rejected;
  - invalid syntax rejected.
- Applying a method to a plan creates a snapshot including method/formula/parameters and configuration version.
- Existing plan calculations do not retroactively switch to a later method version.
- List view should expose Method ID and configuration/version information because every interest ledger entry must retain those references.

### 5.5 Commission Lifecycle — `/admin/commissions`
- Header **Commission Lifecycle** + **Back to admin**.
- Pending approval (`ACCRUED`):
  - amount;
  - type;
  - cycle position;
  - referrer;
  - referred user;
  - plan;
  - **Approve / Reject**.
- Approved (`APPROVED`):
  - show non-reversible warning;
  - **Credit to ledger**.
- Additional status/filter:
  - `CREDITED`
  - `AVAILABLE FOR WITHDRAWAL`
  - `WITHDRAWN`
  - `NOT_ACCRUED`
- `NOT_ACCRUED` is a terminal non-financial state with ₹0 and is not an unsuccessful "commission transaction".
- Recurring Commission UI must communicate:
  - commission after every four consecutive successful payments;
  - failed/late-after-grace payments do not count as successful commission-cycle payments;
  - cycle resets after each four-payment commission event.
- One-Time Commission:
  - generated only on the first eligible successful payment under the applicable plan.
- Commission calculation basis is the actual successfully received payment amount.
- Payout frequency:
  - weekly / monthly / quarterly / yearly;
  - default monthly;
  - payout timing does not change calculation/eligibility.
- Approved commission is non-reversible.

### 5.6 Razorpay AutoPay Simulator — `/admin/payments`
- Header **Razorpay AutoPay Simulator** + **Back to admin**.
- Card 1 — subscriptions due for charge:
  - outcome: `SUCCESS` / `FAILED`;
  - **Simulate invalid webhook signature** option for BRD Rule XXXVI.5;
  - **Run due AutoPay charges now**.
- Card 2 — automatic retries due:
  - same outcome/signature controls;
  - **Run due payment retries now**.
- Card 3 — active mandates:
  - user;
  - plan;
  - gateway mandate ID;
  - **Simulate expiry**;
  - **Simulate cancellation**.
- Card 4 — plan maturity:
  - plans due for maturity;
  - **Run plan maturity check now**;
  - one-time interest calculation + maturity transition/unlock.
- Recent simulated payments:
  - user;
  - plan;
  - amount;
  - gateway Order ID;
  - retry count;
  - status;
  - next retry / grace end;
  - manual payment window end.
- Payment simulation must respect gateway context validation and idempotency outcomes.

### 5.7 Redemption & Enquiry Approval Queue — `/admin/redemptions`
This screen handles **Redemption Requests and Franchisee Redemption Enquiries only**.

Top action:
- **Expire stale requests now**.

Redemption requests:
- User.
- Category.
- Requested amount.
- Reservation.
- Status.
- Shortfall.
- Shortfall verification state.
- Admin concern/message.
- **Approve / Reject**.

Shortfall verification UI:
- Show the original requested amount, Available Margin and Shortfall.
- Collect:
  - payment/reference/receipt number;
  - payment date;
  - amount;
  - payment method;
  - verification comments;
  - supporting proof upload where required.
- **Verify shortfall resolution** action.
- Verification does not itself create a redemption ledger debit.

Approval rules:
- `PENDING` may be approved according to the normal request flow.
- `AWAITING_SHORTFALL_RESOLUTION` may only proceed after successful Admin verification.
- Approval transitions into financial processing and ledger/balance update.
- Reject/cancel/expire releases applicable reservations and creates no redemption debit.

Franchisee enquiries:
- user;
- Franchisee Plan;
- selected College;
- requested/deductible amount;
- status;
- shortfall/verification state;
- Approve / Reject / Verify flow.

Admin concerns/messages:
- Admin can raise concerns and share messages against a redemption/enquiry.
- The user request detail surface must expose those messages.

### 5.8 General Enquiries — `/admin/enquiries/general` *(BRD-required / gap)*
This is a **separate domain** from Redemption Requests and Franchisee Enquiries.

Queue:
- Search.
- Filter.
- Sort/paging as needed for the queue.
- Fields:
  - Enquiry ID;
  - Name;
  - Email;
  - Phone where provided;
  - Message;
  - Status;
  - Created At;
  - Updated At;
  - Resolved At / Resolved By where present;
  - source;
  - Admin comment.

Allowed statuses:
- `NEW`
- `IN_PROGRESS`
- `RESOLVED`

Actions:
- **Open / View details**
- **Change status**
- **Add resolution note / admin comment**

Lifecycle:
- Submission creates `GENERAL_ENQUIRY_SUBMITTED` audit event.
- Admin receipt triggers `GENERAL_ENQUIRY_RECEIVED` notification.
- Status changes/resolution are audit logged.
- This record has **no** balance, reservation, margin, ledger, commission, interest, Reward Points or Plan-status effect.
- Do not display financial approval actions on this screen.

Empty state:
- **"No general enquiries yet."**

### 5.9 Catalog Management — `/admin/catalog`
**Universities & Courses**
- Create University.
- Create Course tied to exactly one University.
- University → many Courses.
- Display course name and fee.
- Empty states.

**Gadgets**
- Create Gadget.
- Category.
- Name.
- Price.
- Stock.
- Reserved.
- Adjust Stock.
- Available = stock − reserved.
- Do not add GST/tax or shipping cost to catalogue price.

**Colleges**
- Create College.
- List colleges.

**Franchisee Plans & College Mapping**
- Create Franchisee Plan.
- Plan Name.
- One-Time Deductible Price.
- Map/unmap colleges for each Franchisee Plan.
- Mapping control must make the selected college list deterministic for the user redemption flow.

### 5.10 Simulated Email Outbox — `/admin/emails`
- Header **Simulated Email Outbox** + **Back to admin**.
- Latest email list and selected detail.
- Clearly state: **No real emails are sent** in the prototype.
- Email remains a simulated provider/channel. **Push Notification is separate and is delivered through Firebase FCM.**
- Include event/template/status information sufficient to inspect:
  - Registration;
  - Payment Success;
  - Payment Failure;
  - Plan Maturity;
  - Referral Earned;
  - Refund Processed;
  - Admin Messages;
  - Upcoming Instalment Reminder;
  - General Enquiry submission confirmation where configured.
- Duplicate successful-payment callbacks must not produce duplicate success receipts/emails.

### 5.11 Audit Log — `/admin/audit-log`
- Header **Audit Log** + **Back to admin**.
- Event type.
- Actor email or `system`.
- Entity reference.
- Timestamp.
- JSON-style details block.
- Must include security-sensitive events:
  - login success/failure;
  - logout;
  - password change/reset;
  - 2FA verification/resend/security events;
  - session/token events;
  - account lockout.
- Must include financial/business events:
  - payment processing/duplicates/webhook verification;
  - interest calculation/version references;
  - commission lifecycle events;
  - redemption approval/rejection/cancellation/expiry;
  - offline shortfall verification;
  - inventory reservation/release/stock deduction.
- Rule XXXIX events:
  - `GENERAL_ENQUIRY_SUBMITTED`;
  - General Enquiry status/resolution;
  - About Us/Contact Us draft/edit/delete/reorder/publish.
- Empty state: **"No audit events yet."**

### 5.12 Reports & Exports — `/admin/reports`
- Header **Reports & Exports** + **Back to admin**.
- Download each report as **CSV**, **Excel**, or **PDF**.

Reports:
1. **User Report**
   - Email, Role, Referral Code, Referral Code Active, Email Verified, Reward Points Balance, Locked, Created At.
2. **Payment Report**
   - Payment ID, User Email, Plan, Amount, Status, Method, Scheduled Date, Actual Date, Retry Count.
3. **Interest Report**
   - Ledger Entry ID, User Email, Interest Amount, Balance Before, Balance After, Description, Transaction Date.
   - [BRD-required traceability] include the Interest Calculation Method ID and configuration/version ID where the report exposes calculation details.
4. **Referral Report**
   - Referral ID, Referrer Email, Referred Email, Referral Code Used, Status, Expiry Date, Latest Commission Status, Created At.
5. **Plan Report**
   - Plan ID, Plan Name, Tenure (Months), Payment Frequency, Commission %, Reward %, Status, Active Subscriptions, Total Subscriptions.
6. **Refund Report**
   - Request ID, User Email, Status, Requested Amount, Shortfall Amount, Created At, Updated At.
7. **Reward Report**
   - Request ID, User Email, Category, Status, Requested Amount, Reserved Amount, Created At, Expires At.
8. **Enquiry Report**
   - Supports existing Redemption/Franchisee enquiries and General Enquiries through an **Enquiry Type** reporting classification, e.g. `GENERAL`, `REDEMPTION`, `FRANCHISEE`.
   - General Enquiry data may include Enquiry ID, Name, Email, Phone, Message, Status, Admin Comment, Created At, Updated At, Resolved At, Resolved By, Source.
   - The report classification does **not** merge the underlying domain records or status models.
9. **Revenue Report**
   - Month, Successful Payments, Total Revenue.
10. **Audit Report**
   - Log ID, Actor Email, Event Type, Entity Ref, Details, Timestamp.

### 5.13 Site Settings & Website Content — `/admin/settings`
The Settings screen has two distinct behavior models:

#### A. Program / operational settings
Instant-save settings for applicable configurations, such as:
- referral validity period;
- OTP resend window / resend count / verification attempts;
- OTP/password-reset TTL where configurable;
- payment retry count/interval;
- payment grace period;
- manual payment window;
- redemption request expiry;
- shortfall verification period;
- commission cycle length;
- reward percentage configuration;
- Upcoming Instalment Reminder lead time;
- commission payout frequency;
- other BRD-defined configurable program parameters.

Settings controls must enforce BRD ranges and types, including:
- redemption expiry: default 7 days, range 1–30 calendar days;
- shortfall verification period: default 24h, range 1–72h;
- payment grace period: default 24h, range 0–168h;
- automatic retry count: default 3, range 1–5;
- retry interval: default 24h, range 1–72h;
- manual payment window: default 24h, range 1–72h;
- referral validity: positive whole days within the BRD-supported configured range;
- percentages: 0.00–100.00%, up to two decimals;
- reminder timing is Admin-configurable.

#### B. Referral configuration
Where referral validity can apply to selected referrers or all referrers:
- provide a global/default configuration control;
- provide selected-referrer assignment where supported by the implementation;
- existing referral relationships retain the validity period effective at relationship creation and do not silently change after later reconfiguration.

#### C. About Us content
- Repeatable section list.
- **Add section**.
- Optional Heading, max 150 chars.
- Mandatory Rich Text Paragraph, max 20,000 chars.
- Reorder sections.
- Remove section.
- Delete requires confirmation and is audit logged.
- Maximum 20 sections server-side.
- Heading-only section is invalid.
- 21st section cannot be saved or published.
- Empty/unconfigured content means public About Us remains hidden.

#### D. Contact Us content
- Optional generic contact detail fields:
  - Address;
  - Phone;
  - Email;
  - Website / URL;
  - Social/Other Contact Links.
- Unconfigured fields are omitted from the public page.
- The General Enquiry form is a fixed public feature when Contact Us is reachable; it is not a separately configurable financial feature.
- Contact detail limits follow Rule XXXIX.

#### E. Draft / Preview / Publish
For **About Us and Contact Us content only**:
- **Save Draft**
- **Preview**
- **Publish**
- Saving draft does not change public content.
- Preview does not publish.
- Only published content is public.
- Publishing replaces the active public version.
- Version/audit history is maintained through the existing audit model where supported.

The Draft/Preview/Publish behavior above does **not** change save/publish semantics for Plans, Interest, Payments, Referral configuration, Notifications, or other financial settings.

#### F. General Enquiry retention
- Default proposed retention: 24 months.
- Admin-configurable.
- Retention expiry/deletion/anonymization must be controlled, audited, PII-protective and reporting-aware.

---

## 6. Firebase Cloud Messaging (FCM) Push Notification Implementation — Cross-Cutting

Firebase Cloud Messaging (FCM) is the concrete implementation of the BRD's **Push Notification** delivery channel for supported web browsers. The BRD requires Push Notification; FCM is the selected implementation provider and does not introduce any new business notification event.
This section adds implementation requirements needed to make the existing BRD Notification System deliver real web push notifications. It does not introduce new business notification events.

**Important:** FCM is a real external service dependency. Unlike the prototype's simulated Razorpay/payment and simulated Email provider behavior, the Push Notification channel is intentionally implemented using Firebase FCM. The application must therefore remain functional for core in-app notification history even when Firebase configuration, browser support, or user permission prevents push delivery.

### 6.1 Notification Delivery Architecture

Use the existing provider-neutral notification/business-event model:

```text
Business Event
      ↓
Notification Service
      ├────────────→ Persistent In-App Notification Record
      │
      ├────────────→ Simulated / Existing Email Channel
      │
      └────────────→ Firebase FCM Push Provider
                           ↓
                    Active App Registrations
```

Rules:
- Do not create a second notification domain for FCM.
- Do not bypass the existing Notification Service/business-event generation.
- FCM is a transport/provider for the existing Push Notification channel.
- Existing in-app Notification records remain the durable notification history.
- Existing Email behavior remains unchanged unless the BRD explicitly requires a change.
- FCM delivery failure must not roll back a successfully created business notification merely because push delivery failed.

### 6.2 Web Client Firebase Messaging

Use the Firebase Web SDK appropriate for the project's current Firebase dependency version.

The Web Push/FCM client must run in a browser/security context supported by Firebase Web Messaging; deployed environments must use HTTPS. Local development may use a supported secure localhost development context.

The client implementation must:
- initialize Firebase Messaging only in browser-capable/client code;
- detect unsupported messaging/browser capabilities before registration;
- request browser notification permission only in a user-driven/contextual flow;
- register the application's FCM messaging instance using a root-scope messaging service worker;
- use the current supported Firebase web registration APIs for the installed SDK version;
- use one current, supported FCM registration-target strategy consistently. Prefer the current Firebase Installation ID (FID)-based registration/targeting APIs when supported by the installed Firebase SDK and server SDK. Do not use the deprecated legacy registration-token APIs for a new FID-based registration lifecycle, and do not mix targeting models for the same application instance; if the project is temporarily constrained to an older SDK, document that compatibility exception explicitly.
- use the project's Web Push VAPID public key as required by the installed Firebase Web SDK;
- upload the current supported FCM/Firebase installation identifier or registration target to the application's Next.js backend after successful registration;
- refresh/synchronize the registration identifier on application startup or when Firebase reports a registration change;
- notify the backend when the registration is unregistered or no longer active where the installed SDK exposes the applicable lifecycle callback;
- never send Firebase service-account credentials or private keys to the browser.

### 6.3 Firebase Messaging Service Worker

Provide the Firebase Messaging service-worker integration required by the selected Firebase Web SDK.

Requirements:
- service worker must be available from the scope required by Firebase Messaging;
- integrate safely with the application's existing PWA service worker architecture;
- do not create competing service workers that overwrite each other or claim the same scope incorrectly;
- background notifications must be handled by the FCM-compatible service-worker path;
- foreground notifications must be handled by the page/application messaging listener and existing in-app notification UI;
- notification click actions must open only safe application routes/deep links;
- do not place secrets in the service worker;
- do not cache authenticated/private API responses merely because FCM is enabled.

If the application already has a service worker, reconcile the Firebase Messaging requirement into the existing service-worker architecture rather than creating an incompatible second worker.

### 6.4 Server-Side Firebase Admin SDK

Use the Firebase Admin SDK from trusted Next.js server-side code only.

Requirements:
- initialize the Firebase Admin SDK once using secure server-side credentials;
- credentials must come from environment/configuration, never source code;
- the private service-account key must never be exposed through client bundles, API responses, logs, screenshots, reports or UI;
- use the Firebase Admin Messaging API to send Push Notification messages to the relevant registered application instances;
- use the current Firebase-supported targeting model for the installed SDK version;
- do not send from browser/client code using privileged credentials;
- do not use the Firebase console as the application's runtime notification mechanism.

### 6.5 Required Configuration

Add only the environment variables required by the actual Firebase setup.
A typical web configuration includes public client values such as:

```env
NEXT_PUBLIC_FIREBASE_API_KEY=...
NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN=...
NEXT_PUBLIC_FIREBASE_PROJECT_ID=...
NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET=...
NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID=...
NEXT_PUBLIC_FIREBASE_APP_ID=...
NEXT_PUBLIC_FIREBASE_VAPID_KEY=...
```

Trusted-server configuration must remain server-only. Prefer a secure server credential mechanism such as Application Default Credentials / `GOOGLE_APPLICATION_CREDENTIALS` where practical for local/server execution, or an equivalent secret-backed configuration supported by the project.

Example provider flag:

```env
PUSH_NOTIFICATION_PROVIDER=FIREBASE_FCM
```

Payment and Email provider configuration remains unchanged; FCM only replaces the Push Notification delivery implementation.

Do not expose server credentials through any `NEXT_PUBLIC_*` variable.

Document all required variables in `.env.example` without real secrets.

### 6.6 Firebase Project Requirements

The implementation requires a Firebase project configured for the web application and Firebase Cloud Messaging.

The setup documentation must cover, where applicable:
- Firebase project creation/selection;
- web app registration;
- Firebase Web configuration values;
- Web Push/VAPID configuration;
- enabling the Firebase Cloud Messaging API for server-side sending;
- creation/configuration of the trusted server credential used by the Firebase Admin SDK;
- local configuration of the credentials without committing secrets.

This is an explicit implementation dependency introduced by the FCM requirement. The rest of the application's business logic must remain independent of Firebase-specific APIs.

### 6.7 Push Registration Data Model

The application must maintain server-side records for active FCM-enabled application instances.

The exact model name may follow the existing schema, but it must support at least:
- internal registration record ID;
- User/Admin ID;
- Firebase Installation ID or current supported FCM registration identifier;
- active/inactive state;
- registration created timestamp;
- last-seen/last-synchronized timestamp;
- browser/platform metadata where useful for diagnostics;
- last error/provider status where needed for operational cleanup;
- unique constraint preventing duplicate active registrations for the same application instance/identifier.

Do not store unnecessary browser fingerprinting data.

Multiple active application instances may be associated with the same authenticated user/admin account.

### 6.8 Account Association and Security

Push registrations are associated with the authenticated application identity.

Requirements:
- a User registration must be associated only with that User account;
- an Admin registration must be associated only with that Admin account;
- a logged-out or switched-account browser instance must not continue receiving notifications for the previous account;
- re-authentication may re-associate the same application instance with the currently authenticated account after server-side validation;
- never trust a client-supplied User/Admin ID when registering a push identifier; derive the authenticated account from the server session;
- registration/unregistration operations require the appropriate authenticated session;
- do not allow one user to register a push identifier against another user through a crafted request.

### 6.9 Permission and Registration UX

Do not immediately force a browser permission prompt on every public page load.

Preferred UX:
- user reaches the authenticated dashboard or Notifications page;
- application explains why push notifications are useful;
- user selects **Enable notifications**;
- browser permission is requested;
- on grant, FCM registration is established and synchronized to the backend;
- UI changes to **Push notifications enabled**;
- on denial/blocking, provide a non-blocking recovery path and retain in-app notifications/email;
- unsupported browsers show a clear non-blocking state.

Do not make push permission a prerequisite for login, payment, redemption, enquiry submission, or other core business actions.

### 6.10 Notification Event to FCM Target Mapping

Use the BRD-defined events without renaming them.

| Event | Intended recipient | Push target |
|---|---|---|
| Registration | Newly registered User | User's active FCM registrations |
| Payment Success | User whose payment succeeded | User's active FCM registrations |
| Payment Failure | User whose payment failed | User's active FCM registrations |
| Plan Maturity | User whose plan matured | User's active FCM registrations |
| Referral Earned | Referrer | Referrer's active FCM registrations |
| Refund Processed | User receiving the refund result | User's active FCM registrations |
| Admin Message | Intended User recipient(s) | Their active FCM registrations |
| Upcoming Instalment Reminder | User with applicable ACTIVE Plan | User's active FCM registrations |
| `GENERAL_ENQUIRY_RECEIVED` | Admin | All active FCM registrations of the intended Admin recipient set; where no narrower recipient configuration exists, all active Admin registrations |

The BRD's specific channel/event rules remain authoritative. Do not invent a new event or silently remove an existing one because FCM is being introduced.

### 6.11 FCM Message Content

Each push message should contain, as applicable:
- short title;
- concise body;
- event type in data payload where needed;
- notification ID/business reference where needed;
- safe application route/deep-link where needed.

For Upcoming Instalment Reminder, the notification must include:
- applicable Plan;
- scheduled payment amount;
- scheduled payment date.

For General Enquiry Received, the Admin notification should identify that a new General Enquiry has been received and include a safe route to `/admin/enquiries/general` or the relevant enquiry detail page, subject to Admin authorization.

Do not include:
- passwords;
- OTPs;
- raw card details;
- session tokens;
- service-account credentials;
- VAPID private key material;
- unnecessary sensitive profile information.

Keep push payloads small and use the data payload only for values required to route/handle the notification.

### 6.12 Foreground vs Background Behavior

When the application is open and FCM delivers a message:
- handle the message in the page/client messaging listener;
- refresh or update the persistent in-app notification count/list without creating duplicate business notifications;
- provide an appropriate in-app visual indication where useful.

When the application is in the background:
- the Firebase Messaging service worker must display the push notification using the configured payload/handler;
- clicking the notification must open the appropriate application route;
- if the route is protected, the application must authenticate/authorize before showing private data.

Do not create two user-visible push notifications for the same event solely because both foreground and background handlers run.

### 6.13 Delivery Failure and Stale Registration Handling

The server-side FCM delivery layer must distinguish:
- successful send;
- temporary/transient send error;
- invalid/unregistered application instance;
- authentication/configuration error;
- unsupported or missing recipient registration.

When Firebase reports that an application registration is no longer valid/unregistered:
- mark/remove the stale registration from the application's active target set;
- do not repeatedly attempt delivery to known-invalid registrations;
- preserve the underlying in-app Notification record;
- record an operational/audit diagnostic where appropriate without leaking provider secrets.

Use bounded retry/backoff for transient server-side FCM errors where the execution model permits. Do not create an uncontrolled retry loop.

### 6.14 Idempotency / Duplicate Notification Protection

FCM must not create duplicate business notifications.

The business event remains the source of truth.

A repeated delivery attempt must not create a second:
- Notification record;
- Payment Success notification event;
- General Enquiry Received event;
- Upcoming Instalment Reminder business event;
- audit event for the original business action.

FCM delivery retries concern transport delivery only, not business-event creation.

### 6.15 Multi-Device Handling

A User/Admin may have multiple active application instances.

When sending a Push Notification:
- target every currently active valid registration associated with the intended recipient account, unless the existing implementation deliberately defines a narrower supported target model;
- do not send the same business event more than once per active registration unless a repeated transport attempt is required because delivery failed;
- clean up invalid registrations returned by Firebase.

### 6.16 Logout / Account Switching

When a user logs out or a browser session changes account:
- do not leave the old account's push registration active for the new account;
- disassociate/deactivate the old authenticated registration as appropriate;
- after the next authenticated login, register/re-associate the application instance for the current account only;
- verify this behavior in User → Admin switching/account-isolation test scenarios.

The exact technical approach may use the FCM/Firebase Installation lifecycle and an application-side registration record, but it must prevent cross-account push delivery.

### 6.17 Admin Notification Delivery

For Admin-facing events such as `GENERAL_ENQUIRY_RECEIVED`:
- persist the business event/notification record first;
- then dispatch the Push Notification through FCM to the intended Admin registrations;
- also send through the BRD-required Email channel for `GENERAL_ENQUIRY_RECEIVED`;
- do not create a separate Admin notification domain;
- expose the notification operational evidence through existing Admin Email/Audit/notification surfaces as appropriate.

### 6.18 Upcoming Instalment Reminder

The existing BRD Rule XXXVIII flow remains authoritative:

```text
ACTIVE PLAN
    ↓
NEXT SCHEDULED INSTALMENT
    ↓
ADMIN-CONFIGURED LEAD TIME
    ↓
EMAIL + FCM PUSH NOTIFICATION
    ↓
USER SEES PLAN + AMOUNT + DATE
```

The reminder must not be sent for a plan that is no longer ACTIVE at the time of evaluation.

Admin configuration of reminder lead time remains part of Site Settings.

### 6.19 General Enquiry Push Flow

```text
Public Contact Us
      ↓
General Enquiry Submitted
      ↓
Persist General Enquiry
      ↓
GENERAL_ENQUIRY_SUBMITTED Audit
      ↓
GENERAL_ENQUIRY_RECEIVED Notification Event
      ↓
Email + FCM Push to intended Admin recipients
      ↓
Admin opens /admin/enquiries/general
      ↓
Admin resolves / updates status
```

The General Enquiry itself remains financially isolated. FCM delivery has no financial effect.

### 6.20 Documentation and Secrets

Update project documentation with:
- Firebase setup steps;
- required public web configuration;
- VAPID public-key setup;
- trusted server credential setup;
- local `.env.local` configuration;
- service-worker requirements;
- FCM registration lifecycle;
- testing steps;
- troubleshooting invalid/stale registrations;
- how to replace or rotate Firebase credentials safely.

Never commit:
- Firebase service-account JSON files;
- private keys;
- VAPID private keys;
- production secrets.

### 6.21 Testing Requirements

Test FCM through the actual application flow, not by manually sending arbitrary Firebase console messages only.

Required scenarios include:

**User**
- enable push permission;
- successful FCM registration;
- notification reception while app is foreground;
- notification reception while app is background where browser support permits;
- notification click/deep-link;
- payment success push;
- payment failure push;
- plan maturity push;
- referral-earned push;
- refund-processed push;
- upcoming instalment reminder push;
- Admin Message push where an applicable existing workflow exists;
- denied permission;
- unsupported browser;
- registration failure;
- stale/invalid registration cleanup;
- multi-device delivery.

**Admin**
- FCM registration for an Admin account;
- `GENERAL_ENQUIRY_RECEIVED` push after a public General Enquiry submission;
- notification click opens the protected General Enquiry route;
- one General Enquiry submission does not generate duplicate Admin notifications;
- Admin account isolation.

**Cross-role**
```text
User / anonymous visitor submits General Enquiry
        ↓
General Enquiry persisted
        ↓
Audit event created
        ↓
FCM Push + Email sent to Admin
        ↓
Admin receives/open notification
        ↓
Admin opens General Enquiry
        ↓
Admin changes NEW → IN_PROGRESS → RESOLVED
        ↓
Audit history updated
```

Also test:

```text
Admin-configured Upcoming Instalment Reminder
        ↓
ACTIVE user plan reaches configured reminder window
        ↓
FCM Push + Email sent to User
        ↓
User sees reminder
        ↓
Notification opens Dashboard / relevant payment context
```

And:

```text
User subscription / payment event
        ↓
Business event
        ↓
Persistent Notification record
        ↓
FCM Push
        ↓
User receipt/dashboard update
        ↓
Admin Payment monitoring / Audit evidence
```

### 6.22 No Business Rule Changes

Implementing FCM must not change:
- notification event names;
- financial calculations;
- payment states;
- redemption states;
- commission states;
- plan states;
- General Enquiry lifecycle;
- About Us / Contact Us publication semantics.

FCM is only the delivery implementation for the Push Notification channel required by the BRD. Browser/OS permission and support limitations may prevent transport delivery on a particular device, but the application must still preserve the underlying notification record and must never claim push delivery succeeded when it did not.

---

### 6.23 No Separate FCM Management Route

- Do not create a separate `/admin/notifications` route solely because FCM is implemented.
- Admin notification responsibility remains represented through the existing Admin dashboard, existing notification records, simulated Email Outbox, Audit Log, and the Admin-facing General Enquiry workflow.
- FCM registration/health information may be shown contextually in the existing notification/admin surfaces only where needed for operation and diagnostics.
- User push permission/registration controls belong in the existing `/dashboard/notifications` experience or existing authenticated shell, not in a new route.


---

## 7. Shared Flow / State Requirements

### 7.1 Redemption lifecycle
For Course, Refund, Reinvestment, Donation, Gadgets & Accessories and Franchisee redemption, the common approval model is:

```text
REQUEST
  ↓
BALANCE / AVAILABLE MARGIN CHECK
  ↓
┌───────────────────────────────────────────────┐
│ Sufficient Margin                             │
│ → PENDING → ADMIN REVIEW → APPROVAL           │
│   → FINANCIAL PROCESSING → LEDGER → BALANCE   │
└───────────────────────────────────────────────┘

OR

┌───────────────────────────────────────────────┐
│ Shortfall                                     │
│ → AWAITING_SHORTFALL_RESOLUTION               │
│ → OFFLINE RESOLUTION                          │
│ → USER SUBMITS DETAILS/EVIDENCE               │
│ → ADMIN VERIFICATION                           │
│ → APPROVAL                                     │
│ → FINANCIAL PROCESSING → LEDGER → BALANCE      │
└───────────────────────────────────────────────┘
```

Before approval:
- Redeemable/Actual Redeemable Balance unchanged.
- Reservations can reduce Available Margin.
- No final redemption ledger debit.
- No financial settlement from the shortfall-resolution submission itself.

Cancellation/rejection/expiry:
- release reservations;
- no redemption debit unless an actual prior financial transaction exists under the applicable rule.

### 7.2 Full vs Partial Redemption
For a matured plan:
- Partial redemption:
  - balance before > approved redemption;
  - balance after = balance before − approved redemption;
  - Plan status may move `MATURED → PARTIALLY_REDEEMED`.
- Full redemption:
  - approved redemption equals available redeemable balance;
  - resulting balance = ₹0.00;
  - Plan status `MATURED → REDEEMED`.

The UI must not infer that a pending request has reduced the balance.

### 7.3 Concurrent reservations
Across Course, Gadgets, Franchisee, Refund, Reinvestment and Donation:
- show current Available Margin using all active reservations;
- prevent a new request from collectively exceeding Actual Redeemable Balance;
- after approval, balance changes and Available Margin recalculates;
- after cancel/reject/expire, reservation release recalculates Available Margin.

### 7.4 Gadget inventory reservation
- Submission reserves the requested inventory quantity.
- Pending modifications replace the previous requested amount/reservation.
- Approval converts the reservation into stock deduction.
- Cancel/reject/expire releases the reservation.

### 7.5 Payment retry / manual fallback
```text
Scheduled Payment
      ↓
INITIATED
      ↓
PENDING
      ↓
SUCCESS
```

Failure path:
```text
FAILED ATTEMPT
      ↓
RETRYING
      ↓
success → SUCCESS
      OR
retry exhausted / grace expired / non-retriable
      ↓
FAILED
      ↓
MANUAL PAYMENT WINDOW
      ↓
manual success → SUCCESS for scheduled instalment
manual window closed → FAILED final state
```

A payment failure must never cancel/suspend the enrolled plan.

### 7.6 Commission lifecycle
```text
Eligible Payment
      ↓
ACCRUED
      ↓
APPROVED
      ↓
CREDITED
      ↓
AVAILABLE FOR WITHDRAWAL
      ↓
WITHDRAWN
```

Non-accrual:
```text
Eligibility Evaluation
      ↓
NOT_ACCRUED (₹0, terminal, non-financial)
```

Approved commission is non-reversible.

### 7.7 Interest configuration versioning
```text
ADMIN CREATES / UPDATES METHOD
        ↓
VERSIONED CONFIGURATION
        ↓
ASSIGN TO PLAN
        ↓
SNAPSHOT FORMULA + PARAMETERS + VERSION
        ↓
CALCULATE INTEREST
        ↓
UNIFIED LEDGER ENTRY
        ↓
STORE METHOD ID + VERSION ID
```

Existing plans continue to use their snapshotted configuration.

### 7.8 Public General Enquiry
```text
PUBLIC CONTACT US
      ↓
VALIDATE + ANTI-SPAM
      ↓
GENERAL ENQUIRY CREATED
      ↓
GENERAL_ENQUIRY_SUBMITTED AUDIT
      ↓
GENERAL_ENQUIRY_RECEIVED ADMIN NOTIFICATION
      ↓
NEW
      ↓
IN_PROGRESS
      ↓
RESOLVED
```

This flow has no financial effect.

---

## 8. Screen Map (BRD-aligned navigation graph)

```text
/  ──────────────┬─ /plans ────────────────┬─ /plans/[id]/subscribe (auth) ─ /dashboard
                  ├─ /franchisee           │
                  ├─ /about (conditional)  │
                  ├─ /contact (conditional)│
                  ├─ /login ──┬─ /forgot-password ─ /reset-password
                  │           ├─ /login/social/google
                  │           └─ /verify-2fa ─ /dashboard | /admin
                  └─ /register ─ /login/social/google ─ /verify-2fa

/dashboard ───────┬─ /dashboard/account
                  ├─ /dashboard/notifications
                  ├─ /dashboard/payments
                  ├─ /dashboard/referrals
                  ├─ /dashboard/verify-email
                  └─ /dashboard/redeem ─┬─ /course
                                         ├─ /gadgets
                                         ├─ /refund
                                         ├─ /reinvestment
                                         ├─ /donation
                                         └─ /franchisee

/admin ───────────┬─ /admin/users
                   ├─ /admin/plans
                   ├─ /admin/interest-methods       [BRD-required / gap]
                   ├─ /admin/commissions
                   ├─ /admin/payments
                   ├─ /admin/redemptions
                   ├─ /admin/enquiries/general     [BRD-required / gap]
                   ├─ /admin/catalog
                   ├─ /admin/emails
                   ├─ /admin/audit-log
                   ├─ /admin/reports
                   └─ /admin/settings
```

### Route / capability notes
- `/admin/interest-methods` and `/admin/enquiries/general` are added because the BRD requires capabilities not represented as complete dedicated screens in the previous Design inventory.
- Admin notification management is a BRD responsibility, but the BRD does not prescribe a separate notification-management route; notification events/configuration must therefore be represented through the Admin dashboard/settings and the existing Email/Audit surfaces without inventing an unsupported management workflow.
- About Us / Contact Us remain conditional public pages.
- User/Admin authenticated shells do not automatically inherit public navigation.

---

## 9. BRD Traceability Checklist

The design must visibly support or provide a UI surface for the following BRD areas:

| BRD area | Design coverage |
|---|---|
| Plans, tenure, payment frequency, interest and reward configuration | Public Plans, Subscribe, Admin Plans, Interest Methods |
| Plan discontinuation and maturity | Admin Plans, Dashboard plan states, AutoPay Simulator |
| Existing Plan Terms immutability | Admin Plans / Interest Methods |
| Payments, receipts and methods | Subscribe, Account, Payment History, AutoPay Simulator, Email Outbox |
| Failed payment retry/grace/manual fallback | Payment History, AutoPay Simulator, Settings |
| Duplicate payment / webhook idempotency | AutoPay Simulator + visible result/audit states |
| Referral code / relationship | Register, Referrals, User Management |
| Recurring / One-Time commission | Referrals, User Management, Commission Lifecycle |
| Commission non-reversal | Commission Lifecycle |
| Referral validity / cancellation | Settings, Referrals, User Management/Audit |
| Redeemable Balance / Actual Redeemable Balance | Dashboard, Redeem Hub, redemption screens |
| Available Margin and reservations | Redeem Hub + every redemption flow |
| Shortfall resolution / offline verification | User redemption detail + Admin Redemption Queue |
| Full/partial redemption | Redeem Hub / category flows / request states |
| Reward Points on courses | Course redemption UI |
| Reward Points ceiling rounding | Global conventions + Course UI |
| Gadget inventory reservation | Gadget redemption + Catalog + Admin queue |
| Franchisee Plan / College mapping | Public Franchisee, Franchisee redemption, Catalog |
| Unified Financial Ledger | Admin approval/commission/interest/payment/redemption result views |
| General Enquiry | Contact Us + Admin General Enquiries + Reports + Audit + Notifications |
| About Us / Contact Us Draft/Preview/Publish | Public pages + Admin Settings |
| Upcoming Instalment Reminder | Dashboard Next Deduction + Notifications + Settings + Email Outbox |
| Firebase FCM Push Notification channel | Notifications screen + authenticated notification UI + FCM service worker + server-side FCM provider + Push registration lifecycle |
| FCM delivery for General Enquiry | Contact Us submission + Admin notification + FCM Push + Admin General Enquiries + Audit |
| FCM delivery for Upcoming Instalment Reminder | User Notifications + Next Deduction + Admin-configurable reminder timing + FCM Push + Email |
| FCM permission/registration states | Notifications UI + PWA/service-worker integration + server-side Push registration records |
| Student Dashboard metrics | Dashboard Home |
| Admin Dashboard metrics | Admin Dashboard |
| Reports and exports | Admin Reports |
| Security controls | Authentication, Account, Audit Log, session/login states |
| PWA/responsive/accessibility | Global conventions |
| Performance / scalability acceptance implications | Avoid blocking UI, show async/pending states, preserve responsive/PWA design |

---

## 10. Implementation Constraints for Designers / Claude

- Do not add new business rules that are not in `BRD.md`.
- Do not silently convert a **Drafted Clarification — pending client confirmation** into a client-approved requirement.
- Do not show Reward Points with a currency symbol.
- Do not represent Redeemable Balance and Actual Redeemable Balance as two balances.
- Do not deduct a pending redemption from Available Margin twice.
- Do not display an online financial debit before Admin approval where the BRD prohibits it.
- Do not provide a "Reactivate" path that reverses a DISCONTINUED plan lifecycle contrary to Rule III.
- Do not create a separate commission ledger UI; commissions belong to the Unified Financial Ledger.
- Do not merge General Enquiries with Redemption/Franchisee Enquiries even if they are grouped under one reporting/export mechanism.
- Do not introduce mobile-number verification or mobile OTP.
- Do not introduce hardware/security-key/biometric/TOTP authentication UI.
- Do not expose raw card/payment credentials.
- Do not treat failed recurring payments as Plan cancellation.
- Do not treat offline shortfall resolution itself as a redemption ledger debit.
- Preserve simulated integrations for Razorpay AutoPay and Email. Implement the BRD Push Notification channel using Firebase FCM as specified in Section 6. FCM is the only external provider introduced by this notification implementation.
- Treat Firebase FCM as a real runtime integration dependency for Push Notifications; do not silently fall back to a fake push transport while claiming FCM is implemented.
- Keep the implementation PWA-friendly and stable across Android and iPhone browsers as well as tablet/desktop.

---

## 11. Existing-vs-BRD-Gap Summary

The previous Design inventory already covered the main public, authentication, user dashboard, redemption, subscription, Admin catalog/payment/email/audit/report/settings surfaces. The BRD reconciliation above additionally requires or corrects:

1. A dedicated **Interest Methods** management surface with formula validation/versioning.
2. A dedicated **General Enquiries** Admin workflow separated from Redemption/Franchisee enquiries.
3. **Next Deduction**, **Transaction Graph**, and **Payout Information** on the user dashboard.
4. User-side **AutoPay/payment-method management**.
5. Full **Referral/Commission views**, including per-referred-user, plan-level, upcoming commission and code rotation.
6. Complete **Redemption Request lifecycle** visibility, shortfall verification evidence, Admin messages and expiry states.
7. Course **Reward Points adjustment/earning calculation** based on the full course fee.
8. Reinvestment **target-plan selection**.
9. Gadget **multi-item cart, reservation and pending-request modification** behavior.
10. Correct **DISCONTINUED → MATURED** lifecycle without a Reactivate reversal path.
11. Complete **General Enquiry Draft/Preview/Publish/content management**, status flow, audit and financial isolation.
12. Explicit **Upcoming Instalment Reminder** behavior.
13. BRD-defined **security state constraints** for login lockout, email OTP 2FA, password reset, JWT/session and login history.
14. Firebase **FCM Push Notification implementation** for all applicable BRD notification events, including device/browser registration, permission states, service-worker handling, server-side delivery, failure handling and General Enquiry/Admin notification delivery.
15. Report changes for **Interest Method/version traceability** and **General Enquiry Enquiry Type** classification.

These additions are intended to make the Design document an implementation-ready UI/UX reference aligned to the attached BRD while preserving the existing prototype's functional structure.
