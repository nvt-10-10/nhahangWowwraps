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
      <svg xmlns="http://www.w3.org/2000/svg" height="1em"  viewBox="0 0 512 512">
                            <path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"
                            />
                        </svg>
`;
                var cancelsvg=`
                    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
                    <style> .cancel-button svg {
                    fill: #ff0000
                }</style>
                <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
            </svg>`;
                var confirmsvg=`
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
                        <p>${item.numberPerson}</p>
                    </div>
                    <div class="table-item-column">
                        <p>${item.dateFormat}</p>
                    </div>
                    <div class="table-item-column">
                        <p>${item.formattedTime}</p>
                    </div>
                    <div class="table-item-column">
                        <p  class="line-clamp">${item.message!=null?item.message:''}</p>
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
        $(".Reason").css('display','none');

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
            url = 'api/book/search/' + keyword + '/';
        } else {
            url = 'api/book/indexPage/';
        }
        var formData = $("#reasonForm").serialize();
        formData += "&keyword=" + keyword; // Thay "newKey" và "newValue" bằng giá trị thực tế
        console.log(formData);
        $.ajax({
            url: "book/cancel",
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
            url = 'api/book/search/' + keyword + '/';
        } else {
            url = 'api/book/indexPage/';
        }
        var formData = $("#addForm").serialize();
        formData += "&keyword=" + keyword; // Thay "newKey" và "newValue" bằng giá trị thực tế
        console.log(formData);
        $.ajax({
            url: "book/save",
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



    $("#List").on('click', '.edit-button', function () {
        event.preventDefault();
        $(".form-title").text("Edit Book");
        var categoryid = $(this).data("id");
        $("#id").val(categoryid);
        console.log("da click");
        var url = 'api/book/Byid/' + categoryid;
        $.ajax({
            url: url, // Sửa thành chefId thay vì id
            type: 'GET', // Sửa thành 'GET'
            success: function (book) { // Thêm từ khóa 'function' trước (chef)
                $("#id").val(book.id);
                $("#name").val(book.name);
                $("#phone").val(book.phone);
                $("#email").val(book.email);
                $("#numberPerson").val(book.numberPerson);
                $("#time").val(book.time);
                $("#date").val(book.dateFormat);
                $("#status").val(book.status);
                $("#message").val(book.message);
                $(".Add-edit:not(#reasonSection)").css("display", "flex"); // Di chuyển vào ngoài success để hiển thị phần tạo hoặc chỉnh sửa đầu bếp
            }
        });
    });
    var categoryid;


    $("#List").on('click', '.confirm-button', function () {
        var keyword = $.cookie("keyword");
        var id = $(this).data('id');
        $.ajax({
            type: 'get',
            url: "book/confirm?id=" + id + "&keyword=" + keyword,
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
                        url = 'api/book/search/' + keyword + '/' + indexpage;
                    } else {
                        url = '/api/book/indexPage/' + indexpage;
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
        $("#reasonSection").css('display','flex');
    });


    $("#confirm__Action__delete").on('click', function () {
        var keyword = $.cookie("keyword");
        console.log("categoryid" + categoryid);
        $.ajax({
            type: 'delete',
            url: "book/delete?id=" + categoryid + "&keyword=" + keyword,
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
                        url = 'api/book/search/' + keyword + '/' + indexpage;
                    } else {
                        url = '/api/book/indexPage/' + indexpage;
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

            type: 'post', url: 'book/search', data: {keyword: keyword}, success(response) {
                $("#indexPage").val(0);
                if (keyword != null && keyword.trim().length > 0) {
                    updateList('api/book/search/' + keyword + '/0');
                } else {
                    updateList('api/book/indexPage/' + 0);
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

    // $("#paginationContainer").on('click', '.page__item__link', function () {
    //     event.preventDefault();
    //     const indexpage = $(this).data('indexpage');
    //     const countPage = $(".page__list").data("countpage");
    //     console.log("countPage", countPage);
    //     updateChefList('api/chef/indexPage/' + indexpage);
    //     updatePagination(countPage, indexpage);
    // })

    $("#paginationContainer").on('click', '.page__item__link', function () {
        event.preventDefault();
        var keyword = $.cookie("keyword");
        var url;

        if (keyword != null && keyword.length > 0) {
            url = 'api/book/Search/' + keyword + '/';
        } else {
            url = 'api/book/indexPage/';
            url = 'api/book/indexPage/';
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
            url = 'api/book/search/' + keyword + '/';
        } else {
            url = 'api/book/indexPage/';
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

        if ($.cookie("updateDataListBook") != null && $.cookie("updateDataListBook") === "true") {

            var keyword = $.cookie("keyword");
            var activePageElement = $('.page__active');
            var indexpage = 0;

            if (activePageElement.length > 0) {
                indexpage = activePageElement.data('indexpage');
            }

            if (keyword != null && keyword.length > 0) {
                url = 'api/book/search/' + keyword + '/' + indexpage;
            } else {
                url = '/api/book/indexPage/' + indexpage;
            }


            updateList(url);
            showSuccessNotification(2, "Có người đặt bàn mới");
        }
    }

    setInterval(updatedata, 5000);

});

