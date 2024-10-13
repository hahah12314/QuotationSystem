package com.wanshu.sys.vo;


import com.wanshu.sys.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuVo {
    List<SysMenu> menuList;
    List<Long> checkList;
}
