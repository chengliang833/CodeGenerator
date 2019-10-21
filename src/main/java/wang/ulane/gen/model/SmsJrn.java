package wang.ulane.gen.model;

import java.util.Date;

public class SmsJrn {
    /**
     * 主键, 自增
     */
    private Integer id;

    /**
     * 流水号
     */
    private String jrnNo;

    /**
     * 发送日期
     */
    private String sndDt;

    /**
     * 发送时间
     */
    private String sndTm;

    /**
     * 手机号
     */
    private String mblNo;

    /**
     * 短信编码
     */
    private String smsCd;

    /**
     * 发送结果S-成功F-失败
     */
    private String sts;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 最近一次修改时间
     */
    private Date gmtModified;

    /**
     * 短信内容
     */
    private String smsSub;

    /**
     * 主键, 自增
     * @return id 主键, 自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键, 自增
     * @param id 主键, 自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 流水号
     * @return jrn_no 流水号
     */
    public String getJrnNo() {
        return jrnNo;
    }

    /**
     * 流水号
     * @param jrnNo 流水号
     */
    public void setJrnNo(String jrnNo) {
        this.jrnNo = jrnNo;
    }

    /**
     * 发送日期
     * @return snd_dt 发送日期
     */
    public String getSndDt() {
        return sndDt;
    }

    /**
     * 发送日期
     * @param sndDt 发送日期
     */
    public void setSndDt(String sndDt) {
        this.sndDt = sndDt;
    }

    /**
     * 发送时间
     * @return snd_tm 发送时间
     */
    public String getSndTm() {
        return sndTm;
    }

    /**
     * 发送时间
     * @param sndTm 发送时间
     */
    public void setSndTm(String sndTm) {
        this.sndTm = sndTm;
    }

    /**
     * 手机号
     * @return mbl_no 手机号
     */
    public String getMblNo() {
        return mblNo;
    }

    /**
     * 手机号
     * @param mblNo 手机号
     */
    public void setMblNo(String mblNo) {
        this.mblNo = mblNo;
    }

    /**
     * 短信编码
     * @return sms_cd 短信编码
     */
    public String getSmsCd() {
        return smsCd;
    }

    /**
     * 短信编码
     * @param smsCd 短信编码
     */
    public void setSmsCd(String smsCd) {
        this.smsCd = smsCd;
    }

    /**
     * 发送结果S-成功F-失败
     * @return sts 发送结果S-成功F-失败
     */
    public String getSts() {
        return sts;
    }

    /**
     * 发送结果S-成功F-失败
     * @param sts 发送结果S-成功F-失败
     */
    public void setSts(String sts) {
        this.sts = sts;
    }

    /**
     * 创建时间
     * @return gmt_created 创建时间
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * 创建时间
     * @param gmtCreated 创建时间
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * 最近一次修改时间
     * @return gmt_modified 最近一次修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 最近一次修改时间
     * @param gmtModified 最近一次修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 短信内容
     * @return sms_sub 短信内容
     */
    public String getSmsSub() {
        return smsSub;
    }

    /**
     * 短信内容
     * @param smsSub 短信内容
     */
    public void setSmsSub(String smsSub) {
        this.smsSub = smsSub;
    }
}