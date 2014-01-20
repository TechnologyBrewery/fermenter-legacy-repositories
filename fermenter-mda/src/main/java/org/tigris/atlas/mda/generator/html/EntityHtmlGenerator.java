package org.tigris.atlas.mda.generator.html;

import java.util.Iterator;

import org.apache.velocity.VelocityContext;
import org.tigris.atlas.mda.generator.AbstractResourcesGenerator;
import org.tigris.atlas.mda.generator.GenerationContext;
import org.tigris.atlas.mda.generator.GenerationException;
import org.tigris.atlas.mda.metadata.MetadataRepository;
import org.tigris.atlas.mda.metadata.element.Entity;

public class EntityHtmlGenerator extends AbstractResourcesGenerator {

	protected String getOutputSubFolder() {
		return OUTPUT_SUB_FOLDER_RESOURCES;
	}

	public void generate(GenerationContext context) throws GenerationException {
		String applicationName = context.getArtifactId();
		Iterator entities = MetadataRepository.getInstance().getAllEntities(applicationName).values().iterator();
		
		String fileName;
		String basefileName = context.getOutputFile();	
		basefileName = replaceBasePackage(basefileName, context.getBasePackageAsPath());	
		while (entities.hasNext()) {
			Entity entity = (Entity) entities.next();
			
			VelocityContext vc = new VelocityContext();
			vc.put("entity", entity);

			fileName = replaceEntityName(basefileName, entity.getName());
			context.setOutputFile(fileName);
			
			generateFile(context, vc);
		}

	}

}
