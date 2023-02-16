package com.doge.utils;

import com.doge.entity.vo.response.AntMenuVO;
import com.doge.entity.vo.response.SysMenuVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ant菜单工具类
 *
 * @author shixinyu
 * @date 2021-08-25 09:55
 */
public class AntMenuUtils {
    public static List<AntMenuVO> buildMenu(List<SysMenuVO> allData) {
        List<AntMenuVO> menuTree = getMenuChild(0, allData);
        return menuTree;
    }
    /**
     * 递归查询子节点
     *
     * @param id      节点id
     * @param allData 全部数据
     * @return java.util.List<com.doge.entity.vo.response.AntMenuVO>
     */
    private static List<AntMenuVO> getMenuChild(Integer id, List<SysMenuVO> allData) {
        List<AntMenuVO> children = new ArrayList<>();
        List<SysMenuVO> childrenData = allData.stream()
                .filter(item -> item.getPid().equals(id))
                .sorted(Comparator.comparing(SysMenuVO::getSort))
                .collect(Collectors.toList());
        for (SysMenuVO item : childrenData) {
            List<AntMenuVO> nextChildren = getMenuChild(item.getId(), allData);
            AntMenuVO antMenuVO = new AntMenuVO();
            antMenuVO.setPath(item.getPath());
            antMenuVO.setName(item.getMenuName());
            antMenuVO.setIcon(item.getIcon());
            antMenuVO.setChildren(nextChildren);
            children.add(antMenuVO);
        }
        return children;
    }
}
