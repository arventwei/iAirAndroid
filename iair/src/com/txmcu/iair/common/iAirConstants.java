/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.txmcu.iair.common;

/**
 * BASE 
 * 
 * @author SINA
 * @since 2013-09-29
 */
public interface iAirConstants {

    /**  */
    public static final String APP_KEY      = "1173965945";


    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";


    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    
    
    public static final String QQ_APP_ID      = "101017203";
    
    public static final String API_Login = "http://211.103.161.120:9999/mobile/login";
    public static final String API_Bind  ="http://211.103.161.120:9999/mobile/bind";
    public static final String API_UnBind = "http://211.103.161.120:9999/mobile/unbind";
    public static final String API_QueryBindlist = "http://211.103.161.120:9999/mobile/query_bindlist";
    public static final String API_GetXiaoxin ="http://211.103.161.120:9999/mobile/getxiaoxin";
}
