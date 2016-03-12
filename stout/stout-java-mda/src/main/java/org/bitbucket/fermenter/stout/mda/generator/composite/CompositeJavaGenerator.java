package org.bitbucket.fermenter.stout.mda.generator.composite;

import java.util.Iterator;

import org.apache.velocity.VelocityContext;
import org.bitbucket.fermenter.mda.generator.GenerationContext;
import org.bitbucket.fermenter.mda.generator.GenerationException;
import org.bitbucket.fermenter.mda.metadata.MetadataRepository;
import org.bitbucket.fermenter.mda.metadata.MetadataRepositoryManager;
import org.bitbucket.fermenter.mda.metadata.element.Composite;
import org.bitbucket.fermenter.stout.mda.JavaComposite;
import org.bitbucket.fermenter.stout.mda.generator.AbstractJavaGenerator;

public class CompositeJavaGenerator extends AbstractJavaGenerator {

	public void generate(GenerationContext context) throws GenerationException {
		String currentApplication = context.getArtifactId();
		MetadataRepository metadataRepository = 
                MetadataRepositoryManager.getMetadataRepostory(MetadataRepository.class);
		Iterator i = metadataRepository.getAllComposites(currentApplication).values().iterator();
		
		Composite metadata = null;
		JavaComposite composite = null;
		
		String basefileName = context.getOutputFile();		
		basefileName = replaceBasePackage(basefileName, context.getBasePackageAsPath());
		while (i.hasNext()) {
			metadata = (Composite) i.next();
			composite = new JavaComposite(metadata);
			
			VelocityContext vc = new VelocityContext();
			vc.put("composite", composite);
			vc.put("basePackage", context.getBasePackage());
			
			String fileName = context.getOutputFile();
			fileName = replaceBasePackage(basefileName, context.getBasePackageAsPath());
			fileName = replaceCompositeName(basefileName, composite.getType());
			context.setOutputFile(fileName);
			
			generateFile(context, vc);
		}
	}

}
