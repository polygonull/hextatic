var startAll = function () {

    document.getElementById("null-email-wrapper").addEventListener("click", function(){
        document.getElementById("null-email").innerHTML = "polygonull" + "@" + "gmail.com";
        this.removeAttribute("id");
    });

    var adaptive_size = 30;
    var viewportWidth = window.innerWidth;

    if(viewportWidth < 1800) {
        adaptive_size = (viewportWidth / 75) + 6;
    }

    particlesJS("null-particles",
        {
            "particles": {
                "number": {
                    "value": 10,
                    "density": {
                        "enable": false,
                        "value_area": 950
                    }
                },
                "color": {
                    "value": ["#feac5e", "#de6eeb", "#56dae4", "#11dd88"]
                },
                "shape": {
                    "type": ["triangle", "edge", "polygon"],
                    "stroke": {
                        "width": 0,
                        "color": "#000000"
                    },
                    "polygon": {
                        "nb_sides": 6
                    },
                    "image": {
                        "src": "img/github.svg",
                        "width": 100,
                        "height": 100
                    }
                },
                "opacity": {
                    "value": 0.8,
                    "random": false,
                    "anim": {
                        "enable": false,
                        "speed": 1,
                        "opacity_min": 0.1,
                        "sync": false
                    }
                },
                "size": {
                    "value": adaptive_size,
                    "random": true,
                    "anim": {
                        "enable": false,
                        "speed": 10,
                        "size_min": 10,
                        "sync": false
                    }
                },
                "line_linked": {
                    "enable": false,
                    "distance": 150,
                    "color": "#ffffff",
                    "opacity": 0.4,
                    "width": 1
                },
                "move": {
                    "enable": true,
                    "speed": 2,
                    "direction": "none",
                    "random": false,
                    "straight": false,
                    "out_mode": "out",
                    "bounce": false,
                    "attract": {
                        "enable": false,
                        "rotateX": 600,
                        "rotateY": 1200
                    }
                }
            },
            "interactivity": {
                "detect_on": "canvas",
                "events": {
                    "onhover": {
                        "enable": false,
                        "mode": "repulse"
                    },
                    "onclick": {
                        "enable": false,
                        "mode": "push"
                    },
                    "resize": true
                },
                "modes": {
                    "grab": {
                        "distance": 400,
                        "line_linked": {
                            "opacity": 1
                        }
                    },
                    "bubble": {
                        "distance": 400,
                        "size": 40,
                        "duration": 2,
                        "opacity": 8,
                        "speed": 3
                    },
                    "repulse": {
                        "distance": 200,
                        "duration": 0.4
                    },
                    "push": {
                        "particles_nb": 1
                    },
                    "remove": {
                        "particles_nb": 2
                    }
                }
            },
            "retina_detect": true
        }
    );
};

if (document.readyState === "complete" || (document.readyState !== "loading" && !document.documentElement.doScroll)) {
    startAll();
} else {
    document.addEventListener("DOMContentLoaded", startAll);
}