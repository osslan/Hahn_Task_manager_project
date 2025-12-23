import api from './api';

export const authService = {
  login: async (username, password) => {
    const response = await api.post('/auth/login', { username, password });
    return response.data;
  },

  register: async (username, password) => {
    const response = await api.post('/auth/register', { username, password });
    return response.data;
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  },

  getToken: () => {
    return localStorage.getItem('token');
  },

  getUsername: () => {
    return localStorage.getItem('username');
  },

  isAuthenticated: () => {
    return !!localStorage.getItem('token');
  },
};


