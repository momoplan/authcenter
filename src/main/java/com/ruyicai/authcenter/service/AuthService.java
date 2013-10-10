package com.ruyicai.authcenter.service;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.ruyicai.authcenter.domain.AuthInfo;
import com.ruyicai.authcenter.domain.AuthInfoPK;
import com.ruyicai.authcenter.domain.UidPwd;
import com.ruyicai.authcenter.util.IDCard;
import com.ruyicai.authcenter.util.WqyxAuth;

@Service
public class AuthService implements ApplicationListener<ContextRefreshedEvent> {

	private Logger logger = LoggerFactory.getLogger(AuthService.class);

	private String uid = "jrrc";
	private String password = null;
	private String returnPhoto = "1";
	private String serviceNo = "02";

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		UidPwd uidPwd = UidPwd.findUidPwd(uid);
		if (uidPwd == null) {
			logger.error("身份认证账号密码加载失败");
		} else {
			password = uidPwd.getPwd();
			logger.info("获取ws密码成功");
		}
	}

	public AuthInfo auth(String id, String name) throws DocumentException {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("the arguments id or name are require!");
		}
		IDCard.IDCardValidate(id);
		AuthInfoPK pk = new AuthInfoPK(id, name);
		AuthInfo authInfo = AuthInfo.findAuthInfo(pk);
		if (authInfo != null) {
			// 如果状态为-1,则是第三方服务器错误,重新查询
			if (authInfo.getStatus() != null && authInfo.getStatus().equals("-1")) {
				String result = WqyxAuth.verifyService(uid, password, returnPhoto, serviceNo, id, name);
				Document document = DocumentHelper.parseText(result);
				authInfo.setInfo(result);
				authInfo.setCreateTime(new Date());
				Node statusnode = document.selectSingleNode("//serviceresponse/status");
				String status = statusnode.getText();
				authInfo.setStatus(status);
				if (status != null && status.equals("-1")) {
					Node errorcodenode = document.selectSingleNode("//serviceresponse/errorcode");
					authInfo.setErrorcode(errorcodenode.getText());
				}
				if (status != null && status.equals("0")) {
					Node verifyresultnode = document.selectSingleNode("//serviceresponse/verifyresult");
					authInfo.setVerifyResult(verifyresultnode.getText());
				}
				authInfo.merge();
				authInfo.setHasExist(false);
			} else {
				authInfo.setHasExist(true);
			}
			return authInfo;
		} else {
			String result = WqyxAuth.verifyService(uid, password, returnPhoto, serviceNo, id, name);
			Document document = DocumentHelper.parseText(result);
			authInfo = new AuthInfo();
			authInfo.setId(pk);
			authInfo.setInfo(result);
			authInfo.setCreateTime(new Date());
			Node statusnode = document.selectSingleNode("//serviceresponse/status");
			String status = statusnode.getText();
			authInfo.setStatus(status);
			if (status != null && status.equals("-1")) {
				Node errorcodenode = document.selectSingleNode("//serviceresponse/errorcode");
				authInfo.setErrorcode(errorcodenode.getText());
			}
			if (status != null && status.equals("0")) {
				Node verifyresultnode = document.selectSingleNode("//serviceresponse/verifyresult");
				authInfo.setVerifyResult(verifyresultnode.getText());
			}
			authInfo.persist();
			authInfo.setHasExist(false);
			return authInfo;
		}
	}

	public byte[] checkphoto(String id, String name) throws DocumentException {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("the arguments id or name are require!");
		}
		AuthInfoPK pk = new AuthInfoPK(id, name);
		AuthInfo authInfo = AuthInfo.findAuthInfo(pk);
		if (authInfo != null) {
			String result = authInfo.getInfo();
			Document document = DocumentHelper.parseText(result);
			Node photonode = document.selectSingleNode("//serviceresponse/photo");
			if (photonode == null) {
				return null;
			} else {
				byte[] photo = Base64.decodeBase64(photonode.getText());
				return photo;
			}
		} else {
			return null;
		}
	}

}
