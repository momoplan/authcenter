package com.ruyicai.authcenter.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.ruyicai.authcenter.service.MemcachedService;

@RooJavaBean
@RooToString
@RooEntity(versionField = "", table = "UIDPWD", identifierField = "uid")
public class UidPwd implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UID", length = 50)
	private String uid;

	@Column(name = "PWD", length = 50)
	private String pwd;

	@Autowired
	transient MemcachedService<UidPwd> memcachedService;
}
