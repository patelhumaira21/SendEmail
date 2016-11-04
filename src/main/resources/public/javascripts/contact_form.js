$(document).ready(function(e){

    $("#name").focus();
    $("#form_submit").on('click', function(e) {
        var name = $("#name").val().trim();
        var email = $("#user_email").val().trim();
        var message = $("#message").val().trim();

        if(name == ""){
            $("#confirmation_status").text("Please enter username.");
            $("#name").focus();
            e.preventDefault();
        }
        else if(email == "") {
            $("#confirmation_status").text("Please enter email.");
            $("#user_email").focus();
            e.preventDefault();
        }
        else if(message == "") {
            $("#confirmation_status").text("Please enter a message.");
            $("#message").focus();
            e.preventDefault();
        }
        else {
        	$.ajax({
                url:'/email',
                type: 'post',
                data: JSON.stringify({
                        name:name,
                        email:email,
                        message:message
                      }),
                contentType: "application/json",
                success: onSuccess,
                error: onError
            });

            function onSuccess(result){
                console.log(result);
                if(result.status=="success"){
                	window.location = "confirmation.html";
                }
                else{
                	$("#confirmation_status").text("Error encountered while sending email. Please try again.");
                }
            }

            function onError(error){
                $("#confirmation_status").text("Error encountered while sending email. Please try again.");
            }
        }
    });
});