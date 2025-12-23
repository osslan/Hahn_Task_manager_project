# CORS Configuration

## What was configured:

The backend CORS (Cross-Origin Resource Sharing) configuration has been updated to allow requests from the React frontend.

### Allowed Origins:
- `http://localhost:3000` - Create React App default port
- `http://localhost:5173` - Vite default port (used by this project)
- `http://localhost:5174` - Vite alternative port
- `http://localhost:8080` - Backend port (for Swagger UI)
- `http://127.0.0.1:3000` - Alternative localhost format
- `http://127.0.0.1:5173` - Alternative localhost format
- `http://127.0.0.1:5174` - Alternative localhost format

### Allowed Methods:
- GET
- POST
- PUT
- DELETE
- OPTIONS

### Allowed Headers:
- All headers (`*`)

### Credentials:
- Enabled (for JWT authentication)

## Files Modified:
- `backend/src/main/java/com/example/backend/security/SecurityConfig.java`
- `backend/src/main/resources/application.properties` (added explicit server port)

## Testing:
After restarting the backend, the frontend should be able to make API calls without CORS errors.


