package android.slc.network;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadReceiver;
import com.arialyy.aria.core.download.target.HttpBuilderTarget;
import com.arialyy.aria.core.task.DownloadTask;

/**
 * @author slc
 * @date 2019/11/21 11:17
 */
public class SimpleAriaDownload implements IAriaDownload {
    protected String TAG = "IAriaDownload";
    protected long taskId;
    private boolean isRegister;
    private AriaDownloadListener ariaDownloadListener;

    public SimpleAriaDownload() {
    }

    @Override
    public DownloadReceiver getDownloadReceiver() {
        return Aria.download(this);
    }

    @Override
    public void setDownloadListener(AriaDownloadListener downloadListener) {
        this.ariaDownloadListener = downloadListener;
    }

    @Override
    public long create(HttpBuilderTarget httpBuilderTarget) {
        if (!isRegister) {
            isRegister = true;
            Aria.download(this).register();
        }
        return taskId = httpBuilderTarget.create();
    }

    @Override
    public long add(HttpBuilderTarget httpBuilderTarget) {
        if (!isRegister) {
            isRegister = true;
            Aria.download(this).register();
        }
        return taskId = httpBuilderTarget.add();
    }

    @Download.onWait
    @Override
    public void onWait(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.onWait(task);
        }
    }

    @Download.onPre
    @Override
    public void onPre(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.onPre(task);
        }
    }

    @Download.onTaskStart
    @Override
    public void taskStart(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskStart(task);
        }
    }

    @Download.onTaskRunning
    @Override
    public void running(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.running(task);
        }
    }

    @Download.onTaskResume
    @Override
    public void taskResume(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskResume(task);
        }
    }

    @Download.onTaskStop
    @Override
    public void taskStop(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskStop(task);
        }
    }

    @Download.onTaskCancel
    @Override
    public void taskCancel(DownloadTask task) {
        if (isRegister) {
            isRegister = false;
            unRegister();
        }
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskCancel(task);
        }
    }

    @Download.onTaskFail
    @Override
    public void taskFail(DownloadTask task) {
        if (isRegister) {
            isRegister = false;
            unRegister();
        }
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskFail(task);
        }
    }

    @Download.onTaskComplete
    @Override
    public void taskComplete(DownloadTask task) {
        if (isRegister) {
            isRegister = false;
            unRegister();
        }
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskComplete(task);
        }
    }

    @Override
    public void unRegister() {
        if (taskId != -1) {
            Aria.download(this).load(taskId).removeRecord();
            Aria.download(this).load(taskId).cancel();
        }
        Aria.download(this).unRegister();
        isRegister = false;
    }

}
