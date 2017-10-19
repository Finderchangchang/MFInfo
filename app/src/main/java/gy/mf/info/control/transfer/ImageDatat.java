package gy.mf.info.control.transfer;

import java.util.List;

/**
 * Created by bing.ma on 2017/8/25.
 */

public class ImageDatat {
    /**
     * code : 200
     * message : 获取成功
     * data : {"state":"1","link":[{"picture_id":"35","picture_name":"46996b7dbed01176f0e2f387bd01b8e00.jpg","picture_set":"2","picture_url":"www.ggogle.com","is_collection":"0"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * state : 1
         * link : [{"picture_id":"35","picture_name":"46996b7dbed01176f0e2f387bd01b8e00.jpg","picture_set":"2","picture_url":"www.ggogle.com","is_collection":"0"}]
         */

        private String state;
        private List<LinkBean> link;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<LinkBean> getLink() {
            return link;
        }

        public void setLink(List<LinkBean> link) {
            this.link = link;
        }

        public static class LinkBean {
            /**
             * picture_id : 35
             * picture_name : 46996b7dbed01176f0e2f387bd01b8e00.jpg
             * picture_set : 2
             * picture_url : www.ggogle.com
             * is_collection : 0
             */

            private String picture_id;
            private String picture_name;
            private String picture_set;
            private String picture_url;
            private String is_collection;

            public String getPicture_id() {
                return picture_id;
            }

            public void setPicture_id(String picture_id) {
                this.picture_id = picture_id;
            }

            public String getPicture_name() {
                return picture_name;
            }

            public void setPicture_name(String picture_name) {
                this.picture_name = picture_name;
            }

            public String getPicture_set() {
                return picture_set;
            }

            public void setPicture_set(String picture_set) {
                this.picture_set = picture_set;
            }

            public String getPicture_url() {
                return picture_url;
            }

            public void setPicture_url(String picture_url) {
                this.picture_url = picture_url;
            }

            public String getIs_collection() {
                return is_collection;
            }

            public void setIs_collection(String is_collection) {
                this.is_collection = is_collection;
            }
        }
    }
}
