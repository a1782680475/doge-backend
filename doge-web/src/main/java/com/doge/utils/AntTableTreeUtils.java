package com.doge.utils;

import com.doge.entity.vo.response.SysMenuVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ant树形表格工具类
 *
 * @author shixinyu
 * @date 2021-07-02 11:00
 */
public class AntTableTreeUtils {

    /**
     * 生成树形表格数据
     *
     * @author shixinyu
     * @date 2021-07-02 11:03
     */
    public static List<SysMenuVO> buildMenuTableTree(List<SysMenuVO> allData) {
        List<SysMenuVO> menuTableTree = getMenuChild(0, allData);
        return menuTableTree;
    }

    /**
     * 递归查询子节点
     *
     * @param id      节点id
     * @param allData 全部数据
     * @return java.util.List<com.doge.entity.vo.response.AntTreeVO>
     */
    private static List<SysMenuVO> getMenuChild(Integer id, List<SysMenuVO> allData) {
        List<SysMenuVO> children = new ArrayList<>();
        List<SysMenuVO> childrenData = allData.stream()
                .filter(item -> item.getPid().equals(id))
                .sorted(Comparator.comparing(SysMenuVO::getSort))
                .collect(Collectors.toList());
        for (SysMenuVO item : childrenData) {
            List<SysMenuVO> nextChildren = getMenuChild(item.getId(), allData);
            if (nextChildren != null && !nextChildren.isEmpty()) {
                item.setChildMaxSort(nextChildren.get(nextChildren.size() - 1).getSort());
            } else {
                item.setChildMaxSort(0);
            }
            item.setChildren(nextChildren);
            children.add(item);
        }
        return children.size() == 0 ? null : children;
    }
}
