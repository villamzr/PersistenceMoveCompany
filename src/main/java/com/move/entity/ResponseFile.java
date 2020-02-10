package com.move.entity;

import java.io.File;

public class ResponseFile {
	private int httpStatus;
	private String message;
	private File file;

	public ResponseFile(int httpStatus, String message, File file) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.file = file;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "ResponseFile [httpStatus=" + httpStatus + ", message=" + message + ", file=" + file + "]";
	}
}
