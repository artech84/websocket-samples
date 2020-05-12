package com.arijit.fileuploader;

import javax.websocket.DecodeException;
import javax.websocket.Decoder.Text;

import com.google.gson.Gson;

import javax.websocket.EndpointConfig;

public class MetaDataDecoder implements Text<FileMetadata>{

	@Override
	public void init(EndpointConfig endpointConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FileMetadata decode(String message) throws DecodeException {
		Gson gson = new Gson();
		return gson.fromJson(message, FileMetadata.class);
	}

	@Override
	public boolean willDecode(String message) {
		return message.contains("name");
	}

}
