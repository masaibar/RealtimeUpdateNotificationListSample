package com.masaibar.realtimeupdatenotificationlistsample;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;


public class NotificationGetterService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        NotificationData.insert(this, NotificationData.create(sbn));
    }
}
