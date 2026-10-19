# UrbanFlow MVP (LandIQ)

AI-powered urban investment decision MVP.

## Stack

- **Frontend:** Next.js (App Router), TypeScript, TailwindCSS, Mapbox GL JS
- **Backend:** Spring Boot 3, Java 17, PostgreSQL, Spring Data JPA
- **AI Layer:** Deterministic mock analysis logic (easy to swap for OpenAI later)

---

## Featu
- Landing page with product intro
- Map-centric analysis flow
- Click map to select a location
- Choose project type + enter project description
- Run urban development analysis
- Result page with:
  - Score (0–100)
  - Risk level
  - 3–5 insights
  - Recommendation
  - Before / After toggle visualization
- Saved analysis scenarios retrievable via API

---

## Project Structure

```text
urbanflow-mvp/
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── page.tsx
│   │   │   ├── map/page.tsx
│   │   │   ├── result/[id]/page.tsx
│   │   │   ├── globals.css
│   │   │   └── layout.tsx
│   │   ├── components/
│   │   │   ├── LocationMap.tsx
│   │   │   ├── ScenarioPreviewMap.tsx
│   │   │   ├── AnalysisForm.tsx
│   │   │   ├── InsightList.tsx
│   │   │   └── ScoreBadge.tsx
│   │   ├── lib/
│   │   │   ├── api.ts
│   │   │   └── types.ts
│   │   └── utils/
│   │       └── cn.ts
│   ├── package.json
│   ├── tailwind.config.ts
│   ├── postcss.config.js
│   ├── tsconfig.json
│   ├── next.config.js
│   └── .env.local.example
├── backend/
│   ├── src/main/java/com/urbanflow/
│   │   ├── UrbanFlowApplication.java
│   │   ├── controller/AnalysisController.java
│   │   ├── dto/AnalysisRequest.java
│   │   ├── dto/AnalysisResponse.java
│   │   ├── model/AnalysisScenario.java
│   │   ├── repository/AnalysisScenarioRepository.java
│   │   ├── service/AnalysisService.java
│   │   └── config/WebConfig.java
│   ├── src/main/resources/application.properties
│   ├── pom.xml
│   └── .env.example
└── docker-compose.yml
```

---

## Quick Start

### 1) Start PostgreSQL

```bash
docker compose up -d
```

This starts PostgreSQL on port `5432`.

---

### 2) Run backend

Requirements:
- Java 17+
- Maven 3.9+

```bash
cd backend
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

### 3) Run frontend

Requirements:
- Node.js 18+

```bash
cd frontend
cp .env.local.example .env.local
npm install
npm run dev
```

Frontend runs on:

```text
http://localhost:3000
```

---

## Environment Variables

### Frontend `.env.local`

```env
NEXT_PUBLIC_MAPBOX_TOKEN=your_mapbox_token_here
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api
```

### Backend environment (optional overrides)

Defaults are already set in `application.properties`, but you can override them:

```env
DB_URL=jdbc:postgresql://localhost:5432/urbanflow
DB_USERNAME=urbanflow
DB_PASSWORD=urbanflow
SERVER_PORT=8080
```

---

## API

### POST `/api/analyze`

Request:

```json
{
  "lat": 35.6892,
  "lng": 51.389,
  "projectType": "residential",
  "description": "Mid-rise mixed-use residential block near transit"
}
```

Response:

```json
{
  "id": 1,
  "score": 78,
  "risk": "medium",
  "insights": [
    "Transit accessibility improves long-term demand outlook.",
    "Residential demand profile appears favorable for this zone.",
    "Site conditions suggest moderate execution risk.",
    "Project narrative aligns better with phased development than a single large delivery."
  ],
  "recommendation": "Proceed with caution: prioritize phased delivery, mobility access, and municipal permitting checks.",
  "lat": 35.6892,
  "lng": 51.389,
  "projectType": "residential",
  "description": "Mid-rise mixed-use residential block near transit",
  "createdAt": "2026-06-25T00:00:00"
}
```

### GET `/api/scenario/{id}`

Returns a saved scenario.

---

## MVP Notes

- This is intentionally **simple and demo-ready**.
- No real digital twin, BIM, or 3D modeling.
- Before/After is simulated using:
  - map marker
  - risk/value overlay circle
  - UI toggle
- AI analysis uses deterministic mock logic for reliability and repeatability.

---

## How the mock AI works

The score is based on a weighted heuristic using:

- project type
- coordinate-derived locality signal
- description complexity / confidence
- urban execution penalty / opportunity bonus

This produces:

- `score: 0–100`
- `risk: low | medium | high`
- `3–5 insights`
- `recommendation`

---

## Demo Flow

1. Open app
2. Click **Start Analysis**
3. Select a location on the map
4. Choose project type
5. Enter description
6. Click **Run Analysis**
7. Review score, insights, recommendation, and before/after view

---

## Future Extensions

- Replace mock AI with OpenAI API
- Add zoning and parcel datasets
- Add user authentication
- Store map snapshots
- Add PDF export and shareable reports
