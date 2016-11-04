$(document).ready( function() {

    $("#username").focus();
    $("#login_submit").on('click', function(e) {

        //capturing the data
        var user_element = $("#username");
        var pwd_element = $("#password");
        var user = user_element.val().trim();
        var pwd = pwd_element.val().trim();

        // validating the username
        if(user == "") {
            $("#status").text("Please enter your username.");
            user_element.focus();
            e.preventDefault(); 
        }
        // validating the password
        else if(pwd == "") {
            $("#status").text("Please enter your password.");
            pwd_element.focus();
            e.preventDefault(); 
        }
        else{
        	 $.ajax({
                 url:'/login',
                 type: 'post',
                 data: JSON.stringify({
                         username:user,
                         password:pwd
                       }),
                 contentType: "application/json",
                 success: function(result){
                 	console.log(result.status);
                     if(result.status == "YES"){
                     	$.cookie("username",user);
                     	window.location = "contact_form.html";
                     }
                     else{
                     	$("#status").text("Invalid username or password. Try again");
                     }	
                 },
                 error:  function(error){
                 	$("#status").text("Invalid username or password. Try again"); 
                 }	
             });  
        }
    });
});





