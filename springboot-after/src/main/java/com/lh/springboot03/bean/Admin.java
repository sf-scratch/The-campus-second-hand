package com.lh.springboot03.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("t_admin")
public class Admin implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String salt;
    private String img;
    private String phone;
    private String email;
    private Date created_time;
    private Date modified_time;

    public static void main(String[] args) {
        String parent = "file:/java/upload";
        if (parent.split("/")[0].equals("file:")){
            parent = parent.substring(5);
        }
        System.out.println(parent);
    }

    public Admin() {
    }

    public Admin(Integer id, String name, String password, String salt, String img, String phone, String email, Date created_time, Date modified_time) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.img = img;
        this.phone = phone;
        this.email = email;
        this.created_time = created_time;
        this.modified_time = modified_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", img='" + img + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", created_time=" + created_time +
                ", modified_time=" + modified_time +
                '}';
    }
}
