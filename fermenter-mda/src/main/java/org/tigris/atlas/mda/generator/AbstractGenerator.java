package org.tigris.atlas.mda.generator;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public abstract class AbstractGenerator implements Generator {

	protected static final String VERSION = "version";
	protected static final String ARTIFACT_ID = "artifactId";
	protected static final String GROUP_ID = "groupId";	

	protected final void generateFile(GenerationContext gc, VelocityContext vc) throws GenerationException {
		try {
			Template template = gc.getEngine().getTemplate(gc.getTemplateName());
			long templateLastModified = template.getLastModified();
			File baseFile = null;
			if (gc.isOverwritable()) {
				baseFile = gc.getGeneratedSourceDirectory();
			} else {
				baseFile = gc.getMainSourceDirectory();
			}
			
			String baseFileName = getOutputSubFolder() + gc.getOutputFile();
			String tempFileName = baseFileName + ".tmp";
			
			File destinationFile = new File(baseFile, baseFileName);
			File tempFile = new File(baseFile, tempFileName);
			
			boolean isCleanWrite = false;
			if (destinationFile.exists()) {				
				if (!gc.isOverwritable()) {
					//never overwrite a CM-ed (by declaration) file
					return;
				} else if ( destinationFile.lastModified() < templateLastModified ) {
					//if the template is newer that the destination file, just overwrite
					isCleanWrite = true;
				} 
				
			} else {
				isCleanWrite = true;
			}
			
			File outputFile = ( isCleanWrite || gc.isAppend() ) ? destinationFile : tempFile;
			
			outputFile.getParentFile().mkdirs();
			
			FileWriter fw = new FileWriter(outputFile, true);
			template.merge(vc, fw);
			fw.close();
			
			if (!isCleanWrite) {
				if( gc.isAppend() ) {
					return;
				}
				boolean isContentEqual = FileUtils.contentEquals(destinationFile, tempFile);
				if (!isContentEqual) {
					System.out.println("Updating file: " + destinationFile.getName());
					destinationFile.delete();
					tempFile.renameTo(destinationFile);
				} else {
					tempFile.deleteOnExit();
				}
			}
			
		} catch (Exception ex) {
			throw new GenerationException("Unable to generate file", ex);
		}
	}
	
	/**
	 * Provides common velocity attributes from the project perspective
	 * @param gc The generation context for this generator
	 * @return A defaulted <tt>VelocityContext</tt> instance
	 */
	protected VelocityContext getNewVelocityContext(GenerationContext gc) {
		VelocityContext vc = new VelocityContext();
		//these are common attribute that are useful across context instances:
		vc.put(GROUP_ID, gc.getGroupId());
		vc.put(ARTIFACT_ID, gc.getArtifactId());
		vc.put(VERSION, gc.getVersion());
		
		return vc;
		
	}

	protected abstract String getOutputSubFolder();
	
	protected final String replaceBasePackage(String original, String basePackage) {
		return StringUtils.replace(original, "${basePackage}", basePackage);
	}

	protected final String replaceFormName(String original, String formName) {
		return StringUtils.replace(original, "${formName}", formName);
	}

	protected final String replaceEntityName(String original, String entityName) {
		return StringUtils.replace(original, "${entityName}", entityName);
	}

	protected final String replaceServiceName(String original, String serviceName) {
		return StringUtils.replace(original, "${serviceName}", serviceName);
	}
	
	protected final String replaceEnumerationName(String original, String enumerationName) {
		return StringUtils.replace(original, "${enumerationName}", enumerationName);
	}
	
	protected final String replaceProjectName(String original, String projectName) {
		return StringUtils.replace(original, "${projectName}", projectName);
	}
	
	protected final String replaceCompositeName(String original, String compositeName) {
		return StringUtils.replace(original, "${compositeName}", compositeName);
	}
	
}
