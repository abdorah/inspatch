package com.inspatch.model;

import com.inspatch.annotation.JsonElement;
import com.inspatch.annotation.JsonList;
import com.inspatch.annotation.JsonSerializable;

@JsonSerializable
public class Report {

    @JsonElement
    private String folderName;
    @JsonElement
    private String countAll;
    @JsonElement
    private String countOk;
    @JsonElement
    private String countKo;
    @JsonList(key = "executedSequences", ofObjects = true)
    private Sequence[] sequences;
    
    public Report(String folderName, String countAll, String countOk, String countKo, Sequence[] sequences) {
        this.folderName = folderName;
        this.countAll = countAll;
        this.countOk = countOk;
        this.countKo = countKo;
        this.sequences = sequences;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getCountAll() {
        return countAll;
    }

    public void setCountAll(String countAll) {
        this.countAll = countAll;
    }

    public String getCountOk() {
        return countOk;
    }

    public void setCountOk(String countOk) {
        this.countOk = countOk;
    }

    public String getCountKo() {
        return countKo;
    }

    public void setCountKo(String countKo) {
        this.countKo = countKo;
    }

    public Sequence[] getSequences() {
        return sequences;
    }

    public void setSequences(Sequence[] sequences) {
        this.sequences = sequences;
    }
    
}
