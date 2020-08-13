package taokdao.plugins.lua.doc;

import android.content.Intent;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;

@Keep
public class DynamicEntrance extends BaseDynamicPluginEntrance {

    @Override
    public void onCall(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        Intent intent = new Intent();
        intent.setClassName(pluginContext.getPackageName(), LuaDocActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        iMainContext.startActivity(intent);
    }
}
