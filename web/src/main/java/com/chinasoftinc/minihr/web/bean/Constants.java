package com.chinasoftinc.minihr.web.bean;

/**
 * Created by Aizhanglin on 2018/4/16.
 */
public class Constants {
    public enum DeptLevel{
        PROJECT(1),DEPT_FIRST(2),DEPT_SEC(3);
        private Integer code;
        private DeptLevel(Integer code){
            this.code=code;
        }
        public Integer getCode(){
            return code;
        }
    }
}
