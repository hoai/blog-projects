
function getTrace () {


            $.ajax({
                type : "GET",
                contentType : "application/json",
                url : "http://localhost:8080/trace",
                data : {
                     path: "/api/resource"
                },
                dataType : 'json',
                timeout : 100000,
                success : function(data) {
                	var html = "";
                	//var result = JSON.parse(data);
                    console.log("SUCCESS: ", data);
                    $("#inner_tbl").html(buildTable(data));
                    showTree();
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                }
            });
}
       
$(window).on('load', function() {
    console.log('All assets are loaded');
    getTrace();
})