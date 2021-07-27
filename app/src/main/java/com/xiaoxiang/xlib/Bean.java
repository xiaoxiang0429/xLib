package com.xiaoxiang.xlib;

/**
 * @author xiaoxiang
 * @date 2021/7/27
 * email：tianzh@ccbsun.com
 * description：
 */
public class Bean {
    String type;
    String content;

    public Bean(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
