var startAll = function () {
    particlesJS.load('null-particles', 'config/particlesjs.json');
};

if (document.readyState === "complete" || (document.readyState !== "loading" && !document.documentElement.doScroll)) {
    startAll();
} else {
    document.addEventListener("DOMContentLoaded", startAll);
}