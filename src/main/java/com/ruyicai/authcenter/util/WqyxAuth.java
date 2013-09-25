package com.ruyicai.authcenter.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wqyx.IdVerifyService;
import com.wqyx.IdVerifyServicePortType;

public class WqyxAuth {

	private static Logger logger = LoggerFactory.getLogger(WqyxAuth.class);

	public static String verifyService(String uid, String password, String returnPhoto, String serviceNo, String id,
			String name) throws DocumentException {
		logger.info("ws verify uid:{},returnPhoto:{}", new String[] { uid, password });
		if (StringUtils.isBlank(id) || StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("the arguments id or name are require!");
		}
		IdVerifyService servicesService = new IdVerifyService();
		IdVerifyServicePortType service = servicesService.getIdVerifyServiceHttpPort();
		String result = service.verifyService(uid, password, returnPhoto, id, name, serviceNo, "11111111");
		logger.info("id:{},name:{}verifyService result::{}", new String[] { id, name, result });
		return result;
	}
}
