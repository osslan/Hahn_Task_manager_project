import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import Button from '../components/ui/Button';

const Home = () => {
  const { isAuthenticated } = useAuth();

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      <div className="container mx-auto px-4 py-16">
        <div className="max-w-4xl mx-auto text-center">
          <h1 className="text-5xl font-bold text-gray-900 mb-6">
            Project Management System
          </h1>
          <p className="text-xl text-gray-600 mb-8">
            Organize your projects and tasks efficiently. Track progress, manage deadlines, and stay productive.
          </p>
          
          {isAuthenticated ? (
            <div className="flex gap-4 justify-center">
              <Link to="/projects">
                <Button size="lg" className="text-lg px-8">
                  Go to Projects
                </Button>
              </Link>
            </div>
          ) : (
            <div className="flex gap-4 justify-center">
              <Link to="/login">
                <Button size="lg" className="text-lg px-8">
                  Login
                </Button>
              </Link>
              <Link to="/login">
                <Button size="lg" variant="outline" className="text-lg px-8">
                  Get Started
                </Button>
              </Link>
            </div>
          )}

          <div className="mt-16 grid md:grid-cols-3 gap-8 text-left">
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-3">📋 Manage Projects</h3>
              <p className="text-gray-600">
                Create and organize your projects with ease. Keep everything in one place.
              </p>
            </div>
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-3">✅ Track Tasks</h3>
              <p className="text-gray-600">
                Break down projects into tasks. Set deadlines and mark them as complete.
              </p>
            </div>
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-3">📊 Monitor Progress</h3>
              <p className="text-gray-600">
                Visualize your project progress with real-time analytics and completion rates.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;


