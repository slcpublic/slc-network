package android.slc.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liulishuo.okdownload.DownloadSerialQueue;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;

/**
 * 简单的全局下载服务
 */
public class SimpleDownloadService extends Service implements SimpleDownloadManager.OnNotifyDownloadTaskListener {
    protected final SimpleDownloadByOkListener queueDownloadByOkListener = new SimpleDownloadByOkListener() {

        @Override
        protected void started(@NonNull DownloadTask task) {
            SimpleDownloadService.this.started(task);
        }

        @Override
        protected void progress(@NonNull DownloadTask task, int percentage, long currentOffset, long totalLength) {
            SimpleDownloadService.this.progress(task, percentage, currentOffset, totalLength);
        }

        @Override
        protected void completed(@NonNull DownloadTask task) {
            SimpleDownloadService.this.completed(task);
        }

        @Override
        protected void error(@NonNull DownloadTask task, @NonNull Exception e) {
            SimpleDownloadService.this.error(task, e);
        }

        @Override
        protected void canceled(@NonNull DownloadTask task) {
            super.canceled(task);
            SimpleDownloadService.this.canceled(task);
        }

    };
    /**
     * 队列下载器
     */
    protected final DownloadSerialQueue serialQueue = new DownloadSerialQueue(queueDownloadByOkListener);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SimpleDownloadManager.getInstance().setOnNotifyDownloadTaskListener(this);
    }

    protected void started(@NonNull DownloadTask task) {
        DownloadState downloadState = getDownloadState(task);
        downloadState.setState(DownloadState.P_WAITING);
        notifyDownloadUpdate(task, downloadState);
    }

    protected void progress(@NonNull DownloadTask task, int percentage, long currentOffset, long totalLength) {
        DownloadState downloadState = getDownloadState(task);
        downloadState.setState(DownloadState.P_LOADING);
        downloadState.setProgress(percentage);
        downloadState.setCurrentOffset(currentOffset);
        downloadState.setTotalLength(totalLength);
        notifyDownloadUpdate(task, downloadState);
    }

    protected void completed(@NonNull DownloadTask task) {
        DownloadState downloadState = getDownloadState(task);
        downloadState.setState(DownloadState.P_FINISH);
        downloadState.setProgress(100);
        notifyDownloadUpdate(task, downloadState);
        SimpleDownloadManager.getInstance().downloadStateArrayMapOf.remove(task);

    }

    protected void error(@NonNull DownloadTask task, @NonNull Exception e) {
        DownloadState downloadState = getDownloadState(task);
        downloadState.setState(DownloadState.P_ERROR);
        downloadState.setProgress(-1);
        downloadState.setException(e);
        notifyDownloadUpdate(task, downloadState);
    }

    protected void canceled(@NonNull DownloadTask task) {
        DownloadState downloadState = getDownloadState(task);
        downloadState.setState(DownloadState.P_CANCEL);
        notifyDownloadUpdate(task, downloadState);
        SimpleDownloadManager.getInstance().downloadStateArrayMapOf.remove(task);
    }

    @Override
    public void onNotifyDownload(DownloadTask downloadTask) {
        DownloadState downloadState = getDownloadState(downloadTask);
        downloadState.setState(DownloadState.P_WAITING);
        notifyDownloadUpdate(downloadTask, downloadState);
        serialQueue.enqueue(downloadTask);
    }

    @Override
    public void onNotifyCancelCurrentTask() {
        OkDownload.with().downloadDispatcher().cancel(serialQueue.getWorkingTaskId());
    }

    private DownloadState getDownloadState(@NonNull DownloadTask task) {
        DownloadState downloadState = SimpleDownloadManager.getInstance().downloadStateArrayMapOf.get(task);
        if (downloadState == null) {
            downloadState = new DownloadStateImp();
        }
        return downloadState;
    }

    private void notifyDownloadUpdate(@NonNull DownloadTask task, DownloadState downloadState) {
        SimpleDownloadManager.getInstance().downloadStateArrayMapOf.put(task, downloadState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serialQueue.shutdown();
    }
}
