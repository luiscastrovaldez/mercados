package pe.com.minagri.mercados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class DashboardActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        webView = (WebView)findViewById(R.id.dashboard);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://geosiea.minagri.gob.pe/portal/apps/webappviewer/index.html?id=23e90e2c82d14dcf8fb06a317193fcac");
    }
}
