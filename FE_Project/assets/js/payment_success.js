// Todo: Loader
// function loader() {
//   document.querySelector(".loader-container").classList.add("fade-out");
// }

// function fadeOut() {
//   setInterval(loader, 1000);
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
// Todo: Không load lại trang khi click vào thẻ a trong delete
const links = document.querySelectorAll(".cart-item-actions");
links.forEach((link) => {
  link.addEventListener("click", (event) => {
    event.preventDefault();
  });
});

function retTo() {
  window.location.href = "./blog.html";
}

function reTo() {
  window.location.href = "./contact.html";
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

function banerLink() {
  window.location.href = "./product_detail.html";
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

//Todo: Lấy thông tin từ locoStorate
function getInformation() {
  // Retrieve the stored values from localStorage
  var buyerName = localStorage.getItem("name");
  var buyerEmail = localStorage.getItem("email");
  var buyerPhoneNumber = localStorage.getItem("phoneNumber");
  var shippingName = localStorage.getItem("name");
  var shippingProvince = localStorage.getItem("province");
  var shippingAddress =
    localStorage.getItem("district") + ", " + localStorage.getItem("ward");
  var shippingPhoneNumber = localStorage.getItem("phoneNumber");

  // Update the HTML elements with the retrieved values
  document.getElementById("buyerName").textContent = buyerName;
  document.getElementById("buyerEmail").textContent = buyerEmail;
  document.getElementById("buyerPhoneNumber").textContent = buyerPhoneNumber;
  document.getElementById("shippingName").textContent = shippingName;
  document.getElementById("shippingProvince").textContent = shippingProvince;
  document.getElementById("shippingAddress").textContent = shippingAddress;
  document.getElementById("shippingPhoneNumber").textContent =
    shippingPhoneNumber;

  var cartItems = localStorage.getItem("cartItems");
  cartItems = JSON.parse(cartItems);
  var htmlContent = "";
  // Lặp qua các thông tin trong Local Storage
  cartItems.forEach(function abs(product) {
    let { price, proTitle, backgroundImage } = product;
    const imageUrl = backgroundImage.match(/url\(["']?(.*?)["']?\)/)[1];
    // Tạo đoạn mã HTML cho mỗi thông tin
    var cartItemHTML = `
                <tr class="pr-main">
                    <td class="prduct-image">
                        <div class="prduct-thumbnail">
                            <div class="prduct-img-thum">
                                <div class="bg-img" style='background-image: url("${imageUrl}")'></div>
                                <span class="pr-thum-quan">1</span>
                            </div>
                        </div>
                    </td>
                    <td class="prduct-des">
                        <span class="prduct-des-name">${proTitle}</span>
                    </td>
                    <td class="prduct-quantity"></td>
                    <td class="prduct-price">${price}</td>
                </tr>
  `;

    // Thêm đoạn mã HTML vào biến htmlContent
    htmlContent += cartItemHTML;
  });
  // Gán nội dung của htmlContent vào innerHTML của phần tử cha trên trang
  var cartTable = document.getElementById("tbl");
  cartTable.innerHTML = htmlContent;

  // Lấy giá trị từ localStorage
  var toPrValue = localStorage.getItem("toPrValue");

  // In giá trị vào phần tử <td>
  var tltTotalPaymentPriceElement = document.querySelector(
    ".tlt-total-payment-price"
  );
  tltTotalPaymentPriceElement.textContent = toPrValue;
}
getInformation();
