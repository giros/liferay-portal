package com.liferay.portlet.messageboards.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.messageboards.model.MBThread;

public class MessageCountComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC =
			"MBThread.messageCount ASC";

	public static final String ORDER_BY_DESC =
		"MBThread.messageCount DESC";

	public static final String[] ORDER_BY_FIELDS = {"messageCount"};

	public MessageCountComparator() {
		this(false);
	}

	public MessageCountComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		MBThread thread1 = (MBThread)obj1;
		MBThread thread2 = (MBThread)obj2;

		int value = 0;

		if (thread1.getMessageCount() > thread2.getMessageCount()) {
			value = 1;
		}
		else if (thread1.getMessageCount() < thread2.getMessageCount()) {
			value = -1;
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}
