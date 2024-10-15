package com.example.brokenmirror.data;

public class FriendDto {
    private String id;
    private String userId;
    private String friendId;
    private String friendName;
    private String friendPhoneNum;
    private String friendProfileUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendPhoneNum() {
        return friendPhoneNum;
    }

    public void setFriendPhoneNum(String friendPhoneNum) {
        this.friendPhoneNum = friendPhoneNum;
    }

    public String getFriendProfileUrl() {
        return friendProfileUrl;
    }

    public void setFriendProfileUrl(String friendProfileUrl) {
        this.friendProfileUrl = friendProfileUrl;
    }
}