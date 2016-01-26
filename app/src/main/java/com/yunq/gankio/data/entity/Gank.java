package com.yunq.gankio.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Created by admin on 16/1/5.
 */
@Table("ganks")
public class Gank extends Soul {

    @Column("used")
    public boolean used;
    @Column("type")
    public String type;
    @Column("url")
    public String url;
    @Column("who")
    public String who;
    @Column("desc")
    public String desc;
    @Column("updatedAt")
    public Date updatedAt;
    @Column("createdAt")
    public Date createdAt;
    @Column("publishedAt")
    public Date publishedAt;
}
