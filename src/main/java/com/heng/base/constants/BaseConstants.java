package com.heng.base.constants;

//系统常量
public class BaseConstants {

    //redis主key
    public static final String CARTYPE_TREE_IN_REDIS = "cartype_tree_in_redis";
    //redis副key
    public static final String CARTYPE_TREE_IN_REDIS_BACKUP= "cartype_tree_in_redis_backup";

    //店铺状态
    public class Shop{
        public static final int STATE_WAIT_AUTID = 1 ; //"待审核";
        public static final int STATE_WAIT_ACTIVE = 2 ; //"审核通过，待激活";
        public static final int STATE_ACTIVE_SUCCESS = 3 ; //"激活成功";
        public static final int STATE_REJECT_AUDIT = 4 ; //"驳回";
    }

    //员工状态
    public class Employee{
        public static final int STATE_WAIT_AUTID = -1 ; //"待激活",
        public static final int STATE_NORMAL = 1 ; //"正常",
        public static final int STATE_LOCK = 2 ; //"锁定",
        public static final int STATE_LOGOUT = 3 ; //"注销"
    }

    //员工中间表状态
    public class IsManager{
        public static final int STATE_WAIT_AUTID = 0 ; //"员工",
        public static final int STATE_NORMAL = 1 ; //"店长",
    }

    //店铺审核记录表状态
    public class ShopAudit{
        public static final int STATE_WAIT_AUTID = 0 ; //"审核失败",
        public static final int STATE_NORMAL = 1 ; //"审核成功",
    }

    //车辆售卖状态
    public class Car{
        public static final int STATE_WAIT_CAR_OFFSHELF = 0 ; //"下架",
        public static final int STATE_CAR_SHELF = 1 ; //"上架",
        public static final int STATE_WAIT_CAR_NOAUTID= 0 ; //"审核驳回",
        public static final int STATE_CAR_AUTID = 1 ; //"审核通过",
    }

    //车辆审核记录表状态
    public class CarAudit{
        public static final int STATE_WAIT_AUTID = 0 ; //"审核失败",
        public static final int STATE_NORMAL = 1 ; //"审核成功",
    }

    //日志状态表
    public class CarLog{
        public static final int Audit_Failure = 0 ; //"审核失败",
        public static final int Audit_Pass = 1 ; //"审核成功",
        public static final int Car_Onsale = 2 ; //"上架",
        public static final int Car_Offsale = 3 ; //"下架",
    }

    /**
     * 短信常量类
     */
    public class SmsContants {
        //用户名
        public static final String UID = "JarvisSmith";
        //秘钥
        public static final String KEY = "43A293807A8E0FF7A347D6320A56E709";
    }

    /**
     * 手机验证码存入redis key的前缀
     */
    public class MessageCode {
        //用户名
        public static final String REGISTER_SMS_CODE = "register";
        //秘钥
        public static final String LOGIN_SMS_CODE = "login";
    }

    //logininfo表的状态
    public class LoginInfo {
        public static final int TYPE_MANAGER = 0;
        public static final int TYPE_USER = 1;
        public static final int DISABLE_USE = 1;
        public static final int DISABLE = 0;
    }

    //User表的状态
    public class UserState {
        public static final int REGISTER_STATE = 1;
        public static final int ACTIVATE_STATE = 2;
        public static final int DISABLE_STATE = 0;
    }

}