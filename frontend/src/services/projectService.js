import api from './api';

export const projectService = {
  getProjects: async (username, page = 0, size = 10) => {
    const response = await api.get(`/projects/user/${username}`, {
      params: { page, size },
    });
    return response.data;
  },

  createProject: async (projectData) => {
    const response = await api.post('/projects', projectData);
    return response.data;
  },

  updateProject: async (projectData) => {
    const response = await api.put('/projects', projectData);
    return response.data;
  },

  deleteProject: async (id) => {
    await api.delete(`/projects/${id}`);
  },
};


