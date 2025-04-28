import { fontFamily } from "tailwindcss/defaultTheme";

/** @type {import('tailwindcss').Config} */
const config = {
	darkMode: ["class"],
	content: ["./src/**/*.{html,js,svelte,ts}", './node_modules/flowbite-svelte/**/*.{html,js,svelte,ts}'],
    plugins: [require('flowbite/plugin')],
	safelist: ["dark"],
	theme: {
		container: {
			center: true,
			padding: "2rem",
			screens: {
				"2xl": "1400px"
			}
		},
		extend: {
			colors: {
				border: "hsl(var(--border) / <alpha-value>)",
				input: "hsl(var(--input) / <alpha-value>)",
				ring: "hsl(var(--ring) / <alpha-value>)",
				background: "hsl(var(--background) / <alpha-value>)",
				foreground: "hsl(var(--foreground) / <alpha-value>)",
				primary: {
					DEFAULT: "hsl(var(--primary) / <alpha-value>)",
					foreground: "hsl(var(--primary-foreground) / <alpha-value>)"
				},
				secondary: {
					DEFAULT: "hsl(var(--secondary) / <alpha-value>)",
					foreground: "hsl(var(--secondary-foreground) / <alpha-value>)"
				},
				destructive: {
					DEFAULT: "hsl(var(--destructive) / <alpha-value>)",
					foreground: "hsl(var(--destructive-foreground) / <alpha-value>)"
				},
				muted: {
					DEFAULT: "hsl(var(--muted) / <alpha-value>)",
					foreground: "hsl(var(--muted-foreground) / <alpha-value>)"
				},
				accent: {
					DEFAULT: "hsl(var(--accent) / <alpha-value>)",
					foreground: "hsl(var(--accent-foreground) / <alpha-value>)"
				},
				popover: {
					DEFAULT: "hsl(var(--popover) / <alpha-value>)",
					foreground: "hsl(var(--popover-foreground) / <alpha-value>)"
				},
				card: {
					DEFAULT: "hsl(var(--card) / <alpha-value>)",
					foreground: "hsl(var(--card-foreground) / <alpha-value>)"
				},
                'nord-0': '#2E3440',
                'nord-1': '#3B4252',
                'nord-2': '#434C5E',
                'nord-3': '#4C566A',
                'nord-4': '#D8DEE9',
                'nord-5': '#E5E9F0',
                'nord-6': '#ECEFF4',
                'nord-7': '#8FBCBB',
                'nord-8': '#88C0D0',
                'nord-9': '#81A1C1',
                'nord-10': '#5E81AC',
                'nord-11': '#BF616A',
                'nord-12': '#D08770',
                'nord-13': '#EBCB8B',
                'nord-14': '#A3BE8C',
                'nord-15': '#B48EAD',
			},
            fontFamily: {
				sans: [...fontFamily.sans],
                oxanium: ["Oxanium", "sans-serif"],
                orbitron: ["Orbitron", "sans-serif"],
			},
		}
	},
};

export default config;
