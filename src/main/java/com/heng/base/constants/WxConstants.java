package com.heng.base.constants;

public class WxConstants {
    public static final String APPID = "wx03bd86056bd3fc0e";
    public static final String SECRET = "f52fb26ebd494fe6f44eb4da50bd22da";
    //获取access token
    public static final String GET_ACK_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //获取微信用户信息 url
    public static final String GET_USER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

}