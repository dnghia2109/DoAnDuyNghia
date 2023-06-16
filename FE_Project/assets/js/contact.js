// // Todo: Loader
// function loader() {
//   document.querySelector(".loader-container").classList.add("fade-out");
// }

// function fadeOut() {
//   setInterval(loader, 500);
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
// Lấy icon giỏ hàng
const cartIcon = document.querySelector(".cart");
// Đính kèm sự kiện click cho icon giỏ hàng
cartIcon.addEventListener("click", () => {
    // Chuyển hướng đến trang chứa thông tin giỏ hàng
    window.location.href = "./cart.html";
});