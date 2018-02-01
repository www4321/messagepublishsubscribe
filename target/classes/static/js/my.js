
$(document).ready(function(){
  $("#add").click(function(){
	  var userName = $("#userName").val();
	  var passWord = $("#passwd").val();
	  var jsondata = {"userName":"Runoob", "passWord":"www.runoob.com"};
	  $.ajax({  
          url:'/add',  
          type:'POST',  
          dataType:'json', 
          data:jsondata,  
          success:function(response){  
        	  alert(userName+":"+userName);
        	  window.location.reload();
          },  
          error:function() {  
        	  alert(userName+":"+userName);
          }  
	  }); 
	  $("#test").text(userName);
  });
});


