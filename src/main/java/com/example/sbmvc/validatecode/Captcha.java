package com.example.sbmvc.validatecode;

import com.example.sbmvc.filter.ValidateCodeSecurityFilter;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * 图片验证码
 */
public class Captcha extends AbstractValidateCode{
    private static final Logger log = LoggerFactory.getLogger(ValidateCodeSecurityFilter.class);

    public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

    //--kapcha验证码。
    private Properties props = new Properties();
    private Producer kaptchaProducer = null;
    private String sessionKeyValue = null;
    private String sessionKeyDateValue = null;
    private Integer expiredSeconds = 120;  //120秒后过期

    private String formName = "captcha";

    public Captcha(String parameterName) {
        //ImageIO.setUseCache(false);

        //this.props.put(Constants.KAPTCHA_SESSION_KEY, "captcha");
        formName = parameterName;

        //设置宽和高。
        this.props.put(Constants.KAPTCHA_IMAGE_WIDTH, "200");
        this.props.put(Constants.KAPTCHA_IMAGE_HEIGHT, "60");
        //kaptcha.border：是否显示边框。
        this.props.put(Constants.KAPTCHA_BORDER, "no");
        //kaptcha.textproducer.font.color：字体颜色
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        //kaptcha.textproducer.char.space：字符间距
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
        //设置字体。
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "40");
        //this.props.put(Constants.KAPTCHA_NOISE_COLOR, "");
        //更多的属性设置可以在com.google.code.kaptcha.Constants类中找到。


        Config config1 = new Config(this.props);
        this.kaptchaProducer = config1.getProducerImpl();
        this.sessionKeyValue = config1.getSessionKey();
        this.sessionKeyDateValue = config1.getSessionDate();
    }



    public void create(HttpServletRequest request , HttpServletResponse response) throws IOException {
        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

        String capText = this.kaptchaProducer.createText();
        BufferedImage bi = this.kaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        try {
            request.getSession().setAttribute(this.sessionKeyValue, capText);
            request.getSession().setAttribute(this.sessionKeyDateValue, new Date());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("captcha error " , e.getCause());
        }
        ImageIO.write(bi, CAPTCHA_IMAGE_FORMAT, out);

    }

    @Override
    public String getSessionCodeKey() {
        return this.sessionKeyValue;
    }

    @Override
    public String getSessionCreateTimeKey() {
        return this.sessionKeyDateValue;
    }

    @Override
    public Integer getExpiredSeconds() {
        return expiredSeconds;
    }

    @Override
    public String getValidateType() {
        return formName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
