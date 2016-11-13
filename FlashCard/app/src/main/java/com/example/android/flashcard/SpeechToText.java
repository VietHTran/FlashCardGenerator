package com.example.android.flashcard;

/**
 * Created by Viet on 11/12/2016.
 */
import android.content.Context;
import android.util.Log;
import com.ibm.watson.developer_cloud.android.speech_common.v1.TokenProvider;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.ISpeechDelegate;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.audio.AudioCaptureThread;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.audio.IAudioConsumer;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.audio.IChunkUploader;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.audio.WebSocketUploader;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.dto.SpeechConfiguration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.java_websocket.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeechToText {
    protected static final String TAG = "SpeechToText";
    private Context appCtx;
    private SpeechConfiguration sConfig = null;
    private AudioCaptureThread audioCaptureThread = null;
    private IChunkUploader uploader = null;
    private ISpeechDelegate delegate = null;
    private String username;
    private String password;
    private String model;
    private TokenProvider tokenProvider = null;
    private URI hostURL;
    private static SpeechToText _instance = null;

    public SpeechToText() {
    }

    public static SpeechToText sharedInstance() {
        if(_instance == null) {
            Class var0 = SpeechToText.class;
            synchronized(SpeechToText.class) {
                _instance = new SpeechToText();
            }
        }
        return _instance;
    }

    public void initWithContext(URI uri, Context ctx, SpeechConfiguration sc) {
        this.setHostURL(uri);
        this.appCtx = ctx;
        this.sConfig = sc;
    }

    private void startRecording() {
        this.uploader.prepare();
        SpeechToText.STTIAudioConsumer audioConsumer = new SpeechToText.STTIAudioConsumer(this.uploader);
        this.audioCaptureThread = new AudioCaptureThread(22050, audioConsumer);
        this.audioCaptureThread.start();
    }

    public void recognize() {
        Log.d("SpeechToText", "recognize");

        try {
            HashMap e = new HashMap();
            e.put("Content-Type", this.sConfig.audioFormat);
            String wsURL;
            if(this.sConfig.isAuthNeeded) {
                if(this.tokenProvider != null) {
                    e.put("X-Watson-Authorization-Token", this.tokenProvider.getToken());
                    Log.d("SpeechToText", "ws connecting with token based authentication");
                } else {
                    wsURL = "Basic " + Base64.encodeBytes((this.username + ":" + this.password).getBytes(Charset.forName("UTF-8")));
                    e.put("Authorization", wsURL);
                    Log.d("SpeechToText", "ws connecting with Basic Authentication");
                }
            }

            wsURL = this.getHostURL().toString() + "/v1/recognize" + (this.model != null?"?model=" + this.model:"");
            this.uploader = new WebSocketUploader(wsURL, e, this.sConfig);
            this.uploader.setDelegate(this.delegate);
            this.startRecording();
        } catch (URISyntaxException var3) {
            var3.printStackTrace();
        }

    }

    public void stopRecording() {
        if(this.audioCaptureThread != null) {
            this.audioCaptureThread.end();
        }

    }

    public void stopRecognition() {
        this.stopRecording();
        if(this.uploader != null) {
            this.uploader.stop();
            this.uploader.close();
        }

    }

    private void buildAuthenticationHeader(HttpGet httpGet) {
        if(this.tokenProvider != null) {
            Log.d("SpeechToText", "using token based authentication");
            httpGet.setHeader("X-Watson-Authorization-Token", this.tokenProvider.getToken());
        } else {
            Log.d("SpeechToText", "using basic authentication");
            httpGet.setHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(this.username, this.password), "UTF-8", false));
        }

    }

    public JSONObject getModels() {
        JSONObject object = null;

        try {
            Log.d("SpeechToText", "starting getModels");
            DefaultHttpClient e = new DefaultHttpClient();
            String strHTTPURL = this.hostURL.toString().replace("wss", "https").replace("ws", "http");
            HttpGet httpGet = new HttpGet(strHTTPURL + "/v1/models");
            this.buildAuthenticationHeader(httpGet);
            httpGet.setHeader("accept", "application/json");
            HttpResponse executed = e.execute(httpGet);
            InputStream is = executed.getEntity().getContent();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            Log.d("SpeechToText", "response: " + responseStrBuilder.toString());
            object = new JSONObject(responseStrBuilder.toString());
            Log.d("SpeechToText", object.toString());
        } catch (JSONException | IOException var10) {
            var10.printStackTrace();
        }

        return object;
    }

    public JSONObject getModelInfo(String strModel) {
        JSONObject object = null;

        try {
            DefaultHttpClient e = new DefaultHttpClient();
            String strHTTPURL = this.hostURL.toString().replace("wss", "https").replace("ws", "http");
            HttpGet httpGet = new HttpGet(strHTTPURL + "/v1/models/en-US_NarrowbandModel");
            this.buildAuthenticationHeader(httpGet);
            httpGet.setHeader("accept", "application/json");
            HttpResponse executed = e.execute(httpGet);
            InputStream is = executed.getEntity().getContent();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            object = new JSONObject(responseStrBuilder.toString());
            Log.d("SpeechToText", object.toString());
        } catch (JSONException | IOException var11) {
            var11.printStackTrace();
        }

        return object;
    }

    public URI getHostURL() {
        return this.hostURL;
    }

    public void setHostURL(URI hostURL) {
        this.hostURL = hostURL;
    }

    public ISpeechDelegate getDelegate() {
        return this.delegate;
    }

    public void setDelegate(ISpeechDelegate val) {
        this.delegate = val;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private class STTIAudioConsumer implements IAudioConsumer {
        private IChunkUploader mUploader = null;

        public STTIAudioConsumer(IChunkUploader uploader) {
            this.mUploader = uploader;
        }

        public void consume(byte[] data) {
            this.mUploader.onHasData(data);
        }

        public void onAmplitude(double amplitude, double volume) {
            if(SpeechToText.this.delegate != null) {
                SpeechToText.this.delegate.onAmplitude(amplitude, volume);
            }

        }
    }
}
