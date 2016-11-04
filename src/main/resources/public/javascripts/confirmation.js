$(document).ready(function(e){
	 $.ajax({
         url:'/messages',
         type: 'get',
         success: onSuccess,
         error: onError
     });

     function onSuccess(result){
         console.log(result);
         $.each(result, function(index, value ) {
             var row = $("<tr><td>" + value.to + "</td><td>" + value.from + "</td><td>"
             		+ value.subject + "</td><td>" + value.message+ "</td></tr>");
             $("#dataTable").append(row);
          });
     }

     function onError(error){
         $("#status").text("Error encountered while sending email. Please try again.");
     }

    $("#show").on('click', function() {
    	$("#tableContent").show();
    	$("#show").hide();
    	$("#hide").show();
    });
    
    $("#hide").on('click', function() {
    	$("#tableContent").hide();
    	$("#show").show();
    	$("#hide").hide();
    });
    
    $("#send_another").on("click",function(){
    	window.location = "contact_form.html";
    });
});