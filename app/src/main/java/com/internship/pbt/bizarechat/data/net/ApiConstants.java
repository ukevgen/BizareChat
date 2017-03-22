package com.internship.pbt.bizarechat.data.net;


/**
 * This class is the only place to store all API constants
 */
public class ApiConstants {
    public static final String APP_ID = "52565";
    public static final String AUTH_KEY = "bc9N8yVXFazhvu9";
    public static final String AUTH_SECRET = "jU7RJgbBv8jCVhf";
    public static final String ACCOUNT_KEY = "tCjYy95ssiZCecTch1YU";
    public static final String API_END_POINT = "https://api.quickblox.com";
    public static final String CHAT_END_POINT = "chat.quickblox.com";
    public static final String MULTI_USERS_CHAT_ENDPOINT = "muc.chat.quickblox.com";
    public static final String SERVICE_NAME = "quickblox.com";

    public static final String TOKEN_HEADER_NAME = "QB-Token";

    //Constants for uploading file to amazon server
    public static final String AMAZON_END_POINT = "https://qbprod.s3.amazonaws.com/";
    public static final String AMAZON_CONTENT_TYPE = "Content-Type";
    public static final String AMAZON_EXPIRES = "Expires";
    public static final String AMAZON_ACL = "acl";
    public static final String AMAZON_KEY = "key";
    public static final String AMAZON_POLICY = "policy";
    public static final String AMAZON_ACTION_STATUS = "success_action_status";
    public static final String AMAZON_ALGORITHM = "x-amz-algorithm";
    public static final String AMAZON_CREDENTIAL = "x-amz-credential";
    public static final String AMAZON_DATE = "x-amz-date";
    public static final String AMAZON_SIGNATURE = "x-amz-signature";
    public static final String AMAZON_FILE = "file";
    public static final String CONTENT_TYPE_IMAGE_JPEG = "image/jpeg";

    //Constants for retrieving users
    public static final String ORDER_ASC_FULL_NAME = "asc+string+full_name";
    public static final String ORDER_DESC_FULL_NAME = "desc+string+full_name";
    public static final String ORDER_DEFAULT = "desc+date+created_at";
    public static final Integer USERS_PER_PAGE = 20;

    //Constants for notification subscribing
    public static final String NOTIFICATION_CHANNELS = "gcm";
    public static final String ENVIRONMENT = "development";
    public static final String PLATFORM = "android";
}
