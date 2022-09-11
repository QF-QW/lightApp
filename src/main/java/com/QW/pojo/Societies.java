package com.QW.pojo;

public class Societies {
    private int societiesId;
    private int createId;
    private String societiesName;
    private String introduction;
    private String roomCoordinate;
    private String headerUrl;

    private String createName;
    private String createHeadUrl;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateHeadUrl() {
        return createHeadUrl;
    }

    public void setCreateHeadUrl(String createHeadUrl) {
        this.createHeadUrl = createHeadUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSocietiesName() {
        return societiesName;
    }

    public void setSocietiesName(String societiesName) {
        this.societiesName = societiesName;
    }

    public int getSocietiesId() {
        return societiesId;
    }

    public void setSocietiesId(int societiesId) {
        this.societiesId = societiesId;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getRoomCoordinate() {
        return roomCoordinate;
    }

    public void setRoomCoordinate(String roomCoordinate) {
        this.roomCoordinate = roomCoordinate;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }
}
