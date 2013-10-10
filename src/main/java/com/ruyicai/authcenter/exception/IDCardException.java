package com.ruyicai.authcenter.exception;

import com.ruyicai.authcenter.util.ErrorCode;

public class IDCardException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;

	public IDCardException(String msg) {
		super(msg);
	}

	public IDCardException(String msg, Throwable e) {
		super(msg, e);
	}

	public IDCardException(ErrorCode errorCode) {
		super(errorCode.memo);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}