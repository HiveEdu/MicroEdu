package com.myedu.project.store.storeSearch.entityVo;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.Date;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: ${梁少鹏}
 * Date: 2020/4/11
 * Time: 15:51
 * Description:
 */
@Document(indexName = "store", type = "t_store", shards = 1, replicas = 0, refreshInterval = "-1")
public class StoreSearchVo {
    /**
     * 主键
     */
    private Long id;

    /*
     * 门店类型id
     */

    private List<Long> typeIds;
    /*
     * 课程名称
     */

    private List<String> coureseNames;

    /**
     * 名称
     */
    @Field(type = FieldType.Keyword)
    private String name;
    /**
     * 经度
     */
    private Double lon;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 到我的距离
     */
    private String distanceMeters;

    @GeoPointField
    private GeoPoint location;

    /** 门店logo */
    private String logo;

    /** 门店负责人 */

    private String manager;

    /** 负责人电话 */
    private String managerPhone;

    /** 门店照片墙 */
    private String photos;

    /** 健康证 */
    private String healths;

    /** 门店宣传视频地址 */
    private String video;

    /** 门店营业执照 */
    private String license;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String area;

    /** 门店详细地址 */
    private String address;

    /** 门牌号 */
    private String addressNum;


    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;


    /** 创建人Id */
    private Long createById;


    /** 状态（0待审核1审核通过2审核驳回） */
    private String status;

    /** 驳回原因 */

    private String rejectResion;

    /** 体验开始时间 */
    private Date beginExperienceTime;

    /** 体验结束时间 */
    private Date endExperienceTime;

    /** 门店等级关联id */
    private Long vipLevelId;

    /** 门店总点击量 */
    private int hitsAll;

    /** 门店介绍 */
    private String introduction;
    /** 关键字 */
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String keywords;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(String distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Long getCreateById() {
        return createById;
    }

    public void setCreateById(Long createById) {
        this.createById = createById;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectResion() {
        return rejectResion;
    }

    public void setRejectResion(String rejectResion) {
        this.rejectResion = rejectResion;
    }

    public Date getBeginExperienceTime() {
        return beginExperienceTime;
    }

    public void setBeginExperienceTime(Date beginExperienceTime) {
        this.beginExperienceTime = beginExperienceTime;
    }

    public Date getEndExperienceTime() {
        return endExperienceTime;
    }

    public void setEndExperienceTime(Date endExperienceTime) {
        this.endExperienceTime = endExperienceTime;
    }

    public Long getVipLevelId() {
        return vipLevelId;
    }

    public void setVipLevelId(Long vipLevelId) {
        this.vipLevelId = vipLevelId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getHealths() {
        return healths;
    }

    public void setHealths(String healths) {
        this.healths = healths;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getAddressNum() {
        return addressNum;
    }

    public void setAddressNum(String addressNum) {
        this.addressNum = addressNum;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public int getHitsAll() {
        return hitsAll;
    }

    public void setHitsAll(int hitsAll) {
        this.hitsAll = hitsAll;
    }

    public List<Long> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(List<Long> typeIds) {
        this.typeIds = typeIds;
    }

    public List<String> getCoureseNames() {
        return coureseNames;
    }

    public void setCoureseNames(List<String> coureseNames) {
        this.coureseNames = coureseNames;
    }

    public StoreSearchVo(){

    }


    public StoreSearchVo(Long id, List<Long> typeIds,List<String> coureseNames, String name, Double lon, Double lat, String distanceMeters,
                         GeoPoint location, String logo, String manager, String managerPhone, String photos, String healths, String video, String license,
                         String province, String city, String area, String address, String delFlag, Long createById,
                         String status, String rejectResion, Date beginExperienceTime, Date endExperienceTime,
                         Long vipLevelId, int hitsAll, String introduction,String keywords,String addressNum) {
        this.id = id;
        this.typeIds = typeIds;
        this.coureseNames = coureseNames;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.distanceMeters = distanceMeters;
        this.location = location;
        this.logo = logo;
        this.manager = manager;
        this.managerPhone = managerPhone;
        this.photos = photos;
        this.healths = healths;
        this.video = video;
        this.license = license;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.delFlag = delFlag;
        this.createById = createById;
        this.status = status;
        this.rejectResion = rejectResion;
        this.beginExperienceTime = beginExperienceTime;
        this.endExperienceTime = endExperienceTime;
        this.vipLevelId = vipLevelId;
        this.hitsAll = hitsAll;
        this.introduction = introduction;
        this.keywords = keywords;
        this.addressNum = addressNum;
    }


    @Override
    public String toString() {
        return "StoreSearchVo{" +
                "id=" + id +
                ", typeIds=" + typeIds +
                ", coureseNames=" + coureseNames +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", distanceMeters='" + distanceMeters + '\'' +
                ", location=" + location +
                ", logo='" + logo + '\'' +
                ", manager='" + manager + '\'' +
                ", managerPhone='" + managerPhone + '\'' +
                ", photos='" + photos + '\'' +
                ", healths='" + healths + '\'' +
                ", video='" + video + '\'' +
                ", license='" + license + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", addressNum='" + addressNum + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", createById=" + createById +
                ", status='" + status + '\'' +
                ", rejectResion='" + rejectResion + '\'' +
                ", beginExperienceTime=" + beginExperienceTime +
                ", endExperienceTime=" + endExperienceTime +
                ", vipLevelId=" + vipLevelId +
                ", hitsAll=" + hitsAll +
                ", introduction='" + introduction + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
