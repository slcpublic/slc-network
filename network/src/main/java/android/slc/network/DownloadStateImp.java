package android.slc.network;

public class DownloadStateImp implements DownloadState {
    private int progress;
    private int state;
    private int percentage;
    private long currentOffset;
    private long totalLength;
    private Exception exception;

    public DownloadStateImp() {

    }

    public DownloadStateImp(int state) {
        this.state = state;
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public long getCurrentOffset() {
        return currentOffset;
    }

    @Override
    public void setCurrentOffset(long currentOffset) {
        this.currentOffset = currentOffset;
    }

    @Override
    public long getTotalLength() {
        return totalLength;
    }

    @Override
    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }
}
