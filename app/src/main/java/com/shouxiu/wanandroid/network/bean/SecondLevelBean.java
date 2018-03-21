package com.shouxiu.wanandroid.network.bean;

import java.util.List;

/**
 * @author yeping
 * @date 2018/3/13 20:42
 * TODO
 */

public class SecondLevelBean {

    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"xiaoyang","chapterId":269,"chapterName":"官方发布","collect":false,"courseId":13,"desc":"","envelopePic":"","id":2462,"link":"http://www.wanandroid.com/blog/show/2073","niceDate":"2018-03-09","origin":"","projectLink":"","publishTime":1520583007000,"superChapterId":60,"superChapterName":"开发环境","title":"[快讯] Android P 开发者预览版","visible":1,"zan":0},{"apkLink":"","author":"移动开发前线","chapterId":269,"chapterName":"官方发布","collect":false,"courseId":13,"desc":"","envelopePic":"","id":2434,"link":"https://mp.weixin.qq.com/s/gICH9osyIzz9WerXO1C_8g","niceDate":"2018-03-02","origin":"","projectLink":"","publishTime":1519961226000,"superChapterId":60,"superChapterName":"开发环境","title":"谷歌确认将限制Android非SDK接口使用了","visible":1,"zan":0},{"apkLink":"","author":"一只有交流障碍的丑程序猿","chapterId":269,"chapterName":"官方发布","collect":false,"courseId":13,"desc":"","envelopePic":"","id":1837,"link":"https://juejin.im/post/5a3b9de2f265da43085e336b","niceDate":"2018-01-04","origin":"","projectLink":"","publishTime":1515037605000,"superChapterId":60,"superChapterName":"开发环境","title":"Android8.0 新SupportLibrary26、27功能及变化介绍","visible":1,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 3
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
}
