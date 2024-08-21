/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/main/resources/**/*.{html,js}"],
  theme: {
    screens: {
      xs: '400px',
      sm: '640px',
      md: '768px',
      lg: '1024px',
      xl: '1280px',
    },
    extend: {},
  },
  plugins: [],
  darkMode: "selector",
}

