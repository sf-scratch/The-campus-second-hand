package com.lh.springboot03.bean;



import java.io.Serializable;
import java.util.Date;

public class GoodsDetail implements Serializable {
    private Integer id;
    private String coverImage;
    private String image1;
    private String image2;
    private String image3;
    private Integer typeId;
    private String typeName;
    private Integer categoryId;
    private String categoryName;
    private String name;
    private Integer num;
    private Double price;
    private Double originalPrice;
    private Integer status;
    private String content;
    private Integer ownerId;
    private String ownerName;
    private Date createDate;
    private String contactQq;
    private String contactWx;
    private Boolean isCollect;

    public GoodsDetail() {
    }

    public GoodsDetail(Integer id, String coverImage, String image1, String image2, String image3, Integer typeId, String typeName, Integer categoryId, String categoryName, String name, Integer num, Double price, Double originalPrice, Integer status, String content, Integer ownerId, String ownerName, Date createDate, String contactQq, String contactWx, Boolean isCollect) {
        this.id = id;
        this.coverImage = coverImage;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.typeId = typeId;
        this.typeName = typeName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.name = name;
        this.num = num;
        this.price = price;
        this.originalPrice = originalPrice;
        this.status = status;
        this.content = content;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.createDate = createDate;
        this.contactQq = contactQq;
        this.contactWx = contactWx;
        this.isCollect = isCollect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    public String getContactWx() {
        return contactWx;
    }

    public void setContactWx(String contactWx) {
        this.contactWx = contactWx;
    }

    public Boolean getCollect() {
        return isCollect;
    }

    public void setCollect(Boolean collect) {
        isCollect = collect;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @Override
    public String toString() {
        return "GoodsDetail{" +
                "id=" + id +
                ", coverImage='" + coverImage + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", createDate=" + createDate +
                ", contactQq='" + contactQq + '\'' +
                ", contactWx='" + contactWx + '\'' +
                ", isCollect=" + isCollect +
                '}';
    }
}
