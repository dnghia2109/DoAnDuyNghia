// Todo: Banner Header Top
document.getElementById("nextas").onclick = function () {
  let lists = document.querySelectorAll(".itemas");
  document.getElementById("slide").appendChild(lists[0]);
};

document.getElementById("prevas").onclick = function () {
  let lists = document.querySelectorAll(".itemas");
  document.getElementById("slide").prepend(lists[lists.length - 1]);
};

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
// // Todo: Loader
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

// Todo: Slider banner header
$(".owl-carousel").owlCarousel({
  loop: true,
  margin: 10,
  nav: true,
  autoplay: true,
  autoplayTimeout: 3000,
  responsive: {
    0: {
      items: 1,
    },
    600: {
      items: 1,
    },
    1000: {
      items: 1,
    },
  },
});
// Todo: Sort Price
const titleBtn = document.querySelector(".title-price-name");
const sortPrice = document.querySelector(".sort-price");
document.addEventListener("click", function (e) {
  if (e.target == titleBtn || e.target == sortPrice) {
    sortPrice.style.display = "block";
  } else {
    sortPrice.style.display = "none";
  }
});
//Nhận các nút sắp xếp và danh sách sản phẩm
const sortHighButton = document.getElementById("sort-high");
const sortLowButton = document.getElementById("sort-low");
const productList = document.getElementById("pr-rlisst");

// Sort products by price (high to low)
function sortProductsHighToLow() {
  // Chuyển đổi danh sách các sản phẩm thành một mảng và sắp xếp theo giá(cao sang thấp)
  const products = Array.from(productList.children);
  products.sort(
    (a, b) =>
      parseFloat(b.querySelector(".price").innerText.slice(1)) -
      parseFloat(a.querySelector(".price").innerText.slice(1))
  );
  // Làm trống danh sách sản phẩm và thêm vào các sản phẩm được sắp xếp
  while (productList.firstChild) {
    productList.removeChild(productList.firstChild);
  }
  products.forEach((product) => {
    productList.appendChild(product);
  });
}

// Sort products by price (low to high)
function sortProductsLowToHigh() {
  // Convert the list of products to an array and sort by price (low to high)
  const products = Array.from(productList.children);
  products.sort(
    (a, b) =>
      parseFloat(a.querySelector(".price").innerText.slice(1)) -
      parseFloat(b.querySelector(".price").innerText.slice(1))
  );
  // Empty the product list and re-add the sorted products
  while (productList.firstChild) {
    productList.removeChild(productList.firstChild);
  }
  products.forEach((product) => {
    productList.appendChild(product);
  });
}

// Add event listeners to the sort buttons
sortHighButton.addEventListener("click", sortProductsHighToLow);
sortLowButton.addEventListener("click", sortProductsLowToHigh);

// Todo: Phân trang
function dividePage() {
  const productMain = [
    {
      name: "Enim Expedita Sed",
      price: 45.0,
      image: "./assets/images/ImagesFigma/furniture_img3.jpg",
      del: "$55.00",
    },
    {
      name: "Chris Dining Chair",
      price: 47.0,
      image: "./assets/images/ImagesFigma/furniture_img1.jpg",
      del: "$59.00",
    },
    {
      name: "Silver Eat Chair",
      price: 25.0,
      image: "./assets/images/ImagesFigma/furniture_img2.jpg",
      del: "$37.00",
    },
    {
      name: "Denmark Channel",
      price: 95.0,
      image: "./assets/images/ImagesFigma/furniture5_3.jpg",
      del: "$100.00",
    },
    {
      name: "Hadley Eat Chair",
      price: 25.0,
      image: "./assets/images/ImagesFigma/furniture_img4.jpg",
      del: "$50.00",
    },
    {
      name: "Vienna Dining Chair",
      price: 95.00,
      image: "./assets/images/ImagesFigma/furniture_img5.jpg",
      del: "$105.00",
    },
    {
      name: "London Dining Chair",
      price: 12.00,
      image: "./assets/images/ImagesFigma/furniture_img6.jpg",
      del: "$24.00",
    },
    {
      name: "Northe Eating Sheet",
      price: 80.00,
      image: "./assets/images/ImagesFigma/furniture_img7.jpg",
      del: "$24.00",
    },
    {
      name: "Pu Leather Leather",
      price: 22.00,
      image: "./assets/images/ImagesFigma/furniture_img8.jpg",
      del: "$24.00",
    },
    {
      name: "Eames Plastic Side Chair",
      price: 70.00,
      image: "./assets/images/ImagesFigma/product-furniture1.jpg",
      del: "$84.00",
    },
    {
      name: "Scelerisque pulvinar ligula",
      price: 65.00,
      image: "./assets/images/ImagesFigma/furniture5_5.jpg",
      del: "$74.00",
    },
    {
      name: "Vivant janus charles",
      price: 37.00,
      image: "./assets/images/ImagesFigma/product-accessories-9-2.jpg",
      del: "$44.00",
    },
    {
      name: "Classic wooden chair",
      price: 40.00,
      image: "./assets/images/ImagesFigma/furniture10.jpg",
      del: "$75.00",
    },
    {
      name: "Milo Diet Chair",
      price: 140.00,
      image: "./assets/images/ImagesFigma/product-furniture-1.jpg",
      del: "$150.00",
    },
    {
      name: "Panton tunior chair",
      price: 110.00,
      image: "./assets/images/ImagesFigma/product-furniture-19.jpg",
      del: "$124.00",
    },
    {
      name: "Eames lounge chair",
      price: 190.00,
      image: "./assets/images/ImagesFigma/product-furniture-8.jpg",
      del: "$255.00",
    },
    {
      name: "Ornare auctor",
      price: 70.00,
      image: "./assets/images/ImagesFigma/light10_4.jpg",
      del: "$85.00",
    },
    {
      name: "Senectus nisi a imperdiet",
      price: 72.00,
      image: "./assets/images/ImagesFigma/furniture39.jpg",
      del: "$87.00",
    },
    {
      name: "Char with Magazine",
      price: 92.00,
      image: "./assets/images/ImagesFigma/product-furniture-5.jpg",
      del: "$107.00",
    },
    {
      name: "Ullamcorper nisl",
      price: 38.00,
      image: "./assets/images/ImagesFigma/furniture8.jpg",
      del: "$58.00",
    },
    {
      name: "Feelgood designs",
      price: 148.00,
      image: "./assets/images/ImagesFigma/product-accessories-12.jpg",
      del: "$198.00",
    },
    {
      name: "Gravida condimentum",
      price: 128.00,
      image: "./assets/images/ImagesFigma/furniture7-430.jpg",
      del: "$150.00",
    },
  ];

  const productsPerPage = 8;
  let currentPage = 1;
  const productList = document.getElementById("pr-rlisst");
  const pagination = document.querySelector(".pagination");
  const prev = document.querySelector(".prev");
  const next = document.querySelector(".next");

  prev.addEventListener("click", function () {
    if (currentPage > 1) {
      currentPage--;
      displayProducts();
    } else if ((currentPage = 1)) {
      checkCart();
    }
  });

  function numPages() {
    return Math.ceil(productMain.length / productsPerPage);
  }

  function displayProducts() {
    productList.innerHTML = "";
    let startIndex = (currentPage - 1) * productsPerPage;
    let endIndex = startIndex + productsPerPage;
    let pageProducts = productMain.slice(startIndex, endIndex);
    pageProducts.forEach((product) => {
      let productDiv = document.createElement("div");
      productDiv.classList.add("col-6", "col-md-4", "col-lg-3");
      productDiv.innerHTML = `
      <div class="card-item">
              <div class="product-img" style="background-image: url(${
                product.image
              });">
                  <div class="product-action-box">
                    <div class="pr-dc">
                      <a href="#" class="detail-product" onclick="redirect()">
                        <img src="./assets/images/ImagesFigma/detail-imag.svg" alt="">
                        </a>
                      </a>
                    </div>
                  </div>
                </div>
              <div class="product-info">
                <h6 class="pro-title">${product.name}</h6>
                <div class="product-price">
                  <span class="price">$${product.price}.00</span>
                  <del>${product.del}</del>
                </div>
                <div class="rating-wrap">
                  <div class="rating">
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-regular fa-star-half-stroke"></i>
                  </div>
                  <span class="rating-num">(21)</span>
                </div>
                <div class="add-to-cart" data-name="${
                  product.name
                }" data-price="${product.price.toFixed(2)}">
                  <a href="#" class="btn-radius"">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <span class="adtoca">Add To Cart</span>
                  </a>
                </div>
              </div>
            </div>
    `;
      productList.appendChild(productDiv);
    });
    updatePagination();
  }
  displayProducts();

  function updatePagination() {
    pagination.innerHTML = "";
    let totalPages = numPages();
    let startPage, endPage;
    if (totalPages <= 5) {
      startPage = 1;
      endPage = totalPages;
    } else {
      if (currentPage <= 3) {
        startPage = 1;
        endPage = 5;
      } else if (currentPage >= totalPages - 2) {
        startPage = totalPages - 4;
        endPage = totalPages;
      } else {
        startPage = currentPage - 2;
        endPage = currentPage + 2;
      }
    }
    for (let i = startPage; i <= endPage; i++) {
      let pageLink = document.createElement("a");
      pageLink.style.cursor = "pointer";
      pageLink.textContent = i;
      if (i === currentPage) {
        pageLink.classList.add("active");
      }
      pageLink.addEventListener("click", function () {
        currentPage = i;
        let scrollDistance;
        // Sử dụng media queries để xác định kích thước màn hình và khoảng cách cuộn tương ứng
        if (window.matchMedia("(min-width: 1024px)").matches) {
          // Màn desktop
          scrollDistance = 650;
        } else if (window.matchMedia("(min-width: 768px)").matches) {
          // Tablet
          scrollDistance = 750;
        } else {
          // Mobile
          scrollDistance = 530;
        }

        window.scrollTo({
          top: scrollDistance,
          behavior: "smooth",
        });
        displayProducts();
      });
      pagination.appendChild(pageLink);
    }
    if (currentPage > 1) {
      prev.style.display = "inline-block";
    } else {
      prev.style.display = "none";
    }
    if (currentPage < numPages()) {
      next.style.display = "inline-block";
    } else {
      next.style.display = "none";
    }
    const btnRadius = document.querySelectorAll(".btn-radius");
    btnRadius.forEach((a) => {
      a.addEventListener("click", (e) => {
        e.preventDefault();
      });
    });
    checkCart();
  }
}
dividePage();

function retTo() {
  window.location.href = "./blog.html";
}

function redirect() {
  window.location.href = "product_detail.html";
}

function reTo() {
  window.location.href = "./contact.html";
}

// function showContentCart() {
//   // Lấy icon giỏ hàng
//   const cartCount = document.getElementById("cart-count");
//   const cartIcon = document.querySelector(".cart");
//   // Đính kèm sự kiện click cho icon giỏ hàng
//   cartIcon.addEventListener("click", () => {
//     // Kiểm tra nếu giỏ hàng có sản phẩm
//     if (cartCount.textContent !== "0") {
//       // Chuyển hướng đến trang chứa thông tin giỏ hàng
//       window.location.href = "./cart.html";
//     } else {
//       const main = document.getElementById("toast");
//       const toast = document.createElement("div");
//       // Auto remove toast
//       const autoRemoveId = setTimeout(function () {
//         main.removeChild(toast);
//       }, 9000);

//       // Remove toast when clicked
//       toast.onclick = function (e) {
//         if (e.target.closest(".toast__close")) {
//           main.removeChild(toast);
//           clearTimeout(autoRemoveId);
//         }
//       };
//       toast.classList.add("tot");
//       toast.innerHTML = `
//             <div class="toast__icon">
//               <i class="fas fa-exclamation-circle"></i>
//             </div>
//             <div class="toast__body">
//               <h3 class="toast__title">Sorry!</h3>
//               <p class="toast__msg">No product has been added yet.</p>
//             </div>
//             <div class="toast__close">
//               <i class="fas fa-times"></i>
//             </div>
//         `;
//       main.appendChild(toast);
//     }
//   });
// }
// showContentCart();


// Todo: Add To Cart Icon Header
function checkCart() {
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
// checkCart();
function wishlist() {
  window.location.href = "/wishlistmain.html";
}

function linkHome() {
  window.location.href = "/index.html";
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

// Function to handle the click event for "Add To Cart" buttons
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
  button.addEventListener("click", addToCart, { one: true }); // Thêm option { once: true } để chỉ gọi hàm addToCart một lần
});

const cartIcon = document.querySelector(".cart");
var cartCount = document.getElementById("cart-count");
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
