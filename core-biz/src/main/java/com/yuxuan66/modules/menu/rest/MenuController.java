/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuxuan66.modules.menu.rest;

import com.yuxuan66.sdk.BuildMenuHook;
import com.yuxuan66.sdk.eneity.Menu;
import com.yuxuan66.support.plugin.Plugin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sir丶雨轩
 * @since 2022/11/14
 */
@RequestMapping(path = "/menu")
@RestController
public class MenuController {


    @GetMapping
    public List<Menu> buildMenu() {
        // 查询菜单
        List<Menu> menus = new ArrayList<>();

        // 过一遍所有的菜单拦截插件
        for (BuildMenuHook menuHook : Plugin.getInstance().getBuildMenuHooks()) {
            menus = menuHook.buildMenu(menus);
        }

        // 查询挂载点执行处理
        return menus;
    }

}
