const btnLogout = document.getElementById("btn-logout");
btnLogout.addEventListener("click", async (e) => {
    try {
        let res = await axios.post("/api/v1/logout")

        if(res.status === 200) {
            toastr.success("Đăng xuất thành công");
            setTimeout(() => {
                window.location.href = "/homepage"
            }, 1500)
        }
    } catch (e) {
        toastr.error("Đăng xuất thất bại");
        console.log(e)
    }
})