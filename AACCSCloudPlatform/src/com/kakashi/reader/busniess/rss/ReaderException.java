package com.kakashi.reader.busniess.rss;

public class ReaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8057594009465044668L;

	public ReaderException() {
		super();
	}

	public ReaderException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ReaderException(String detailMessage) {
		super(detailMessage);
	}

	public ReaderException(Throwable throwable) {
		super(throwable);
	}

	
}
