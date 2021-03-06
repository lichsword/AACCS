package com.kakashi.reader.model;

import java.io.Serializable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.kakashi.reader.database.ReaderProvider;

public class Tag2Sub implements Serializable, BaseColumns {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2550587268149771775L;

	public static final String TABLE_NAME = "tag2sub";

    public static final Uri CONTENT_URI
        = Uri.parse(ReaderProvider.TAG2SUB_CONTENT_URI_NAME);

    public static final String _TAG_UID = "tag_uid";
    public static final String _SUB_ID = "sub_id";
    public static final String _SYNC_TIME = "sync_time";

    public static final String SQL_CREATE_TABLE
        = "create table if not exists " + TABLE_NAME + " ("
        + _ID + " integer primary key, "
        + _TAG_UID + " text not null,"
        + _SUB_ID + " integer not null,"
        + _SYNC_TIME + " integer not null default 0"
        + ")";

    public static final String[][] INDEX_COLUMNS = {
        {_TAG_UID, _SUB_ID}
    };

    public static String[] sqlForUpgrade(int oldVersion, int newVersion) {
        return new String[0];
    }
}
