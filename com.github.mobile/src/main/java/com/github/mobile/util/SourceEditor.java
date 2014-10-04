/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mobile.util;

import static org.eclipse.egit.github.core.Blob.ENCODING_BASE64;
import static org.eclipse.egit.github.core.client.IGitHubConstants.CHARSET_UTF8;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.mobile.ui.UrlLauncher;

import java.io.UnsupportedEncodingException;

import org.eclipse.egit.github.core.Blob;
import org.eclipse.egit.github.core.util.EncodingUtils;

/**
 * Utilities for displaying source code in a {@link WebView}
 */
public class SourceEditor {

    private static final String URL_PAGE = "file:///android_asset/source-editor.html";

    private static final String TAG = "SourceEditor";

    private final WebView view;

    private boolean wrap;

    private String name;

    private String content;

    private boolean encoded;

    private boolean markdown;

    /**
     * Create source editor using given web view
     *
     * @param view
     */
    public SourceEditor(final WebView view) {
        WebViewClient client = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URL_PAGE.equals(url)) {
                    view.loadUrl(url);
                    Log.i(TAG,"shouldOverrideUrlLoading:"+url);
                    return false;
                } else {
                    Log.i(TAG,"startActivity:"+url);
                    Context context = view.getContext();
                    Intent intent = new UrlLauncher(context).create(url);
                    context.startActivity(intent);
                    return true;
                }
            }
        };
        view.setWebViewClient(client);

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        view.addJavascriptInterface(this, "SourceEditor");

        this.view = view;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return content
     */
    public String getRawContent() {
        return content;
    }

    /**
     * @return content
     */
    public String getContent() {
        if (encoded)
            try {
                return new String(EncodingUtils.fromBase64(content),
                        CHARSET_UTF8);
            } catch (UnsupportedEncodingException e) {
                return getRawContent();
            }
        else
            return getRawContent();
    }

    /**
     * @return wrap
     */
    public boolean getWrap() {
        return wrap;
    }

    /**
     * @return markdown
     */
    public boolean isMarkdown() {
        return markdown;
    }

    /**
     * Set whether lines should wrap
     *
     * @param wrap
     * @return this editor
     */
    public SourceEditor setWrap(final boolean wrap) {
        this.wrap = wrap;
        loadSource();
        return this;
    }

    /**
     * Sets whether the content is a markdown file
     *
     * @param markdown
     * @return this editor
     */
    public SourceEditor setMarkdown(final boolean markdown) {
        this.markdown = markdown;
        return this;
    }

    /**
     * Bind content to current {@link WebView}
     *
     * @param name
     * @param content
     * @param encoded
     * @return this editor
     */
    public SourceEditor setSource(final String name, final String content,
            final boolean encoded) {
        this.name = name;
        this.content = content;
//        this.content = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KCjxwcm9qZWN0IHhtbG5zPSJodHRwOi8vbWF2ZW4uYXBhY2hlLm9yZy9QT00vNC4wLjAiIHhtbG5zOnhzaT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEtaW5zdGFuY2UiIHhzaTpzY2hlbWFMb2NhdGlvbj0iaHR0cDovL21hdmVuLmFwYWNoZS5vcmcvUE9NLzQuMC4wIGh0dHA6Ly9tYXZlbi5hcGFjaGUub3JnL21hdmVuLXY0XzBfMC54c2QiPgogIDxtb2RlbFZlcnNpb24+NC4wLjA8L21vZGVsVmVyc2lvbj4KCiAgPHBhcmVudD4KICAgIDxncm91cElkPm9yZy5zb25hdHlwZS5vc3M8L2dyb3VwSWQ+CiAgICA8YXJ0aWZhY3RJZD5vc3MtcGFyZW50PC9hcnRpZmFjdElkPgogICAgPHZlcnNpb24+NzwvdmVyc2lvbj4KICA8L3BhcmVudD4KCiAgPGdyb3VwSWQ+Y29tLmFjdGlvbmJhcnNoZXJsb2NrPC9ncm91cElkPgogIDxhcnRpZmFjdElkPnBhcmVudDwvYXJ0aWZhY3RJZD4KICA8cGFja2FnaW5nPnBvbTwvcGFja2FnaW5nPgogIDx2ZXJzaW9uPjQuNC4wPC92ZXJzaW9uPgoKICA8bmFtZT5BY3Rpb25CYXJTaGVybG9jayAoUGFyZW50KTwvbmFtZT4K";
        this.encoded = encoded;
        Log.i(TAG,"setSource content:"+this.content);
        loadSource();

        return this;
    }

    private void loadSource() {
        if (name != null && content != null)
            if (markdown){
                view.loadData(content, "text/html", null);
                Log.i(TAG,"loadData content:"+content);
            }
//                view.loadData("LyoKICogQ29weXJpZ2h0IDIwMTIgR2l0SHViIEluYy4KICoKICogTGljZW5z", "text/html", null);
            else{
                Log.i(TAG,"loadUrl URL_PAGE:"+URL_PAGE);
                view.loadUrl(URL_PAGE);
            }
    }

    /**
     * Bind blob content to current {@link WebView}
     *
     * @param name
     * @param blob
     * @return this editor
     */
    public SourceEditor setSource(final String name, final Blob blob) {
        String content = blob.getContent();
        if (content == null)
            content = "";
        Log.i(TAG,"1content:"+content);
        boolean encoded = !TextUtils.isEmpty(content)
                && ENCODING_BASE64.equals(blob.getEncoding());
        return setSource(name, content, encoded);
    }

    /**
     * Toggle line wrap
     *
     * @return this editor
     */
    public SourceEditor toggleWrap() {
        return setWrap(!wrap);
    }

    /**
     * Toggle markdown file rendering
     *
     * @return this editor
     */
    public SourceEditor toggleMarkdown() {
        return setMarkdown(!markdown);
    }
}
