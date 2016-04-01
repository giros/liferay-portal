package com.liferay.journal.model.adapter.impl;

import java.io.File;
import java.util.Map;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.portal.kernel.util.Validator;

public class StagedJournalArticleImpl implements StagedJournalArticle {
	
	public StagedJournalArticleImpl() {
	}

	public StagedJournalArticleImpl(JournalArticle journalArticle) {
		_journalArticle = journalArticle;
	}

	public String getArticleResourceUuid() {
		return _articleResourceUuid;
	}

	public boolean getAutoArticleId() {
		return _autoArticleId;
	}

	public Map<String, byte[]> getImages() {
		return _images;
	}

	public boolean getPreloaded() {
		return _preloaded;
	}

	public File getSmallFile() {
		return _smallFile;
	}

	public void setArticleResourceUuid(String articleResourceUuid) {
		_articleResourceUuid = articleResourceUuid;
	}

	public void setAutoArticleId(boolean autoArticleId) {
		_autoArticleId = autoArticleId;
	}

	public void setImage(Map<String, byte[]> images) {
		_images = images;
	}

	public void setPreloaded(boolean preloaded) {
		_preloaded = preloaded;
	}

	public void setSmallFile(File smallFile) {
		_smallFile = smallFile;
	}

	private JournalArticle _journalArticle;
	private String _articleResourceUuid;
	private boolean _autoArticleId;
	private Map<String, byte[]> _images;
	private boolean _preloaded;
	private File _smallFile;
}
