// Function to set active navigation item
function setActiveNavItem(navItem) {
    $('.menu__nav__link').removeClass('menu__nav__active'); // Remove active class from all nav items
    $(navItem).addClass('menu__nav__active'); // Add active class to the clicked nav item
}

// Function to get all menu items
function getAllMenuItems() {
    event.preventDefault();
    const currentNavItem = event.target;
    setActiveNavItem(currentNavItem);
    // Here you can call the API to get all menu items, if needed
    // For this example, we will display all menu items directly
    $.ajax({
        url: '/api/product/all', method: 'GET', dataType: 'json', success: function (data) {
            populateMenuItems(data);
        }, error: function (error) {
            console.log('Error fetching all menu items:', error);
        }
    });
}

// Function to get menu items by category
function getMenuItemsByCategory(categoryId) {
    const currentNavItem = event.target;
    setActiveNavItem(currentNavItem);
    event.preventDefault();
    $.ajax({
        url: `/api/product/${categoryId}`, method: 'GET', dataType: 'json', success: function (data) {
            populateMenuItems(data);
        }, error: function (error) {
            console.log('Error fetching menu items by category:', error);
        }
    });
}

// Function to populate menu items
function populateMenuItems(menuItems) {
    const menuList = $('#menuList');
    menuList.empty(); // Clear the previous menu items

    $.each(menuItems, function (index, item) {
        const menuItemHtml = `
<div>
      <a href= "productDetails/${item.id}" >
        <article class="menu__item">
          <figure class="menu__item__thumb">
            <img
              loading="lazy"
              src="${item.imageLink}"
              alt="${item.name}"
              class="menu__item__img"
            />
          </figure>
          <h4 class="menu__item__name">
            ${item.name}
          </h4>
          <span class="menu__item__price">${item.formattedPrice}</span>
        </article>
      </a>
</div>
    `;

        menuList.append(menuItemHtml);
    });
}


$(document).ready(function () {
    const chefItems = $(".chef__item");
    const chefSize = chefItems.length;
    console.log(chefSize); // Kích thước của mảng chefItems

    // Khai báo biến index và chefsize trước khi sử dụng
    let index = 0;
    const chefsize = chefItems.length - 3;

    // if (window.location.hash === "#book-section") {
    //     var targetSection = $("#book-section");
    //     if (targetSection.length) {
    //         var offset = targetSection.offset().top;
    //
    //         $("html, body").animate({
    //             scrollTop: offset
    //         }, 1000); // 1000 mili giây (1 giây) cho hiệu ứng cuộn
    //     }
    // }

    $('.menu__nav__link').on('click', function () {
        const categoryId = $(this).data('category-id');
        if (categoryId !== undefined) {
            getMenuItemsByCategory(categoryId);
        } else {
            getAllMenuItems();
        }
        // Đảm bảo xử lý phần còn lại của sự kiện click không thực hiện (thường là chuyển trang)
        return false;
    });

    function handleButtonClick(action) {
        console.log(action === "back");
        console.log(index);
        console.log(chefsize);

        if (action === "back" && index > 0) {
            index -= 1;
        }

        if (action === "next" && index < chefsize) {
            index += 1;
        }

        chefItems.css("transform", `translateX(calc(-${index} * 100% - ${30 * index}px))`);
    }

    // Gán sự kiện "click" cho nút "chef_Cta_back" và "chef_Cta_next"
    $("#chef_Cta_back").on("click", function () {
        handleButtonClick("back");
    });

    $("#chef_Cta_next").on("click", function () {
        handleButtonClick("next");
    });

    $("#submitBook").on('click', function (event) {
        event.preventDefault();
        var formData = $("#book-form").serialize();
        formData+="&keyword=";
        console.log(formData);
        $.ajax({
            url: '/book/save', type: 'post', data: formData, success: function (reponse) {
                alert("Cảm ơn bạn đã đặt bạn. Bạn vui lòng xem thông báo gmail để nhận được phẩn hồi của Wawwraps");
                $("#book-form")[0].reset();
            }
        })
    })

    $("#submitSubscribe").on('click', function (event) {
        event.preventDefault();
        var formData = $(".Subscribe-form").serialize();

        $.ajax({
            url: '/subscribe/save', type: 'post', data: formData, success: function (reponse) {
                // Đặt thời gian tắt thông báo sau 3 giây (3000 milliseconds)
                alert(reponse.mess);

            }
        })
    })
});
