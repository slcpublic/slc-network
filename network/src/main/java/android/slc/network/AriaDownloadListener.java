package android.slc.network;

import com.arialyy.aria.core.task.DownloadTask;

/**
 * @author slc
 * @date 2019/11/21 14:51
 */
public interface AriaDownloadListener {
    void onWait(DownloadTask task);

    void onPre(DownloadTask task);

    void taskStart(DownloadTask task);

    void running(DownloadTask task);

    void taskResume(DownloadTask task);

    void taskStop(DownloadTask task);

    void taskCancel(DownloadTask task);

    void taskFail(DownloadTask task);

    void taskComplete(DownloadTask task);
}
