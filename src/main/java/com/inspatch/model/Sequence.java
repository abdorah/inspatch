package com.inspatch.model;

import com.inspatch.annotation.JsonElement;
import com.inspatch.annotation.JsonSerializable;

@JsonSerializable
public class Sequence {

    @JsonElement
    private String sequenceName;
    @JsonElement
    private String reportPath;
    
    public Sequence(String sequenceName, String reportPath) {
        this.sequenceName = sequenceName;
        this.reportPath = reportPath;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public String getReportPath() {
        return reportPath;
    }

}
