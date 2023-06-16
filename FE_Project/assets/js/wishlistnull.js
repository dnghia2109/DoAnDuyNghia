// Todo: Loader
// function loader() {
//   document.querySelector(".loader-container").classList.add("fade-out");
// }

// function fadeOut() {
//   setInterval(loader, 1000);
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

// Todo: Quantity

// Todo: Tăng giảm số lượng và cập nhật lại giá
const minusBtn = document.querySelector(".minus-btn");
const plusBtn = document.querySelector(".plus-btn");
const quantityInput = document.querySelector(".quantity-input");
const subAmu = document.querySelector(".sub-amu");
const amount = document.querySelector(".amount");

function updateSubtotal() {
  const cartItems = document.querySelectorAll(".cart-item");
  cartItems.forEach((item) => {
    const quantityInput = item.querySelector(".quantity-input");
    const quantityValue = parseInt(quantityInput.value);
    const priceValue = parseInt(
      item.querySelector(".amount").textContent.replace(/\./g, "")
    );
    const subtotal = quantityValue * priceValue * 1000000;
    item.querySelector(".sub-amu").textContent = formatMoney(subtotal);
  });
}

function formatMoney(value) {
  return value.toLocaleString("vi-VN", { style: "currency", currency: "VND" });
}
plusBtn.addEventListener("click", function () {
  let quantity = parseInt(quantityInput.value);
  quantity++;
  quantityInput.value = quantity;
  let price = parseInt(amount.textContent.replace(/,/g, ""));
  let subPrice = price * quantity;
  subAmu.textContent = subPrice.toLocaleString("en-US") + "đ";
  updateSubtotal();
});

minusBtn.addEventListener("click", () => {
  const quantityInput = minusBtn.parentElement.querySelector(".quantity-input");
  let quantityValue = parseInt(quantityInput.value);
  if (quantityValue > 0) {
    quantityValue--;
    quantityInput.value = quantityValue;
    updateSubtotal();
  }
});

// Todo: Xóa Cart Item
const cartItem = document.querySelector(".cart-item");
const del = document.querySelector(".fa-trash-can");
del.addEventListener("click", function () {
  cartItem.remove();
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
