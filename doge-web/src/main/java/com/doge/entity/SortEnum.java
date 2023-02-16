package com.doge.entity;

/**
 * Ant排序类型
 *
 * @author shixinyu
 * @date 2021-06-28 17:02
 */
public enum SortEnum {
    //升序
    ASCEND("ascend", "asc"),
    //降序
    DESCEND("descend", "desc");
    private String name;
    private String sortName;

    SortEnum(String name, String sortName) {
        this.name = name;
        this.sortName = sortName;
    }

    public String getSortName() {
        return sortName;
    }
}
