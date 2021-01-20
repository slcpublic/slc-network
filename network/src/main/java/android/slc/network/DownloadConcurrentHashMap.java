package android.slc.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liulishuo.okdownload.DownloadTask;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author slc
 * @date 2021/1/20 11:48
 */
public class DownloadConcurrentHashMap extends ConcurrentHashMap<DownloadTask, DownloadState> {
    protected final Set<OnMapChangedCallback> onMapChangedCallbackList = new HashSet<>();

    @Nullable
    @Override
    public DownloadState put(@NonNull DownloadTask key, @NonNull DownloadState value) {
        DownloadState result = super.put(key, value);
        Iterator<OnMapChangedCallback> callbackIterator = onMapChangedCallbackList.iterator();
        while (callbackIterator.hasNext()) {
            OnMapChangedCallback callback = callbackIterator.next();
            if (callback != null) {
                callback.onDownloadChanged(key, value);
            }
        }
        return result;
    }

    public void addOnMapChangedCallback(OnMapChangedCallback onMapChangedCallback) {
        onMapChangedCallbackList.add(onMapChangedCallback);
    }

    public void removeOnMapChangedCallback(OnMapChangedCallback onMapChangedCallback) {
        onMapChangedCallbackList.remove(onMapChangedCallback);
    }

    public void removeAllCallback() {
        onMapChangedCallbackList.clear();
    }

    public interface OnMapChangedCallback {
        void onDownloadChanged(DownloadTask downloadTask, DownloadState downloadState);
    }

}
