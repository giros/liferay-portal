create table AssetEntryViewStats (
	statsId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	assetEntryId LONG
);