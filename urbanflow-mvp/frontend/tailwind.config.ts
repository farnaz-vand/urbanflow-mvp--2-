import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          50: "#eef8ff",
          100: "#d8efff",
          200: "#b9e4ff",
          300: "#8bd4ff",
          400: "#54bbff",
          500: "#269dff",
          600: "#0d7de8",
          700: "#0f64b8",
          800: "#145493",
          900: "#174977",
        },
      },
      boxShadow: {
        soft: "0 20px 60px rgba(2, 12, 27, 0.18)",
      },
      backgroundImage: {
        grid: "radial-gradient(circle at 1px 1px, rgba(255,255,255,0.06) 1px, transparent 0)",
      },
    },
  },
  plugins: [],
};
export default config;
