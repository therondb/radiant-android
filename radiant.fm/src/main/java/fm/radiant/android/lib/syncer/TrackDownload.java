package fm.radiant.android.lib.syncer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import fm.radiant.android.Config;
import fm.radiant.android.models.Track;
import fm.radiant.android.utils.AccountUtils;

public class TrackDownload extends AbstractDownload {
    private final Context mContext;
    private final Track   mTrack;

    public TrackDownload(Context context, Track track, OnProgressListener event) {
        super(context, track, event);

        mContext = context;
        mTrack   = track;
    }

    @Override
    protected void store(File tempFile) throws IOException {
        try {
            HttpRequest request = HttpRequest.put(Config.API_ENDPOINT + "/downloads/" + mTrack.getDownloadId() + "/sign", true).basic(AccountUtils.getUUID(), AccountUtils.getPassword());

            if (request.ok()) {
                super.store(tempFile);
            } else {
                throw new IOException("Could not sign download (code=" + request.code() + ", message=" + request.message() + ")");
            }
        } catch (HttpRequest.HttpRequestException exception) {
            throw new IOException(exception);
        }
    }
}