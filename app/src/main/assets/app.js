document.addEventListener("DOMContentLoaded", function () {
    const imageInput = document.getElementById("image-input");
    const imagePreview = document.getElementById("image-preview");
    let currentCanvas = document.createElement("canvas");

    imageInput.addEventListener("change", function (e) {
        const file = e.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = function (event) {
            imagePreview.style.backgroundImage = "url(" + event.target.result + ")";
        };
        reader.readAsDataURL(file);
    });

    window.saveImage = function() {
        if (typeof Android !== "undefined") {
            Android.saveImage("base64_data_here");
            alert("ارسال به اندروید...");
        } else {
            alert("این قابلیت فقط در اپلیکیشن فعال است.");
        }
    };

    window.requestPermission = function() {
        document.getElementById("permission-page").style.display = "none";
        document.getElementById("register-page").style.display = "block";
    };

    document.getElementById("register-form").onsubmit = function(e) {
        e.preventDefault();
        document.getElementById("register-page").style.display = "none";
        document.getElementById("main-page").style.display = "block";
    };
});
