package com.move.impl.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.move.entity.MFile;
import com.move.repository.IFileRepository;

@Service
public class ServiceSaveFile {
	private static final Logger log = LoggerFactory.getLogger(ServiceSaveFile.class);
	@Autowired
	private IFileRepository iFileRepository;
	private String fileName;
	private String fileType;
	private byte[] fileContent;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private String date = simpleDateFormat.format(new Date());

	public File saveFile(MultipartFile file, int idCard) {
		log.debug("");
		File fileOutPut = null;
		try {
			fileName = StringUtils.cleanPath(file.getOriginalFilename());
			fileType = file.getContentType();
			fileContent = file.getBytes();
			MFile mfile = new MFile(null, idCard, date, fileName, fileType, fileContent);
			iFileRepository.save(mfile);
			fileOutPut = fileToDownload(iterateFile(file));
		} catch (IOException e) {
			e.getMessage();
		}
		return fileOutPut;
	}

	public List<String> iterateFile(MultipartFile file) throws IOException {
		List<String> listFromFile = new ArrayList<String>();
		if (!file.isEmpty()) {
			String line;
			InputStream inputStream = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {
				listFromFile.add(line);
			}
		}
		return listFromFile;
	}

	public File fileToDownload(List<String> list) {
		List<Integer> integerList = convertArrayStringToIntegerArray(list);
		File fileOutPut = processMoveForDay(integerList);
		return fileOutPut;
	}

	public File processMoveForDay(List<Integer> integerList) {
		List<Integer> sub = new ArrayList<Integer>();
		File fileOutPut = null;
		int aux = 2;
		int caseNumber = 1;
		int itemQuantity = integerList.get(1);
		for (int i = itemQuantity; (aux - 1) < integerList.size() - 1; i++) {
			for (int j = i + 1; j <= (i + itemQuantity); j++) {
				sub.add(integerList.get(aux));
				aux++;
			}
			aux++;
			if ((aux - 1) < (integerList.size() - 1)) {
				itemQuantity = integerList.get(aux - 1);
			}
			fileOutPut = calculateMoveForDay(sub, caseNumber);
			caseNumber++;
			sub.clear();
		}
		return fileOutPut;
	}

	public File calculateMoveForDay(List<Integer> sub, int caseNumber) {
		int day = 0;
		int count = 0;
		int sum = 0;
		int multiplication = 0;
		int minWeight = 50;
		int element;
		int total = 0;
		boolean flag = false;

		List<Integer> AuxList = sub;
		Comparator<Integer> comparador = Collections.reverseOrder();
		Collections.sort(sub, comparador);
		while (count < sub.size()) {
			element = sub.get(count);
			if ((element * 2) >= minWeight) {
				day++;
				if (sub.size() != 1) {
					sub.remove(count);
					sub.remove(sub.size() - 1);
					Collections.sort(sub, comparador);
				} else {
					sub.clear();
				}

				if (sub.isEmpty()) {
					flag = true;
				}
			} else {
				if (sub.size() != 1) {
					count++;
					sum = element + sub.get(count);
					multiplication = sum * 2;
					total = multiplication + total;
					sub.remove(count);
					sub.remove(count - 1);
					if (total >= 50) {
						day++;
						if (!sub.isEmpty()) {
							sub.remove(sub.size() - 1);
						}
						{
							flag = true;
						}
						total = 0;
					}
					Collections.sort(sub, comparador);
					count = 0;
				} else {
					sub.clear();
				}

				if (!sub.isEmpty()) {
					flag = false;
				}
			}
		}

		if ((multiplication < 50) && !flag) {
			count = 0;
			sum = 0;
			day = 0;
			while (count < AuxList.size()) {
				element = sub.get(count);
				sum = element + sum;
			}
			day++;
			sub.clear();
			sub = AuxList;
		}
		String caseOut = "CASO #" + caseNumber + ": " + day;
		return createFile(caseOut);
	}

	public static <T, U> List<U> convertIntListToStringList(List<T> listOfInteger, Function<T, U> function) {
		return listOfInteger.stream().map(function).collect(Collectors.toList());
	}

	public File createFile(String caseOut) {
		File logFile = new File("lazy_loading_example_output.txt");
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(caseOut + "\n");
			bufferedWriter.close();
		} catch (IOException e) {

		}
		return logFile;
	}

	public List<Integer> convertArrayStringToIntegerArray(List<String> list) {
		List<Integer> integerList = new ArrayList<Integer>();
		for (String n : list)
			integerList.add(Integer.valueOf(n));
		return integerList;
	}
}
