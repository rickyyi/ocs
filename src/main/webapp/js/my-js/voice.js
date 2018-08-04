//播放暂停切换  
function playAudio() {  
	var media = $("#a")[0];
    if(media.paused) {  
    	media.play();  
    } else {  
    	media.pause();  
    }  
}