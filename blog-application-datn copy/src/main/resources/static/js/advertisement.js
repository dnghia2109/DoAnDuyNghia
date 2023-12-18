var closeButton1 = document.getElementById("closeAds1");
var closeButton2 = document.getElementById("closeAds2");
var closeButton3 = document.getElementById("closeAds3");

var imgElement1 = document.querySelector(".advertisement01");
var imgElement2 = document.querySelector(".advertisement02");
var imgElement3 = document.querySelector(".gif-qc");

closeButton1.addEventListener("click", function () {
    imgElement1.style.display = "none";
});

closeButton2.addEventListener("click", function () {
    imgElement2.style.display = "none";
});

closeButton3.addEventListener("click", function () {
    imgElement3.style.display = "none";
});




// Làm ẩn hiện Ads
document.addEventListener("DOMContentLoaded", function () {
    const gifQC = document.querySelector(".gif-qc");
    const adsMain01 = document.querySelector(".advertisement01");
    const adsMain02 = document.querySelector(".advertisement02");
    const footer = document.querySelector(".footer");
    const footerTop = footer.offsetTop;
    let isFixed = true;

    const lastNewParent = document.querySelector(".lastest-new-parent-1");

    window.addEventListener("scroll", function () {
        var scrollDistance = window.scrollY;
        var distanceToFooter = footerTop - scrollDistance;
        var scrollThreshold = 500;
        var scrollAbs = 700;
        if (
            distanceToFooter <= scrollThreshold &&
            distanceToFooter <= scrollAbs &&
            isFixed
        ) {
            gifQC.style.position = "absolute";
            gifQC.style.transform = "translateY(" + -distanceToFooter + "px)";
            adsMain01.style.position = "absolute";
            adsMain01.style.transform = "translateY(" + -distanceToFooter + "px)";
            adsMain02.style.position = "absolute";
            adsMain02.style.transform = "translateY(" + -distanceToFooter + "px)";
            isFixed = false;
        } else if (
            distanceToFooter > scrollThreshold &&
            distanceToFooter > scrollAbs &&
            !isFixed
        ) {
            gifQC.style.position = "fixed";
            gifQC.style.transform = "translateY(0)";
            adsMain01.style.position = "fixed";
            adsMain01.style.transform = "translateY(0)";
            adsMain02.style.position = "fixed";
            adsMain02.style.transform = "translateY(0)";
            isFixed = true;
        }
    });
});