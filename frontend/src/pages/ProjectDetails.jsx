import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { taskService } from '../services/taskService';
import { analyticsService } from '../services/analyticsService';
import Button from '../components/ui/Button';
import { Card, CardHeader, CardTitle, CardContent } from '../components/ui/Card';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from '../components/ui/Dialog';
import Input from '../components/ui/Input';
import Progress from '../components/ui/Progress';

const ProjectDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { logout } = useAuth();
  const [tasks, setTasks] = useState([]);
  const [progress, setProgress] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [isTaskDialogOpen, setIsTaskDialogOpen] = useState(false);
  const [newTask, setNewTask] = useState({
    title: '',
    description: '',
    deadline: '',
    completed: false,
  });
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    fetchData();
  }, [id]);

  const fetchData = async () => {
    try {
      setLoading(true);
      setError('');
      const [tasksResponse, progressResponse] = await Promise.all([
        taskService.getTasks(id, 0, 100),
        analyticsService.getProgress(id).catch(() => ({ percentageProgression: 0 })),
      ]);
      setTasks(tasksResponse.items || []);
      setProgress(progressResponse.percentageProgression || 0);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to load project data');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTask = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setError('');
    try {
      const taskData = {
        title: newTask.title,
        description: newTask.description,
        deadline: newTask.deadline || null,
        completed: false,
        projectId: parseInt(id),
      };
      await taskService.createTask(taskData);
      setIsTaskDialogOpen(false);
      setNewTask({ title: '', description: '', deadline: '', completed: false });
      fetchData();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create task');
      console.error(err);
    } finally {
      setSubmitting(false);
    }
  };

  const handleToggleTask = async (task) => {
    try {
      const updatedTask = {
        ...task,
        completed: !task.completed,
      };
      await taskService.updateTask(updatedTask);
      fetchData();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update task');
      console.error(err);
    }
  };

  const handleDeleteTask = async (taskId) => {
    if (!window.confirm('Are you sure you want to delete this task?')) {
      return;
    }
    try {
      await taskService.deleteTask(taskId);
      fetchData();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete task');
      console.error(err);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-lg">Loading project details...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-white shadow-sm border-b">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex gap-4 items-center">
            <Button variant="ghost" onClick={() => navigate('/projects')}>
              ← Back to Projects
            </Button>
            <h1 className="text-2xl font-bold">Project Details</h1>
          </div>
          <Button variant="outline" onClick={logout}>Logout</Button>
        </div>
      </nav>

      <div className="container mx-auto px-4 py-8">
        {error && (
          <div className="mb-4 p-4 bg-destructive/10 text-destructive rounded-md">
            {error}
          </div>
        )}

        <Card className="mb-6">
          <CardHeader>
            <CardTitle>Project Progress</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-2">
              <div className="flex justify-between text-sm">
                <span>Completion</span>
                <span className="font-semibold">{Math.round(progress)}%</span>
              </div>
              <Progress value={progress} className="h-3" />
            </div>
          </CardContent>
        </Card>

        <div className="flex justify-between items-center mb-6">
          <h2 className="text-xl font-semibold">Tasks</h2>
          <Button onClick={() => setIsTaskDialogOpen(true)}>Add Task</Button>
        </div>

        {tasks.length === 0 ? (
          <Card>
            <CardContent className="pt-6 text-center">
              <p className="text-gray-500 mb-4">No tasks yet. Create your first task!</p>
              <Button onClick={() => setIsTaskDialogOpen(true)}>Create Task</Button>
            </CardContent>
          </Card>
        ) : (
          <div className="space-y-4">
            {tasks.map((task) => (
              <Card key={task.id}>
                <CardContent className="pt-6">
                  <div className="flex items-start justify-between">
                    <div className="flex-1">
                      <div className="flex items-center gap-3">
                        <input
                          type="checkbox"
                          checked={task.completed || false}
                          onChange={() => handleToggleTask(task)}
                          className="w-5 h-5 rounded border-gray-300"
                        />
                        <h3 className={`text-lg font-semibold ${task.completed ? 'line-through text-gray-500' : ''}`}>
                          {task.title}
                        </h3>
                      </div>
                      {task.description && (
                        <p className="text-gray-600 mt-2 ml-8">{task.description}</p>
                      )}
                      {task.deadline && (
                        <p className="text-sm text-gray-500 mt-2 ml-8">
                          Due: {new Date(task.deadline + 'T00:00:00').toLocaleDateString()}
                        </p>
                      )}
                    </div>
                    <Button
                      variant="destructive"
                      size="sm"
                      onClick={() => handleDeleteTask(task.id)}
                    >
                      Delete
                    </Button>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        )}
      </div>

      <Dialog open={isTaskDialogOpen} onOpenChange={setIsTaskDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Create New Task</DialogTitle>
          </DialogHeader>
          <form onSubmit={handleCreateTask}>
            <div className="space-y-4">
              <div>
                <label htmlFor="task-title" className="block text-sm font-medium mb-2">
                  Title
                </label>
                <Input
                  id="task-title"
                  value={newTask.title}
                  onChange={(e) => setNewTask({ ...newTask, title: e.target.value })}
                  required
                  placeholder="Task title"
                />
              </div>
              <div>
                <label htmlFor="task-description" className="block text-sm font-medium mb-2">
                  Description
                </label>
                <textarea
                  id="task-description"
                  value={newTask.description}
                  onChange={(e) => setNewTask({ ...newTask, description: e.target.value })}
                  className="flex min-h-[80px] w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
                  placeholder="Task description"
                />
              </div>
              <div>
                <label htmlFor="task-deadline" className="block text-sm font-medium mb-2">
                  Due Date
                </label>
                <Input
                  id="task-deadline"
                  type="date"
                  value={newTask.deadline}
                  onChange={(e) => setNewTask({ ...newTask, deadline: e.target.value })}
                />
              </div>
            </div>
            <DialogFooter>
              <Button
                type="button"
                variant="outline"
                onClick={() => setIsTaskDialogOpen(false)}
              >
                Cancel
              </Button>
              <Button type="submit" disabled={submitting}>
                {submitting ? 'Creating...' : 'Create'}
              </Button>
            </DialogFooter>
          </form>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default ProjectDetails;

