package com.example.agri_help.models;

public class Mitigation {
    private int mitigationID;
    private String engText;
    private String urduText;
    private String suggestedProducts;
    private String severityScale;

    public Mitigation (int mitigationID, String engText, String urduText, String suggestedProducts, String severityScale) {
        this.mitigationID = mitigationID;
        this.engText = engText;
        this.urduText = urduText;
        this.suggestedProducts = suggestedProducts;
        this.severityScale = severityScale;
    }

    public String GetEngMitigation () { return this.engText; }
    public String GetUrduMitigation () { return this.urduText; }
    public String GetSuggestedProducts () { return this.suggestedProducts; }
    public String GetSeverityIndex () { return this.severityScale; }
}
