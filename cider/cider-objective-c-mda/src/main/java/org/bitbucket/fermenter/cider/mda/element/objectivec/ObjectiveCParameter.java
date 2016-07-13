package org.bitbucket.fermenter.cider.mda.element.objectivec;

import org.apache.commons.lang.StringUtils;
import org.bitbucket.fermenter.mda.PackageManager;
import org.bitbucket.fermenter.mda.metadata.MetadataRepository;
import org.bitbucket.fermenter.mda.metadata.MetadataRepositoryManager;
import org.bitbucket.fermenter.mda.metadata.element.Parameter;

public class ObjectiveCParameter implements Parameter {

	private Parameter parameter;
	private String importName;
	private String objectiveCType;
	private String uncapitalizedObjectiveCType;
	private String basePackage;
	private String signatureName;
	private String signatureSuffix;

	public ObjectiveCParameter(Parameter parameterToDecorate) {
		parameter = parameterToDecorate;
	}

	@Override
	public String getName() {
		return parameter.getName();
	}

	@Override
	public String getDocumentation() {
		return parameter.getDocumentation();
	}

	@Override
	public String getType() {
		MetadataRepository repo = MetadataRepositoryManager.getMetadataRepostory(MetadataRepository.class);
		return ObjectiveCElementUtils.getObjectiveCType(repo.getApplicationName(), parameter.getType());
	}

	public String getTypeReferenceAttribute() {
		return ObjectiveCElementUtils.getObjectiveCTypeReferenceAttribute(getType());
	}

	@Override
	public String getProject() {
		return parameter.getProject();
	}

	/**
	 * @deprecated Use getImport() instead
	 */
	@Deprecated
	public String getObjectiveCImportType() {
		return getImport();
	}

	private String getProjectValue() {
		String project = getProject();
		MetadataRepository repo = MetadataRepositoryManager.getMetadataRepostory(MetadataRepository.class);
		project = (project != null) ? project : repo.getApplicationName();
		return project;
	}

	public String getImport() {
		if (importName == null ) {
			importName = ObjectiveCElementUtils.getObjectiveCImportType(getProjectValue(), getType());
		}

		return importName;
	}

	public String getObjectiveCType() {
		if (objectiveCType == null) {
			objectiveCType = ObjectiveCElementUtils.getObjectiveCType(getProjectValue(), getType());
		}
		return objectiveCType;
	}

	public String getUncapitalizedObjectiveCType() {
		if (uncapitalizedObjectiveCType == null) {
			uncapitalizedObjectiveCType = StringUtils.uncapitalize(getObjectiveCType());
		}
		return uncapitalizedObjectiveCType;
	}

	public boolean isEntity() {
		MetadataRepository repo = MetadataRepositoryManager.getMetadataRepostory(MetadataRepository.class);
		return repo.getEntity(getProjectValue(), getType() ) != null;
	}

	public boolean isEnumeration() {
		MetadataRepository repo = MetadataRepositoryManager.getMetadataRepostory(MetadataRepository.class);
		return repo.getEnumeration(getProjectValue(), getType() ) != null;
	}
    /*
	public Enumeration getEnumeration() {
		Enumeration e = MetadataRepository.getInstance().getEnumeration(getProjectValue(), getType());
		return (e != null) ? new ObjectiveCEnumeration(e) : null;
	}
    */
	public String getUppercaseName() {
		return StringUtils.capitalize( getName() );
	}

	public String getBasePackage() {
		if (basePackage == null) {
			String projectName = getProjectValue();
			basePackage = PackageManager.getBasePackage(projectName);
		}

		return basePackage;
	}

	/**
	 * The parameter name with a comma, if appropriate
	 */
	public String getSignatureName() {
		if (signatureName != null) {
			StringBuffer sb = new StringBuffer(150);
			sb.append(getName()).append(getSignatureSuffix());
			signatureName = sb.toString();
		}

		return signatureName;
	}

	/**
	 * @return Returns the signatureSuffix.
	 */
	public String getSignatureSuffix() {
		return (signatureSuffix != null) ? signatureSuffix : "";
	}

	/**
	 * @param signatureSuffix The signatureSuffix to set.
	 */
	public void setSignatureSuffix(String signatureSuffix) {
		this.signatureSuffix = signatureSuffix;
	}


	@Override
	public boolean isMany() {
		return parameter.isMany();
	}
}