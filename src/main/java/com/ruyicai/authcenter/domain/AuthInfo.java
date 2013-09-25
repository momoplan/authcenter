package com.ruyicai.authcenter.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField = "", table = "AUTHINFO", identifierField = "id")
public class AuthInfo {

	@EmbeddedId
	private AuthInfoPK id;

	@Column(name = "INFO", columnDefinition = "text")
	private String info;

	/** =0:服务请求成功;=-1,服务请求失败 */
	@Column(name = "STATUS", length = 10)
	private String status;

	/**
	 * 当 status=-1 时有返回值. =1:ip 认证失败;=2 用户名错;=3 密码错误;=4 服务编号错误;=5系统服务异常;=6
	 * 姓名或身份号码格式错误
	 */
	@Column(name = "ERRORCODE", length = 10)
	private String errorcode;

	/**
	 * 当 status=0 时有值. =1:一致有照片;2:不 一致;3:库中无此号;4:一致无照片
	 */
	@Column(name = "VERIFYRESULT", length = 10)
	private String verifyResult;

	@Column(name = "CREATETIME")
	private Date createTime;

}
