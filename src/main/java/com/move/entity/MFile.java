package com.move.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "TFile")
public class MFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "CC")
	@NotNull
	private int cc;

	@NotNull
	@Column(name = "Date")
	private String date;

	@NotNull
	@Column(name = "FileName")
	private String fileName;

	@NotNull
	@Column(name = "FileType")
	private String fileType;

	@NotNull
	@Column(name = "FileData")
	@Lob
	private byte[] fileData;

	public MFile() {
		super();
	}

	public MFile(Long id, int cc, String date, String fileName, String fileType, byte[] fileData) {
		super();
		this.id = id;
		this.cc = cc;
		this.date = date;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileData = fileData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
}
