package com.masaibar.realtimeupdatenotificationlistsample;

import android.content.Context;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class NotificationData {

    private static final String KEY_TITLE = "android.title";
    private static final String KEY_BODY = "android.text";

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public long time;

    @Column
    public String packageName;

    @Column
    @Nullable
    public String title;

    @Column
    @Nullable
    public String body;

    public static void insert(final Context context, final NotificationData data) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma(context).insertIntoNotificationData(data);
            }
        });
    }

    public static NotificationData create(StatusBarNotification sbn) {
        NotificationData data = new NotificationData();
        Bundle bundle = sbn.getNotification().extras;
        data.time = sbn.getPostTime();
        data.packageName = sbn.getPackageName();
        data.title = getValue(bundle, KEY_TITLE);
        data.body = getValue(bundle, KEY_BODY);

        return data;
    }

    @Nullable
    private static String getValue(Bundle bundle, String key) {
        if (bundle == null) {
            return null;
        }

        if (!bundle.containsKey(key)) {
            return null;
        }

        CharSequence c = bundle.getCharSequence(key);
        return TextUtils.isEmpty(c) ? null : c.toString();
    }

    private static OrmaDatabase getOrma(Context context) {
        return OrmaUtil.getInstance(context);
    }
}
