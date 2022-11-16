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
package com.yuxuan66.support.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * @author Sir丶雨轩
 * @since 2022/11/16
 */
public final class ClassUtil {

    /**
     * 打印并获取所有的class
     *
     * @param entries jar中的所有文件
     * @return 所有的class
     */
    public static List<Class<?>> getClassNames(ClassLoader classLoader,Enumeration<JarEntry> entries) throws ClassNotFoundException {
        List<Class<?>> classNames = new ArrayList<>();
        while (entries.hasMoreElements()) {
            JarEntry nextElement = entries.nextElement();
            String name = nextElement.getName();
            if (name.endsWith(".class")) {
                String replace = name.replace(".class", "").replace("/", ".");
                classNames.add(classLoader.loadClass(replace));
            }
        }
        return classNames;
    }
}
