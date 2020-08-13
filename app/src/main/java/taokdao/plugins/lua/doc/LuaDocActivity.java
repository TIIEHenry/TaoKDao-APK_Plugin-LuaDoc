package taokdao.plugins.lua.doc;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.just.agentweb.AgentWeb;

import java.io.File;

import taokdao.plugins.setup.Constant;


public class LuaDocActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private LinearLayout mdView;
    private long mTouchTime;
    private AgentWeb.PreAgentWeb mAgentWeb;


    @Override
    protected void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());//绕过provider
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        mdView = findViewById(R.id.ll_container);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mdView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready();
        mAgentWeb.get().getAgentWebSettings().getWebSettings().setUseWideViewPort(false);

        File file = new File(Constant.extractedDir, "luadoc/manual.html");
        if (file.isFile()) {
            mAgentWeb.go("file://" + file.getAbsolutePath());
        } else {
            mAgentWeb.go("file:///android_asset/luadoc/manual.html");
//            Toast.makeText(this, file.toString() + " does not exists", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.get().handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
//        if (mAgentWeb.get().back()) {
//        } else {
        if ((System.currentTimeMillis() - mTouchTime) > 2000) {
            mTouchTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.pressagaintoexit, Toast.LENGTH_LONG).show();
        } else {
            mTouchTime = 0;
            finish();
        }
//        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, R.string.catlog, 0, R.string.catlog).setOnMenuItemClickListener(this).setShowAsAction(2);
        menu.add(0, R.string.back, 0, R.string.back).setOnMenuItemClickListener(this).setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.string.catlog:
                File file = new File(Constant.extractedDir, "luadoc/contents.html#contents");
                mAgentWeb.go("file://" + file.getAbsolutePath());
                break;
            case R.string.back:
                onBackPressed();
                break;
        }
        return false;
    }
}
