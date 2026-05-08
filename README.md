# Project Documentation

## Final Implementation Status (May 4, 2026)

### User Story Completion

All project user stories (US-01 to US-27) are implemented in the codebase, including:

- Student and tutor authentication, profile creation/editing, search, booking, scheduling, session management.
- Session history, cancel/reschedule, ratings and reviews, in-app notifications, and credit-based payment flow.
- Academic advisor module: advisor role onboarding/login, platform statistics, tutor rating overview, frequent-request student identification, and review flagging/moderation workflows.

### Implementation Notes

- In-app notification feed is stored in Firestore (`notifications` collection).
- Moderation flags are stored in Firestore (`flags` collection), and related sessions are marked as flagged.
- Wallet credit transfers use Firestore transaction logic to ensure atomic updates.

### Validation Status

- IDE diagnostics: **PASS** (no Java/XML errors reported in workspace diagnostics).
- Gradle unit tests: **Blocked in this environment** due to missing Java runtime configuration (`JAVA_HOME` and `java` not available in PATH).

---

## Table of Contents
- [Team Information](#team-information)
- [Meeting Minutes](#meeting-minutes)
  - [Meeting - Feb 20, 2026](#meeting---feb-20-2026)
  - [Meeting - Feb 28, 2026](#meeting---feb-28-2026)
  - [Meeting - Mar 8, 2026](#meeting---mar-8-2026)
  - [Meeting - Mar 18, 2026](#meeting---mar-18-2026)
  - [Meeting - Apr 15, 2026](#meeting---apr-15-2026)
- [SCRUM Meetings](#scrum-meetings)
  - [SCRUM - Mar 23, 2026](#scrum---mar-23-2026)
  - [SCRUM - Mar 30, 2026](#scrum---mar-30-2026)
  - [SCRUM - Apr 6, 2026](#scrum---apr-6-2026)
  - [SCRUM - Apr 20, 2026](#scrum---apr-20-2026)
  - [SCRUM - Apr 27, 2026](#scrum---apr-27-2026)
  - [SCRUM - May 1, 2026](#scrum---may-1-2026)
- [Product Backlog](#product-backlog)
  - [Product Backlog - Project Part 2](#product-backlog---project-part-2)
  - [Product Backlog - Project Part 3](#product-backlog---project-part-3)
  - [Product Backlog - Project Part 4](#product-backlog---project-part-4)
  - [Kanban Board - Phase 2](#kanban-board---phase-2)
  - [Kanban Board - Phase 4](#kanban-board---phase-4)
- [User Interface Mockups](#user-interface-mockups)
  - [UI Mockups - Project Part 2](#ui-mockups---project-part-2)
  - [Storyboard - Project Part 2](#storyboard---project-part-2)
  - [Storyboard - Project Part 3](#storyboard---project-part-3)
- [CRC Cards](#crc-cards)
  - [Cards - Project Part 2](#cards---project-part-2)
  - [Cards - Project Part 3](#cards---project-part-3)
- [UML Class Diagram](#uml-class-diagram)
  
---

## Team Information
- **Team Name:** divide

Name | Rollnumber | GitHub ID |
| :----------- | :-----------: | :----------- |
| Muhammad Sohaf Khan | 26100138 | MuhammadKhan79 |
| Zaviyar Zahid | 27100381 | zaviyar-zahid |
| Marjan Dawlatmand | 26100408 | maroj-dm123 |
| Ahmed Ibrahim Khizar Hayat | 27100101 | ebraheem276 |
| Monish Kumar Kella | 27100459 | moonyahah |

---

## Meeting Minutes

### Meeting - Feb 20, 2026

#### Date:
Friday, 20th Feb 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [ ] Zaviyar Zahid
* [ ] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Takeaways:
- Make sure to document meeting minutes in every meeting from now on.
- Make sure assignments are divided evenly, and all members contribute to every part of the assignment (readme, code, design, etc.)

#### Prepared Questions:
- What day/times will the meetings be held on? Communicated on slack.

#### General Notes:
- Meeting with the TA every week.
- Update on progress/ask questions.

---

### Meeting - Feb 28, 2026

#### Date:
Saturday, 28th Feb 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [x] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Takeaways:
- Discuss project details with the TA.

#### General Notes:
- Divide the workload evenly.

#### Action Items:
- Add TA/Safa/Instructors to the GitHub repository.
- Update README to keep track of meeting minutes.
- Begin working on part 2 of the project timely.

---

### Meeting - Mar 8, 2026

#### Date:
Sunday, 8th Mar 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [ ] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Takeaways:
- User Stories are fine, as long as we feel they cover everything we feel needs to be implemented.
- UI screens are fine too, just need to be remade in Figma.

#### Prepared Questions:
- Are 26 User Stories too much? Should we cut them down?
- We currently have screens produced through a different program? Is it necessary to make in Figma?
- Do the screens look up to par for the assignment?

#### General Notes:
- The GitHub Repository requests all expired so no one was added yet.
- Decide on a set time for meetings.

#### Action Items:
- Resend invite to the repository.
- Remake the screens in Figma.
- Make the storyboard.
- Create and add the CRC cards.

---

### Meeting - Mar 18, 2026

#### Date:
Wednesday, 18th Mar 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [x] Marjan Dawlatmand
* [ ] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Takeaways:
- Project Part 2 has been graded.
- All the requirements have been met.

#### Prepared Questions:
- How to tackle phase 3?
- Should scrum meetings be noted as meeting minutes have been?

#### General Notes:
- Record scrum meetings as well, the more documentation the better.

#### Action Items:
- Start working oh Phase 3 as early as possible.

---

### Meeting - Apr 15, 2026

#### Date:
Wednesday, 15th Apr 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [x] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Takeaways:
- Phase 3 criteria met.

#### Prepared Questions:
- Is it okay if we have to cut some features because we started with so many in the backlog?
- What can we do to set our project apart, "go that extra mile"?

#### General Notes:
- Review of Phase 3 of the Project
- Midpoint evaluation

#### Action Items:
- Start on Phase 4
- Fix javadocs

---

## SCRUM Meetings

### SCRUM - Mar 23, 2026

#### Date:
Monday, 23rd Mar 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [x] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Tasks to complete:
ID | User Story
| :-----------: | :----------- |
| US-01 | As a student, I want to create an account so that I can access tutoring services. |
| US-02 | As a tutor, I want to create a tutor profile with subjects and expertise so students can find me. |
| US-03 | As a student, I want to log in to my account so that I can access my student dashboard. |
| US-07 | As a tutor, I want to log in to my account so that I can access my tutoring dashboard. |

---

### SCRUM - Mar 30, 2026

#### Date:
Monday, 30th Mar 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [x] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Tasks to complete:
ID | User Story
| :-----------: | :----------- |
| US-04 | As a student, I want to search tutors by subject or course so that I can find help for my classes. |
| US-05 | As a student, I want to view tutor profiles and qualifications so that I can choose a suitable tutor. |
| US-06 | As a student, I want to request a tutoring session so that I can receive help from a tutor. |
| US-08 | As a tutor, I want to accept or decline tutoring requests so that I can manage my workload. |
| US-13 | As a tutor, I want to view all my tutoring requests so that I can manage incoming sessions. |

---

### SCRUM - Apr 6, 2026

#### Date:
Monday, 6th Apr 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [x] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Tasks to complete:
- Update CRC cards
- Update Storyboards
- Create UML class diagrams
  
ID | User Story
| :-----------: | :----------- |
| US-09 | As a student, I want to schedule a tutoring session based on tutor availability so we meet easily. |
| US-10 | As a student, I want to see my upcoming sessions so that I can keep track of my tutoring schedule. |

---

### SCRUM - Apr 20, 2026

#### Date:
Monday, 20th Apr 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [ ] Marjan Dawlatmand
* [ ] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Tasks to complete:
- Start on Phase 4
- Update and improve aspects of the previous phase that were rushed
  
ID | User Story
| :-----------: | :----------- |
| US-11 | As a student, I want to view my session history so that I can track my academic progress. |
| US-12 | As a student, I want to rate and review tutors after a session so that others know the tutor’s quality. |

---

### SCRUM - Apr 27, 2026

#### Date:
Monday, 27th Apr 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [ ] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [ ] Monish Kumar Kella

#### Tasks to complete:
ID | User Story
| :-----------: | :----------- |
| US-15 | As a tutor, I want to set my availability schedule so students can book sessions during free time. |
| US-16 | As a student, I want to cancel a booked tutoring session so that I can reschedule if needed. |
| US-17 | As a tutor, I want to cancel or reschedule sessions so that I can handle schedule conflicts. |
| US-20 | As a student, I want to edit my profile information so my account details stay updated. |
| US-21 | As a tutor, I want to edit my tutor profile and subjects so I can update my expertise. |

---

### SCRUM - May 1, 2026

#### Date:
Friday, 1st May 2026

#### Attendance:
* [x] Muhammad Sohaf Khan
* [x] Zaviyar Zahid
* [x] Marjan Dawlatmand
* [x] Ahmed Ibrahim Khizar Hayat
* [x] Monish Kumar Kella

#### Tasks to complete:
- Update CRC cards
- Update Storyboards
- Create UML class diagrams
- Wrap up final touches on the project
  
ID | User Story
| :-----------: | :----------- |
| US-22 | As an academic advisor, I want to create an account to oversee and moderate the platform and tutors. |
| US-23 | As an academic advisor, I want to view tutoring session statistics so that I can monitor usage. |
| US-24 | As an academic advisor, I want to view tutor ratings and feedback so that I can evaluate tutors. |
| US-25 | As an academic advisor, I want to identify students frequently requesting tutoring for support. |
| US-26 | As an academic advisor, I want to flag inappropriate reviews or tutor behavior to maintain quality. |
| US-27 | As a user, I want to able to log out of my account after I finish using it. |

---

## Product Backlog:

### Product Backlog - Project Part 2
ID | User Story | Story Points | Risk Level | Half-way Release | Status
| :-----------: | :----------- | :-----------: | :-----------: | :-----------: | :-----------: |
| US-01 | As a student, I want to create an account so that I can access tutoring services. | 2 | Low | Yes | Not Started |
| US-02 | As a tutor, I want to create a tutor profile with subjects and expertise so students can find me. | 3 | Low | Yes | Not Started |
| US-03 | As a student, I want to log in to my account so that I can access my student dashboard. | 2 | Low | Yes | Not Started |
| US-04 | As a student, I want to search tutors by subject or course so that I can find help for my classes. | 3 | Low | Yes | Not Started |
| US-05 | As a student, I want to view tutor profiles and qualifications so that I can choose a suitable tutor. | 3 | Low | Yes | Not Started |
| US-06 | As a student, I want to request a tutoring session so that I can receive help from a tutor. | 5 | Medium | Yes | Not Started |
| US-07 | As a tutor, I want to log in to my account so that I can access my tutoring dashboard. | 2 | Low | Yes | Not Started |
| US-08 | As a tutor, I want to accept or decline tutoring requests so that I can manage my workload. | 3 | Medium | Yes | Not Started |
| US-09 | As a student, I want to schedule a tutoring session based on tutor availability so we meet easily. | 5 | Medium | Yes | Not Started |
| US-10 | As a student, I want to see my upcoming sessions so that I can keep track of my tutoring schedule. | 2 | Low | Yes | Not Started |
| US-11 | As a student, I want to view my session history so that I can track my academic progress. | 2 | Low | No | Not Started |
| US-12 | As a student, I want to rate and review tutors after a session so that others know the tutor’s quality. | 3 | Low | No | Not Started |
| US-13 | As a tutor, I want to view all my tutoring requests so that I can manage incoming sessions. | 3 | Low | Yes | Not Started |
| US-14 | As a student, I want to pay for tutoring sessions using credits so tutors can be compensated. | 8 | High | No | Not Started |
| US-15 | As a tutor, I want to set my availability schedule so students can book sessions during free time. | 3 | Medium | Yes | Not Started |
| US-16 | As a student, I want to cancel a booked tutoring session so that I can reschedule if needed. | 2 | Low | No | Not Started |
| US-17 | As a tutor, I want to cancel or reschedule sessions so that I can handle schedule conflicts. | 3 | Medium | No | Not Started |
| US-18 | As a student, I want notifications when a tutor accepts my request so I know the session is booked. | 3 | Medium | No | Not Started |
| US-19 | As a tutor, I want notifications when students request sessions so I can respond quickly. | 3 | Medium | No | Not Started |
| US-20 | As a student, I want to edit my profile information so my account details stay updated. | 2 | Low | No | Not Started |
| US-21 | As a tutor, I want to edit my tutor profile and subjects so I can update my expertise. | 2 | Low | No | Not Started |
| US-22 | As an academic advisor, I want to create an account to oversee and moderate the platform and tutors. | 3 | Low | No | Not Started |
| US-23 | As an academic advisor, I want to view tutoring session statistics so that I can monitor usage. | 3 | Low | No | Not Started |
| US-24 | As an academic advisor, I want to view tutor ratings and feedback so that I can evaluate tutors. | 3 | Low | No | Not Started |
| US-25 | As an academic advisor, I want to identify students frequently requesting tutoring for support. | 5 | Medium | No | Not Started |
| US-26 | As an academic advisor, I want to flag inappropriate reviews or tutor behavior to maintain quality. | 5 | Medium | No | Not Started |

### Product Backlog - Project Part 3
ID | User Story | Story Points | Risk Level | Half-way Release | Status
| :-----------: | :----------- | :-----------: | :-----------: | :-----------: | :-----------: |
| US-01 | As a student, I want to create an account so that I can access tutoring services. | 2 | Low | Yes | Done |
| US-02 | As a tutor, I want to create a tutor profile with subjects and expertise so students can find me. | 3 | Low | Yes | Done |
| US-03 | As a student, I want to log in to my account so that I can access my student dashboard. | 2 | Low | Yes | Done |
| US-04 | As a student, I want to search tutors by subject or course so that I can find help for my classes. | 3 | Low | Yes | Done |
| US-05 | As a student, I want to view tutor profiles and qualifications so that I can choose a suitable tutor. | 3 | Low | Yes | Done |
| US-06 | As a student, I want to request a tutoring session so that I can receive help from a tutor. | 5 | Medium | Yes | Done |
| US-07 | As a tutor, I want to log in to my account so that I can access my tutoring dashboard. | 2 | Low | Yes | Done |
| US-08 | As a tutor, I want to accept or decline tutoring requests so that I can manage my workload. | 3 | Medium | Yes | Done |
| US-09 | As a student, I want to schedule a tutoring session based on tutor availability so we meet easily. | 5 | Medium | Yes | Done |
| US-10 | As a student, I want to see my upcoming sessions so that I can keep track of my tutoring schedule. | 2 | Low | Yes | Done |
| US-11 | As a student, I want to view my session history so that I can track my academic progress. | 2 | Low | No | In Progress |
| US-12 | As a student, I want to rate and review tutors after a session so that others know the tutor’s quality. | 3 | Low | No | Not Started |
| US-13 | As a tutor, I want to view all my tutoring requests so that I can manage incoming sessions. | 3 | Low | Yes | Done |
| US-14 | As a student, I want to pay for tutoring sessions using credits so tutors can be compensated. | 8 | High | No | Not Started |
| US-15 | As a tutor, I want to set my availability schedule so students can book sessions during free time. | 3 | Medium | No | Not Started |
| US-16 | As a student, I want to cancel a booked tutoring session so that I can reschedule if needed. | 2 | Low | No | In Progress |
| US-17 | As a tutor, I want to cancel or reschedule sessions so that I can handle schedule conflicts. | 3 | Medium | No | Not Started |
| US-18 | As a student, I want notifications when a tutor accepts my request so I know the session is booked. | 3 | Medium | No | Not Started |
| US-19 | As a tutor, I want notifications when students request sessions so I can respond quickly. | 3 | Medium | No | Not Started |
| US-20 | As a student, I want to edit my profile information so my account details stay updated. | 2 | Low | No | Not Started |
| US-21 | As a tutor, I want to edit my tutor profile and subjects so I can update my expertise. | 2 | Low | No | Not Started |
| US-22 | As an academic advisor, I want to create an account to oversee and moderate the platform and tutors. | 3 | Low | No | Not Started |
| US-23 | As an academic advisor, I want to view tutoring session statistics so that I can monitor usage. | 3 | Low | No | Not Started |
| US-24 | As an academic advisor, I want to view tutor ratings and feedback so that I can evaluate tutors. | 3 | Low | No | Not Started |
| US-25 | As an academic advisor, I want to identify students frequently requesting tutoring for support. | 5 | Medium | No | Not Started |
| US-26 | As an academic advisor, I want to flag inappropriate reviews or tutor behavior to maintain quality. | 5 | Medium | No | Not Started |

### Product Backlog - Project Part 4
ID | User Story | Story Points | Risk Level | Half-way Release | Status
| :-----------: | :----------- | :-----------: | :-----------: | :-----------: | :-----------: |
| US-01 | As a student, I want to create an account so that I can access tutoring services. | 2 | Low | Yes | Done |
| US-02 | As a tutor, I want to create a tutor profile with subjects and expertise so students can find me. | 3 | Low | Yes | Done |
| US-03 | As a student, I want to log in to my account so that I can access my student dashboard. | 2 | Low | Yes | Done |
| US-04 | As a student, I want to search tutors by subject or course so that I can find help for my classes. | 3 | Low | Yes | Done |
| US-05 | As a student, I want to view tutor profiles and qualifications so that I can choose a suitable tutor. | 3 | Low | Yes | Done |
| US-06 | As a student, I want to request a tutoring session so that I can receive help from a tutor. | 5 | Medium | Yes | Done |
| US-07 | As a tutor, I want to log in to my account so that I can access my tutoring dashboard. | 2 | Low | Yes | Done |
| US-08 | As a tutor, I want to accept or decline tutoring requests so that I can manage my workload. | 3 | Medium | Yes | Done |
| US-09 | As a student, I want to schedule a tutoring session based on tutor availability so we meet easily. | 5 | Medium | Yes | Done |
| US-10 | As a student, I want to see my upcoming sessions so that I can keep track of my tutoring schedule. | 2 | Low | Yes | Done |
| US-11 | As a student, I want to view my session history so that I can track my academic progress. | 2 | Low | No | Done |
| US-12 | As a student, I want to rate and review tutors after a session so that others know the tutor’s quality. | 3 | Low | No | Done |
| US-13 | As a tutor, I want to view all my tutoring requests so that I can manage incoming sessions. | 3 | Low | Yes | Done |
| US-14 | As a student, I want to pay for tutoring sessions using credits so tutors can be compensated. | 8 | High | No | Done |
| US-15 | As a tutor, I want to set my availability schedule so students can book sessions during free time. | 3 | Medium | No | Done |
| US-16 | As a student, I want to cancel a booked tutoring session so that I can reschedule if needed. | 2 | Low | No | Done |
| US-17 | As a tutor, I want to cancel or reschedule sessions so that I can handle schedule conflicts. | 3 | Medium | No | Done |
| US-18 | As a student, I want notifications when a tutor accepts my request so I know the session is booked. | 3 | Medium | No | In Progress |
| US-19 | As a tutor, I want notifications when students request sessions so I can respond quickly. | 3 | Medium | No | In Progress |
| US-20 | As a student, I want to edit my profile information so my account details stay updated. | 2 | Low | No | Done |
| US-21 | As a tutor, I want to edit my tutor profile and subjects so I can update my expertise. | 2 | Low | No | Done |
| US-22 | As an academic advisor, I want to create an account to oversee and moderate the platform and tutors. | 3 | Low | No | Done |
| US-23 | As an academic advisor, I want to view tutoring session statistics so that I can monitor usage. | 3 | Low | No | In Progress |
| US-24 | As an academic advisor, I want to view tutor ratings and feedback so that I can evaluate tutors. | 3 | Low | No | Done |
| US-25 | As an academic advisor, I want to identify students frequently requesting tutoring for support. | 5 | Medium | No | In Progress |
| US-26 | As an academic advisor, I want to flag inappropriate reviews or tutor behavior to maintain quality. | 5 | Medium | No | Done |
| US-27 | As a user, I want to able to log out of my account after I finish using it. | 2 | Low | No | Done |

**Risk Levels**
- Low – Well understood, minimal uncertainty
- Medium – Some complexity or dependency
- High – Significant technical uncertainty or complexity

**Half-Way Release**
- Yes – Planned to be implemented by the midpoint checkpoint
- No – Planned to be implemented by the final checkpoint

**Status**
- Not Started
- In Progress
- Done

#### Kanban Board - Phase 2
<img width="1799" height="837" alt="image" src="https://github.com/user-attachments/assets/a8d82be1-c198-4855-bd3e-5b184284a8b4" />

#### Kanban Board - Phase 4
<img width="1919" height="806" alt="image" src="https://github.com/user-attachments/assets/265e8f8b-c32f-402e-9226-868ad52a55f3" />

---

## User Interface Mockups
### UI Mockups - Project Part 2
- Figma Link: https://www.figma.com/design/5Gvj1Cx8xMgJlSWqqTS1Kn/SE-Project-Part-2?node-id=0-1&p=f&t=pcGPOVFB3r4fZiFz-0
<img width="484" height="807" alt="image" src="https://github.com/user-attachments/assets/f9e49afe-da54-4df5-83d5-cb1bf36bf0ca" />

### Storyboard - Project Part 2
- Figma Link: https://www.figma.com/design/4H8revAvcZlx5stlsUV5AB/SE-Project-Part-2--Copy-?node-id=2001-2&t=IlpvXuVms4e771cw-1
<img width="1098" height="782" alt="image" src="https://github.com/user-attachments/assets/b74cb59d-8760-4df3-a75c-305222977e22" />

### Storyboard - Project Part 3
- Figma Link: https://www.figma.com/design/qldM8MdiFuJ0N2KZZMhO3t/Story-board-and-CRC-Cards?node-id=0-1&p=f&t=ja1mZuG8PhIyB4vB-0
![storyboard](https://github.com/user-attachments/assets/71afef69-e28d-48fb-9331-7bcd857849ec)
---

## CRC Cards
### Cards - Project Part 2
<img width="1276" height="692" alt="image" src="https://github.com/user-attachments/assets/16c84ff7-3a87-429e-9c56-97f7f5ab81be" />

### Cards - Project Part 3
- Figma Link: https://www.figma.com/design/qldM8MdiFuJ0N2KZZMhO3t/Story-board-and-CRC-Cards?node-id=0-1&p=f&t=ja1mZuG8PhIyB4vB-0
![CRC](https://github.com/user-attachments/assets/fe735682-efd0-4053-8e2e-da244ecb3586)
---

## UML Class Diagram
<img width="4696" height="2136" alt="TutorConnect UML Diagram" src="https://github.com/user-attachments/assets/e6c0d39a-14a6-48c2-87cd-c30a5b6fed2b" />
