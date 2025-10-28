package com.mypoc.ClaimApplication.utils;



public class EcmServicesConstants
{
    public static final String ELIG_MEMBER_DATE_FORMAT = "yyyyMMdd";

    public static final String ELIG_MEMBER_NAME_FORMAT =".*\\W+.*";

    public static final String RULES_DELIMITER = ",";

    public static final String RULES_PACKAGE = "com.multiplan.edi.ecm.validator.";

    //Standard UI Value Constants
    public static final String APP_NAME_ENROLLMENT_MANAGER = "ENROLLMENT_MANAGER";
    public static final String PAGE_NAME_COMMON = "COMMON";
    public static final String PAGE_NAME_BATCH_DETAIL = "BATCH_DETAIL";
    public static final String PAGE_NAME_BATCH_SUMMARY = "BATCH_SUMMARY";
    public static final String VALUE_TYPE_FILE_STATUS = "FILE_STATUS";
    public static final String VALUE_TYPE_RECORD_STATUS = "RECORD_STATUS";
    public static final String VALUE_TYPE_MAINT_CODES = "MAINT_CODES";

    //Environments
    public static final String ENV_MEMBER_DETAIL = "EMD";
    public static final String ENV_STAGING_MEMBER_DETAIL = "SMD";
    public static final String ENV_PROD_MEMBER_DETAIL = "PMD";

    public static final Long RECORD_STATUS_CODES_IND = new Long(999L);

    public static final Long WORKFLOW_GROUP_DATA_TRANSFER = new Long(500L);
    public static final Long WORKFLOW_GROUP_ACCEPT = new Long(1100L);

    public static final int MAX_POST_PROCESS_FOR_SQL = -99;

    public static final int LOCK_EXPIRATION_MINUTES = 15;

    //Match Rule Constants
    public static final String MATCH_TYPE_HALT = "HALT";

    //Claim Status Codes
    public static final Long CLAIM_STATUS_CLIENT_MATCH = 180300000L;
    public static final Long CLAIM_STATUS_MANUAL_MATCH = 180300110L;

    public static final String CLAIM_TYPE_HCFA = "H";
    public static final String CLAIM_TYPE_UB = "U";

    public static final String STC0101_MANUAL_MATCH = "A1";
    public static final String STC0102_MANUAL_MATCH = "19";

    public static final String PAYER_CHANGE_SCENARIO = " - Payer Change";

    public static final String STRIP_NON_APLPHA_NUM_REGEX = "[^a-zA-Z0-9]";

}
