// Todo: Loader
// function loader() {
//   document.querySelector(".loader-container").classList.add("fade-out");
// }

// function fadeOut() {
//   setInterval(loader, 100);
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

// Todo: Pay
const contentBow = document.querySelector(".ct-box");
const payBoxCheck = document.querySelector(".content-bow-row");
const checkInput = document.querySelector(".paymentMethod");
const transportCotent = document.querySelector(".money-transport-content");
checkInput.addEventListener("click", () => {
  contentBow.textContent = "You only pay when receiving the goods";
});

transportCotent.addEventListener("click", () => {
  contentBow.textContent = "You only pay when receiving the goods";
  checkInput.checked = true;
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

// Todo: Check input sale
const inputSale = document.querySelector(".input-sale");
const btnSale = document.querySelector(".btn-sale");
const messSale = document.querySelector(".mess-sale");
btnSale.addEventListener("click", () => {
  if (inputSale.value === "") {
    messSale.textContent = "Please enter the discount code";
  } else {
    messSale.textContent = "Successfully pounded reducing codes";
  }
});

// Todo: Check input value click order
document.querySelector(".btn-order").addEventListener("click", () => {
  var inputEmail = document.querySelector(".input-email");
  var inputName = document.querySelector(".input-name");
  var inputNumber = document.querySelector(".input-number");
  var inputAddr = document.querySelector(".input-addr");
  var inputDistrict = document.querySelector(".input-district");
  var inputWard = document.querySelector(".input-ward");
  if (!inputEmail.value) {
    alert("Please enter your email.");
    inputEmail.focus();
    return false;
  }
  if (!inputName.value) {
    alert("Please enter your name.");
    inputName.focus();
    return false;
  }
  if (!inputNumber.value) {
    alert("Please enter your Phone.");
    inputNumber.focus();
    return false;
  }
  if (!inputAddr.value) {
    alert("Please enter your address.");
    inputAddr.focus();
    return false;
  }
  if (!inputDistrict.value) {
    alert("Please enter your district.");
    inputDistrict.focus();
    return false;
  }
  if (!inputWard.value) {
    alert("Please enter your ward.");
    inputWard.focus();
    return false;
  }
  alert("Thank you for your message!");
  window.location.href = "./payment_success.html";
  return true;
});

// Todo: Input option value click order
// const selectName = document.querySelector("#select-phone");
// const inputSelect = document.getElementById("ip-select");
// selectName.addEventListener("change", () => {
//   const selectedOption = selectName.options[selectName.selectedIndex];
//   inputSelect.value = selectedOption.value;
// })

// const inSelect = document.getElementById("ip-se");
// selectName.addEventListener("change", () => {
//   const selectedOption = selectName.options[selectName.selectedIndex];
//   inSelect.value = selectedOption.value;
// });

// Todo: Select Option Input
function selectInputMain() {
  // Phone
  const emailInput = document.querySelector("#ip-select");
  const billingPhoneRegion = document.querySelector(
    "select[name=billingPhoneRegion]"
  );

  billingPhoneRegion.addEventListener("change", () => {
    const selectedOption =
      billingPhoneRegion.options[billingPhoneRegion.selectedIndex];
    emailInput.value = selectedOption.value;
  });
  // Province
  const emailInput1 = document.querySelector("#ip-select1");
  const billingProvinceRegion = document.querySelector(
    "select[name=billingProvinceRegion]"
  );

  billingProvinceRegion.addEventListener("change", () => {
    const selectedOption =
      billingProvinceRegion.options[billingProvinceRegion.selectedIndex];
    emailInput1.value = selectedOption.value;
  });
  // District
  const emailInput2 = document.querySelector("#ip-select2");
  const billingDistrictRegion = document.querySelector(
    "select[name=billingDistrictRegion]"
  );

  billingDistrictRegion.addEventListener("change", () => {
    const selectedOption =
      billingDistrictRegion.options[billingDistrictRegion.selectedIndex];
    emailInput2.value = selectedOption.value;
  });
  // Ward
  const emailInput3 = document.querySelector("#ip-select3");
  const billingWardRegion = document.querySelector(
    "select[name=billingWardRegion]"
  );

  billingWardRegion.addEventListener("change", () => {
    const selectedOption =
      billingWardRegion.options[billingWardRegion.selectedIndex];
    emailInput3.value = selectedOption.value;
  });
}

selectInputMain();

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

function saveFormData() {
  // Get the values from the input fields
  var email = document.getElementById("ip-select-4").value;
  var name = document.getElementById("ip-select-5").value;
  var phoneNumber = document.getElementById("ip-select").value;
  var province = document.getElementById("ip-select1").value;
  var district = document.getElementById("ip-select2").value;
  var ward = document.getElementById("ip-select3").value;

  // Store the values in localStorage
  localStorage.setItem("email", email);
  localStorage.setItem("name", name);
  localStorage.setItem("phoneNumber", phoneNumber);
  localStorage.setItem("province", province);
  localStorage.setItem("district", district);
  localStorage.setItem("ward", ward);

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
}
saveFormData();

// Lấy giá trị từ localStorage
var toPrValue = localStorage.getItem("toPrValue");

// In giá trị vào phần tử <td>
var tltTotalPaymentPriceElement = document.querySelector(".tlt-total-payment-price");
tltTotalPaymentPriceElement.textContent = toPrValue;
