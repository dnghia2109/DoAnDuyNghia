// render blogs from blogs to body of table
const tableBody = document.querySelector('table tbody');
const renderBanners = (bannerList) => {
    let html = '';
    bannerList.forEach(banner => {
        html += `
                  <tr>
                      <td class="text-center">
                          <a href="/dashboard/admin/advertisement/${banner.id}/detail">${banner.name}</a>
                      </td>
                      <td>
                          ${banner.status ? `<span class="badge badge-success">Kích hoạt</span>` : `<span class="badge badge-secondary">Vô hiệu hóa</span>`}
                      </td>
                      <td>
                          <a href="${banner.linkRedirect}">${banner.linkRedirect}</a>
                      </td>
                      <td>${formatDate(banner.createdAt)}</td>
                  </tr>
              `;
    })
    tableBody.innerHTML = html;
}

const formatDate = dateString => {
    const date = new Date(dateString);
    const day = `0${date.getDate()}`.slice(-2);
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();
    const hour = `0${date.getHours()}`.slice(-2);
    const minute = `0${date.getMinutes()}`.slice(-2);
    const second = `0${date.getSeconds()}`.slice(-2);
    return `${hour}:${minute}:${second} - ${day}/${month}/${year}`;
}
// render pagination using pagination.js
const renderPagination = () => {
    $('#pagination').pagination({
        dataSource: banners,
        className: 'paginationjs-theme-blue paginationjs-big',
        pageSize : 10,
        callback: function (data, pagination) {
            renderBanners(data);
        },
        // hideOnlyOnePage: true
    })
}
renderPagination();