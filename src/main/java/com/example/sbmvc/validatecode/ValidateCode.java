package com.example.sbmvc.validatecode;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 验证码，可以是图片、短信等
 */
public interface ValidateCode {
    /**
     * 保存到session的key
     * @return
     */
    public String getSessionCodeKey();

    /**
     * 保存到session的日期key
     * @return
     */
    public String getSessionCreateTimeKey();

    /**
     * 过期秒数
     * @return
     */
    public Integer getExpiredSeconds();

    public void clear(HttpSession session);

    public boolean isExpired(HttpSession session);

    public boolean compareCode(HttpSession session , String userInputCode);

    /**
     * 验证类型，现时用作名称使用
     * @return
     */
    public String getValidateType();

}
