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
package com.yuxuan66.sdk;

import com.yuxuan66.sdk.eneity.Menu;

import java.util.List;

/**
 * @author Sir丶雨轩
 * @since 2022/11/14
 */
public interface BuildMenuHook {

    /**
     * 对原始菜单进行处理
     * @param menus 原始菜单
     * @return 处理后的菜单
     */
    List<Menu> buildMenu(List<Menu> menus);
}
