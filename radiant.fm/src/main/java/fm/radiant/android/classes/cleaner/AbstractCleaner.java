package fm.radiant.android.classes.cleaner;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

import java.io.File;
import java.util.Collection;
import java.util.List;

import fm.radiant.android.lib.AudioModel;

public abstract class AbstractCleaner {
    private List<? extends AudioModel> queue;
    private File directory;

    public AbstractCleaner(List<? extends AudioModel> queue, File directory) {
        this.queue     = queue;
        this.directory = directory;
    }

    public void clean() {
        if (!directory.isDirectory()) return;

        for (File expiredFile : getExpiredFiles()) { FileUtils.deleteQuietly(expiredFile); }
    }

    protected Collection<File> getExpiredFiles() {
        IOFileFilter filter = new NotFileFilter(new NameFileFilter(queueToFilenames()));

        return FileUtils.listFiles(directory, filter, null);
    }

    protected List<String> queueToFilenames() {
        Iterable<String> filenames = Iterables.transform(queue, new Function<AudioModel, String>() {
            @Override
            public String apply(AudioModel model) {
                return model.getFilename();
            }
        });

        return Lists.newArrayList(filenames);
    }
}