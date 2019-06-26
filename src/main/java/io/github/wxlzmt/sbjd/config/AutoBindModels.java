/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package io.github.wxlzmt.sbjd.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;

import io.github.wxlzmt.sbjd.config.annotation.ModelBind;

/**
 * 本类代码借鉴自 https://github.com/jflyfox/jfinal_cms/tree/master/src/main/java/com/jflyfox/jfinal/component/annotation/AutoBindModels.java
 * 
 * 自动绑定（来自jfinal ext) 1.如果没用加入注解，必须以Controller结尾,自动截取前半部分为key 2.加入ModelBind的 获取
 * key
 * 
 * @author zb 2014-3-20
 */
public class AutoBindModels {

    protected final Log logger = Log.getLog(getClass());

    private String configName = "main";

    private ActiveRecordPlugin arp;

    private List<Class<? extends Controller>> excludeClasses = new ArrayList<Class<? extends Controller>>();

    public AutoBindModels(ActiveRecordPlugin arp) {
        this.arp = arp;
        config();
    }

    public AutoBindModels(String configName, ActiveRecordPlugin arp) {
        this.configName = configName;
        this.arp = arp;
        config();
    }

    public AutoBindModels addExcludeClass(Class<? extends Controller> clazz) {
        if (clazz != null) {
            excludeClasses.add(clazz);
        }
        return this;
    }

    public AutoBindModels addExcludeClasses(Class<? extends Controller>[] clazzes) {
        excludeClasses.addAll(Arrays.asList(clazzes));
        return this;
    }

    public AutoBindModels addExcludeClasses(List<Class<? extends Controller>> clazzes) {
        excludeClasses.addAll(clazzes);
        return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void config() {
        List<Class<? extends Model>> controllerClasses = ClassSearcher.findInClasspath(Model.class);
        ModelBind ModelBind = null;
        for (Class model : controllerClasses) {
            if (excludeClasses.contains(model)) {
                continue;
            }
            ModelBind = (ModelBind) model.getAnnotation(ModelBind.class);
            if (ModelBind == null || StrKit.isBlank(ModelBind.table())) {
                continue;
            }

            // all default ,so not null
            if (StrKit.isBlank(ModelBind.configName()) || StrKit.isBlank(configName)) {
                logger.error("routes.add is null");
                continue;
            }

            // join many database support
            if (configName.equals(ModelBind.configName())) {
                arp.addMapping(ModelBind.table(), ModelBind.key(), model);
                logger.debug(ModelBind.configName() //
                        + " --> routes.add(" + model + "," + ModelBind.table() + ", " + ModelBind.key() + ")");
            }

        }
    }

    public List<Class<? extends Controller>> getExcludeClasses() {
        return excludeClasses;
    }

    public void setExcludeClasses(List<Class<? extends Controller>> excludeClasses) {
        this.excludeClasses = excludeClasses;
    }

}
