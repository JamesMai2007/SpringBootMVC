package com.example.sbmvc.validatecode;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by James.Mai
 * on 2018-05-14 15:11
 * 验证码抽象类，提取公共方法
 */
public abstract class AbstractValidateCode implements ValidateCode{

    @Override
    public void clear(HttpSession session) {
        session.removeAttribute(this.getSessionCodeKey());
        session.removeAttribute(this.getSessionCreateTimeKey());
    }

    @Override
    public boolean isExpired(HttpSession session) {

        Object obj = session.getAttribute(this.getSessionCreateTimeKey());

        if(obj instanceof Date){
            Date beg = (Date)obj;
            Long bet = beg.getTime();
            Long nowt = new Date().getTime();
            if(nowt < (bet + this.getExpiredSeconds()*1000))
            {
                return false;
            }
            else
            {
                //如果过期就清除session里相关的值
                this.clear(session);
            }
        }

        return true;
    }

    @Override
    public boolean compareCode(HttpSession session, String userInputCode) {
        Object obj = session.getAttribute(this.getSessionCodeKey());
        if(obj instanceof String){
            String code = (String)obj;

            return code.equalsIgnoreCase(userInputCode);
        }

        return false;
    }
}
