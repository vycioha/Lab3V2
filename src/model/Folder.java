package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Folder implements Serializable {

    private String folderName;
    private Date dateCreated;
    private Date dateModified;
    private ArrayList<CourseFile> folderFiles;


    public Folder(String folderName, Date dateCreated, Date dateModified, ArrayList<CourseFile> folderFiles) {
        this.folderName = folderName;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.folderFiles = folderFiles;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public ArrayList<CourseFile> getFolderFiles() {
        return folderFiles;
    }

    public void setFolderFiles(ArrayList<CourseFile> folderFiles) {
        this.folderFiles = folderFiles;
    }

    public void CreateFile(String fileName){
        //truksta tikros failu kurimo logikos.
        if(!folderFiles.contains(fileName)){
            folderFiles.add(new CourseFile(fileName, 100, new Date(), new Date(), ".pdf"));
        }
        else
        {
            System.out.println("File with that name already exists.");
        }
    }

    public void DeleteFile(String fileName){
        if(folderFiles.stream().filter(x -> x.getFilename().equals(fileName)).findFirst().orElse(null) != null){
            int fileIndex = folderFiles.indexOf(folderFiles.stream().filter(x -> x.getFilename().equals(fileName)).findFirst().orElse(null));
            folderFiles.remove(fileIndex);
        }
        else{
            System.out.println("Folder with that name does not exist");
        }
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderName='" + folderName + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
