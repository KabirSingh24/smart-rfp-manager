# Aerchain SDE-1 Assignment – AI-Powered RFP Management System

Candidate: Kabir Singh  
Role Applied: Software Development Engineer – 1 (SDE-1)  

---

## 1. Overview

This project is a single-user AI-powered RFP (Request for Proposal) Management System.

It demonstrates how AI can be used to:
- Convert natural language procurement requests into structured RFPs
- Send RFPs to vendors
- Parse unstructured vendor responses automatically
- Compare vendor proposals and recommend the best option

The focus is on real-world thinking, clean architecture, and practical AI usage rather than exhaustive features.

---

## 2. Features Implemented

- Create RFPs from natural language input
- View structured RFP data
- Maintain vendor master data
- Send RFPs to selected vendors via email
- Parse vendor email responses using AI
- Compare vendor proposals with a scoring system
- Recommend the best vendor based on objective criteria

---

## 3. Tech Stack

Backend:
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA (Hibernate)
- WebClient
- H2 / PostgreSQL

Frontend:
- React (Vite)
- Plain CSS (single global stylesheet)

AI:
- Groq API
- Model: llama-3.1-8b-instant

Email:
- SMTP for outbound emails
- Inbound emails simulated via UI for deterministic testing

---

## 4. Project Structure

rfp-management/
backend/
controller/
service/
repository/
model/
config/
frontend/
src/
pages/
api.js
App.jsx
index.css
README.md

---

## 5. API Endpoints

Create RFP  
POST /rfps

Request:
{
  "text": "I need laptops and monitors..."
}

---

Send RFP to Vendors  
POST /rfps/send

Request:
{
  "text": "RFP text",
  "vendorIds": [1, 2]
}

---

Parse Vendor Response  
POST /proposals/parse

Request:
{
  "rfpId": 1,
  "vendorId": 1,
  "emailText": "Vendor quotation email..."
}

---

Evaluate Proposals  
GET /proposals/rfp/{rfpId}/evaluate

Returns a list of proposals with calculated scores.

---

## 6. Proposal Evaluation Logic

Each proposal is scored using the following formula:

score = (100000 / total_price) + (100 / delivery_days)

Lower price and faster delivery result in a higher score.

This approach is intentionally simple, transparent, and easy to extend.

---

## 7. AI Integration Details

AI is used for:
- Structuring RFPs from natural language
- Extracting structured data from unstructured vendor emails
- Normalizing inconsistent vendor responses

AI responses are parsed defensively before being stored.

---

## 8. Email Handling Assumption

Inbound vendor emails are simulated using a paste-based UI.

This avoids dependency on external email infrastructure and keeps the workflow deterministic.

In a production system, this would be replaced with IMAP, webhooks, or an email ingestion service.

---

## 9. Environment Variables

Create a .env file with the following variables:

OPENAI_API_KEY=your_api_key_here  
AI_BASE_URL=https://api.groq.com/openai/v1  

---

## 10. Running the Project Locally

Backend:
cd backend
./mvnw spring-boot:run

Backend runs on http://localhost:8080

Frontend:
cd frontend
npm install
npm run dev

Frontend runs on http://localhost:5173

---

## 11. Assumptions

- Single-user system (no authentication)
- Vendors are pre-seeded
- Email sending and receiving is simplified
- One RFP can have multiple vendor proposals
- AI output is trusted but validated

---

## 12. Future Improvements

- Real inbound email ingestion
- Vendor onboarding UI
- Advanced AI-based scoring and explanations
- Attachment and PDF parsing
- Audit trail and reporting

---

## 13. AI Tools Usage Declaration

AI tools were used for:
- Debugging
- Prompt design
- Architectural reasoning

All final implementation decisions and code understanding belong to the author.

---

## 14. Demo Video

The demo video includes:
- Creating an RFP
- Viewing structured RFP output
- Parsing vendor responses
- Comparing proposals and scores
- Brief code walkthrough

---

Submitted by: Kabir Singh  
Assignment: Aerchain SDE-1 Take-Home Challenge
