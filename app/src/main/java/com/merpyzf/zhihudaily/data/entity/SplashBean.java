package com.merpyzf.zhihudaily.data.entity;

import java.util.List;

/**
 * Created by 春水碧于天 on 2017/4/21.
 */

public class SplashBean {

    private List<CreativesBean> creatives;

    public List<CreativesBean> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<CreativesBean> creatives) {
        this.creatives = creatives;
    }

    public static class CreativesBean {
        /**
         * url : https://pic4.zhimg.com/v2-d0837fd8e39d98b2d58d1911e4fbd913.jpg
         * start_time : 1492774391
         * impression_tracks : ["https://sugar.zhihu.com/track?vs=1&ai=3956&ut=&cg=2&ts=1492774391.35&si=c9e0f29730a24b6991121242455915c4&lu=0&hn=ad-engine.ad-engine.58f2cc63&at=impression&pf=PC&az=11&sg=8a652fcbe35ac7848bfe21c02e41ab3c"]
         * type : 0
         * id : 3956
         */

        private String url;
        private int start_time;
        private int type;
        private String id;
        private List<String> impression_tracks;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getImpression_tracks() {
            return impression_tracks;
        }

        public void setImpression_tracks(List<String> impression_tracks) {
            this.impression_tracks = impression_tracks;
        }
    }
}
