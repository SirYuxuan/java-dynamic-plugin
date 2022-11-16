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
package com.yuxuan66.support.plugin;

import com.yuxuan66.sdk.BuildMenuHook;
import com.yuxuan66.sdk.PluginRegistrar;
import com.yuxuan66.sdk.eneity.PluginInfo;
import com.yuxuan66.support.utils.ClassUtil;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 系统全局唯一的插件类,维护插件的注册关系等
 *
 * @author Sir丶雨轩
 * @since 2022/11/16
 */
public class Plugin {

    private Plugin() {
    }

    private static final Plugin INSTANCE = new Plugin();

    public static Plugin getInstance() {
        return INSTANCE;
    }

    // 插件注册列表
    private final Map<String, PluginInfo> pluginInfoMap = new HashMap<>();


    /**
     * 上传一个新的插件文件并启动
     * @param file 插件文件
     * @param sessionFactory 数据库操作
     */
    public void uploadPlugin(MultipartFile file, SqlSessionFactory sessionFactory){
        try {
            // TODO 这里假设上传的文件是合理合法的插件jar，具体需要对文件进行校验, 保存的目录必须存在
            File pluginFile = new File("D://plugins/" + UUID.randomUUID() + ".jar");
            // 上传的jar保存到临时目录
            file.transferTo(pluginFile);
            // 加载jar
            try (URLClassLoader jarUrlClassLoader = new URLClassLoader(new URL[]{pluginFile.toURI().toURL()},
                    Thread.currentThread().getContextClassLoader()); JarFile jarFile = new JarFile(pluginFile)) {
                // 开始获取jar中的.class文件
                Enumeration<JarEntry> entries = jarFile.entries();
                List<Class<?>> classList = ClassUtil.getClassNames(jarUrlClassLoader,entries);
                for (Class<?> aClass : classList) {
                    if (PluginRegistrar.class.isAssignableFrom(aClass)) {
                        PluginRegistrar registrar = (PluginRegistrar) aClass.getConstructor().newInstance();
                        // 注册一个插件
                        Plugin.getInstance().register(registrar.register(sessionFactory));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 注册插件
     *
     * @param pluginInfo 插件信息
     */
    public void register(PluginInfo pluginInfo) {
        pluginInfoMap.put(pluginInfo.getId(), pluginInfo);
    }

    /**
     * 开启或关闭一个插件
     *
     * @param pluginId 插件id
     */
    public void switchPlugin(String pluginId, boolean enable) {
        if (pluginInfoMap.containsKey(pluginId)) {
            pluginInfoMap.get(pluginId).setEnable(enable);
        }
    }

    /**
     * 移除一个插件
     * @param pluginId 插件id
     */
    public void removePlugin(String pluginId) {
        pluginInfoMap.remove(pluginId);
    }

    /**
     * TODO 这里实现有问题，应该换成每个类的实例，不能在每次获取的时候重新实例化。
     * 获取所有菜单拦截插件
     * @return 菜单拦截插件
     */
    @SneakyThrows
    public List<BuildMenuHook> getBuildMenuHooks() {
        List<BuildMenuHook> buildMenuHooks = new ArrayList<>();
        for (PluginInfo pluginInfo : pluginInfoMap.values()) {
            if (pluginInfo.isEnable() && pluginInfo.getBuildMenuHook() != null) {
                buildMenuHooks.add(pluginInfo.getBuildMenuHook().getConstructor().newInstance());
            }
        }
        return buildMenuHooks;
    }

}
