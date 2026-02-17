import axios from 'axios';
import { toast } from 'sonner';
import { API_BASE_URL } from './config';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  },
});

// Global response interceptor for centralized error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const { response } = error || {};

    // Default message
    let message = 'Unexpected error occurred';

    if (response) {
      const { status, data, config } = response;

      // Try to parse RFC 9457 ProblemDetails shape if present
      // { type, title, status, detail, instance, ... }
      const title = data?.title ?? null;
      const detail = data?.detail ?? null;

      if (title || detail) {
        message = [title, detail].filter(Boolean).join(': ');
      } else if (typeof data === 'string' && data.trim().length > 0) {
        message = data;
      } else if (error?.message) {
        message = error.message;
      }

      // Log a compact, structured error for debugging
      // Avoid logging sensitive data
      // eslint-disable-next-line no-console
      console.error('[API_ERROR]', {
        status,
        url: config?.url,
        method: config?.method,
        message,
      });

      // User-facing toast
      toast.error(message, {
        description: `Request failed with status ${status}`,
      });
    } else {
      // Network/timeout or request setup error
      message = error?.message || message;
      // eslint-disable-next-line no-console
      console.error('[API_NETWORK_ERROR]', { message });
      toast.error(message);
    }

    return Promise.reject(error);
  },
);

export default api;
