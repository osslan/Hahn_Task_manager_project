# Frontend Setup Guide

## Quick Start

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Configure environment:**
   Create a `.env` file in the `frontend` directory:
   ```
   VITE_API_BASE_URL=http://localhost:8080
   ```

3. **Start development server:**
   ```bash
   npm run dev
   ```

4. **Build for production:**
   ```bash
   npm run build
   ```

## Features Implemented

✅ **Authentication**
- Login/Register pages
- JWT token storage in localStorage
- Protected routes
- Automatic token injection in API requests
- Logout functionality

✅ **Projects Management**
- List all user projects
- Create new project (modal dialog)
- Delete project
- Navigate to project details

✅ **Tasks Management**
- List tasks for a project
- Create new task (title, description, due date)
- Mark task as completed/incomplete
- Delete task
- Real-time UI updates

✅ **Progress Tracking**
- Display project completion percentage
- Visual progress bar using shadcn/ui Progress component
- Fetched from analytics API

✅ **UI Components**
- Modern, clean design with Tailwind CSS
- shadcn/ui style components (Button, Card, Dialog, Input, Progress)
- Responsive layout
- Professional styling

## Project Structure

```
frontend/
├── src/
│   ├── components/          # Reusable components
│   │   ├── ui/              # shadcn/ui style components
│   │   └── ProtectedRoute.jsx
│   ├── contexts/            # React contexts
│   │   └── AuthContext.jsx
│   ├── pages/               # Page components
│   │   ├── Home.jsx
│   │   ├── Login.jsx
│   │   ├── Projects.jsx
│   │   └── ProjectDetails.jsx
│   ├── services/            # API services
│   │   ├── api.js
│   │   ├── authService.js
│   │   ├── projectService.js
│   │   ├── taskService.js
│   │   └── analyticsService.js
│   ├── config/
│   │   └── api.js
│   ├── App.jsx
│   ├── main.jsx
│   └── index.css
├── .env.example
├── package.json
└── vite.config.js
```

## API Integration

The frontend connects to the Spring Boot backend. All API calls are centralized in the `services/` directory:

- **Auth Service**: Login, Register, Logout
- **Project Service**: CRUD operations for projects
- **Task Service**: CRUD operations for tasks
- **Analytics Service**: Progress and statistics

JWT tokens are automatically included in request headers via Axios interceptors.

## Notes

- The app uses React Router for navigation
- Authentication state is managed via React Context
- All private routes are protected with the `ProtectedRoute` component
- Error handling is implemented throughout
- Loading states are shown during API calls

## Demo Ready

The frontend is fully functional and ready for demo. All features from the requirements are implemented:
- ✅ Home page with hero section
- ✅ Login/Register functionality
- ✅ Projects list and management
- ✅ Project details with tasks
- ✅ Progress tracking
- ✅ Clean, professional UI


