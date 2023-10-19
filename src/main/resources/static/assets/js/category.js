$(document).ready(function () {
    function showSuccessNotification(boolean, message) {
        $(".Notification").css("display", "flex");
        $(".message").text(message);
        if (boolean) {
            $(".notification__icon").attr('src', '/assets/icons/index-admin/error.png');
        } else {
            $(".notification__icon").attr('src', '/assets/icons/index-admin/success.png');
        }
        setTimeout(function () {
            $(".Notification").fadeOut("slow");
        }, 3000); // Thời gian hiển thị thông báo (3 giây)
        // $(".Notification").css("display", "none");
    }


    $("#close").on("click", function () {
        $("#inputFile").attr("required", "required");
        $(".Add-edit").css("display", "none");
    });
    $(".add-btn").on("click", function () {
        $(".form-title").text("Add Category");
        $("#name").val("");
        $(".Add-edit").css("display", "flex");
    });

    $("#addForm").on('submit', function (event) {
        event.preventDefault();
        var keyword = $.cookie("keyword");
        var url;

        if (keyword != null && keyword.length > 0) {
            url = 'api/category/search/' + keyword + '/';
        } else {
            url = 'api/category/indexPage/';
        }
        var formData = $("#addForm").serialize();
        formData += "&keyword=" + keyword; // Thay "newKey" và "newValue" bằng giá trị thực tế
        console.log(formData);
        $.ajax({
            url: "category/save",
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
                // console.log("indexpage", indexpage);
                // console.log("pageCount", response.pageCount);
                $(".Add-edit").css("display", "none");

                console.log("Success: " + response);
            },
            error: function () {
                console.error("Error occurred.");
            }
        });
    });


    function updateList(url) {

        console.log("url: " + url);
        $.ajax({
            type: "GET", url: url, // Thay đổi thành endpoint của bạn
            success: function (categories) {
                var indexpage = parseInt($("#indexPage").val());
                var chefListHtml = '';
                var deleteIconSvg = `
    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512" class="action-delete">
        <path d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"/>
    </svg>`;
                var editsvg = `
      <svg xmlns="http://www.w3.org/2000/svg" height="1em"  viewBox="0 0 512 512">
                            <path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"
                            />
                        </svg>
`;


                categories.forEach(function (category, index) {
                    var indexpageRow = index + 1 + indexpage * 10;
                    // Thêm timestamp vào URL của hình ảnh
                 chefListHtml += `
    <div class="table-item">
        <div class="table-item-column"><p>${indexpageRow}</p></div>
        <div class="table-item-column"><p>${category.id}</p></div>
        <div class="table-item-column"><p>${category.name}</p></div>
        <div class="table-item-column table-item-action">
            <div class="table-item-column table-item-action">
                 <a  class="edit-button"  data-id="${category.id}">
                       ${editsvg}
                    </a>
                <a  class="delete-button" data-id="${category.id}">
                        ${deleteIconSvg}
                    </a>
            </div>
        </div>
    </div>`;
                });

                // Cập nhật nội dung danh sách chef
                $("#List").html(chefListHtml);
            }
        });
    }

    $("#List").on('click', '.edit-button', function () {
        event.preventDefault();
        $(".form-title").text("Edit Category");
        var categoryid = $(this).data("id");
        $("#id").val(categoryid);
        var url = 'api/category/Byid/' + categoryid;
        $.ajax({
            url: url, // Sửa thành chefId thay vì id
            type: 'GET', // Sửa thành 'GET'
            success: function (category) { // Thêm từ khóa 'function' trước (chef)
                $("#name").val(category.name);
                $("#id").val(category.id);
                $(".Add-edit").css("display", "flex"); // Di chuyển vào ngoài success để hiển thị phần tạo hoặc chỉnh sửa đầu bếp
            }
        });
    });
    var categoryid;
    $("#List").on('click', '.delete-button', function () {
        event.preventDefault();
        categoryid = $(this).attr("data-id");
        console.log("hello");
        $("#confirm__Action__delete").attr('data-id', categoryid);
        $(".confirm").css('display', 'flex').fadeIn(500);

    });

    $("#confirm__Action__No").on('click', function () {
        $(".confirm").fadeOut(500);
    })

    $("#confirm__Action__delete").on('click', function () {
        var keyword = $.cookie("keyword");
        console.log("categoryid" + categoryid);
        $.ajax({
            type: 'delete',
            url: "category/delete?id=" + categoryid + "&keyword=" + keyword,
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
                        url = 'api/category/search/' + keyword + '/' + indexpage;
                    } else {
                        url = '/api/category/indexPage/' + indexpage;
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

            type: 'get', url: 'category/search', data: {keyword: keyword}, success(response) {
                $("#indexPage").val(0);
                if (keyword != null && keyword.trim().length > 0) {
                    updateList('api/category/search/' + keyword + '/0');
                } else {
                    updateList('api/category/indexPage/' + 0);
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
            url = 'api/category/Search/' + keyword + '/';
        } else {
            url = 'api/category/indexPage/';
            url = 'api/category/indexPage/';
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
            url = 'api/category/search/' + keyword + '/';
        } else {
            url = 'api/category/indexPage/';
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


});