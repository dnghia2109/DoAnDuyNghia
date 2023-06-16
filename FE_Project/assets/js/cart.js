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

// Lấy thông tin từ Local Storage
var cartItems = localStorage.getItem("cartItems");
cartItems = JSON.parse(cartItems);

// Biến lưu trữ nội dung HTML
var htmlContent = "";
// Lặp qua các thông tin trong Local Storage
cartItems.forEach(function abs(product) {
  let { price, proTitle, backgroundImage } = product;
  // Tạo đoạn mã HTML cho mỗi thông tin
  var cartItemHTML = `
    <tr class="cart-item">
      <td class="product-imag pr">
        <div class="img-pr-main" style='background-image: ${backgroundImage};'></div>
      </td>
      <td data-label="Name" class="product-na pr">
        <a href="./product_detail.html">${proTitle}</a>
      </td>
      <td data-label="Unit Price" class="product-ri pr">
        <span class="amount">${price}</span>
      </td>
      <td data-label="Quantity" class="product-qatity pr">
          <div class="quantity">
              <button class="minus-btn btnpr">-</button>
              <span class="qtity">1</span>
              <button class="plus-btn btnpr">+</button>
          </div>
      </td>
      <td data-label="Into Money" class="product-sub pr">
          <span class="sub-amu"></span>
      </td>
      <td data-label="Delete" class="product-cl pr">
          <a href="#" class="cart-item-actions">
              <i class="fa-solid fa-trash-can"></i>
          </a>
      </td>
    </tr>
  `;

  // Thêm đoạn mã HTML vào biến htmlContent
  htmlContent += cartItemHTML;
});
// Gán nội dung của htmlContent vào innerHTML của phần tử cha trên trang
var cartTable = document.getElementById("table");
cartTable.innerHTML = htmlContent;

// Todo: giỏ hàng
const cartIcon = document.querySelector("#cart-link");
cartIcon.addEventListener("click", function () {
  window.location.href = "./cart.html";
});

// Todo: Xóa thông tin giỏ hàng
const deleteBtn = document.querySelectorAll(".cart-item-actions");
deleteBtn.forEach((i) => {
  i.addEventListener("click", () => {
    const cartItemElement = i.closest(".cart-item");
    // Ẩn hiện thông báo sản phẩm trong cart
    const notificationDiv = document.querySelector(".nav");
    const deleteNavMain = document.querySelector("#ok");
    const closeNavMain = document.querySelector("#cancel");
    notificationDiv.style.display = "block";
    deleteNavMain.addEventListener("click", function () {
      notificationDiv.style.display = "none";
      cartItemElement.remove();
      updateCartInfor();
    });
    closeNavMain.addEventListener("click", function () {
      notificationDiv.style.display = "none";
    });
    updateCartInfor();
  });
});

// Todo: Cập nhật số lượng
const dowBtn = document.querySelectorAll(".minus-btn");
dowBtn.forEach((button) => {
  button.addEventListener("click", () => {
    const quantityElement = button.nextElementSibling;
    const current = +quantityElement.textContent;
    if (current > 1) {
      quantityElement.textContent = current - 1;
      updateCartInfor();
    }
  });
});

const upBtn = document.querySelectorAll(".plus-btn");
upBtn.forEach((button) => {
  button.addEventListener("click", () => {
    const quantityElement = button.previousElementSibling;
    const current = +quantityElement.textContent;
    quantityElement.textContent = current + 1;
    updateCartInfor();
  });
});

// Todo: Tăng giảm số lượng và cập nhật lại giá
function updateCartInfor() {
  const emtyCart = document.querySelector(".wish-list-main");
  const cartTable = document.querySelector(".cart-table-main");
  const cartItems = document.querySelectorAll(".cart-item");
  const totalPriceElement = document.querySelector(".to-pr");
  if (cartItems.length == 0) {
    emtyCart.style.display = "block";
    cartTable.style.display = "none";
  } else {
    emtyCart.style.display = "none";
    cartTable.style.display = "block";
  }

  let totalPrice = 0;
  cartItems.forEach((cartItem) => {
    const price = +cartItem
      .querySelector(".amount")
      .textContent.replace(".", "")
      .replace("$", "");
    const quantity = +cartItem.querySelector(".qtity").textContent;

    const quantityElements = document.querySelectorAll(".qtity");
    const quantities = [];
    quantityElements.forEach((element) => {
      const quantity = element.textContent;
      quantities.push(quantity);
    });

    localStorage.setItem("quantities", JSON.stringify(quantities));

    const total = cartItem.querySelector(".sub-amu");
    const dowBtn = document.querySelector(".minus-btn");

    // Cập nhật giá tiền cho Item
    let itemTotal = price * quantity;
    let displayTotal = "$" + Math.round(itemTotal / 100).toFixed(2);
    total.textContent = displayTotal;
    totalPrice += price * quantity;
  });

  totalPriceElement.textContent = "$" + Math.round(totalPrice / 100).toFixed(2);
  var toPrValue = totalPriceElement.textContent;
  // Lưu trữ giá trị trong localStorage
  localStorage.setItem("toPrValue", toPrValue);
}

updateCartInfor();

function retTo() {
  window.location.href = "./blog.html";
}

function reTo() {
  window.location.href = "./contact.html";
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

function linkHome() {
  window.location.href = "/index.html";
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
