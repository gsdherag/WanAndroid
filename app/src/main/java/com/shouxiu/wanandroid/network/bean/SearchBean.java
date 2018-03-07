package com.shouxiu.wanandroid.network.bean;

import java.util.List;

/**
 * @author yeping
 * @date 2018/3/5 11:12
 * @description ${TODO}
 */

public class SearchBean {
    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"08_carmelo","chapterId":0,"chapterName":"","collect":false,"courseId":13,"desc":"","envelopePic":"","id":2364,"link":"http://www.jianshu.com/p/b005ccddb29a","niceDate":"2018-02-23","origin":"","projectLink":"","publishTime":1519398785000,"title":"app拆分，多产品<em class='highlight'>打包<\/em>实录.md","visible":1,"zan":0},{"apkLink":"","author":"Tencent","chapterId":230,"chapterName":"打包","collect":false,"courseId":13,"desc":"","envelopePic":"","id":2338,"link":"http://www.wanandroid.com/blog/show/2045","niceDate":"2018-02-22","origin":"","projectLink":"","publishTime":1519297455000,"title":"腾讯开源 多渠道<em class='highlight'>打包<\/em> VasDolly实现原理","visible":1,"zan":0},{"apkLink":"","author":"骑小猪看流星","chapterId":230,"chapterName":"打包","collect":false,"courseId":13,"desc":"","envelopePic":"","id":2238,"link":"http://www.jianshu.com/p/332525b09a88  ","niceDate":"2018-01-29","origin":"","projectLink":"","publishTime":1517210113000,"title":"十分钟快速集成美团多渠道<em class='highlight'>打包<\/em>","visible":1,"zan":0},{"apkLink":"","author":"loonggg","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"我们都知道国内应用市场非常多，为了统计各个应用市场的app下载量和使用情况，我们需要多渠道的打包。如果一个一个的手动去打包岂不烦死了，要多麻烦就有多麻烦。这就要求我们学会使用Gradle进行多渠道打包。","envelopePic":"","id":985,"link":"https://mp.weixin.qq.com/s/yKfesG8lodfhJVA-rPfyRg","niceDate":"2018-01-15","origin":"非著名程序员","projectLink":"","publishTime":1516003287000,"title":"Android Studio 使用Gradle多渠道<em class='highlight'>打包<\/em>","visible":1,"zan":0},{"apkLink":"","author":"君莫醉","chapterId":295,"chapterName":"混淆","collect":false,"courseId":13,"desc":"","envelopePic":"","id":2084,"link":"https://www.jianshu.com/p/3e56303fb375","niceDate":"2018-01-15","origin":"","projectLink":"","publishTime":1515986435000,"title":"android插件自定义之多渠道<em class='highlight'>打包<\/em>插件（支持微信资源混淆andResGuard）","visible":0,"zan":0},{"apkLink":"","author":"腾讯Bugly","chapterId":295,"chapterName":"混淆","collect":false,"courseId":13,"desc":"","envelopePic":"","id":2083,"link":"https://mp.weixin.qq.com/s?__biz=MzA3NTYzODYzMg==&mid=214472913&idx=1&sn=92b54b5fcd9bbab6513e46d92095a07f&scene=1&srcid=0427eTI2x0dnk2EsFnysnjZI#rd","niceDate":"2018-01-15","origin":"","projectLink":"","publishTime":1515986421000,"title":"安装包立减1M--微信Android资源混淆<em class='highlight'>打包<\/em>工具","visible":0,"zan":0},{"apkLink":"","author":"QQ音乐技术团队","chapterId":230,"chapterName":"打包","collect":false,"courseId":13,"desc":"","envelopePic":"","id":1307,"link":"https://mp.weixin.qq.com/s/wNLXBaLznQwCtQ4nmQkUYw","niceDate":"2017-10-11","origin":"","projectLink":"","publishTime":1507687323000,"title":"分析Android V2新签名<em class='highlight'>打包<\/em>机制","visible":1,"zan":0},{"apkLink":"","author":"acrh","chapterId":230,"chapterName":"打包","collect":false,"courseId":13,"desc":"","envelopePic":"","id":1303,"link":"http://www.jianshu.com/p/e4ed249e4cab","niceDate":"2017-10-10","origin":"","projectLink":"","publishTime":1507637383000,"title":"Android N Signature Scheme v2 渠道<em class='highlight'>打包<\/em>","visible":1,"zan":0},{"apkLink":"","author":"Kane_Wood","chapterId":222,"chapterName":"持续集成","collect":false,"courseId":13,"desc":"","envelopePic":"","id":1280,"link":"http://www.jianshu.com/p/5d78a084f97f","niceDate":"2017-10-01","origin":"","projectLink":"","publishTime":1506864498000,"title":"Jenkins Android 自动<em class='highlight'>打包<\/em>","visible":1,"zan":0},{"apkLink":"","author":"adison","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"","envelopePic":"","id":1055,"link":"http://www.jianshu.com/p/32cdfd323c56","niceDate":"2016-10-27","origin":"简书","projectLink":"","publishTime":1477578363000,"title":"可能是最通用全面的Android studio<em class='highlight'>打包<\/em>jar方法","visible":1,"zan":0},{"apkLink":"","author":"Wing_Li","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"最近有个需求一次要打包9个类型的App，而且常量和String.xml都有变量。虽然之前也是一直存在变量，但是每次也仅仅只打包一个。这让我每次改变量，打包9个。要是以后每次都打包9次，我得疯了。 根据之前的了解，gradle 应该是可以解决这个问题的。","envelopePic":"","id":984,"link":"http://www.jianshu.com/p/533240d222d3#","niceDate":"2016-09-03","origin":"简书","projectLink":"","publishTime":1472914553000,"title":"Gradle多渠道<em class='highlight'>打包<\/em>(动态设定App名称，应用图标，替换常量，更改包名，变更渠道)","visible":1,"zan":0},{"apkLink":"","author":"MarkZhai","chapterId":79,"chapterName":"黑科技","collect":false,"courseId":13,"desc":"","envelopePic":"","id":207,"link":"http://www.jianshu.com/p/96d9ef3a1960","niceDate":"2016-06-18","origin":"简书","projectLink":"","publishTime":1466242505000,"title":"Android逆向分析(2) APK的<em class='highlight'>打包<\/em>与安装背后的故事","visible":1,"zan":0},{"apkLink":"","author":"stormzhang","chapterId":60,"chapterName":"Android Studio相关","collect":false,"courseId":13,"desc":"","envelopePic":"","id":74,"link":"http://stormzhang.com/devtools/2015/01/15/android-studio-tutorial6/","niceDate":"2016-06-12","origin":"stormzhang.com","projectLink":"","publishTime":1465695263000,"title":"Android Studio系列教程六--Gradle多渠道<em class='highlight'>打包<\/em>","visible":1,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 13
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticleBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleBean> datas) {
        this.datas = datas;
    }

    public static class ArticleBean {
        /**
         * apkLink :
         * author : 08_carmelo
         * chapterId : 0
         * chapterName :
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 2364
         * link : http://www.jianshu.com/p/b005ccddb29a
         * niceDate : 2018-02-23
         * origin :
         * projectLink :
         * publishTime : 1519398785000
         * title : app拆分，多产品<em class='highlight'>打包</em>实录.md
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private long publishTime;
        private String title;
        private int visible;
        private int zan;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
