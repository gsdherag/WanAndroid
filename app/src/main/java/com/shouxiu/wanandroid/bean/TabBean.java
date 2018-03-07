package com.shouxiu.wanandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.lang.reflect.Constructor;

/**
 * 类描述：单个选项卡类，每个选项卡包含名字，图标以及提示（可选，默认不显示）
 */
public class TabBean implements Parcelable {

    /**
     * id
     */
    private int id;

    /**
     * icon
     */
    private int icon;

    /**
     * name
     */
    private String name;

    /**
     * 是否有小标签
     */
    public boolean hasTips = true;

    /**
     * 对应的fragment
     */
    public Fragment fragment;

    /**
     * 提醒更新
     */
    public boolean notifyChange = false;

    /**
     * 对应的fragmentClass
     */
    public Class fragmentClass = null;

    public TabBean(int id, String name, Class clazz) {
        this(id, name, 0, clazz);
    }

    public TabBean(int id, String name, boolean hasTips, Class clazz) {
        this(id, name, 0, clazz);
        this.hasTips = hasTips;
    }

    public TabBean(int id, String name, int iconId, Class clazz) {
        super();
        this.name = name;
        this.id = id;
        this.icon = iconId;
        this.fragmentClass = clazz;
    }

    public TabBean(Parcel p) {
        this.id = p.readInt();
        this.name = p.readString();
        this.icon = p.readInt();
        this.notifyChange = p.readInt() == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int iconid) {
        icon = iconid;
    }

    public int getIcon() {
        return icon;
    }

    public Fragment createFragment() {
        if (fragment == null) {
            Constructor constructor;
            try {
                constructor = fragmentClass.getConstructor(new Class[0]);
                fragment = (Fragment) constructor.newInstance(new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    public static final Creator<TabBean> CREATOR = new Creator<TabBean>() {
        public TabBean createFromParcel(Parcel p) {
            return new TabBean(p);
        }

        public TabBean[] newArray(int size) {
            return new TabBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(id);
        p.writeString(name);
        p.writeInt(icon);
        p.writeInt(notifyChange ? 1 : 0);
    }

}