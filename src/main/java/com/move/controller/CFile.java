package com.move.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.move.impl.service.ServiceSaveFile;

@Controller
public class CFile {
	@Autowired
	private ServiceSaveFile serviceSaveFile;
	private File fileOutPut;

	@PostMapping("/upload")
	@ResponseBody
	private void createNewFile(@Valid @RequestParam("file") MultipartFile file,
			@Valid @RequestParam("idCard") int idCard, HttpServletResponse response) throws IOException {

		fileOutPut = serviceSaveFile.saveFile(file, idCard);

		FileInputStream fileInputStream;
		response.setContentType("application/txt");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileOutPut);
		response.setHeader("Content-Transfer-Encoding", "Binary");

		try {
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
			fileInputStream = new FileInputStream(fileOutPut);
			int len;
			byte[] byteData = new byte[1024];
			while ((len = fileInputStream.read(byteData)) > 0) {
				bufferedOutputStream.write(byteData, 0, len);
			}
			bufferedOutputStream.close();
			response.flushBuffer();
		} catch (Exception e) {
			response.setStatus(500);
		}
	}
}
