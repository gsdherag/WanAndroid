package com.shouxiu.wanandroid.network.bean;

import android.content.Context;
import android.os.Parcel;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.shouxiu.wanandroid.utils.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author yeping
 * @date 2018/3/9 11:37
 * TODO
 */

@Route(path = "/service/json")
public class FirstLevelBean implements SerializationService {

    /**
     * children : [{"children":[],"courseId":13,"id":97,"name":"音视频","order":10000,"parentChapterId":38,"visible":1},{"children":[],"courseId":13,"id":95,"name":"相机Camera","order":10000,"parentChapterId":38,"visible":1},{"children":[],"courseId":13,"id":170,"name":"闹钟","order":10005,"parentChapterId":38,"visible":1}]
     * courseId : 13
     * id : 38
     * name : 多媒体技术
     * order : 50
     * parentChapterId : 0
     * visible : 1
     */

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    private List<ChildrenBean> children;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return GsonUtils.convertObj(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return GsonUtils.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return GsonUtils.convertObj(input, clazz);
    }

    @Override
    public void init(Context context) {

    }

    public static class ChildrenBean implements SerializationService {
        /**
         * children : []
         * courseId : 13
         * id : 97
         * name : 音视频
         * order : 10000
         * parentChapterId : 38
         * visible : 1
         */

        private int courseId;
        private int id;
        private String name;
        private int order;
        private int parentChapterId;
        private int visible;
        private List<?> children;

        protected ChildrenBean(Parcel in) {
            courseId = in.readInt();
            id = in.readInt();
            name = in.readString();
            order = in.readInt();
            parentChapterId = in.readInt();
            visible = in.readInt();
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
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

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }


        @Override
        public <T> T json2Object(String input, Class<T> clazz) {
            return GsonUtils.convertObj(input, clazz);
        }

        @Override
        public String object2Json(Object instance) {
            return GsonUtils.toJson(instance);
        }

        @Override
        public <T> T parseObject(String input, Type clazz) {
            return GsonUtils.convertObj(input, clazz);
        }

        @Override
        public void init(Context context) {

        }

        public ChildrenBean(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
