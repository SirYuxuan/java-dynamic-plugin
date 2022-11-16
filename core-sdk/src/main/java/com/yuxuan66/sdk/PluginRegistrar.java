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

import com.yuxuan66.sdk.eneity.PluginInfo;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 插件接口，插件实现此类完成一个插件
 * @author Sir丶雨轩
 * @since 2022/11/16
 */
public interface PluginRegistrar {

    /**
     * 插件注册, 交给插件sqlSessionFactory, 获得插件返回的插件信息
     * @param sessionFactory sql session 工厂
     */
    PluginInfo register(SqlSessionFactory sessionFactory);
}
