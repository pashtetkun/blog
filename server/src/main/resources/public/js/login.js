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
        			window.location.replace("/");
        		}
      		},
	    	error: function(response) {
				
			}
    	});
        return false;
    });
});