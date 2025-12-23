import api from './api';

export const analyticsService = {
  getProgress: async (projectId) => {
    const response = await api.get(`/analytics/progression/${projectId}`);
    return response.data;
  },

  getTotalTasks: async (projectId) => {
    const response = await api.get(`/analytics/totalTasks/${projectId}`);
    return response.data;
  },

  getCompletedTasks: async (projectId) => {
    const response = await api.get(`/analytics/totalCompletdTasks/${projectId}`);
    return response.data;
  },
};


