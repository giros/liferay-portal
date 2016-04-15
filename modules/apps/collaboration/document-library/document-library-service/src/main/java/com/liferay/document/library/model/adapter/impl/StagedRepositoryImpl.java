package com.liferay.document.library.model.adapter.impl;

import com.liferay.document.library.model.adapter.StagedRepository;
import com.liferay.portal.kernel.model.Repository;

public class StagedRepositoryImpl implements StagedRepository {

	public StagedRepositoryImpl() {
	}

	public StagedRepositoryImpl(Repository repository) {
		_repository = repository;
	}

	public boolean getHidden() {
		return _hidden;
	}

	public void setHidden(boolean hidden) {
		_hidden = hidden;
	}

	private boolean _hidden;
	private Repository _repository;
}
