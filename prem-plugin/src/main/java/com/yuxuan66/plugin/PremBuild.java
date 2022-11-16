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
package com.yuxuan66.plugin;

import com.yuxuan66.plugin.mapper.PremMapper;
import com.yuxuan66.sdk.BuildMenuHook;
import com.yuxuan66.sdk.eneity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Sir丶雨轩
 * @since 2022/11/14
 */
@Slf4j
public class PremBuild implements BuildMenuHook {

    final String TABLE_NAME = "";

    @Override
    public List<Menu> buildMenu(List<Menu> menus) {
        // 判断表不存在时创建表

        // 获得全局保存的SqlSessionFactory
        SqlSessionFactory sessionFactory = PremPlugin.getSessionFactory();
        // 注册一个新的Mapper
        sessionFactory.getConfiguration().addMapper(PremMapper.class);
        // 获得Mapper
        PremMapper premMapper = sessionFactory.openSession().getMapper(PremMapper.class);

        log.info("获取到Mapper: {}", premMapper);

        if (premMapper.checkTableExist(TABLE_NAME) == 0) {
            premMapper.createTable(TABLE_NAME);
        }
        // 执行SQL查询权限 TODO 没有实现具体查询过程
        // premMapper.selectPrem(TABLE_NAME);
        // 根据权限过滤菜单

        // 返回处理后的菜单
        return menus;
    }
}
