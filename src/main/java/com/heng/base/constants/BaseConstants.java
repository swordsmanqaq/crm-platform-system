package com.heng.base.constants;

//系统常量
public class BaseConstants {

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
}