package com.magni5.m5tracker.models;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class UserModel extends Model {
    private String DisplayName;
    private String Email;
    private String UserMagic;
    private String Phone;
    private String OwnerId;
    private String RoleId;
    private boolean IsEmailVerified;
    private boolean IsPhoneVerified;
    private RoleModel Role;
    private OwnerModel Owner;
    private String _id;

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUserMagic() {
        return UserMagic;
    }

    public void setUserMagic(String UserMagic) {
        this.UserMagic = UserMagic;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String OwnerId) {
        this.OwnerId = OwnerId;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String RoleId) {
        this.RoleId = RoleId;
    }

    public boolean getIsEmailVerified() {
        return IsEmailVerified;
    }

    public void setIsEmailVerified(boolean IsEmailVerified) {
        this.IsEmailVerified = IsEmailVerified;
    }

    public boolean getIsPhoneVerified() {
        return IsPhoneVerified;
    }

    public void setIsPhoneVerified(boolean IsPhoneVerified) {
        this.IsPhoneVerified = IsPhoneVerified;
    }

    public RoleModel getRole() {
        return Role;
    }

    public void setRole(RoleModel Role) {
        this.Role = Role;
    }

    public OwnerModel getOwner() {
        return Owner;
    }

    public void setOwner(OwnerModel Owner) {
        this.Owner = Owner;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}