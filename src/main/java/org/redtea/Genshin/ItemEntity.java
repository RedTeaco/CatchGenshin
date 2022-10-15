package org.redtea.Genshin;

import java.util.Objects;

public class ItemEntity {
    private String uid;
    private String item_id;
    private String item_type;
    private String count;
    private String name;
    private String gacha_type;
    private String time;
    private String id;
    private String lang;
    private String rank_type;

    public ItemEntity() {
    }

    public ItemEntity(String uid, String item_id, String item_type, String count, String name, String gacha_type, String time, String id, String lang, String rank_type) {
        this.uid = uid;
        this.item_id = item_id;
        this.item_type = item_type;
        this.count = count;
        this.name = name;
        this.gacha_type = gacha_type;
        this.time = time;
        this.id = id;
        this.lang = lang;
        this.rank_type = rank_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGacha_type() {
        return gacha_type;
    }

    public void setGacha_type(String gacha_type) {
        this.gacha_type = gacha_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getRank_type() {
        return rank_type;
    }

    public void setRank_type(String rank_type) {
        this.rank_type = rank_type;
    }


    @Override
    public String toString() {
        return "ItemEntity{" +
                "uid='" + uid + '\'' +
                ", item_id='" + item_id + '\'' +
                ", item_type='" + item_type + '\'' +
                ", count='" + count + '\'' +
                ", name='" + name + '\'' +
                ", gacha_type='" + gacha_type + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", lang='" + lang + '\'' +
                ", rank_type='" + rank_type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemEntity that = (ItemEntity) o;
        return uid.equals(that.uid) && item_type.equals(that.item_type) && count.equals(that.count) && name.equals(that.name) && gacha_type.equals(that.gacha_type) && time.equals(that.time) && id.equals(that.id) && lang.equals(that.lang) && rank_type.equals(that.rank_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, item_id, count, name, gacha_type, time, id, lang, rank_type);
    }
}
