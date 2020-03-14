package android.slc.network;

import com.arialyy.aria.core.download.DownloadReceiver;
import com.arialyy.aria.core.download.target.HttpBuilderTarget;

/**
 * @author slc
 * @date 2019/11/21 11:17
 */
public interface IAriaDownload extends AriaDownloadListener {
    DownloadReceiver getDownloadReceiver();

    void setDownloadListener(AriaDownloadListener downloadListener);

    long create(HttpBuilderTarget httpBuilderTarget);

    long add(HttpBuilderTarget httpBuilderTarget);

    void unRegister();

}
