package fm.radiant.android.models;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.radiant.android.lib.Model;

public class Campaign extends Model {
    private int periodicity;
    private int selectivity;

    private boolean ducked;

    private List<Ad> ads = new ArrayList<Ad>();

    public static Campaign sample(List<Campaign> campaigns) {
        List<Campaign> cloned = new ArrayList<Campaign>(campaigns);
        Collections.shuffle(cloned);

        final DateTime now = new DateTime();

        return Iterables.find(cloned, new Predicate<Campaign>() {
            @Override
            public boolean apply(Campaign campaign) {
                return (campaign.getPeriodicity() % (now.getMinuteOfHour() - (now.getMinuteOfHour() % 5))) == 0;
            }
        }, null);
    }

    public List<Ad> getAds() {
        return ads;
    }

    public List<Ad> randomAds() {
        List<Ad> cloned = new ArrayList<Ad>(ads);
        Collections.shuffle(cloned);

        return cloned.subList(0, selectivity);
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public int getAdsCountPerBlock() {
        return selectivity;
    }

    public boolean isDucked() {
        return ducked;
    }
}