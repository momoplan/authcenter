package com.ruyicai.authcenter.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruyicai.authcenter.exception.RuyicaiException;
import com.ruyicai.authcenter.service.AuthService;
import com.ruyicai.authcenter.util.ErrorCode;

@Controller
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	/**
	 * @param id
	 *            身份证
	 * @param name
	 *            姓名
	 * @return
	 */
	@RequestMapping(value = "/auth")
	public @ResponseBody
	ResponseData auth(@RequestParam("id") String id, @RequestParam("name") String name) {
		logger.info("/auth id:{},name:{}", new String[] { id, name });
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.OK;
		try {
			rd.setValue(authService.auth(id, name));
		} catch (RuyicaiException e) {
			logger.error("/auth error id:" + id + "name:" + name, e);
			rd.setValue(e.getMessage());
			result = ErrorCode.ERROR;
		} catch (Exception e) {
			logger.error("/auth error id:" + id + "name:" + name, e);
			result = ErrorCode.ERROR;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	/**
	 * @param id
	 *            身份证
	 * @param name
	 *            姓名
	 * @return
	 */
	@RequestMapping(value = "/checkphoto")
	public void checkphoto(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam(value = "download", required = false) String download, HttpServletResponse response) {
		logger.info("/checkphoto id:{},name:{},download:{}", new String[] { id, name, download });
		OutputStream os = null;
		try {
			response.reset();
			os = response.getOutputStream();
			byte[] photo = null;
			photo = authService.checkphoto(id, name);
			if (photo != null) {
				if (StringUtils.isNotBlank(download)) {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(id.getBytes(), "ISO8859-1"));
					response.setContentType("image/jpeg");
				}
				os.write(photo);
			} else {
				os.write("photo not found".getBytes());
			}
			os.flush();
		} catch (DocumentException e) {
			logger.error("/checkphoto error id:" + id + "name:" + name, e);
		} catch (IOException e) {
			logger.error("/checkphoto error id:" + id + "name:" + name, e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error("/checkphoto error id:" + id + "name:" + name, e);
				}
			}
		}
	}
}
