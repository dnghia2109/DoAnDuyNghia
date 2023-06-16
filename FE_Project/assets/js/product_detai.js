// Todo: Loader
// function loader() {
//   document.querySelector(".loader-container").classList.add("fade-out");
// }
// function fadeOut() {
//   setInterval(loader, 500);
// }

// window.onload = fadeOut;

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

//todo: Close and Open Search
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

function redirect() {
  window.location.href = "product_detail.html";
}
//Todo: Slide Trending Items
$(".trendings").owlCarousel({
  loop: true,
  margin: 10,
  nav: true,
  // autoplay: true,
  // autoplayTimeout: 3000,
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

$(".adbh").owlCarousel({
  loop: true,
  margin: 10,
  nav: true,
  // autoplay: true,
  // autoplayTimeout: 3000,
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

var nextButton = $('span[aria-label="Next"]');// Tìm phần tử span có thuộc tính aria-label="Next"
nextButton.html('<i class="fa-solid fa-arrow-right-long"></i>');// Thay đổi nội dung của phần tử span
var nextButton = $('span[aria-label="Previous"]');
nextButton.html('<i class="fa-solid fa-arrow-left-long"></i>');

//Todo: PRODUCT IMAGE DETAIL
/*Box*/
function changeBorder(box) {
  var boxes = document.querySelectorAll(".muli");
  for (var i = 0; i < boxes.length; i++) {
    boxes[i].classList.remove("selected");
  }
  box.classList.add("selected");
}
// Todo: BG Image
function changeImage(img) {
  var mainImage = document.querySelector("#myimage");
  mainImage.src = img.src;
}

//Todo:HoverScaleImagemain
function imageZoom(imgID, resultID) {
  var img, lens, result, cx, cy;
  img = document.getElementById(imgID);
  result = document.getElementById(resultID);
  /*create lens:*/
  lens = document.createElement("DIV");
  lens.setAttribute("class", "img-zoom-lens");
  /*insert lens:*/
  img.parentElement.insertBefore(lens, img);
  /*calculate the ratio between result DIV and lens:*/
  cx = result.offsetWidth / lens.offsetWidth;
  cy = result.offsetHeight / lens.offsetHeight;
  /*set background properties for the result DIV:*/
  result.style.backgroundImage = "url('" + img.src + "')";
  result.style.backgroundSize = img.width * cx + "px " + img.height * cy + "px";
  /*execute a function when someone moves the cursor over the image, or the lens:*/
  lens.addEventListener("mousemove", moveLens);
  img.addEventListener("mousemove", moveLens);
  /*and also for touch screens:*/
  lens.addEventListener("touchmove", moveLens);
  img.addEventListener("touchmove", moveLens);
  function moveLens(e) {
    var pos, x, y;
    /*prevent any other actions that may occur when moving over the image:*/
    e.preventDefault();
    /*get the cursor's x and y positions:*/
    pos = getCursorPos(e);
    /*calculate the position of the lens:*/
    x = pos.x - lens.offsetWidth / 2;
    y = pos.y - lens.offsetHeight / 2;
    /*prevent the lens from being positioned outside the image:*/
    if (x > img.width - lens.offsetWidth) {
      x = img.width - lens.offsetWidth;
    }
    if (x < 0) {
      x = 0;
    }
    if (y > img.height - lens.offsetHeight) {
      y = img.height - lens.offsetHeight;
    }
    if (y < 0) {
      y = 0;
    }
    /*set the position of the lens:*/
    lens.style.left = x + "px";
    lens.style.top = y + "px";
    /*display what the lens "sees":*/
    result.style.backgroundPosition = "-" + x * cx + "px -" + y * cy + "px";
  }
  function getCursorPos(e) {
    var a,
      x = 0,
      y = 0;
    e = e || window.event;
    /*get the x and y positions of the image:*/
    a = img.getBoundingClientRect();
    /*calculate the cursor's x and y coordinates, relative to the image:*/
    x = e.pageX - a.left;
    y = e.pageY - a.top;
    /*consider any page scrolling:*/
    x = x - window.pageXOffset;
    y = y - window.pageYOffset;
    return { x: x, y: y };
  }
}

imageZoom("myimage", "myresult");



// Todo: NumberOfAddedShopping
function decrease() {
  var quantity = document.getElementById("quantity");
  if (quantity.value > 1) {
    quantity.value--;
  }
}

function increase() {
  var quantity = document.getElementById("quantity");
  if (quantity.value < 10) {
    quantity.value++;
  }
}

// Todo: WishLishIcon
function wishListAdd() {
  var iconWishLish = document.getElementById("icon");
  var count = 0;
  iconWishLish.addEventListener("click", function () {
    // Nếu người dùng click lần đầu tiên, chuyển màu của icon sang màu đỏ
    if (count % 2 != 0) {
      iconWishLish.style.color = "#b1b6bf";
      hideToast();
    }
    // Nếu người dùng click lần thứ hai, chuyển màu của icon trở lại màu ban đầu
    else {
      iconWishLish.style.color = "red";
      displayToast();
    }
  });
  function displayToast() {
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
              <p class="toast__msg">You have added the desired list.</p>
            </div>
            <div class="toast__close">
              <i class="fas fa-times"></i>
            </div>
        `;
    main.appendChild(toast);
  }

  function hideToast() {
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
              <h3 class="toast__title">Sory!</h3>
              <p class="toast__msg">The product has existed in the cart.</p>
            </div>
            <div class="toast__close">
              <i class="fas fa-times"></i>
            </div>
        `;
    main.appendChild(toast);
  }
}

wishListAdd();

// Todo: AddToCardMain
function addToCartMain() {
  var addToCart = document.querySelector(".custom-btn");
  var clickCount = 0;
  var cartCountElement = document.getElementById("cart-count");

  addToCart.addEventListener("click", function () {
    clickCount++;
    const main = document.getElementById("toast");
    const toast = document.createElement("div");
    const autoRemoveId = setTimeout(function () {
      main.removeChild(toast);
    }, 9000);

    toast.onclick = function (e) {
      if (e.target.closest(".toast__close")) {
        main.removeChild(toast);
        clearTimeout(autoRemoveId);
      }
    };

    toast.classList.add("tot");
    if (clickCount === 1) {
      cartCountElement.textContent = "4";
      toast.innerHTML = `
      <div class="toast__icon">
        <i class="fas fa-exclamation-circle"></i>
      </div>
      <div class="toast__body">
        <h3 class="toast__title">Success!</h3>
        <p class="toast__msg">You have added products to the cart.</p>
      </div>
      <div class="toast__close">
        <i class="fas fa-times"></i>
      </div>
    `;
    } else if (clickCount>= 2) {
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
    }

    main.appendChild(toast);
  });

}
addToCartMain();

// Todo: Click Color
const colorSpans = document.querySelectorAll(".choose-color");

colorSpans.forEach((colorSpan) => {
  colorSpan.addEventListener("click", () => {
    colorSpans.forEach((otherSpan) => {
      if (otherSpan !== colorSpan) {
        otherSpan.classList.remove("selected");
        otherSpan.innerHTML = "";
      }
    });
    colorSpan.classList.add("selected");
    colorSpan.innerHTML = "&#10003;";
  });
});

function testExist() {
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
}
testExist();


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

// Chặn load trang của thẻ a
const btnRadius = document.querySelectorAll(".btn-radius");
btnRadius.forEach((a) => {
  a.addEventListener("click", (e) => {
    e.preventDefault();
  });
});

function retTo() {
  window.location.href = "./blog.html";
}

function reTo() {
  window.location.href = "./contact.html";
}

function banerLink() {
  window.location.href = "./product_detail.html";
}

function linkHome() {
  window.location.href = "/index.html";
}

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


//Todo: Background-image Related products
function backGroundImageCart() {
  // Lấy tất cả các phần tử .product-img
  const productImages = document.querySelectorAll(".product-img");

  // Tạo một mảng chứa các URL hình ảnh mới (tùy thuộc vào nhu cầu của bạn)
  const imageUrls = [
    "url(./assets/images/ImagesFigma/product-furniture-1.jpg)",
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
  ];

  // Sử dụng forEach để gán background-image cho mỗi phần tử .product-img
  productImages.forEach((productImg, index) => {
    const imageUrl = imageUrls[index % imageUrls.length]; // Lấy URL hình ảnh tương ứng dựa trên vị trí trong mảng
    productImg.style.backgroundImage = imageUrl; // Gán background-image
  });
}

backGroundImageCart();

function addToCart(event) {
  var cardItem = event.currentTarget.closest(".card-item");

  // Get the background-image, pro-title, and price from the card item
  var backgroundImage =
    cardItem.querySelector(".product-img").style.backgroundImage;
  console.log(backgroundImage);
  var proTitle = cardItem.querySelector(".pro-title").textContent;
  console.log(proTitle);

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
  button.addEventListener("click", addToCart, { one: true });
  console.log(addToCartButtons); // Thêm option { once: true } để chỉ gọi hàm addToCart một lần
});

// Lấy icon giỏ hàng
const cartIcon = document.querySelector(".cart");
// Đính kèm sự kiện click cho icon giỏ hàng
cartIcon.addEventListener("click", () => {
    // Chuyển hướng đến trang chứa thông tin giỏ hàng
    window.location.href = "./cart.html";
});