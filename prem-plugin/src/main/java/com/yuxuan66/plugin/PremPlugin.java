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

import com.yuxuan66.sdk.PluginRegistrar;
import com.yuxuan66.sdk.eneity.PluginInfo;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 插件初始化类，插件在被加载时会执行register方法。<br />
 * 保存传入的SqlSessionFactory即可全局操作数据库。
 * @author Sir丶雨轩
 * @since 2022/11/16
 */
public class PremPlugin implements PluginRegistrar {
    /**
     * 设置全局的SqlSessionFactory, 可以在全局调用操作数据库
     */
    private static SqlSessionFactory sessionFactory = null;

    public static SqlSessionFactory getSessionFactory(){
        return sessionFactory;
    }

    @Override
    public PluginInfo register(SqlSessionFactory sessionFactory) {
        PremPlugin.sessionFactory = sessionFactory;
        return new PluginInfo().
                setId("com.yuxuan66.premPlugin")
                .setName("权限插件")
                .setBuildMenuHook(PremBuild.class);
    }
}
