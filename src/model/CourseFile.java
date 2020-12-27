package model;

import java.io.Serializable;
import java.util.Date;

public class CourseFile implements Serializable {
    private String filename;
    private double filesize;
    private Date dateCreated;
    private Date dateModified;
    private String fileType;

    public CourseFile(String filename, double filesize, Date dateCreated, Date dateModified, String fileType) {
        this.filename = filename;
        this.filesize = filesize;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.fileType = fileType;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double getFilesize() {
        return filesize;
    }

    public void setFilesize(double filesize) {
        this.filesize = filesize;
    }

    public Date getDareCreated() {
        return dateCreated;
    }

    public void setDareCreated(Date dareCreated) {
        this.dateCreated = dareCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "File{" +
                "filename='" + filename + '\'' +
                ", dareCreated=" + dateCreated +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
