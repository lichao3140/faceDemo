package com.arcsoft.arcfacedemo.model;

public class FaceFeatureModel {
    private byte[] featureData;
    private String name;

    public FaceFeatureModel(byte[] faceFeature, String name) {
        this.featureData = faceFeature;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFeatureData() {
        return featureData;
    }

    public void setFeatureData(byte[] featureData) {
        this.featureData = featureData;
    }
}
