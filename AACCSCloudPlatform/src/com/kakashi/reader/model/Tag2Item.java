package com.kakashi.reader.model;

import java.io.Serializable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.kakashi.reader.database.ReaderProvider;

public class Tag2Item implements Serializable, BaseColumns {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5852494693502780300L;

	public static final String TABLE_NAME = "tag2item";

    public static final Uri CONTENT_URI
        = Uri.parse(ReaderProvider.TAG2ITEM_CONTENT_URI_NAME);

    public static final String _TAG_UID = "tag_uid";
    public static final String _ITEM_ID = "item_id";
    public static final String _ACTION = "action";
    public static final String _SYNC_TIME = "sync_time";

    public static final int ACTION_NONE = 0;
    public static final int ACTION_ADD = 1;
    public static final int ACTION_REMOVE = -1;

    public static final String[] SELECT_ID = {_ID};

    public static final String SQL_CREATE_TABLE
        = "create table if not exists " + TABLE_NAME + " ("
        + _ID + " integer primary key, "
        + _TAG_UID + " text not null,"
        + _ITEM_ID + " integer not null,"
        + _ACTION + " integer not null default 0,"
        + _SYNC_TIME + " integer not null default 0"
        + ")";

    public static final String[][] INDEX_COLUMNS = {
        {_TAG_UID, _ITEM_ID},
        {_TAG_UID, _ITEM_ID, _ACTION},
        {_ITEM_ID, _ACTION},
        {_ACTION},
        {_SYNC_TIME, _ITEM_ID}
    };

    public static String[] sqlForUpgrade(int oldVersion, int newVersion) {
        return new String[0];
    }
}
