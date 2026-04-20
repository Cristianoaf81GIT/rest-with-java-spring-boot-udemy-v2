package br.com.cristianoaf81.dto.v1;

import java.io.Serializable;
import java.util.Objects;

public class UploadFileResponseDTO implements Serializable {
 
  private static final long serialVersionUID = -3306389213576605848L;
  
  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long size;
  
  public UploadFileResponseDTO() {}

  public UploadFileResponseDTO(String fileName, String fileDownloadUri, String fileType, long size) {
    this.fileType = fileType;
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.size = size;
  }


  public String getFileName() {
  	return fileName;
  }

  public void setFileName(String fileName) {
	  this.fileName = fileName;
  }

  public String getFileDownloadUri() {
	  return fileDownloadUri;
  }

  public void setFileDownloadUri(String fileDownloadUri) {
	  this.fileDownloadUri = fileDownloadUri;
  }

  public String getFileType() {
	  return fileType;
  }

  public void setFileType(String fileType) {
	  this.fileType = fileType;
  }

  public long getSize() {
	  return size;
  }

  public void setSize(long size) {
	  this.size = size;
  }

  @Override
  public boolean equals(Object obj) {
   if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    UploadFileResponseDTO o = (UploadFileResponseDTO) obj;
    return Objects.equals(fileName, o.getFileName()) &&
    Objects.equals(fileDownloadUri, o.getFileDownloadUri()) &&
    Objects.equals(fileType, o.getFileType()) &&
    Objects.equals(size, o.getSize());
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName, fileType, fileDownloadUri, size);
  }

  @Override
  public String toString() {
    return "UploadFileResponseDTO [fileName=" + fileName 
    + ", fileDownloadUri=" + fileDownloadUri + ", fileType=" + fileType + ", size=" + size + "]";
  }

}

