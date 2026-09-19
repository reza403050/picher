document.addEventListener("DOMContentLoaded", function () {
 document.getElementById("image-input").addEventListener("change", function () {
 const reader = new FileReader();
 reader.onload = function () {
 document.getElementById("image-preview").style.backgroundImage = "url(" + reader.result + ")";
 // پردازش تصویر با Canvas API برای اعمال فیلترها
 const canvas = document.createElement("canvas");
 const ctx = canvas.getContext("2d");
 const img =