$( document ).ready(function() {
	
	$("#loginForm").on("submit", function (e) {
        //alert("post");
        e.preventDefault();
        var val = $('input[name="username"]').val();
    	var dat = $(this).serialize();
    	$.ajax({
     		type: "POST",
     		url: "doLogin",
      		data: dat,
      		success: function(response) {
        		// callback code here
        		if (response.status == 'success'){
        			localStorage.setItem("login", val);
        			$("#loginFormDiv").remove();
        			$("#app").removeClass("display-no");
        			var script = document.createElement('script');
                    script.type = 'text/javascript';
                    script.async = 'async';
                    script.src = 'dist/app.js';
                    document.getElementById('app').appendChild(script);
        		} else {
                	$(".login-failure").removeClass("display-no"); 
                }
      		},
	    	error: function(response) {
				
			}
    	});
        return false;
    });
});