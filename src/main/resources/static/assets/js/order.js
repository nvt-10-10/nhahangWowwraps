$(document).ready(function () {
    function showSuccessNotification(boolean, message) {
        console.log("Hàm thông báo được gọi");
        $(".Notification").css("display", "flex");
        $(".message").text(message);
        if (boolean == 2) {
            $(".notification__icon").attr('src', '/assets/icons/index-admin/warning.png');
        } else if (boolean) {
            $(".notification__icon").attr('src', '/assets/icons/index-admin/error.png');
        } else {
            $(".notification__icon").attr('src', '/assets/icons/index-admin/success.png');
        }
        setTimeout(function () {
            console.log("Thông báo sẽ ẩn sau 3 giây");
            $(".Notification").fadeOut("slow");
        }, 3000);
    }

    function updateList(url) {

        console.log("url: " + url);
        $.ajax({
            type: "GET", url: url, // Thay đổi thành endpoint của bạn
            success: function (objects) {
                var indexpage = parseInt($("#indexPage").val());
                var ObjectListHtml = '';
                var editsvg = `
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512">
                                <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                                <path d="M288 32c-80.8 0-145.5 36.8-192.6 80.6C48.6 156 17.3 208 2.5 243.7c-3.3 7.9-3.3 16.7 0 24.6C17.3 304 48.6 356 95.4 399.4C142.5 443.2 207.2 480 288 480s145.5-36.8 192.6-80.6c46.8-43.5 78.1-95.4 93-131.1c3.3-7.9 3.3-16.7 0-24.6c-14.9-35.7-46.2-87.7-93-131.1C433.5 68.8 368.8 32 288 32zM144 256a144 144 0 1 1 288 0 144 144 0 1 1 -288 0zm144-64c0 35.3-28.7 64-64 64c-7.1 0-13.9-1.2-20.3-3.3c-5.5-1.8-11.9 1.6-11.7 7.4c.3 6.9 1.3 13.8 3.2 20.7c13.7 51.2 66.4 81.6 117.6 67.9s81.6-66.4 67.9-117.6c-11.1-41.5-47.8-69.4-88.6-71.1c-5.8-.2-9.2 6.1-7.4 11.7c2.1 6.4 3.3 13.2 3.3 20.3z"/>
                            </svg>
`;
                var cancelsvg = `
                    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
                    <style> .cancel-button svg {
                    fill: #ff0000
                }</style>
                <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
            </svg>`;
                var confirmsvg = `
                         <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512">
                                <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                                <style> .confirm-button svg {
                                    fill: #3bff05
                                }</style>
                                <path d="M438.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L160 338.7 393.4 105.4c12.5-12.5 32.8-12.5 45.3 0z"/>
                            </svg>
                `
                objects.forEach(function (item, index) {
                    var indexpageRow = index + 1 + indexpage * 10;
                    // Thêm timestamp vào URL của hình ảnh
                    ObjectListHtml += `
    <div class="table-item">
           <div class="table-item-column"><p>${item.id}</p></div>
                    <div class="table-item-column ">
                        <p  class="line-clamp">${item.name}</p>
                    </div>
                    <div class="table-item-column">
                        <p>${item.dishList}</p>
                    </div>
                
                    <div class="table-item-column">
                        <p  class="line-clamp">${item.message != null ? item.message : ''}</p>
                    </div>
            <div class="table-item-column table-item-action">
                 <a  class="edit-button"  data-id="${item.id}">
                       ${editsvg}
                    </a>
                <a  class="confirm-button" data-id="${item.id}">
                        ${confirmsvg}
                    </a>
                      <a  class="cancel-button" data-id="${item.id}">
                        ${cancelsvg}
                    </a>
            </div>
        </div>
    </div>`;
                });

                // Cập nhật nội dung danh sách chef
                $("#List").html(ObjectListHtml);
            }
        });
    }


    $(".icon-close").on("click", function () {
        $("#inputFile").attr("required", "required");
        $(".Add-edit").css("display", "none");
        $(".Reason").css('display', 'none');

    });
    $(".add-btn").on("click", function () {
        $(".form-title").text("Add Book");
        $("#name").val("");
        $(".Add-edit:not(#reasonSection)").css("display", "flex");
    });


    $("#reasonForm").on('submit', function (event) {
        event.preventDefault();
        var keyword = $.cookie("keyword");
        var url;

        if (keyword != null && keyword.length > 0) {
            url = 'api/order/search/' + keyword + '/';
        } else {
            url = 'api/order/indexPage/';
        }
        var formData = $("#reasonForm").serialize();
        formData += "&keyword=" + keyword; // Thay "newKey" và "newValue" bằng giá trị thực tế
        console.log(formData);
        $.ajax({
            url: "order/cancel",
            type: "post",
            data: formData,
            success: function (response) {
                var activePageElement = $('.page__active');
                var indexpage = activePageElement.data('indexpage');
                if (indexpage == null) {
                    indexpage = 0;
                }
                console.log(url);
                showSuccessNotification(response.error, response.mess);
                updateList(url + indexpage);
                updatePagination(response.pageCount, indexpage);
                $(".Add-edit").css("display", "none");

                console.log("Success: " + response);
            },
            error: function () {
                console.error("Error occurred.");
            }
        });
    });


    $("#addForm").on('submit', function (event) {
        event.preventDefault();
        var keyword = $.cookie("keyword");
        var url;

        if (keyword != null && keyword.length > 0) {
            url = 'api/order/search/' + keyword + '/';
        } else {
            url = 'api/order/indexPage/';
        }
        var formData = $("#addForm").serialize();
        formData += "&keyword=" + keyword; // Thay "newKey" và "newValue" bằng giá trị thực tế
        console.log(formData);
        $.ajax({
            url: "order/save",
            type: "post",
            data: formData,
            success: function (response) {
                var activePageElement = $('.page__active');
                var indexpage = activePageElement.data('indexpage');
                if (indexpage == null) {
                    indexpage = 0;
                }
                console.log(url);
                showSuccessNotification(response.error, response.message);
                updateList(url + indexpage);
                updatePagination(response.pageCount, indexpage);
                $(".Add-edit").css("display", "none");
                console.log("Success: " + response);
            },
            error: function () {
                console.error("Error occurred.");
            }
        });
    });


    $("#List").on('click', '.show-button', function () {
        event.preventDefault();
        $(".form-title").text("Edit Book");
        var id = $(this).data("id");
        $("#id").val(categoryid);
        console.log("da click");
        var url = 'api/order/Byid/' + id;
        $.ajax({
            url: url, // Sửa thành chefId thay vì id
            type: 'GET', // Sửa thành 'GET'
            success: function (object) { // Thêm từ khóa 'function' trước (chef)
                $("#name").text(object.name);
                $("#companyName").text(object.companyName);
                $("#email").text(object.name);
                $("#phone").text(object.phone);
                $("#date").text(object.date);
                $("#address").text(object.address);
                $("#total_amount").text(object.total_amount);
                $("#voucher").text(object.voucherFloat);
                $("#intoMoney").text(object.intoMoney);
                $("#paymentMethods").text(object.PaymentMethodsString);
                $("#listProduct").text(object.dishList);
                $("#message").text(object.message);
                $(".Add-edit").css("display", "flex");
            }

        });
    });
    var categoryid;


    $("#List").on('click', '.confirm-button', function () {
        var keyword = $.cookie("keyword");
        var id = $(this).data('id');
        $.ajax({
            type: 'get',
            url: "order/confirm?id=" + id + "&keyword=" + keyword,
            success(reponse) {
                var url;
                var activePageElement = $('.page__active');
                var indexpage = activePageElement.data('indexpage');
                if (indexpage == null)
                    indexpage = 0;

                if (indexPage > reponse.pageCount) {
                    indexPage--;
                }
                if (keyword != null && keyword.length > 0) {
                    url = 'api/order/search/' + keyword + '/' + indexpage;
                } else {
                    url = '/api/order/indexPage/' + indexpage;
                }
                console.log("indexPage" + indexPage);
                console.log("reponse.pageCount" + reponse.pageCount);
                console.log(reponse.pageCount);
                console.log(url + indexPage);
                showSuccessNotification(false, reponse.message);
                updateList(url);
                $(".confirm").fadeOut(500);

                // updatePagination(reponse.pageCount, indexpage);
                $(".confirm").css('display', 'none');
            }
        });
    });

    $("#List").on('click', '.cancel-button', function () {
        var keyword = $.cookie("keyword");
        var id = $(this).data('id');
        $("#reasonForm #bookId").val(id);
        $("#reasonSection").css('display', 'flex');
    });


    $("#confirm__Action__delete").on('click', function () {
        var keyword = $.cookie("keyword");
        console.log("categoryid" + categoryid);
        $.ajax({
            type: 'delete',
            url: "order/delete?id=" + categoryid + "&keyword=" + keyword,
            success(reponse) {
                var url;

                var activePageElement = $('.page__active');
                var indexpage = activePageElement.data('indexpage');
                if (indexpage == null)
                    indexpage = 0;
                if (reponse.error) {
                    showSuccessNotification(true, "Them that bai");
                } else {
                    if (indexPage > reponse.pageCount) {
                        indexPage--;
                    }
                    if (keyword != null && keyword.length > 0) {
                        url = 'api/order/search/' + keyword + '/' + indexpage;
                    } else {
                        url = '/api/order/indexPage/' + indexpage;
                    }
                    console.log("indexPage" + indexPage);
                    console.log("reponse.pageCount" + reponse.pageCount);
                    console.log(reponse.pageCount);


                    console.log(url + indexPage);

                    showSuccessNotification(false, "Xóa thành công");
                    updateList(url);
                    $(".confirm").fadeOut(500);
                }
                // updatePagination(reponse.pageCount, indexpage);
                $(".confirm").css('display', 'none');
            }
        });
    });


    $("#searchForm").on('submit', function () {
        event.preventDefault();
        console.log("da click");
        const keyword = $(".input-search").val();
        console.log("keyword" + keyword);
        $.ajax({

            type: 'post', url: 'order/search', data: {keyword: keyword}, success(response) {
                $("#indexPage").val(0);
                if (keyword != null && keyword.trim().length > 0) {
                    updateList('api/order/search/' + keyword + '/0');
                } else {
                    updateList('api/order/indexPage/' + 0);
                }
                console.log('response.pageCount: ' + response.pageCount);
                updatePagination(response.pageCount, 0);
            }
        })

    })

    function updatePagination(pageCount, indexPage) {
        var paginationContainer = $("#paginationContainer");
        console.log("indexPage", indexPage);
        console.log("pageCount", pageCount);
        var newPaginationContent = `
        <div class="page" ${pageCount > 1 ? '' : 'style="display: none;"'}>
            <a href="" class="page__link" id="backPage">
                <svg xmlns="http://www.w3.org/2000/svg" width="21" height="10" viewBox="0 0 21 10" fill="none">
                    <path d="M20 5H1M1 5C2.43939 4.78667 5.31818 3.688 5.31818 1M1 5C2.43939 5.21333 5.31818 6.312 5.31818 9" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                Previous
            </a>
            <ul class="page__list" data-countpage="${pageCount}">
                ${Array.from({length: pageCount}, (_, pageNum) => `
                    <li class="page__item">
                        <a href="#!" class="page__item__link ${pageNum == indexPage ? 'page__active' : ''}" data-indexpage="${pageNum}">
                            ${pageNum + 1}
                        </a>
                    </li>
                `).join('')}
            </ul>
            <a href="" class="page__link"  id="nextPage">
                Next
                <svg class="page__next" xmlns="http://www.w3.org/2000/svg" width="21" height="10" viewBox="0 0 21 10" fill="none">
                    <path d="M20 5H1M1 5C2.43939 4.78667 5.31818 3.688 5.31818 1M1 5C2.43939 5.21333 5.31818 6.312 5.31818 9" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </a>
        </div>
    `;
        paginationContainer.html(newPaginationContent);
    }


    $("#paginationContainer").on('click', '.page__item__link', function () {
        event.preventDefault();
        var keyword = $.cookie("keyword");
        var url;

        if (keyword != null && keyword.length > 0) {
            url = 'api/order/Search/' + keyword + '/';
        } else {
            url = 'api/order/indexPage/';
            url = 'api/order/indexPage/';
        }
        const indexpage = $(this).data('indexpage');
        const countPage = $(".page__list").data("countpage");
        console.log("keyword", keyword);
        $("#indexPage").val(parseInt(indexpage));
        updateList(url + indexpage);
        console.log(url + indexpage);
        updatePagination(countPage, indexpage);
    })

    $("#paginationContainer").on('click', '#backPage', function () {
        event.preventDefault();
        pageAction('back');
    });

    $("#paginationContainer").on('click', '#nextPage', function () {
        event.preventDefault();
        pageAction('next');
    });

    function pageAction(action) {
        var keyword = $.cookie("keyword");
        var url;

        if (keyword != null && keyword.length > 0) {
            url = 'api/order/search/' + keyword + '/';
        } else {
            url = 'api/order/indexPage/';
        }

        var activePageElement = $('.page__active');
        var indexpage = activePageElement.data('indexpage');
        const countPage = $(".page__list").data("countpage");
        console.log("countPage", countPage);
        console.log("indexpage", indexpage);
        if (action == 'back' && indexpage > 0) {
            indexpage--;
        } else if (action == 'next' && indexpage < countPage - 1) {
            indexpage++;
        }

        $("#indexPage").val(parseInt(indexpage)); // Đặt giá trị trang hiện tại vào một phần tử HTML nào đó (ví dụ: input#indexPage)
        updateList(url + indexpage);
        updatePagination(countPage, indexpage);
    }

    function updatedata() {

        if ($.cookie("updateDataListOrder") != null && $.cookie("updateDataListOrder") === "true") {

            var keyword = $.cookie("keyword");
            var activePageElement = $('.page__active');
            var indexpage = 0;

            if (activePageElement.length > 0) {
                indexpage = activePageElement.data('indexpage');
            }

            if (keyword != null && keyword.length > 0) {
                url = 'api/order/search/' + keyword + '/' + indexpage;
            } else {
                url = '/api/order/indexPage/' + indexpage;
            }


            updateList(url);
            showSuccessNotification(2, "Có đơn hàng mới");
        }
    }

    setInterval(updatedata, 5000);
});

