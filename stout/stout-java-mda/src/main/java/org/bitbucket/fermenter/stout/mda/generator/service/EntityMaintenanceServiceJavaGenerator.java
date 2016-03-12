package org.bitbucket.fermenter.stout.mda.generator.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.velocity.VelocityContext;
import org.bitbucket.fermenter.mda.generator.GenerationContext;
import org.bitbucket.fermenter.mda.generator.GenerationException;
import org.bitbucket.fermenter.mda.metadata.MetadataRepository;
import org.bitbucket.fermenter.mda.metadata.MetadataRepositoryManager;
import org.bitbucket.fermenter.mda.metadata.element.Entity;
import org.bitbucket.fermenter.stout.mda.JavaElementUtils;
import org.bitbucket.fermenter.stout.mda.JavaEntity;
import org.bitbucket.fermenter.stout.mda.generator.AbstractJavaGenerator;

public class EntityMaintenanceServiceJavaGenerator extends AbstractJavaGenerator {

	private static final String ENTITY_MAINTENANCE = "EntityMaintenance";

	public void generate(GenerationContext context) throws GenerationException {
		String applicationName = context.getArtifactId();
		MetadataRepository metadataRepository = 
                MetadataRepositoryManager.getMetadataRepostory(MetadataRepository.class);
		Iterator entityIterator = metadataRepository.getAllEntities(applicationName).values().iterator();
		
		JavaEntity javaEntity;
		Collection javaEntities = new ArrayList();
		while (entityIterator.hasNext()) {
			javaEntity = new JavaEntity((Entity)entityIterator.next());
			javaEntities.add(javaEntity);
		}
		
		VelocityContext vc = new VelocityContext();
		vc.put("serviceName", ENTITY_MAINTENANCE);
		vc.put("service.name", ENTITY_MAINTENANCE);
		vc.put("basePackage", context.getBasePackage());
		vc.put("entities", javaEntities);
		vc.put("baseJndiName", JavaElementUtils.getBaseJndiName(context.getBasePackage()));
		vc.put("artifactId", context.getArtifactId());
		vc.put("version", context.getVersion());
					
		String basefileName = context.getOutputFile();
		basefileName = replaceBasePackage(basefileName, context.getBasePackageAsPath());
		String fileName = replaceServiceName(basefileName, ENTITY_MAINTENANCE);
		context.setOutputFile(fileName);
		
		generateFile(context, vc);
	}

}
