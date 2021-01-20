package android.slc.network;

public interface DownloadState {
    int P_NONE = 0;         //无状态
    int P_WAITING = 1;      //等待
    int P_LOADING = 2;      //下载中
    int P_PAUSE = 3;        //暂停
    int P_ERROR = 4;        //错误
    int P_FINISH = 5;       //完成

    int getProgress();

    void setProgress(int progress);

    int getState();

    void setState(int state);

    int getPercentage();

    void setPercentage(int percentage);

    long getCurrentOffset();

    void setCurrentOffset(long currentOffset);

    long getTotalLength();

    void setTotalLength(long totalLength);

    void setException(Exception exception);

    Exception getException();
}
