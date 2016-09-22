
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("membershipId")
    @Expose
    private String membershipId;
    @SerializedName("uniqueName")
    @Expose
    private String uniqueName;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("profilePicture")
    @Expose
    private int profilePicture;
    @SerializedName("profileTheme")
    @Expose
    private int profileTheme;
    @SerializedName("userTitle")
    @Expose
    private int userTitle;
    @SerializedName("successMessageFlags")
    @Expose
    private String successMessageFlags;
    @SerializedName("isDeleted")
    @Expose
    private boolean isDeleted;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("firstAccess")
    @Expose
    private String firstAccess;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("context")
    @Expose
    private Context context;
    @SerializedName("showActivity")
    @Expose
    private boolean showActivity;
    @SerializedName("followerCount")
    @Expose
    private int followerCount;
    @SerializedName("followingUserCount")
    @Expose
    private int followingUserCount;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("localeInheritDefault")
    @Expose
    private boolean localeInheritDefault;
    @SerializedName("showGroupMessaging")
    @Expose
    private boolean showGroupMessaging;
    @SerializedName("profilePicturePath")
    @Expose
    private String profilePicturePath;
    @SerializedName("profileThemeName")
    @Expose
    private String profileThemeName;
    @SerializedName("userTitleDisplay")
    @Expose
    private String userTitleDisplay;
    @SerializedName("statusText")
    @Expose
    private String statusText;
    @SerializedName("statusDate")
    @Expose
    private String statusDate;

    /**
     * @return The membershipId
     */
    public String getMembershipId() {
        return membershipId;
    }

    /**
     * @param membershipId The membershipId
     */
    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    /**
     * @return The uniqueName
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * @param uniqueName The uniqueName
     */
    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    /**
     * @return The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return The profilePicture
     */
    public int getProfilePicture() {
        return profilePicture;
    }

    /**
     * @param profilePicture The profilePicture
     */
    public void setProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * @return The profileTheme
     */
    public int getProfileTheme() {
        return profileTheme;
    }

    /**
     * @param profileTheme The profileTheme
     */
    public void setProfileTheme(int profileTheme) {
        this.profileTheme = profileTheme;
    }

    /**
     * @return The userTitle
     */
    public int getUserTitle() {
        return userTitle;
    }

    /**
     * @param userTitle The userTitle
     */
    public void setUserTitle(int userTitle) {
        this.userTitle = userTitle;
    }

    /**
     * @return The successMessageFlags
     */
    public String getSuccessMessageFlags() {
        return successMessageFlags;
    }

    /**
     * @param successMessageFlags The successMessageFlags
     */
    public void setSuccessMessageFlags(String successMessageFlags) {
        this.successMessageFlags = successMessageFlags;
    }

    /**
     * @return The isDeleted
     */
    public boolean isIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted The isDeleted
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return The about
     */
    public String getAbout() {
        return about;
    }

    /**
     * @param about The about
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * @return The firstAccess
     */
    public String getFirstAccess() {
        return firstAccess;
    }

    /**
     * @param firstAccess The firstAccess
     */
    public void setFirstAccess(String firstAccess) {
        this.firstAccess = firstAccess;
    }

    /**
     * @return The lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate The lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return The context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param context The context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * @return The showActivity
     */
    public boolean isShowActivity() {
        return showActivity;
    }

    /**
     * @param showActivity The showActivity
     */
    public void setShowActivity(boolean showActivity) {
        this.showActivity = showActivity;
    }

    /**
     * @return The followerCount
     */
    public int getFollowerCount() {
        return followerCount;
    }

    /**
     * @param followerCount The followerCount
     */
    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    /**
     * @return The followingUserCount
     */
    public int getFollowingUserCount() {
        return followingUserCount;
    }

    /**
     * @param followingUserCount The followingUserCount
     */
    public void setFollowingUserCount(int followingUserCount) {
        this.followingUserCount = followingUserCount;
    }

    /**
     * @return The locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale The locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * @return The localeInheritDefault
     */
    public boolean isLocaleInheritDefault() {
        return localeInheritDefault;
    }

    /**
     * @param localeInheritDefault The localeInheritDefault
     */
    public void setLocaleInheritDefault(boolean localeInheritDefault) {
        this.localeInheritDefault = localeInheritDefault;
    }

    /**
     * @return The showGroupMessaging
     */
    public boolean isShowGroupMessaging() {
        return showGroupMessaging;
    }

    /**
     * @param showGroupMessaging The showGroupMessaging
     */
    public void setShowGroupMessaging(boolean showGroupMessaging) {
        this.showGroupMessaging = showGroupMessaging;
    }

    /**
     * @return The profilePicturePath
     */
    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    /**
     * @param profilePicturePath The profilePicturePath
     */
    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    /**
     * @return The profileThemeName
     */
    public String getProfileThemeName() {
        return profileThemeName;
    }

    /**
     * @param profileThemeName The profileThemeName
     */
    public void setProfileThemeName(String profileThemeName) {
        this.profileThemeName = profileThemeName;
    }

    /**
     * @return The userTitleDisplay
     */
    public String getUserTitleDisplay() {
        return userTitleDisplay;
    }

    /**
     * @param userTitleDisplay The userTitleDisplay
     */
    public void setUserTitleDisplay(String userTitleDisplay) {
        this.userTitleDisplay = userTitleDisplay;
    }

    /**
     * @return The statusText
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * @param statusText The statusText
     */
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    /**
     * @return The statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate The statusDate
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

}
