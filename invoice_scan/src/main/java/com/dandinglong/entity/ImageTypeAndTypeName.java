package com.dandinglong.entity;

public class ImageTypeAndTypeName {

    private String imageType;
    private String typeName;

    public ImageTypeAndTypeName(String imageType, String typeName) {
        this.imageType = imageType;
        this.typeName = typeName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
