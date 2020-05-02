var startAll = function () {
    particlesJS.load('null-particles3', 'config/particles3.json');
    particlesJS.load('null-particles4', 'config/particles4.json');
    particlesJS.load('null-particles6', 'config/particles6.json');
};

if (document.readyState === "complete" || (document.readyState !== "loading" && !document.documentElement.doScroll)) {
    startAll();
} else {
    document.addEventListener("DOMContentLoaded", startAll);
}