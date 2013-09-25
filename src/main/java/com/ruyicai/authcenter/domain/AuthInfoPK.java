package com.ruyicai.authcenter.domain;

import javax.persistence.Column;

import org.springframework.roo.addon.entity.RooIdentifier;
import org.springframework.roo.addon.tostring.RooToString;

@RooIdentifier
@RooToString
public class AuthInfoPK {

	private static final long serialVersionUID = 1L;

	/** 身份证 */
	@Column(name = "ID", length = 18)
	private String id;

	/** 姓名 */
	@Column(name = "NAME", length = 20)
	private String name;
}
