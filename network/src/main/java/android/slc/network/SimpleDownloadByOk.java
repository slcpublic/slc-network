package android.slc.network;

import android.net.Uri;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.liulishuo.okdownload.DownloadTask;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author slc
 * @date 2019/11/21 11:17
 */
public class SimpleDownloadByOk implements LifecycleObserver {
    protected DownloadTask downloadTask;

    public static DownloadBuilder newBuilder(@NonNull String url, @NonNull String parentPath) {
        return new DownloadBuilder(url, new File(parentPath));
    }

    public static DownloadBuilder newBuilder(@NonNull String url, @NonNull String parentPath, @Nullable String filename) {
        return new DownloadBuilder(url, parentPath, filename);
    }

    public static DownloadBuilder newBuilder(@NonNull String url, @NonNull File file) {
        return new DownloadBuilder(url, file);
    }

    public static DownloadBuilder newBuilder(@NonNull String url, @NonNull Uri uri) {
        return new DownloadBuilder(url, uri);
    }

    public static HeadBuilder createHeadBuilder() {
        return new HeadBuilder();
    }

    public SimpleDownloadByOk(DownloadTask downloadTask) {
        this.downloadTask = downloadTask;
    }

    public DownloadTask getDownloadTask() {
        return downloadTask;
    }

    public static class DownloadBuilder {
        private final DownloadTask.Builder builder;
        private Lifecycle lifecycle;

        public DownloadBuilder(@NonNull String url, @NonNull String parentPath) {
            this.builder = new DownloadTask.Builder(url, new File(parentPath));
        }

        public DownloadBuilder(@NonNull String url, @NonNull String parentPath, @Nullable String filename) {
            this.builder = new DownloadTask.Builder(url, parentPath, filename);
        }

        public DownloadBuilder(@NonNull String url, @NonNull File file) {
            this.builder = new DownloadTask.Builder(url, file);
        }

        public DownloadBuilder(@NonNull String url, @NonNull Uri uri) {
            this.builder = new DownloadTask.Builder(url, uri);
        }

        public DownloadBuilder bindLifecycle(Lifecycle lifecycle) {
            this.lifecycle = lifecycle;
            return this;
        }

        public DownloadBuilder toCommonlyConfig() {
            this.builder.setConnectionCount(1)
                    .setMinIntervalMillisCallbackProcess(500)
                    .setAutoCallbackToUIThread(true);
            return this;
        }

        public DownloadBuilder setPreAllocateLength(boolean preAllocateLength) {
            this.builder.setPreAllocateLength(preAllocateLength);
            return this;
        }

        public DownloadBuilder setConnectionCount(@IntRange(from = 1) int connectionCount) {
            this.builder.setConnectionCount(connectionCount);
            return this;
        }

        public DownloadBuilder setFilenameFromResponse(@Nullable Boolean filenameFromResponse) {
            this.builder.setFilenameFromResponse(filenameFromResponse);
            return this;
        }

        public DownloadBuilder setAutoCallbackToUIThread(boolean autoCallbackToUIThread) {
            this.builder.setAutoCallbackToUIThread(autoCallbackToUIThread);
            return this;
        }

        public DownloadBuilder setMinIntervalMillisCallbackProcess(int minIntervalMillisCallbackProcess) {
            this.builder.setMinIntervalMillisCallbackProcess(minIntervalMillisCallbackProcess);
            return this;
        }

        public DownloadBuilder setHeaderMapFields(Map<String, List<String>> headerMapFields) {
            this.builder.setHeaderMapFields(headerMapFields);
            return this;
        }

        public DownloadBuilder setPriority(int priority) {
            this.builder.setPriority(priority);
            return this;
        }

        public DownloadBuilder setReadBufferSize(int readBufferSize) {
            this.builder.setReadBufferSize(readBufferSize);
            return this;
        }

        public DownloadBuilder setFlushBufferSize(int flushBufferSize) {
            this.builder.setFlushBufferSize(flushBufferSize);
            return this;
        }

        public DownloadBuilder setSyncBufferSize(int syncBufferSize) {
            this.builder.setSyncBufferSize(syncBufferSize);
            return this;
        }

        public DownloadBuilder setSyncBufferIntervalMillis(int syncBufferIntervalMillis) {
            this.builder.setSyncBufferIntervalMillis(syncBufferIntervalMillis);
            return this;
        }

        public DownloadBuilder setFilename(String filename) {
            this.builder.setFilename(filename);
            return this;
        }

        public DownloadBuilder setPassIfAlreadyCompleted(boolean passIfAlreadyCompleted) {
            this.builder.setPassIfAlreadyCompleted(passIfAlreadyCompleted);
            return this;
        }

        public DownloadBuilder setWifiRequired(boolean wifiRequired) {
            this.builder.setWifiRequired(wifiRequired);
            return this;
        }

        public SimpleDownloadByOk build() {
            SimpleDownloadByOk simpleDownloadByOk = new SimpleDownloadByOk(builder.build());
            if (lifecycle != null) {
                lifecycle.addObserver(simpleDownloadByOk);
                lifecycle = null;
            }
            return simpleDownloadByOk;
        }
    }

    public static class HeadBuilder {
        private final Map<String, List<String>> headMap = new HashMap<>();

        public HeadBuilder add(@NonNull String key, @NonNull String value) {
            headMap.put(key, Collections.singletonList(value));
            return this;
        }

        public HeadBuilder add(@NonNull String key, @NonNull String... value) {
            if (value != null && value.length > 0) {
                headMap.put(key, Arrays.asList(value));
            }
            return this;
        }

        public Map<String, List<String>> build() {
            return headMap;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unRegister() {
        if (downloadTask != null) {
            downloadTask.cancel();
        }
    }
}
