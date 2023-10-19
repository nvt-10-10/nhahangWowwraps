
function lineChart(url,label,id){
    $.ajax({
        url: url,
        type: 'get',
        success: function(list) {
            var labels = [];
            var data = [];

            for (var i = 0; i < list.length; i++) {
                labels.push(list[i].name);
                data.push(parseFloat(list[i].value));
            }
            var ctx = $('#'+id)[0].getContext("2d");
            var chart = new Chart(ctx, {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: label,
                            data: data,
                            fill: false,
                            backgroundColor: "rgba(0,0,255,1.0)",
                            borderColor: "rgba(0,0,255,0.1)",
                            lineTension: 0,
                            showLine: true,

                        },
                    ],
                },

            });
        }
    });
}

function pieChart(url, label, id){
    $.ajax({
        url: url,
        type: 'get',
        success: function(list) {
            var labels = [];
            var data = [];

            for (var i = 0; i < list.length; i++) {
                labels.push(list[i].name);
                data.push(parseFloat(list[i].value));
            }


            var ctx = $('#'+id);
            var barColors = [
                "#b91d47",
                "#00aba9",
                "#2b5797",
                "#e8c3b9",
                "#1e7145"
            ];

            new Chart(ctx, {
                type: "pie",
                data: {
                    labels: labels,
                    datasets: [{
                        backgroundColor: barColors,
                        data: data
                    }]
                },

            });
        }
    });
}
$(document).ready(function() {
    lineChart("/api/statistical/order/month","Revenue","canvasOrder");
    lineChart("/api/statistical/book/month","Book","canvasBook");
    pieChart("/api/statistical/order/year","","canvasOrderYear")
    pieChart("/api/statistical/book/year","","canvasBookYear")
});