package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 25-Jan-18.
 */

public class OAuthToken {

    public static final String prefix = "Bearer ";

    public static final String authorizationKey = "Authorization";

    public static final String authorizationToken;


    static {

        authorizationToken = prefix + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjE2YzUzYzQ0OTU0MWM1ZjczOTk3MTE1ODVhYTUzNzdjZjQ1ZWE2YzM2NGM0NWJlN2UzOGYyMGVjMzE4ZTY4MmI1MjcwNjRhOTQ5ODIxNWU2In0.eyJhdWQiOiIxIiwianRpIjoiMTZjNTNjNDQ5NTQxYzVmNzM5OTcxMTU4NWFhNTM3N2NmNDVlYTZjMzY0YzQ1YmU3ZTM4ZjIwZWMzMThlNjgyYjUyNzA2NGE5NDk4MjE1ZTYiLCJpYXQiOjE1MTY4NjIzOTMsIm5iZiI6MTUxNjg2MjM5MywiZXhwIjoxNTQ4Mzk4MzkzLCJzdWIiOiIxIiwic2NvcGVzIjpbIioiXX0.mJluATN1Z3kJKwvD6w6tvckylmQLm0UG7VTe2NIUtaMdSu6ff2hBo7m0EXw2fiFXqCP2k5dy3HuueLf3dYIzSv6KCR_--hZyLolo3G463bx_O63lQxtLZXSUxv2Y1abHKzm-DdRTEJo2WLhDo93viICwNAlGjawIkIdjvBD27nA38L08KKr2p_m8ddqvAnCrqNB_F9uEVrM3lD5FqYgpytymiE6jPXexDzJV4CsK5P6aq00pvIrA1-an6yxMGLiHJ8yO5NSNGjDUu_AC2IDladzPZBhbxhfXmFLmQvTTBTwxO7stQ7dxTT02NChSLXKWbC-X3JAnlMFQ0ZIKYh7xwbX5_ow3yUuj5vX7kHZARkThrbtEkfRl8GXhELbI6saN6G6PMzwSAjkCJBQI6TyVsL-sF_tgr4FOBH_RCed8T35iuJzwgCXgfUV254ZkKmeAqLiGyN0BcydEypdy1vfvLb5rPbSVcOr9ErrALxDBdkAI6l4sJiO2SZAJp_WuODtRsSob2VZSb9Ha6H9d2UvJcxpGBseFboCLweX329Aua3AtrqyOJmJ-dxIigvBxYv63vWrwOJ65nWTpNIjKv1jnCnup3gm7v3dXFE2jAq48gpin8VRztTYpBFKW323c8fsowsPm7uKx-dAKUTf_1RxXft0gfy71TswaxMVNROJwJHw";


    }


//    public OAuthToken(String authorization) {
//        this.authorization = prefix + authorization;
//    }
//
//    // Setters
//    public void setAuthorization(String authorization) {
//        this.authorization = authorization;
//    }
//
//
//    // Getters
//
//
//    public static String getPrefix() {
//        return prefix;
//    }
//    public String getAuthorization() {
//        return authorization;
//    }
}
