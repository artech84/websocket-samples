package com.arijit.fileuploader;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class FileMetadata {
	
	private String name;
	private long size;
	private boolean isProcessed;

}
