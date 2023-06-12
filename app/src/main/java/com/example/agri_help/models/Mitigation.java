package com.example.agri_help.models;

public class Mitigation {
    private int mitigationID;
    private String engText;
    private String urduText;

    public Mitigation (int mitigationID, String engText, String urduText) {
        this.mitigationID = mitigationID;
        this.engText = engText;
        this.urduText = urduText;
    }

    public String GetEngMitigation () { return this.engText; }
    public String GetUrduMitigation () { return this.urduText; }
}
