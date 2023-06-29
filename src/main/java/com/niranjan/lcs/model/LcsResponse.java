package com.niranjan.lcs.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LcsResponse {
    private List<StringElement> lcs;

    public List<StringElement> getLcs() {
        return lcs;
    }

    public void setLcs(List<StringElement> lcs) {
        this.lcs = lcs;
    }
}