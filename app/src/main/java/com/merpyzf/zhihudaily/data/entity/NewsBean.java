package com.merpyzf.zhihudaily.data.entity;

import java.util.List;

/**
 * Created by 春水碧于天 on 2017/4/23.
 */

public class NewsBean {

    /**
     * date : 20170423
     * stories : [{"ga_prefix":"042313","id":9368219,"images":["https://pic1.zhimg.com/v2-943c07f49bb018694194469c9e01f2d0.jpg"],"title":"人会打喷嚏，树也会得「流感」，而且后果很严重","type":0},{"ga_prefix":"042312","id":9366874,"images":["https://pic1.zhimg.com/v2-cb82e9260f3388016dffd800b43f73e0.jpg"],"title":"大误 · 外星人不侵略地球的理由难道还不简单？","type":0},{"ga_prefix":"042311","id":9351904,"images":["https://pic1.zhimg.com/v2-a804eb988f15a23d11ab274dffc77b9c.jpg"],"title":"时光回到 04 年，你会看好 Google 的股票还是一个卖披萨的？","type":0},{"ga_prefix":"042311","id":9376712,"images":["https://pic4.zhimg.com/v2-6918a532d0681dfc67f7b3e40a9a8343.jpg"],"title":"地大物博一点儿没错，不过这几种地貌景观中国确实没有","type":0},{"ga_prefix":"042309","id":9373986,"images":["https://pic2.zhimg.com/v2-3c09aa87888eb7e6c5d80bcb90c4f485.jpg"],"multipic":true,"title":"山的那边是山，喜马拉雅的那边则是另一个世界","type":0},{"ga_prefix":"042308","id":9370985,"images":["https://pic1.zhimg.com/v2-56c82390a1e1a9324a1ae58481a17e80.jpg"],"title":"不会日语，如何在复杂的派系中选出自己最喜欢的拉面？","type":0},{"ga_prefix":"042307","id":9371243,"images":["https://pic3.zhimg.com/v2-aee7c0d4c2c4983991ea0638b2df131e.jpg"],"title":"就算杀人放火，该给的政策优惠一样不会少，唯有贩毒不行","type":0},{"ga_prefix":"042307","id":9376384,"images":["https://pic1.zhimg.com/v2-f4a4354f74897777bd630b2d71a7f9f0.jpg"],"title":"没钱，连呼吸都是疼的？","type":0},{"ga_prefix":"042307","id":9375521,"images":["https://pic4.zhimg.com/v2-86c0d65ae1627cb749ed66bc1a8c7557.jpg"],"title":"刘看山的人类学研究笔记 · 脱掉？！","type":0},{"ga_prefix":"042306","id":9371844,"images":["https://pic1.zhimg.com/v2-c1de23d62a06de10f626afc5bd3f48f0.jpg"],"title":"瞎扯 · 如何正确地吐槽","type":0}]
     * top_stories : [{"ga_prefix":"042219","id":9375775,"image":"https://pic3.zhimg.com/v2-decf047d3154bc987e2dbf0344ae9bc2.jpg","title":"世界地球日里，我们有这几个故事想讲给你听","type":0},{"ga_prefix":"042213","id":9374087,"image":"https://pic4.zhimg.com/v2-c42e8046e942dedd951ee5c0c139b35b.jpg","title":"在阳台上种菜不算稀奇，但如果是在非洲呢？","type":0},{"ga_prefix":"042207","id":9374066,"image":"https://pic1.zhimg.com/v2-8f0ef481d074801b658e9593cffad970.jpg","title":"即将上线 WeGame 游戏平台，腾讯下了一步什么棋？","type":0},{"ga_prefix":"042207","id":9369557,"image":"https://pic4.zhimg.com/v2-8cd128739ac6858b96ff186f54008e83.jpg","title":"为什么我不赞成「女性要经济独立，所以要抛弃家务」","type":0},{"ga_prefix":"042115","id":9373553,"image":"https://pic2.zhimg.com/v2-1d112170b41ef0ad8675012fbd43daa1.jpg","title":"叫停 iOS 版微信赞赏这事儿，苹果做得「合法」吗？","type":0}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * ga_prefix : 042313
         * id : 9368219
         * images : ["https://pic1.zhimg.com/v2-943c07f49bb018694194469c9e01f2d0.jpg"]
         * title : 人会打喷嚏，树也会得「流感」，而且后果很严重
         * type : 0
         * multipic : true
         */

        private String ga_prefix;
        private int id;
        private String title;
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        private int type;
        private boolean multipic;
        private List<String> images;

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * ga_prefix : 042219
         * id : 9375775
         * image : https://pic3.zhimg.com/v2-decf047d3154bc987e2dbf0344ae9bc2.jpg
         * title : 世界地球日里，我们有这几个故事想讲给你听
         * type : 0
         */

        private String ga_prefix;
        private int id;
        private String image;
        private String title;
        private int type;

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
