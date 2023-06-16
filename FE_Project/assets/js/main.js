document.getElementById("nextas").onclick = function () {
  let lists = document.querySelectorAll(".itemas");
  document.getElementById("slide").appendChild(lists[0]);
};

document.getElementById("prevas").onclick = function () {
  let lists = document.querySelectorAll(".itemas");
  document.getElementById("slide").prepend(lists[lists.length - 1]);
};

//Todo: Background-image Cart
function backGroundImageCart() {
  // Lấy tất cả các phần tử .product-img
  const productImages = document.querySelectorAll(".product-img");

  // Tạo một mảng chứa các URL hình ảnh mới (tùy thuộc vào nhu cầu của bạn)
  const imageUrls = [
    "url(./assets/images/ImagesFigma/furniture_img1.jpg)",
    "url(./assets/images/ImagesFigma/furniture_img2.jpg)",
    "url(./assets/images/ImagesFigma/furniture_img3.jpg)",
    "url(./assets/images/ImagesFigma/furniture_img4.jpg)",
    "url(./assets/images/ImagesFigma/furniture_img5.jpg)",
    "url(./assets/images/ImagesFigma/furniture_img6.jpg)",
    "url(./assets/images/ImagesFigma/furniture_img7.jpg)",
    "url(./assets/images/ImagesFigma/furniture_img8.jpg)",
    "url(./assets/images/ImagesFigma/furniture10.jpg)",
    "url(./assets/images/ImagesFigma/furniture5_3.jpg)",
    "url(./assets/images/ImagesFigma/furniture7-430.jpg)",
    "url(./assets/images/ImagesFigma/furniture5_5.jpg)",
    "url(./assets/images/ImagesFigma/furniture39.jpg)",
    "url(./assets/images/ImagesFigma/product-accessories-12.jpg)",
    "url(./assets/images/ImagesFigma/product-furniture1.jpg)",
    "url(./assets/images/ImagesFigma/img-pro-01.webp)",
  ];

  // Sử dụng forEach để gán background-image cho mỗi phần tử .product-img
  productImages.forEach((productImg, index) => {
    const imageUrl = imageUrls[index % imageUrls.length]; // Lấy URL hình ảnh tương ứng dựa trên vị trí trong mảng
    productImg.style.backgroundImage = imageUrl; // Gán background-image
  });
}
backGroundImageCart();

// Todo: Block LogIn
const Account = document.querySelector(".account");
const showAccount = document.querySelector(".bg-form");
const signUps = document.querySelector(".signup");
const logIns = document.querySelector(".login");
const closeAccount = document.querySelector("#close-form");
const closeAcc = document.querySelector(".cl-fo");
Account.addEventListener("click", function () {
  showAccount.style.display = "block";
});

// Todo: Hide Account
closeAccount.addEventListener("click", function () {
  showAccount.style.display = "none";
});

closeAcc.addEventListener("click", function () {
  showAccount.style.display = "none";
});

function signUp() {
  logIns.style.display = "none";
  signUps.style.display = "block";
}

function logIn() {
  logIns.style.display = "block";
  signUps.style.display = "none";
}

// Todo: Hide Show Notification
const btnAccept = document.querySelector(".accept-noti");
const boxNotifi = document.querySelector(".notification-account");
btnAccept.addEventListener("click", function () {
  boxNotifi.style.display = "none";
});

function subLog() {
  var fullname = document.querySelectorAll("#fullname").value;
  document.querySelector(".signup-submit").addEventListener("click", (e) => {
    e.preventDefault();
  });
  var email = document.querySelectorAll("#email").value;
  if (fullname !== "" && email !== "") {
    boxNotifi.style.display = "block";
    showAccount.style.display = "none";
  }
}

function subSign() {
  var fullname = document.querySelectorAll("#fullname").value;
  var email = document.querySelectorAll("#email").value;
  if (fullname !== "" && email !== "") {
    boxNotifi.style.display = "block";
    showAccount.style.display = "none";
  }
}

// Todo: Loader
// function loader() {
//   document.querySelector(".loader-container").classList.add("fade-out");
// }

// function fadeOut() {
//   setInterval(loader, 100);
// }

// window.onload = fadeOut;

// Todo: Search
const search = document.querySelector(".search");
const searchBtn = document.querySelector(".search-btn");

// Thêm sự kiện "click" cho phần tử "search"
search.addEventListener("click", function () {
  searchBtn.classList.add("sebtn");
});

// Thêm sự kiện "click" cho toàn bộ tài liệu (document)
document.addEventListener("click", function (event) {
  // Kiểm tra xem phần tử được nhấp chuột có nằm trong phần tử "search" hay không
  const isClickInsideSearch = search.contains(event.target);

  // Nếu phần tử được nhấp chuột không nằm trong phần tử "search", xóa lớp CSS "sebtn" khỏi "searchBtn"
  if (!isClickInsideSearch) {
    searchBtn.classList.remove("sebtn");
  }
});

// Todo: Reponsive Bars
function myFunction(x) {
  x.classList.toggle("change");
}

var close = document.querySelector(".close");
var dropDow = document.querySelector(".dropdow-menu");
close.addEventListener("click", function () {
  if (dropDow.style.display === "block") {
    dropDow.style.display = "none";
  } else {
    dropDow.style.display = "block";
  }
});

//Todo: Slide Trending Items
$(".trending-items").owlCarousel({
  loop: true,
  margin: 10,
  nav: true,
  autoplay: true,
  autoplayTimeout: 3000,
  responsive: {
    0: {
      items: 1,
    },
    400: {
      items: 2,
    },
    768: {
      items: 3,
    },
    992: {
      items: 4,
    },
  },
});
var nextButton = $('span[aria-label="Next"]'); // Tìm phần tử span có thuộc tính aria-label="Next"
nextButton.html('<i class="fa-solid fa-arrow-right-long"></i>'); // Thay đổi nội dung của phần tử span
var nextButton = $('span[aria-label="Previous"]');
nextButton.html('<i class="fa-solid fa-arrow-left-long"></i>');

//Todo: SLIDE BANNER IMAGE
$(".slide-banner-image").owlCarousel({
  loop: true,
  margin: 10,
  nav: true,
  autoplay: true,
  autoplayTimeout: 5000,
  responsive: {
    0: {
      items: 2,
    },
    600: {
      items: 3,
    },
    1000: {
      items: 6,
    },
  },
});

//Todo: COOPERATE IMAGE
$(".cooperate-main").owlCarousel({
  loop: true,
  margin: 10,
  nav: true,
  autoplay: true,
  autoplayTimeout: 3000,
  responsive: {
    0: {
      items: 2,
    },
    600: {
      items: 3,
    },
    1000: {
      items: 6,
    },
  },
});

function redirect() {
  window.location.href = "product_detail.html";
}

// Todo: Add To Cart Icon Header
var addToCartButtons = document.getElementsByClassName("add-to-cart");
var cartCount = document.getElementById("cart-count");

var itemsInCart = [];

for (var i = 0; i < addToCartButtons.length; i++) {
  addToCartButtons[i].addEventListener("click", function () {
    var itemName = this.getAttribute("data-name");
    var itemPrice = parseFloat(this.getAttribute("data-price"));
    var itemExists = false;

    // Kiểm tra sản phẩm đã tồn tại trong danh sách hay chưa
    for (var j = 0; j < itemsInCart.length; j++) {
      if (itemsInCart[j].name === itemName) {
        itemExists = true;
        break;
      }
    }

    // Nếu sản phẩm chưa tồn tại, thêm sản phẩm vào danh sách
    if (!itemExists) {
      itemsInCart.push({ name: itemName, price: itemPrice });
      cartCount.innerHTML = itemsInCart.length;
      const main = document.getElementById("toast");
      const toast = document.createElement("div");
      // Auto remove toast
      const autoRemoveId = setTimeout(function () {
        main.removeChild(toast);
      }, 4000);

      // Remove toast when clicked
      toast.onclick = function (e) {
        if (e.target.closest(".toast__close")) {
          main.removeChild(toast);
          clearTimeout(autoRemoveId);
        }
      };
      const delay = (3).toFixed(2);
      toast.classList.add("tot");
      toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;
      toast.innerHTML = `
          <div class="toast__icon">
            <i class="fas fa-check-circle"></i>
          </div>
          <div class="toast__body">
            <h3 class="toast__title">Success!</h3>
            <p class="toast__msg">You have added products to the cart.</p>
          </div>
          <div class="toast__close">
            <i class="fas fa-times"></i>
          </div>
      `;
      main.appendChild(toast);
    } else {
      const main = document.getElementById("toast");
      const toast = document.createElement("div");
      // Auto remove toast
      const autoRemoveId = setTimeout(function () {
        main.removeChild(toast);
      }, 9000);

      // Remove toast when clicked
      toast.onclick = function (e) {
        if (e.target.closest(".toast__close")) {
          main.removeChild(toast);
          clearTimeout(autoRemoveId);
        }
      };
      toast.classList.add("tot");
      toast.innerHTML = `
          <div class="toast__icon">
            <i class="fas fa-exclamation-circle"></i>
          </div>
          <div class="toast__body">
            <h3 class="toast__title">Sorry!</h3>
            <p class="toast__msg">The product has existed in the cart.</p>
          </div>
          <div class="toast__close">
            <i class="fas fa-times"></i>
          </div>
      `;
      main.appendChild(toast);
    }
  });
}

// Chặn load trang của thẻ a
const btnRadius = document.querySelectorAll(".btn-radius");
btnRadius.forEach((a) => {
  a.addEventListener("click", (e) => {
    e.preventDefault();
  });
});

// Todo: Giỏ hàng
// Lấy icon giỏ hàng
const cartIcon = document.querySelector(".cart");
// Đính kèm sự kiện click cho icon giỏ hàng
cartIcon.addEventListener("click", () => {
  // Kiểm tra nếu giỏ hàng có sản phẩm
  if (cartCount.textContent !== "0") {
    // Chuyển hướng đến trang chứa thông tin giỏ hàng
    window.location.href = "./cart.html";
  } else {
    // Ẩn hiện thông báo sản phẩm trong cart
    const notificationDiv = document.querySelector(".nav");
    const closeNavMain = document.querySelector("#cancle");
    notificationDiv.style.display = "block";
    closeNavMain.addEventListener("click", function () {
      notificationDiv.style.display = "none";
    });
  }
});

function retTo() {
  window.location.href = "./blog.html";
}

function reTo() {
  window.location.href = "./contact.html";
}

function showContentCart() {
  // Lấy icon giỏ hàng
  const cartCount = document.getElementById("cart-count");
  const cartIcon = document.querySelector(".cart");
  // Đính kèm sự kiện click cho icon giỏ hàng
  cartIcon.addEventListener("click", () => {
    // Kiểm tra nếu giỏ hàng có sản phẩm
    if (cartCount.textContent !== "0") {
      // Chuyển hướng đến trang chứa thông tin giỏ hàng
      window.location.href = "./cart.html";
    } else {
      const main = document.getElementById("toast");
      const toast = document.createElement("div");
      // Auto remove toast
      const autoRemoveId = setTimeout(function () {
        main.removeChild(toast);
      }, 9000);

      // Remove toast when clicked
      toast.onclick = function (e) {
        if (e.target.closest(".toast__close")) {
          main.removeChild(toast);
          clearTimeout(autoRemoveId);
        }
      };
      toast.classList.add("tot");
      toast.innerHTML = `
            <div class="toast__icon">
              <i class="fas fa-exclamation-circle"></i>
            </div>
            <div class="toast__body">
              <h3 class="toast__title">Sorry!</h3>
              <p class="toast__msg">No product has been added yet.</p>
            </div>
            <div class="toast__close">
              <i class="fas fa-times"></i>
            </div>
        `;
      main.appendChild(toast);
    }
  });
}
showContentCart();

function wishlist() {
  window.location.href = "/wishlistmain.html";
}

function homeLink() {
  window.location.href = "/index.html";
}

function categoryList() {
  window.location.href = "/category_list.html";
}

function Update() {
  const main = document.getElementById("toast");
  const toast = document.createElement("div");
  // Auto remove toast
  const autoRemoveId = setTimeout(function () {
    main.removeChild(toast);
  }, 9000);

  // Remove toast when clicked
  toast.onclick = function (e) {
    if (e.target.closest(".toast__close")) {
      main.removeChild(toast);
      clearTimeout(autoRemoveId);
    }
  };
  toast.classList.add("tot");
  toast.innerHTML = `
            <div class="toast__icon">
              <i class="fas fa-exclamation-circle"></i>
            </div>
            <div class="toast__body">
              <h3 class="toast__title">Sorry!</h3>
              <p class="toast__msg">This feature is being updated.</p>
            </div>
            <div class="toast__close">
              <i class="fas fa-times"></i>
            </div>
        `;
  main.appendChild(toast);
}

function banerLink() {
  window.location.href = "./product_detail.html";
}

// Todo: Thông báo animation khi có sản phẩm cart
function navCartAnimation() {
  const cart = document.querySelector(".cart");
  const spanElement = document.getElementById("cart-count");
  if (parseInt(spanElement.innerHTML) > 0) {
    cart.classList.add("mycart");
  } else {
    cart.classList.remove("mycart");
  }
}
// Gọi function navCartAnimation() khi giá trị trong cart-count thay đổi
document
  .getElementById("cart-count")
  .addEventListener("DOMSubtreeModified", navCartAnimation);

// Back To Top
// window.onscroll = function () {
//   scrollFunction();
// };

// var scrollToTopBtn = document.querySelector(".back-to-top");

// window.onscroll = function () {
//   scrollFunction();
// };

// function scrollFunction() {
//   if (
//     document.body.scrollTop > 100 ||
//     document.documentElement.scrollTop > 100
//   ) {
//     scrollToTopBtn.style.display = "block";
//   } else {
//     scrollToTopBtn.style.display = "none";
//   }
// }

// function scrollToTop() {
//   var currentPosition =
//     document.documentElement.scrollTop || document.body.scrollTop;
//   if (currentPosition > 0) {
//     window.requestAnimationFrame(scrollToTop);
//     window.scrollTo(0, currentPosition - currentPosition / 10); // Tốc độ cuộn có thể được điều chỉnh ở đây
//   }
// }



// Todo: Hide Show Password
const eyeIcon = document.querySelector(".bx");
const pwFields = document.querySelector("#pass-show");
eyeIcon.addEventListener("click", () => {
  if (pwFields.type === "password") {
    pwFields.type = "text";
    eyeIcon.classList.replace("bx-hide", "bx-show");
    return;
  } else {
    pwFields.type = "password";
    eyeIcon.classList.replace("bx-show", "bx-hide");
  }
});

// Function to handle the click event for "Add To Cart" buttons
function addToCart(event) {
  var cardItem = event.currentTarget.closest(".card-item");

  // Get the background-image, pro-title, and price from the card item
  var backgroundImage = cardItem.querySelector(".product-img").style.backgroundImage;
  // console.log(backgroundImage);
  var proTitle = cardItem.querySelector(".pro-title").textContent;
  // console.log(proTitle);

  var price = cardItem.querySelector(".price").textContent;

  // Create an object to store the product information
  var product = {
    backgroundImage: backgroundImage,
    proTitle: proTitle,
    price: price,
  };
  
  // Retrieve existing cart items from local storage (if any)
  var cartItems = localStorage.getItem("cartItems");
  if (cartItems) {
    cartItems = JSON.parse(cartItems);
  } else {
    cartItems = [];
  }

  // Add the product to the cart items array
  cartItems.push(product);

  // Store the updated cart items back in local storage
  localStorage.setItem("cartItems", JSON.stringify(cartItems));
}

// Get all "Add To Cart" buttons
var addToCartButtons = document.querySelectorAll(".add-to-cart");
addToCartButtons.forEach(function (button) {
  button.addEventListener("click", addToCart, { one: true });// Thêm option { once: true } để chỉ gọi hàm addToCart một lần
});
