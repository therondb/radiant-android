package fm.radiant.android.classes.indexer;

import android.content.Context;
import android.util.SparseIntArray;

import java.util.List;

import fm.radiant.android.lib.AudioModel;
import fm.radiant.android.models.Track;

public class TracksIndexer extends AbstractIndexer {
    SparseIntArray persistedMinutes = new SparseIntArray();

    public TracksIndexer(Context context, List<Track> queue) {
        super(context, queue);
    }

    @Override
    public Class getModelClass() {
        return Track.class;
    }

    @Override
    public boolean isFrontQueue() {
        return false;
    }

    @Override
    public List<Track> getPersistedQueue() {
        return (List<Track>) super.getPersistedQueue();
    }

    @Override
    public List<Track> getRemotedQueue() {
        return (List<Track>) super.getRemotedQueue();
    }

    @Override
    public List<Track> getQueue() {
        return (List<Track>) super.getQueue();
    }

    @Override
    protected void onPersistentModel(AudioModel model) {
        Track track = (Track) model;

        persistedMinutes.put(track.getStyleId(), persistedMinutes.get(track.getStyleId()) + track.getAudio().getTime() / 60000);
    }

    public Integer getPersistedMinutes(Integer styleId) {
        return persistedMinutes.get(styleId);
    }
}