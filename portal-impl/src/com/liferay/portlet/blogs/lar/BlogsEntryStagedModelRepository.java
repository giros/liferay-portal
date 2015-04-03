package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.lar.StagedModelRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;

import java.util.ArrayList;
import java.util.List;

public class BlogsEntryStagedModelRepository
	implements StagedModelRepository<BlogsEntry> {

	public List<? extends StagedModel> fetchChildStagedModels(T stagedModel);

	public <U extends StagedModel> U fetchParentStagedModel(BlogsEntry entry) {
		List<StagedModel> parentStageModels = new ArrayList<>();

		if (entry.getSmallImageFileEntryId() != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				entry.getSmallImageFileEntryId());

			parentStageModels.add(fileEntry);
		}

		return parentStageModels;
	}

	@Override
	public BlogsEntry fetchStagedModel(long entryId) {
		return _blogsEntryLocalService.fetchBlogsEntry(entryId);
	}

	public BlogsEntry fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<BlogsEntry> entries =
			_blogsEntryLocalService.getBlogsEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<BlogsEntry>());

		if (ListUtil.isEmpty(entries)) {
			return null;
		}

		return entries.get(0);
	}

	public BlogsEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(
			uuid, groupId);
	}

	public List<BlogsEntry> fetchStagedModels(long groupId) {
		return _blogsEntryLocalService.getGroupEntries(
			groupId, new QueryDefinition<BlogsEntry>());
	}

	public List<T> fetchStagedModels(PortletDataContext portletDataContext);

	private BlogsEntryLocalService _blogsEntryLocalService;
}
