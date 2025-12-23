import api from './api';

export const taskService = {
  getTasks: async (projectId, page = 0, size = 10) => {
    const response = await api.get(`/tasks/project/${projectId}`, {
      params: { page, size },
    });
    return response.data;
  },

  createTask: async (taskData) => {
    const response = await api.post('/tasks', taskData);
    return response.data;
  },

  updateTask: async (taskData) => {
    const response = await api.put('/tasks', taskData);
    return response.data;
  },

  deleteTask: async (id) => {
    await api.delete(`/tasks/${id}`);
  },
};


