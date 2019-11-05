package plmi.app.mapoapp.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import plmi.app.mapoapp.MainActivity;
import plmi.app.mapoapp.MyWebViewClient;
import plmi.app.mapoapp.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        WebView wv =(WebView)root.findViewById(R.id.webView);
        WebSettings webSetting= wv.getSettings();
        webSetting.setSaveFormData(true);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);

        webSetting.setMixedContentMode(webSetting.MIXED_CONTENT_ALWAYS_ALLOW);

        Context context= getContext();
        //wv.setWebViewClient(new WebViewClient());
        wv.setWebViewClient(new MyWebViewClient(context));
        wv.setWebChromeClient(new WebChromeClient());


        wv.addJavascriptInterface(new WebAppInterface(context),"Android");

        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

            }
        });

        wv.loadUrl("http://210.220.217.132:18088/wonju.mysql/WB_WONJU_web/mWaste/m/main.jsp");
        // wv.loadUrl("file:///android_asset/www/index.html");


        return root;
    }

    public class WebAppInterface{
        Context mContext;

        WebAppInterface(Context c){
            mContext=c;
        }
        @JavascriptInterface
        public void getData(String value){
            Toast.makeText(mContext,value,Toast.LENGTH_LONG).show();
        }
    }

}