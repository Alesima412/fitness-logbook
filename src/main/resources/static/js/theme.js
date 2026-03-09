function initTheme() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
        document.documentElement.setAttribute('data-theme', savedTheme);
    }
}
initTheme(); // Run immediately to prevent flash of unstyled content

document.addEventListener("DOMContentLoaded", () => {
    const toggleBtn = document.getElementById("themeToggle");
    
    function updateToggleButton(theme) {
        if (toggleBtn) {
            if (theme === "dark") {
                toggleBtn.innerHTML = "☀️"; // Sun icon for switching to light
            } else {
                toggleBtn.innerHTML = "🌙"; // Moon icon for switching to dark
            }
        }
    }

    if (toggleBtn) {
        toggleBtn.addEventListener("click", () => {
            let currentTheme = document.documentElement.getAttribute("data-theme");
            if (!currentTheme) {
                const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
                currentTheme = prefersDark ? "dark" : "light";
            }
            let newTheme = currentTheme === "dark" ? "light" : "dark";
            document.documentElement.setAttribute("data-theme", newTheme);
            localStorage.setItem("theme", newTheme);
            updateToggleButton(newTheme);
        });
        
        let initialTheme = document.documentElement.getAttribute("data-theme");
        if (!initialTheme) {
            const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
            initialTheme = prefersDark ? "dark" : "light";
        }
        updateToggleButton(initialTheme);
    }
});