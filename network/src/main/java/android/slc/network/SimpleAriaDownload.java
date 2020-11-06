package android.slc.network;

import android.util.Log;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadReceiver;
import com.arialyy.aria.core.download.DownloadTaskListener;
import com.arialyy.aria.core.download.target.HttpBuilderTarget;
import com.arialyy.aria.core.scheduler.TaskSchedulers;
import com.arialyy.aria.core.task.DownloadTask;

/**
 * @author slc
 * @date 2019/11/21 11:17
 */
public class SimpleAriaDownload implements DownloadTaskListener {
    protected long taskId;
    private boolean isRegister;
    private AriaDownloadListener ariaDownloadListener;
    private DownloadReceiver downloadReceiver;

    public SimpleAriaDownload() {
    }

    public synchronized DownloadReceiver getDownloadReceiver() {
        if (downloadReceiver == null) {
            downloadReceiver = Aria.download(this);
            //ProxyHelper.getInstance().checkProxyType(this.getClass());
        }
        return downloadReceiver;
    }

    public void setDownloadListener(AriaDownloadListener downloadListener) {
        this.ariaDownloadListener = downloadListener;
    }

    public long create(HttpBuilderTarget httpBuilderTarget) {
        if (!isRegister) {
            isRegister = true;
            Aria.download(this).register();
        }
        return taskId = httpBuilderTarget.create();
    }

    public long add(HttpBuilderTarget httpBuilderTarget) {
        if (!isRegister) {
            isRegister = true;
            Aria.download(this).register();
        }
        return taskId = httpBuilderTarget.add();
    }

    @Override
    public void onWait(DownloadTask task) {
    }

    @Override
    public void onPre(DownloadTask task) {
    }

    @Override
    public void onTaskPre(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.onWait(task);
        }
    }

    @Override
    public void onTaskResume(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.onPre(task);
        }
    }

    @Override
    public void onTaskStart(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskStart(task);
        }
    }

    @Override
    public void onTaskStop(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskStop(task);
        }
    }

    @Override
    public void onTaskCancel(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskCancel(task);
        }
        if (isRegister) {
            isRegister = false;
            unRegister();
        }
    }

    @Override
    public void onTaskFail(DownloadTask task, Exception e) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskFail(task);
        }
        if (isRegister) {
            isRegister = false;
            unRegister();
        }
    }

    @Override
    public void onTaskComplete(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.taskComplete(task);
        }
        if (isRegister) {
            isRegister = false;
            unRegister(true);
        }
    }

    @Override
    public void onTaskRunning(DownloadTask task) {
        if (ariaDownloadListener != null) {
            ariaDownloadListener.running(task);
        }
    }

    @Override
    public void onNoSupportBreakPoint(DownloadTask task) {
        Log.i("simpleAriaDownload2", "onNoSupportBreakPoint");
    }

    public void unRegister() {
        unRegister(false);
    }

    public void unRegister(boolean isComplete) {
        if (downloadReceiver != null) {
            if (taskId != -1) {
                if (!isComplete) {
                    downloadReceiver.load(taskId).cancel();
                }
                downloadReceiver.load(taskId).removeRecord();
            }
            downloadReceiver.unRegister();
            TaskSchedulers.getInstance().unRegister(this);
        }
        isRegister = false;
    }

}
