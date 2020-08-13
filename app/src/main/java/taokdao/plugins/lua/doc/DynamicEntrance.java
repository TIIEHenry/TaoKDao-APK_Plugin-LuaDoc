package taokdao.plugins.lua.doc;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.api.plugin.entrance.IDynamicPluginEntrance;

@Keep
public class DynamicEntrance extends BaseDynamicPluginEntrance {

    @Override
    public void onAttach(@NonNull Context pluginContext) {

    }

    @Override
    public void onUpGrade(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onDownGrade(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onCreate(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onInit(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onCall(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        Intent intent = new Intent();
        intent.setClassName(DynamicEntrance.class.getPackage().getName(), DynamicEntrance.class.getPackage().getName() + ".LuaDocActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        iMainContext.startActivity(intent);
    }

    @Override
    public void onResume(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onPause(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onDestroy(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }
}
