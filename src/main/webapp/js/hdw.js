// JavaScript Document
$(document).ready(function(){
	
	$("#add").click(function(){
		
		$("#select1 option:selected").appendTo("#select2");
		
		});
		
	$("#add_all").click(function(){
		
		$("#select1 option").appendTo("#select2");
		
		});
		
	$("#remove").click(function(){
		
		$("#select2 option:selected").appendTo("#select1");
		
		});	
		
	$("#remove_all").click(function(){
		
		$("#select2 option").appendTo("#select1");
		
		});	
		
		$("#select1").dblclick(function(){
			
		$("#select1 option:selected").appendTo("#select2");	
			
			});
		
				$("#select2").dblclick(function(){
			
		$("#select2 option:selected").appendTo("#select1");	
			
			});
		
				
				
				
	
	$("#add2").click(function(){
		
		$("#select3 option:selected").appendTo("#select4");
		
	});
	
	$("#add_all2").click(function(){
		
		$("#select3 option").appendTo("#select4");
		
	});
	
	$("#remove2").click(function(){
		
		$("#select4 option:selected").appendTo("#select3");
		
	});	
	
	$("#remove_all2").click(function(){
		
		$("#select4 option").appendTo("#select3");
		
	});	
	
	$("#select3").dblclick(function(){
		
		$("#select3 option:selected").appendTo("#select4");	
		
	});
	
	$("#select4").dblclick(function(){
		
		$("#select4 option:selected").appendTo("#select3");	
		
	});
		

	});