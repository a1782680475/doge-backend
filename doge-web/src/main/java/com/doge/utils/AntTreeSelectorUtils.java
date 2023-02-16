package com.doge.utils;

import com.doge.entity.vo.response.SysMenuVO;
import com.doge.entity.vo.response.AntTreeSelectVO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Ant树形选择器工具类
 *
 * @author shixinyu
 * @date 2021-07-05 14:35
 */
public class AntTreeSelectorUtils {
    /**
     * 生成菜单树形选择器数据
     *
     * @param allData
     * @return java.util.List<com.doge.entity.vo.response.AntTreeSelectVO>
     */
    public static List<AntTreeSelectVO> buildMenuTreeSelect(List<SysMenuVO> allData) {
        List<AntTreeSelectVO> menuTreeSelect = getMenuChild(0, allData);
        return menuTreeSelect;
    }

    /**
     * 递归查询子节点
     *
     * @param id      节点id
     * @param allData 全部数据
     * @return java.util.List<com.doge.entity.vo.response.AntTreeSelectVO>
     */
    private static List<AntTreeSelectVO> getMenuChild(Integer id, List<SysMenuVO> allData) {
        List<AntTreeSelectVO> children = new ArrayList<>();
        List<SysMenuVO> childrenData = allData.stream().filter(item -> item.getPid().equals(id)).sorted(Comparator.comparing(SysMenuVO::getSort)).collect(Collectors.toList());
        for (SysMenuVO item : childrenData) {
            List<AntTreeSelectVO> nextChildren = getMenuChild(item.getId(), allData);
            int childMaxSort = 0;
            if (nextChildren != null && !nextChildren.isEmpty()) {
                childMaxSort = nextChildren.get(nextChildren.size() - 1).getSort();
            }
            children.add(new AntTreeSelectVO(item.getId(), item.getMenuName(), item.getSort(), childMaxSort, nextChildren));
        }
        return children;
    }
}
