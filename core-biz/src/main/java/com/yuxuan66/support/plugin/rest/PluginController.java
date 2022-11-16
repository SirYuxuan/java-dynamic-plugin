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
package com.yuxuan66.support.plugin.rest;

import com.yuxuan66.sdk.PluginRegistrar;
import com.yuxuan66.support.plugin.Plugin;
import com.yuxuan66.support.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * 插件控制器
 *
 * @author Sir丶雨轩
 * @since 2022/11/14
 */
@RequestMapping(path = "/plugin")
@RestController
@RequiredArgsConstructor
public class PluginController {

    private final SqlSessionFactory sessionFactory;

    /**
     * 上传文件启动插件
     */
    @PostMapping
    public void startPlugin(MultipartFile file) {
        Plugin.getInstance().uploadPlugin(file, sessionFactory);
    }

    /**
     * 开启或关闭一个插件
     *
     * @param pluginId 插件id
     */
    @PutMapping("/{pluginId}/{enable}")
    public void switchPlugin(@PathVariable String pluginId, @PathVariable boolean enable) {
        Plugin.getInstance().switchPlugin(pluginId, enable);
    }

    /**
     * 移除一个插件
     *
     * @param pluginId 插件id
     */
    @DeleteMapping(path = "/{pluginId}")
    public void removePlugin(@PathVariable String pluginId) {
        Plugin.getInstance().removePlugin(pluginId);
    }


}
