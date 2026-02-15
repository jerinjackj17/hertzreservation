package com.hertz.hertzreservation.dto;

public class LoyaltyResponseDTO {

    private String membershipLevel;

    public LoyaltyResponseDTO() {
    }

    public LoyaltyResponseDTO(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }
}
