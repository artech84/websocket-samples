var socketUpload = function(files) {
	
	var websocket = null;
	
	var connectSocket = function() {
		if(websocket == null) {
			websocket = new WebSocket('ws://'+location.host+location.pathname+"websocket");
			
			websocket.onopen = () => {
				console.log("Socket is connected: "+websocket);
				uploadFiles();
			}
			
			websocket.onclose = () => {
				console.log("Socket is closed");
			}
			
			websocket.onerror = (event) => {
				console.log("Error: "+event.data);
			}
			
			websocket.onmessage = (event) => {
				console.log("Message is received: "+event.data);
			}
		} else {
			console.log("Socket is already connected: "+websocket);
		}
	};
	
	var sendData = function(data) {
		if(websocket != null) {
			websocket.send(data);
		}
	};
	
	
	var uploadFiles = function() {
		if (typeof (files.length) !== 'undefined') {
			files.forEach(async file => {
				(function(){
					var metadata = {
							name: file.name,
							size: file.size
					}
					
					var freader = new FileReader();
					freader.onload = (event) => {
						console.log("Sending metadata: "+metadata);
						sendData(JSON.stringify(metadata));
						var rawdata = event.target.result;
						websocket.binaryType = 'arraybuffer';
						sendData(rawdata);
					}
					freader.readAsArrayBuffer(file);
				})();
			});
		}
	}
	
	return {
		connectSocket: connectSocket
	};
}