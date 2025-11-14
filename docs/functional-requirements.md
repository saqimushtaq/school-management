### **Complete Functional Requirements List**

#### **Module 0: System Setup & Configuration (SET)**

*   **SET-01:** The system SHALL provide an interface for the Super Admin to define an Academic Session (e.g., '2024-2025') and set it as the 'Current Session'.
*   **SET-02:** The system SHALL allow the Super Admin to create, view, edit, and delete Classes (e.g., 'Grade 1', 'O-Levels 1').
*   **SET-03:** The system SHALL allow the Super Admin to create, view, edit, and delete Sections (e.g., 'A', 'B') and link them to a specific Class.
*   **SET-04:** The system SHALL allow the Super Admin to create, view, edit, and delete Subjects (e.g., 'Mathematics', 'Physics').
*   **SET-05:** The system SHALL allow the Super Admin to assign one or more Subjects to a specific Class.

#### **Module 1: Staff Management & Payroll (STF)**

*   **STF-01:** The system SHALL provide a form for the Admin to add a new staff member with details like Name, CNIC, Contact Number, Email, Joining Date, Designation, and Basic Salary.
*   **STF-02:** The system SHALL maintain a searchable and filterable list of all staff members.
*   **STF-03:** The system SHALL allow the Admin to edit the details of an existing staff member.
*   **STF-04:** The system SHALL allow the Admin to assign a Teacher to one or more Classes and Subjects.
*   **STF-05:** The system SHALL provide a payroll module for the Accountant role.
*   **STF-06:** The system SHALL allow the Accountant to generate monthly payslips for any staff member for a given month and year.
*   **STF-07:** The generated payslip SHALL be a downloadable PDF displaying the staff member's name, basic salary, and any predefined deductions.

#### **Module 2: Student Information System (SIS) (ADM)**

*   **ADM-01:** The system SHALL provide a form for the Admin to create a new student record.
*   **ADM-02:** The student creation form SHALL capture: Full Name, Date of Birth, B-Form Number, Gender, and a profile picture upload.
*   **ADM-03:** The student creation form SHALL capture Parent/Guardian information: Father's Name, Father's CNIC, Mother's Name, Primary Contact Number, and Secondary Contact Number.
*   **ADM-04:** Upon creation, the system SHALL automatically generate a unique Admission Number for the student.
*   **ADM-05:** The Admin SHALL be able to assign the new student to a specific Academic Session, Class, and Section.
*   **ADM-06:** The system SHALL maintain a searchable and filterable master list of all enrolled students.
*   **ADM-07:** The system SHALL provide a detailed profile view for each student, showing all their personal, academic, and fee information.
*   **ADM-08:** The system SHALL provide a feature to promote an entire class of students to the next grade and new academic session in a batch process.

#### **Module 3: Attendance Management (ATT)**

*   **ATT-01:** The system SHALL provide an interface for a logged-in Teacher to view a list of students assigned to their class for the current day.
*   **ATT-02:** The attendance interface SHALL allow the Teacher to mark attendance for each student as 'Present', 'Absent', or 'Leave'.
*   **ATT-03:** The system SHALL record the date, student ID, and attendance status for every entry.
*   **ATT-04:** The system SHALL prevent a Teacher from submitting attendance for a class that is not assigned to them.
*   **ATT-05:** **(Critical)** When a student is marked 'Absent', the system SHALL automatically trigger an SMS to the student's registered Primary Contact Number.
*   **ATT-06:** The SMS content SHALL be a template that includes the student's name and class.
*   **ATT-07:** The system SHALL provide reports for administrators to view daily, monthly, and yearly attendance statistics for any student or class.

#### **Module 4: Fee Management & Accounting (FEE)**

*   **FEE-01:** The system SHALL allow the Admin to define a standard fee structure for each class, with multiple fee types (e.g., Tuition, Lab, Sports).
*   **FEE-02:** The system SHALL have a Discount Management section.
*   **FEE-03:** The Admin SHALL be able to create, view, and delete discount types (e.g., 'Sibling Discount - 10%').
*   **FEE-04:** The Admin SHALL be able to apply a specific discount to one or more students' profiles.
*   **FEE-05:** The system SHALL provide a function to generate fee vouchers for all active students for a given month.
*   **FEE-06:** The generated fee voucher SHALL be a downloadable PDF containing the student's name, class, billing month, itemized fees, total amount, due date, and payment instructions.
*   **FEE-07:** The system SHALL automatically apply any pre-assigned discounts when generating a student's fee voucher.
*   **FEE-08:** The system SHALL provide an interface for the Accountant to search for a student's fee voucher by name or admission number.
*   **FEE-09:** The Accountant SHALL be able to mark a voucher as 'Paid' and record the payment date and method (from a dropdown: Cash, Bank Transfer, Online Payment).
*   **FEE-10:** The system SHALL provide a dashboard showing total fees generated, total collected, and total outstanding for a selected period.

#### **Module 5: Academics & Examinations (EXM)**

*   **EXM-01:** The system SHALL allow the Admin to create an Examination Term (e.g., 'Mid-Term Exam 2024') and link it to an Academic Session.
*   **EXM-02:** The system SHALL provide an interface for a Teacher to enter marks for their assigned subject and class for a specific exam term.
*   **EXM-03:** The system SHALL prevent a Teacher from entering marks for a subject or class they are not assigned to.
*   **EXM-04:** The system SHALL allow the Admin to define a grading scale (e.g., 90-100 = A+, 80-89 = A).
*   **EXM-05:** The system SHALL automatically calculate a student's percentage and grade based on the entered marks and the defined grading scale.
*   **EXM-06:** The system SHALL provide a feature to generate a report card as a PDF for any student.
*   **EXM-07:** The report card PDF SHALL include the school's logo, student's photo, personal details, a list of subjects with marks and grades, total marks, percentage, and a teacher's remarks section.
*   **EXM-08:** The system SHALL allow the Admin to generate report cards for an entire class in a batch operation.
*   **EXM-09:** All academic data (marks, grades) SHALL be stored against the student's record for a specific Academic Session.

#### **Module 6: Communication Hub (COM)**

*   **COM-01:** The system SHALL provide an interface for the Admin to compose a text message (SMS).
*   **COM-02:** The Admin SHALL be able to select recipients from the following options: All Parents, Parents of a specific Class, or Parents of a specific Section.
*   **COM-03:** The system SHALL allow the Admin to send the composed SMS to the selected group of recipients.
*   **COM-04:** The system SHALL maintain a log of all sent SMS messages, including the message content, recipient group, and timestamp.

#### **Module 7: User Access & Security (USR)**

*   **USR-01:** The system SHALL have a secure login page for all users.
*   **USR-02:** The system SHALL support the following user roles: Super Admin, Admin, Teacher, Accountant, Parent.
*   **USR-03:** The system SHALL enforce Role-Based Access Control (RBAC). For example, a 'Teacher' role cannot access the Fee Management module.
*   **USR-04:** The system SHALL provide a 'Forgot Password' feature that sends a password reset link to the user's registered email address.
*   **USR-05:** The Super Admin SHALL be able to create, edit, and disable user accounts for all roles.
*   **USR-06:** Parent users SHALL have a read-only portal to view their child's attendance, fee status, and download report cards.
