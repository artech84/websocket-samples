/**
 * 
 */
package com.arijit.fileuploader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Arijit
 *
 */
@ServerEndpoint(value = "/websocket", decoders = { MetaDataDecoder.class })
public class WebSocketServer {
	
	private Session session;
	private Path destination;
	private FileMetadata metadata;
	
	@OnOpen
	public void handleConnection(Session session) {
		System.out.println("open connection id: " + session.getId());

		File dir = new File("uploads");
		if (!dir.exists()) {
			try {
				Files.createDirectory(dir.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.session = session;
		this.destination = dir.toPath();

	}
	
	@OnMessage
	public void handleFile(InputStream stream) {
		Path path = Paths.get(destination.normalize().toString(),
				this.metadata.getName());
		try {
			Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
			metadata.setProcessed(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@OnMessage
	public void handleMetaData(FileMetadata data, Session session) {
		this.metadata = data;
	}
	
	@OnError
	public void handleError(Throwable t) {
		System.out.println(t.getMessage());
	}

}
