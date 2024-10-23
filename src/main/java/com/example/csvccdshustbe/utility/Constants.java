package com.example.csvccdshustbe.utility;

public class Constants {



    /**
     * Constants Azure Microsoft Ad
     * */
    public final static String CLAIMS_INFORMATION_USER = "informationUser";

    /*-----------------------------------------------------*/

    /**
     * Constants ACCOUNT
     * */
    public static final Integer ACCOUNT_IS_LOCK = -1;
    public static final Integer ACCOUNT_IS_UN_LOCK = 1;

    /*-----------------------------------------------------*/

    /**
     * Constants ROLE_USER
     * */
    public static final Integer ROLE_USER_PICKED = 1;
    public static final Integer ROLE_STATUS = 1;
    public static final Integer ROLE_UN_STATUS = -1;
    public static final Integer ROLE_USER_UN_PICKED = -1;
    public static final Integer ROLE_DEFAULT = 2;

    /*-----------------------------------------------------*/

    /**
     * Constants ROLE_ALLOW_ASSIGN
     * */

    public static final Integer ROLE_ALLOW_ASSIGN_STATUS = 1;
    public static final Integer ROLE_ALLOW_ASSIGN_UN_STATUS = -1;


    /*-----------------------------------------------------*/



    /**
     * Constants PATTERN ROLE CAPABILITIES
     * */
    public static final String PATTERN_ROLE_CAPABILITIES = "csvc/";
    public static final String PATTERN_ROLE_SEPARATE = ":";

    /*-----------------------------------------------------*/


    /**
     * Constant Asset categories
     * */
    public static final Integer IS_PICKED = 1;
    public static final Integer NOT_IS_PICKED = -1;
    public static final Integer IS_VISIBLE = 1;
    public static final Integer NOT_IS_VISIBLE = -1;

    /*----------------------------------------------------*/

    /**
     * Constant Units
     * */
    public static final Integer UNITS_IS_ACTIVE = 1;
    public static final Integer UNITES_UN_ACTIVE = -1;
    /*----------------------------------------------------*/



    /**
     * Constant Projects
     * */
    public static final Integer PROJECTS_IS_VISIBLE = 1;
    public static final Integer PROJECTS_UN_IS_VISIBLE = -1;

    /*----------------------------------------------------*/

    /**
     * Constant Original Of Formation
     * */
    public static final Integer ORIGINAL_OF_FORMATION_VISIBLE = 1;
    public static final Integer ORIGINAL_OF_FORMATION_UN_VISIBLE = -1;

    /**
     * Constant Medicine Type
     * */
    public static final Integer MEDICINE_TYPE_IS_VISIBLE = 1;
    public static final Integer MEDICINE_TYPE_UN_IS_VISIBLE = -1;

    /*----------------------------------------------------*/

    /**
     * Constant Medicine Group
     * */
    public static final Integer MEDICINE_GROUP_ACTIVE_STATUS = 1;

    /*----------------------------------------------------*/

    /**
     * Constant Country producer
     * */
    public static final Integer COUNTRY_PRODUCER_ACTIVE_STATUS = 1;


    /*----------------------------------------------------*/

    /**
     * Constant Type User
     * */
    public static final Integer TYPE_USE_ACTIVE_STATUS = 1;
    /*----------------------------------------------------*/

    /**
     * Constant Suppliers User
     * */
    public static final Integer SUPPLIERS_ACTIVE_STATUS = 1;
    /*----------------------------------------------------*/
    /**
     * Constant Level Type Asset User
     * */
    public static final Integer LEVEL_TYPE_ASSET_ACTIVE_STATUS = 1;
    /*----------------------------------------------------*/
    /**
     * Constant Level Type Asset User
     * */
    public static final Integer DOCUMENT_ATTACK_ACTIVE_STATUS = 1;
    public static final Integer DOCUMENT_ATTACK_UN_ACTIVE_STATUS = -1;

    /*----------------------------------------------------*/
    /**
     * Constant Department
     * */
    public static final Integer DEPARTMENT_ACTIVE_STATUS = 1;
    public static final Integer DEPARTMENT_UN_ACTIVE_STATUS = -1;
    /*----------------------------------------------------*/
    /**
     * Constant Asset Category
     * */
    public static final Integer ASSET_CATEGORY_INIT_ASSET_COUNT = 0;
    public static final Integer ASSET_CATEGORY_IS_VISIBLE = 1;
    public static final Integer ASSET_CATEGORY_UN_VISIBLE = -1;
    public static final Integer ASSET_CATEGORY_IS_PICK = 1;
    public static final Integer ASSET_CATEGORY_UN_PICK = -1;
    public static final Integer DEFAULT_ASSET_CATEGORY = -100;
    public static final Integer IS_DEFAULT = 1;
    public static final Integer NOT_IS_DEFAULT = -1;
    /*----------------------------------------------------*/

    /*----------------------------------------------------*/
    /**
     * Constant Position
     * */
    public static final Integer POSITION_NAME_ACTIVE_STATUS = 1;
    public static final Integer POSITION_NAME_UN_ACTIVE_STATUS = -1;

    /*----------------------------------------------------*/
    /**
     * Constant Location
     * */
    public static final Integer LOCATION_ACTIVE_STATUS = 1;
    public static final Integer LOCATION_UN_ACTIVE_STATUS = -1;
    /*----------------------------------------------------*/
    /**
     * Constant Asset blue print
     * */
    public static final String KEY_COMMON = "common";
    public static final String KEY_DEPRECIATION = "depreciation";
    public static final String KEY_MODULE = "modules";
    public static final String KEY_ORIGINAL_ASSET = "original";
    public static final String KEY_DECLARE_ASSET = "declare";
    public static final String KEY_TYPE_MODULE = "typeModules";
    public static final String KEY_TYPE_ORIGINAL_ASSET = "typeOriginal";
    public static final String KEY_TYPE_DECLARE = "typeDeclare";
    public static final String KEY_ASSET_ORIGINAL_OF_FORMATION = "originOfFormation";
    /*----------------------------------------------------*/

    /**
     * Constant Original
     * */
    public static final Integer ORIGINAL_VISIBLE = 1;
    public static final Integer ORIGINAL_UN_VISIBLE = -1;
    public static final Integer ORIGINAL_IS_DEFAULT = 1;

    /*----------------------------------------------------*/

    /**
     * Constant Method buy asset
     * */
    public static final Integer METHOD_BUY_ASSET_ACTIVE = 1;
    public static final Integer METHOD_BUY_ASSET_UN_ACTIVE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant type buy asset
     * */
    public static final Integer TYPE_BUY_ASSET_ACTIVE = 1;
    public static final Integer TYPE_BUY_ASSET_UN_ACTIVE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant Declare
     * */
    public static final Integer DECLARE_VISIBLE = 1;
    public static final Integer DECLARE_UN_VISIBLE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant Modules
     * */
    public static final Integer MODULES_VISIBLE = 1;
    public static final Integer MODULES_UN_VISIBLE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant Type Declare Asset
     * */
    public static final Integer TYPE_DECLARE_ASSET_ACTIVE = 1;
    public static final Integer TYPE_DECLARE_ASSET_UN_ACTIVE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant House Modules
     * */
    public static final Integer HOUSE_MODULES_IS_MANAGE_HOUSE = 1;
    public static final Integer HOUSE_MODULES_IS_NOT_MANAGE_HOUSE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant Originals
     * */
    public static final Integer ORIGINALS_VISIBLE = 1;
    public static final Integer ORIGINALS_UN_VISIBLE = -1;
    /*----------------------------------------------------*/
    /**
     * Constant Medicine Group
     * */
    public static final Integer GOALS_USE_GROUND_ACTIVE_STATUS = 1;

    /*----------------------------------------------------*/
    /**
     * Constant Excel
     * */
    public static String[] FILE_EXCEL = {"xls", "xlsx", "xlsm"};
    public static  final Integer SIZE_HANDLE = 50;
    /*----------------------------------------------------*/
    /**
     * Constant Type Process
     * */
    public static String CODE_TYPE_PROCESS_INCREASE = "increase";
    public static String CODE_TYPE_PROCESS_DECREASE = "decrease";
    public static String CODE_TYPE_PROCESS_CHANGE = "change";
    public static String CODE_TYPE_PROCESS_REMOVE = "remove";
    /*----------------------------------------------------*/
    /**
     * Constant PREFIX_DOCUMENT
     * */
    public static final String CODE_TYPE_STATE_INIT = "init";
    public static final String CODE_TYPE_STATE_TEST = "test";
    public static final String CODE_TYPE_STATE_APPROVED = "approved";
    public static final String CODE_TYPE_STATE_COMPLETED = "completed";
    /*----------------------------------------------------*/
    /**
     * Constant STEP_STATES
     * */
    public static final Integer STEP_TYPE_STATE_INIT = 1;
    public static final Integer STEP_TYPE_STATE_TEST = 2;
    public static final Integer STEP_TYPE_STATE_APPROVED = 3;
    public static final Integer STEP_TYPE_STATE_COMPLETED = 4;
    /*----------------------------------------------------*/
    /**
     * Constant NAME_PROCESS
     * */
    public static String NAME_INCREASE_PROCESS = "Increase asset";
    public static String NAME_TEST_PROCESS = "Test process";
    public static String NAME_APPROVED_PROCESS = "Approved process";
    public static String NAME_COMPLETED_PROCESS = "Completed process";
    /*----------------------------------------------------*/
    /**
     * Constant NAME_REQUEST
     * */
    public static String NAME_INCREASE_REQUEST = "Increase asset";
    public static String NAME_TEST_REQUEST = "Test process";
    public static String NAME_APPROVED_REQUEST = "Approved process";
    public static String NAME_COMPLETED_REQUEST = "Completed process";
    /*----------------------------------------------------*/
    /**
     * Constant Status process
     * */
    public static Integer STATUS_FALSE_PROCESS = -1;
    public static Integer STATUS_PENDING_PROCESS = 1;
    public static Integer STATUS_SUCCESS_PROCESS = 2;
    /*----------------------------------------------------*/
    /**
     * Constant Status data process asset
     * */
    public static Integer STATUS_PROCESS_ASSET_ACTIVE = 1;
    public static Integer STATUS_PROCESS_ASSET_UN_ACTIVE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant Status Type State
     * */
    public static Integer STATUS_TYPE_STATE_ACTIVE = 1;
    public static Integer STATUS_TYPE_STATE_UN_ACTIVE = -1;
    /*----------------------------------------------------*/

    /**
     * Constant Status State
     * */
    public static Integer STATUS_STATE_FALSE = -1;
    public static Integer STATUS_STATE_PENDING = 1;
    public static Integer STATUS_STATE_SUCCESS = 2;
    /*----------------------------------------------------*/


    /**
     * Constant Status Request
     * */
    public static Integer STATUS_REQUEST_FALSE = -1;
    public static Integer STATUS_REQUEST_PENDING = 1;
    public static Integer STATUS_REQUEST_SUCCESS = 2;
    /*----------------------------------------------------*/
    /**
     * Constant Status Request
     * */
    public static Integer STATUS_REQUEST_STAKE_HOLDER_FALSE = -1;
    public static Integer STATUS_REQUEST_STAKE_HOLDER_PENDING = 1;
    public static Integer STATUS_REQUEST_STAKE_HOLDER_SUCCESS = 2;
    /*----------------------------------------------------*/
    /**
     * Constant Status Request data
     * */
    public static Integer STATUS_REQUEST_DATA_ACTIVE = 1;
    public static Integer STATUS_REQUEST_DATA_UN_ACTIVE = -1;
    /*----------------------------------------------------*/
    /**
     * Constant PREFIX_DOCUMENT
     * */
    public static String PREFIX_DOCUMENT = "TS";
    /*----------------------------------------------------*/
    /**
     * Constant Status Request data
     * */
    public static Integer STATUS_REASON_ACTIVE = 1;
    public static Integer STATUS_REASON_UN_ACTIVE = -1;
    /*----------------------------------------------------*/

}
