package com.ean.drill.member.model.vo;

import lombok.Getter;

@Getter
public class KakaoProfile {
    private int id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Getter
    public static class KakaoAccount {
        private Boolean profile_needs_agreement;
        private Boolean profile_nickname_needs_agreement;
        private Boolean profile_image_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
        private Boolean has_age_range;
        private Boolean age_range_needs_agreement;
        private String age_range;
        private Boolean has_birthday;
        private String birthday;
        private String birthday_type;
        private Boolean birthday_needs_agreement;
        private String gender;
        private Boolean gender_needs_agreement;

        @Getter
        public static class Profile {
        	private Boolean is_default_image;
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
        }
    }
}
