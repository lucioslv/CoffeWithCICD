package br.com.project.restwithspringboot.domain.dtos.v1;

import java.io.Serializable;
import java.util.Objects;

public class UploadFileResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;
    private String FileDownloadUri;
    private String fileType;
    private Long size;

    public UploadFileResponseDto(){
    }

    public UploadFileResponseDto(String fileName, String fileDownloadUri, String fileType, Long size) {
        this.fileName = fileName;
        FileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return FileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        FileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadFileResponseDto that = (UploadFileResponseDto) o;
        return Objects.equals(fileName, that.fileName) && Objects.equals(FileDownloadUri, that.FileDownloadUri) && Objects.equals(fileType, that.fileType) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, FileDownloadUri, fileType, size);
    }
}
