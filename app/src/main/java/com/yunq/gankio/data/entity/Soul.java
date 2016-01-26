package com.yunq.gankio.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Unique;

/**
 * Created by admin on 16/1/5.
 */
public class Soul {
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    @Column("_id")
    protected long id;

    @NotNull
    @Unique
    @Column("objectId")
    public String objectId;
}
