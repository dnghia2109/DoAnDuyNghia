
// Người dùng Đăng ký nhận tin mới
let isValid = true;
const btnSubcribe = document.getElementById("btn-subcribe");
const emailSubcribeEl = document.getElementById("email-subcribe");
btnSubcribe.addEventListener("click", async (e) => {
    try {
        const emailVal = emailSubcribeEl.value;
        console.log(emailVal)
        if (emailVal.length === 0) {
            toastr.error("Bạn chưa nhập email!");
            isValid = false;
        }
        const request = {
            emailVal
        }
        if (isValid) {
            let res = await axios.post("/api/v1/user/subcribe-news",{
                'email' : emailVal
            });
            console.log(emailVal)
            if (res.status === 200) {
                e.preventDefault()
                toastr.success("Đăng ký nhận tin tức thành công");
                emailSubcribeEl.value = "";
                // alert(`Đăng ký thành công bạn sẽ nhận được các bài báo mới vào 6 giờ sáng mỗi ngày tại địa chỉ email - ${emailVal}`)
            } else {
                toastr.error("Không thành công")
            }
        }
    } catch (err) {
        toastr.warning(err.response.data.message);
        console.log(err);
    }
})