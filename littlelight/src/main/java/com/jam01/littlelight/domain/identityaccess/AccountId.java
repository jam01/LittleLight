package com.jam01.littlelight.domain.identityaccess;

/**
 * Created by jam01 on 6/9/16.
 */
public class AccountId {
    private final int membershipType;
    private final String membershipId;

    public AccountId(int membershipType, String membershipId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
    }

    public int withMembershipType() {
        return membershipType;
    }

    public String withMembershipId() {
        return membershipId;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = ((AccountId) obj).membershipId.equals(this.membershipId);
        result = result && ((AccountId) obj).membershipType == this.membershipType;
        return result;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + membershipId.hashCode();
        result = 31 * result + membershipType;
        return result;
    }
}
