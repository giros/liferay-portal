package com.liferay.document.library.model.adapter;

import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.StagedModel;

public interface StagedRepository extends Repository, StagedModel {

	public boolean getHidden();

	public void setHidden(boolean hidden);

}
