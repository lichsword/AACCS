package org.lichsword.java.tool.design.codestore.android.menu;

/**
 * @author yuewang
 * 
 */
public class OptionsMenuCodestore {
	/**
	 * "menu_"
	 */
	private final String MENU_XML_FILE_NAME_HEAD = "menu_";
	/**
	 * ".xml"
	 */
	private final String MENU_XML_FILE_NAME_END = ".xml";
	/**
	 * 
	 */
	private final String MENU_XML_HEAD_LINE_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	/**
	 * 
	 */
	private final String MENU_XML_HEAD_LINE_2 = "<menu xmlns:android=\"http://schemas.android.com/apk/res/android\">";
	/**
	 * 
	 */
	private final String MENU_XML_ITEM_TAG_BEGIN = "<item";
	/**
	 * 
	 */
	private final String MENU_XML_ITEM_PART_1_ID_BEGIN = " android:id=\"@+id/";
	/**
	 * 
	 */
	private final String MENU_XML_ITEM_PART_1_ID_END = "\"";
	/**
	 * 
	 */
	private final String MENU_XML_ITEM_PART_2_TITLE_BEGIN = " android:title=\"@string/";
	/**
	 * 
	 */
	private final String MENU_XML_ITEM_PART_2_TITLE_END = "\"";
	/**
	 * 
	 */
	private final String MENU_XML_ITEM_PART_3_ICON_BEGIN = " android:icon=\"@drawable/";
	/**
	 * 
	 */
	private final String MENU_XML_ITEM_PART_3_ICON_END = "\"";

	/**
	 * 
	 */
	private final String MENU_XML_ITEM_TAG_TAIL = ">\n</item>";

	private final String MENU_ID_PREFIX = "menu_id_";
	private final String MENU_TITLE_PREFIX = "menu_title_";
	private final String MENU_ICON_PREFIX = "ic_menu_";

	@SuppressWarnings("unused")
	private String getMenuIdPrefix() {
		return MENU_ID_PREFIX;
	}

	@SuppressWarnings("unused")
	private String getMenuTitlePrefix() {
		return MENU_TITLE_PREFIX;
	}

	@SuppressWarnings("unused")
	private String getMenuIconPrefix() {
		return MENU_ICON_PREFIX;
	}

	private String buildMenuXMLHead() {
		String result = null;
		StringBuilder sb = new StringBuilder();

		sb.append(MENU_XML_HEAD_LINE_1);
		sb.append("\n");
		sb.append(MENU_XML_HEAD_LINE_2);

		result = sb.toString();
		System.out.println(result);
		return result;
	}

	private String buildMenuIdAttrStatement(String keyword) {
		String result = null;

		StringBuilder sb = new StringBuilder();
		sb.append(MENU_XML_ITEM_PART_1_ID_BEGIN);
		sb.append(MENU_ID_PREFIX);
		sb.append(keyword);
		sb.append(MENU_XML_ITEM_PART_1_ID_END);

		result = sb.toString();
		System.out.println(result);
		return result;
	}

	private String buildMenuTitleAttrStatement(String keyword) {
		String result = null;

		StringBuilder sb = new StringBuilder();
		sb.append(MENU_XML_ITEM_PART_2_TITLE_BEGIN);
		sb.append(MENU_TITLE_PREFIX);
		sb.append(keyword);
		sb.append(MENU_XML_ITEM_PART_2_TITLE_END);

		result = sb.toString();
		System.out.println(result);
		return result;
	}

	private String buildMenuIconAttrStatement(String keyword) {
		String result = null;

		StringBuilder sb = new StringBuilder();
		sb.append(MENU_XML_ITEM_PART_3_ICON_BEGIN);
		sb.append(MENU_ICON_PREFIX);
		sb.append(keyword);
		sb.append(MENU_XML_ITEM_PART_3_ICON_END);

		result = sb.toString();
		System.out.println(result);
		return result;
	}

	private String buildMenuXMLTail() {
		String result = null;
		StringBuilder sb = new StringBuilder();

		sb.append(MENU_XML_ITEM_TAG_TAIL);

		result = sb.toString();
		System.out.println(result);
		return result;
	}

	public String buildMenuItemAllStatement(String keyword, boolean hasIcon) {
		String result = null;
		StringBuilder sb = new StringBuilder();

		sb.append(buildMenuXMLHead());
		sb.append("\n");
		sb.append(buildMenuIdAttrStatement(keyword));
		sb.append("\n");
		sb.append(buildMenuTitleAttrStatement(keyword));
		sb.append("\n");
		if (hasIcon) {
			sb.append(buildMenuIconAttrStatement(keyword));
			sb.append("\n");
		}// end if
		sb.append(buildMenuXMLTail());

		result = sb.toString();
		System.out.println(result);
		return result;
	}

	/**
	 * {@link #MENU_XML_FILE_NAME_HEAD} + keyword +
	 * {@link #MENU_XML_FILE_NAME_END}
	 * 
	 * @param keyword
	 * @return
	 */
	public String buildMenuXMLFileName(String keyword) {
		String result = null;
		StringBuilder sb = new StringBuilder();

		sb.append(MENU_XML_FILE_NAME_HEAD);
		sb.append(keyword);
		sb.append(MENU_XML_FILE_NAME_END);

		result = sb.toString();
		System.out.println(result);
		return result;
	}
}
