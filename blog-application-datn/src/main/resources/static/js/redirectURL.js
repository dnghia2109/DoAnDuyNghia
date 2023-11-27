function saveRedirectUrl() {
    // Lưu địa chỉ URL hiện tại vào sessionStorage
    sessionStorage.setItem('redirectUrl', window.location.href);
}

function redirectToSavedUrl() {
    // Đọc địa chỉ URL đã lưu từ sessionStorage
    const redirectUrl = sessionStorage.getItem('redirectUrl');
    window.location.href = redirectUrl;

    // if (redirectUrl) {
    //     // Chuyển hướng đến địa chỉ URL đã lưu
    //
    // } else {
    //     // Nếu không có địa chỉ URL đã lưu, chuyển hướng đến trang mặc định
    //     window.location.href = "/homepage";
    // }
}

function clearSavedRedirectUrl() {
    // Xóa địa chỉ URL đã lưu từ sessionStorage
    sessionStorage.removeItem('redirectUrl');
}