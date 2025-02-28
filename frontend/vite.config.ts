import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080/api", // The backend server URL
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
      "/auth": {
        target: "http://localhost:8080/auth", // The backend server URL
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/auth/, ""),
      },
      "/nuke": {
        target: "http://localhost:8080/nuke", // The backend server URL
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/nuke/, ""),
      },
    },
  },
});
