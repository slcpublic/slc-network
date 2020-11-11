package android.slc.network;

import androidx.annotation.NonNull;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener3;

/**
 * @author slc
 * @date 2020/11/11 13:38
 */
public abstract class SimpleDownloadByOkListener extends DownloadListener3 {

    @Override
    protected void canceled(@NonNull DownloadTask task) {

    }

    @Override
    protected void warn(@NonNull DownloadTask task) {

    }

    @Override
    public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

    }

    @Override
    public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
        double percentage = totalLength == 0 ? 0 : (float) currentOffset / totalLength * 100;
        progress(task, (int) percentage, currentOffset, totalLength);
    }

    protected abstract void progress(@NonNull DownloadTask task, int percentage, long currentOffset, long totalLength);
}
