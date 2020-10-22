package taokdao.plugins.lua.doc;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import java.io.File;

import taokdao.plugins.setup.Constant;


public class LuaDocActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private LinearLayout mdView;
    private long mTouchTime;
    private AgentWeb.PreAgentWeb mAgentWeb;

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url= request.getUrl().toString();
            if (url.startsWith("file://")) {
                mAgentWeb.go(url);
                return true;
            }
//            return shouldOverrideUrlLoading(view,.toString());
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("file://")) {
                mAgentWeb.go(url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

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
                .setWebViewClient(mWebViewClient)
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
    protected void onPause() {
        super.onPause();
        mAgentWeb.get().getWebLifeCycle().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAgentWeb.get().getWebLifeCycle().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.get().getWebLifeCycle().onDestroy();
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
                File file = new File(Constant.extractedDir, "luadoc/contents.html");
                String url="file:///android_asset/luadoc/contents.html#contents";
                if (file.isFile()) {
                    url="file://" + file.getAbsolutePath() + "#contents";
                }
                mAgentWeb.go(url);
                break;
            case R.string.back:
                onBackPressed();
                break;
        }
        return false;
    }
}
