
function getHome () {


            $.ajax({
                type : "GET",
                contentType : "application/json",
                url : "http://localhost:8080/api/resource",
                data : {
                	customerId: 600
                },
                dataType : 'json',
                timeout : 100000,
                success : function(data) {
                	var html = "";
                	//var result = JSON.parse(data);
                    //console.log("SUCCESS: ", data);
                    $("#home").html(data.resource);
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                }
            });
}
       
$(window).on('load', function() {
    //console.log('All assets are loaded');
	getHome();
})