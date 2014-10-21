package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.asset.model.AssetViewStats;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

public class AssetViewStatsFinderImpl
	extends BasePersistenceImpl<AssetViewStats> 
		implements AssetViewStatsFinder {
	
	public static final String COUNT_BY_C_C_SD =
		AssetViewStatsFinder.class.getName() + ".countByC_C_SD";

	public int countByC_C_SD(
			long classNameId, long classPK, Date viewDateGT, Date viewDateLT) 
		throws SystemException {

		Timestamp viewDateGT_TS = CalendarUtil.getTimestamp(viewDateGT);
		Timestamp viewDateLT_TS = CalendarUtil.getTimestamp(viewDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_C_SD);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetViewStats", AssetViewStats.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(classPK);
			qPos.add(viewDateGT_TS);
			qPos.add(viewDateLT_TS);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}
}
