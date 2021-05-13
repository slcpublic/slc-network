package android.slc.network;

import com.liulishuo.okdownload.DownloadTask;

/**
 * @author slc
 * @date 2021/1/20 11:07
 */
public class SimpleDownloadManager {
    private final static SimpleDownloadManager simpleDownloadManager = new SimpleDownloadManager();
    public final DownloadConcurrentHashMap downloadStateArrayMapOf = new DownloadConcurrentHashMap();

    private OnNotifyDownloadTaskListener onNotifyDownloadTaskListener;

    private SimpleDownloadManager() {
    }

    public static SimpleDownloadManager getInstance() {
        return simpleDownloadManager;
    }

    public void setOnNotifyDownloadTaskListener(OnNotifyDownloadTaskListener onNotifyDownloadTaskListener) {
        this.onNotifyDownloadTaskListener = onNotifyDownloadTaskListener;
    }

    public void addDownloadTask(DownloadTask downloadTask) {
        if (onNotifyDownloadTaskListener != null) {
            onNotifyDownloadTaskListener.onNotifyDownload(downloadTask);
        }
    }

    public void cancelCurrentTask() {
        if (onNotifyDownloadTaskListener != null) {
            onNotifyDownloadTaskListener.onNotifyCancelCurrentTask();
        }
    }

    public interface OnNotifyDownloadTaskListener {
        void onNotifyDownload(DownloadTask downloadTask);

        void onNotifyCancelCurrentTask();
    }
}
