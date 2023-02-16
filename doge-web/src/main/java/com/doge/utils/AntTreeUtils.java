package com.doge.utils;

import com.doge.entity.vo.response.SysMenuVO;
import com.doge.entity.vo.response.AntTreeVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ant树工具类
 *
 * @author shixinyu
 * @date 2021-07-27 17:18
 */
public class AntTreeUtils {

    /**
     * 生成菜单树数据
     *
     * @param allData
     * @return java.util.List<com.doge.entity.vo.response.AntTreeVO>
     */
    public static List<AntTreeVO> buildMenuTree(List<SysMenuVO> allData) {
        List<AntTreeVO> menuTree = getMenuChild(0, allData);
        return menuTree;
    }

    /**
     * 递归查询子节点
     *
     * @param id      节点id
     * @param allData 全部数据
     * @return java.util.List<com.doge.entity.vo.response.AntTreeSelectVO>
     */
    private static List<AntTreeVO> getMenuChild(Integer id, List<SysMenuVO> allData) {
        List<AntTreeVO> children = new ArrayList<>();
        List<SysMenuVO> childrenData = allData.stream().filter(item -> item.getPid().equals(id)).sorted(Comparator.comparing(SysMenuVO::getSort)).collect(Collectors.toList());
        for (SysMenuVO item : childrenData) {
            List<AntTreeVO> nextChildren = getMenuChild(item.getId(), allData);
            children.add(new AntTreeVO(item.getId(), item.getMenuName(), nextChildren));
        }
        return children;
    }
}
