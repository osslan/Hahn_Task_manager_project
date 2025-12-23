# Project Management Frontend

A modern React frontend for the Hahn Software Morocco internship project management system.

## Features

- 🔐 JWT Authentication (Login/Register)
- 📋 Project Management (Create, View, Delete)
- ✅ Task Management (Create, Update, Delete, Mark Complete)
- 📊 Progress Tracking with visual progress bars
- 🎨 Modern UI with shadcn/ui components and Tailwind CSS
- 🔒 Protected Routes

## Tech Stack

- React 19
- React Router DOM
- Axios
- Tailwind CSS
- Vite

## Setup

1. Install dependencies:
```bash
npm install
```

2. Create a `.env` file in the root directory:
```
VITE_API_BASE_URL=http://localhost:8080
```

3. Start the development server:
```bash
npm run dev
```

4. Build for production:
```bash
npm run build
```

## Project Structure

```
src/
├── components/       # Reusable UI components
│   ├── ui/          # shadcn/ui style components
│   └── ProtectedRoute.jsx
├── contexts/        # React contexts (Auth)
├── pages/           # Page components
│   ├── Home.jsx
│   ├── Login.jsx
│   ├── Projects.jsx
│   └── ProjectDetails.jsx
├── services/        # API service layer
│   ├── api.js
│   ├── authService.js
│   ├── projectService.js
│   ├── taskService.js
│   └── analyticsService.js
├── config/          # Configuration files
├── App.jsx          # Main app component with routing
└── main.jsx         # Entry point
```

## API Integration

The frontend connects to the Spring Boot backend at `http://localhost:8080` by default.

All API calls are handled through centralized service files in `src/services/`.

JWT tokens are automatically included in request headers via Axios interceptors.

## Environment Variables

- `VITE_API_BASE_URL`: Backend API base URL (default: http://localhost:8080)


