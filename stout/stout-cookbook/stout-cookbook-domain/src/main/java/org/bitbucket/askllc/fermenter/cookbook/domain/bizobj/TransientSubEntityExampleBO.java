package org.bitbucket.askllc.fermenter.cookbook.domain.bizobj;


import org.bitbucket.fermenter.stout.util.SpringAutowiringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Business object for the TransientSubEntityExample entity.
 * @see org.bitbucket.askllc.fermenter.cookbook.domain.bizobj.TransientSubEntityExampleBaseBO
 *
 * GENERATED STUB CODE - PLEASE *DO* MODIFY
 */
public class TransientSubEntityExampleBO extends TransientSubEntityExampleBaseBO {
	
	public TransientSubEntityExampleBO() {
		super();
		SpringAutowiringUtil.autowireBizObj(this);
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransientSubEntityExampleBO.class);
	
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected void complexValidation() {

	}
	
}