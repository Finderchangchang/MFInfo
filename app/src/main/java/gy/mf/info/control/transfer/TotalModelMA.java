package gy.mf.info.control.transfer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bing.ma on 2017/8/24.
 */

public class TotalModelMA implements Serializable{
    /**
     * code : 200
     * message : 获取成功
     * data : {"state":"1","all_content":[{"class_sex":"1","data_2":[{"class_id":"1","class_name":"头发","class_set":"1","class_sex":"1","class_lastid":"","data_3":[{"class_id":"2","class_name":"卷毛","class_set":"2","class_sex":"1","class_lastid":"1"},{"class_id":"3","class_name":"烫的","class_set":"2","class_sex":"1","class_lastid":"1"}]},{"class_id":"4","class_name":"脸","class_set":"1","class_sex":"1","class_lastid":"","data_3":[]}]},{"class_sex":"2","data_2":[{"class_id":"5","class_name":"脸","class_set":"1","class_sex":"2","class_lastid":"","data_3":[{"class_id":"6","class_name":"长的","class_set":"2","class_sex":"2","class_lastid":"5"},{"class_id":"7","class_name":"短的","class_set":"2","class_sex":"2","class_lastid":"5"}]}]},{"class_sex":"3","data_2":[{"class_id":"8","class_name":"衣服","class_set":"1","class_sex":"3","class_lastid":"","data_3":[{"class_id":"9","class_name":"绿的","class_set":"2","class_sex":"3","class_lastid":"8"}]}]},{"class_sex":"4","data_2":[{"class_id":"10","class_name":"美妆","class_set":"1","class_sex":"4","class_lastid":"","data_3":[]},{"class_id":"11","class_name":"护发","class_set":"1","class_sex":"4","class_lastid":"","data_3":[]},{"class_id":"12","class_name":"美甲","class_set":"1","class_sex":"4","class_lastid":"","data_3":[]}]},{"class_sex":"5","data_2":[{"class_id":"13","class_name":"美妆","class_set":"1","class_sex":"5","class_lastid":"","data_3":[]},{"class_id":"14","class_name":"护发","class_set":"1","class_sex":"5","class_lastid":"","data_3":[]},{"class_id":"15","class_name":"美甲","class_set":"1","class_sex":"5","class_lastid":"","data_3":[]}]}]}
     */

    private int code;
    private String message;
    private TypeModel data;

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

    public TypeModel getData() {
        return data;
    }

    public void setData(TypeModel data) {
        this.data = data;
    }

    public static class TypeModel implements Serializable{
        /**
         * state : 1
         * all_content : [{"class_sex":"1","data_2":[{"class_id":"1","class_name":"头发","class_set":"1","class_sex":"1","class_lastid":"","data_3":[{"class_id":"2","class_name":"卷毛","class_set":"2","class_sex":"1","class_lastid":"1"},{"class_id":"3","class_name":"烫的","class_set":"2","class_sex":"1","class_lastid":"1"}]},{"class_id":"4","class_name":"脸","class_set":"1","class_sex":"1","class_lastid":"","data_3":[]}]},{"class_sex":"2","data_2":[{"class_id":"5","class_name":"脸","class_set":"1","class_sex":"2","class_lastid":"","data_3":[{"class_id":"6","class_name":"长的","class_set":"2","class_sex":"2","class_lastid":"5"},{"class_id":"7","class_name":"短的","class_set":"2","class_sex":"2","class_lastid":"5"}]}]},{"class_sex":"3","data_2":[{"class_id":"8","class_name":"衣服","class_set":"1","class_sex":"3","class_lastid":"","data_3":[{"class_id":"9","class_name":"绿的","class_set":"2","class_sex":"3","class_lastid":"8"}]}]},{"class_sex":"4","data_2":[{"class_id":"10","class_name":"美妆","class_set":"1","class_sex":"4","class_lastid":"","data_3":[]},{"class_id":"11","class_name":"护发","class_set":"1","class_sex":"4","class_lastid":"","data_3":[]},{"class_id":"12","class_name":"美甲","class_set":"1","class_sex":"4","class_lastid":"","data_3":[]}]},{"class_sex":"5","data_2":[{"class_id":"13","class_name":"美妆","class_set":"1","class_sex":"5","class_lastid":"","data_3":[]},{"class_id":"14","class_name":"护发","class_set":"1","class_sex":"5","class_lastid":"","data_3":[]},{"class_id":"15","class_name":"美甲","class_set":"1","class_sex":"5","class_lastid":"","data_3":[]}]}]
         */

        private String state;
        private List<Type> all_content;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<Type> getAll_content() {
            return all_content;
        }

        public void setAll_content(List<Type> all_content) {
            this.all_content = all_content;
        }

        public static class Type implements Serializable{
            /**
             * class_sex : 1
             * data_2 : [{"class_id":"1","class_name":"头发","class_set":"1","class_sex":"1","class_lastid":"","data_3":[{"class_id":"2","class_name":"卷毛","class_set":"2","class_sex":"1","class_lastid":"1"},{"class_id":"3","class_name":"烫的","class_set":"2","class_sex":"1","class_lastid":"1"}]},{"class_id":"4","class_name":"脸","class_set":"1","class_sex":"1","class_lastid":"","data_3":[]}]
             */
            private String class_id;
            private String class_sex;
            private List<Data2Bean> data_2;

            public String getClass_id() {
                return class_id;
            }

            public void setClass_id(String class_id) {
                this.class_id = class_id;
            }

            public String getClass_sex() {
                return class_sex;
            }

            public void setClass_sex(String class_sex) {
                this.class_sex = class_sex;
            }

            public List<Data2Bean> getData_2() {
                return data_2;
            }

            public void setData_2(List<Data2Bean> data_2) {
                this.data_2 = data_2;
            }

            public static class Data2Bean implements Serializable{
                /**
                 * class_id : 1
                 * class_name : 头发
                 * class_set : 1
                 * class_sex : 1
                 * class_lastid :
                 * data_3 : [{"class_id":"2","class_name":"卷毛","class_set":"2","class_sex":"1","class_lastid":"1"},{"class_id":"3","class_name":"烫的","class_set":"2","class_sex":"1","class_lastid":"1"}]
                 */

                private String class_id;
                private String class_name;
                private String class_set;
                private String class_sex;
                private String class_lastid;
                private boolean isCheck;
                private List<Data3Bean> data_3;

                public boolean isCheck() {
                    return isCheck;
                }

                public void setCheck(boolean check) {
                    isCheck = check;
                }

                public String getClass_id() {
                    return class_id;
                }

                public void setClass_id(String class_id) {
                    this.class_id = class_id;
                }

                public String getClass_name() {
                    return class_name;
                }

                public void setClass_name(String class_name) {
                    this.class_name = class_name;
                }

                public String getClass_set() {
                    return class_set;
                }

                public void setClass_set(String class_set) {
                    this.class_set = class_set;
                }

                public String getClass_sex() {
                    return class_sex;
                }

                public void setClass_sex(String class_sex) {
                    this.class_sex = class_sex;
                }

                public String getClass_lastid() {
                    return class_lastid;
                }

                public void setClass_lastid(String class_lastid) {
                    this.class_lastid = class_lastid;
                }

                public List<Data3Bean> getData_3() {
                    return data_3;
                }

                public void setData_3(List<Data3Bean> data_3) {
                    this.data_3 = data_3;
                }

                public static class Data3Bean implements Serializable{
                    /**
                     * class_id : 2
                     * class_name : 卷毛
                     * class_set : 2
                     * class_sex : 1
                     * class_lastid : 1
                     */

                    private String class_id;
                    private String class_name;
                    private String class_set;
                    private String class_sex;
                    private String class_lastid;
                    private boolean isCheck;

                    public boolean isCheck() {
                        return isCheck;
                    }

                    public void setCheck(boolean check) {
                        isCheck = check;
                    }

                    public String getClass_id() {
                        return class_id;
                    }

                    public void setClass_id(String class_id) {
                        this.class_id = class_id;
                    }

                    public String getClass_name() {
                        return class_name;
                    }

                    public void setClass_name(String class_name) {
                        this.class_name = class_name;
                    }

                    public String getClass_set() {
                        return class_set;
                    }

                    public void setClass_set(String class_set) {
                        this.class_set = class_set;
                    }

                    public String getClass_sex() {
                        return class_sex;
                    }

                    public void setClass_sex(String class_sex) {
                        this.class_sex = class_sex;
                    }

                    public String getClass_lastid() {
                        return class_lastid;
                    }

                    public void setClass_lastid(String class_lastid) {
                        this.class_lastid = class_lastid;
                    }
                }
            }
        }
    }
}
