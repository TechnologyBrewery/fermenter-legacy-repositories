package org.bitbucket.askllc.fermenter.cookbook.domain.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.bitbucket.askllc.fermenter.cookbook.domain.bizobj.SimpleDomainBO;
import org.bitbucket.askllc.fermenter.cookbook.domain.enumeration.SimpleDomainEnumeration;
import org.bitbucket.fermenter.stout.messages.MessageManager;
import org.bitbucket.fermenter.stout.messages.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation class for the SimpleDomainManager service.
 * 
 * @see org.bitbucket.askllc.fermenter.cookbook.domain.service.rest.SimpleDomainManagerService
 *
 *      GENERATED STUB CODE - PLEASE *DO* MODIFY
 */
@Service
public class SimpleDomainManagerServiceImpl extends SimpleDomainManagerBaseServiceImpl {

	/**
	 * Demonstrates how a one-off service operation that requires elements not yet supported natively by Fermenter can
	 * be integrated into a Fermenter-generated architecture.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Response getLargeString(String simpleDomainId) {
		final SimpleDomainBO simpleDomain = SimpleDomainBO.findByPrimaryKey(simpleDomainId);
		if (simpleDomain == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			StreamingOutput output = new StreamingOutput() {

				@Override
				public void write(OutputStream output) throws IOException, WebApplicationException {
					try (Writer writer = new BufferedWriter(new OutputStreamWriter(output))) {
						writer.write(simpleDomain.getLargeString());
					}
				}
			};
			return Response.ok(output).build();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getSimpleDomainCountImpl() {
		List<SimpleDomainBO> allSimpleDomains = SimpleDomainBO.findAll();
		return allSimpleDomains != null ? allSimpleDomains.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createAndPropagateErrorMessagesImpl(Integer numErrorMessagesToGenerate) {
		for (int iter = 0; iter < numErrorMessagesToGenerate; iter++) {
			MessageManager.addMessage(MessageUtils.createErrorMessage(RandomStringUtils.randomAlphabetic(5),
					new String[] {}, new Object[] {}));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SimpleDomainBO saveSimpleDomainEntityAndPropagateErrorMessagesImpl(String targetNameValue,
			Integer numErrorMessagesToGenerate) {
		SimpleDomainBO simpleDomain = new SimpleDomainBO();
		simpleDomain.setName(targetNameValue);
		simpleDomain.setType(RandomStringUtils.randomAlphabetic(10));
		simpleDomain.setTheDate1(new Date());
		simpleDomain.setAnEnumeratedValue(
				SimpleDomainEnumeration.values()[RandomUtils.nextInt(0, SimpleDomainEnumeration.values().length)]);
		simpleDomain.save();

		createAndPropagateErrorMessagesImpl(numErrorMessagesToGenerate);

		return simpleDomain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<SimpleDomainBO> selectAllSimpleDomainsImpl() {
		return SimpleDomainBO.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SimpleDomainBO returnNullEntityImpl() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void deleteAllSimpleDomainsImpl() {
		SimpleDomainBO.deleteAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String echoPlusWazzupImpl(String echoRoot) {
		StringBuilder sb = new StringBuilder();
		sb.append(echoRoot).append("Wazzup");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SimpleDomainBO someBusinessOperationImpl(SimpleDomainBO someBusinessEntity, String otherImportantData) {
		// TODO: Add Business Logic Here

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<SimpleDomainBO> selectAllSimpleDomainsWithPagingImpl(Integer startPage, Integer pageSize) {
		return SimpleDomainBO.findAll(startPage, pageSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<SimpleDomainBO> selectAllSimpleDomainsByTypeImpl(String type) {
		return SimpleDomainBO.findByType(type);
	}

	@Override
	protected Integer countNumInputsImpl(List<SimpleDomainBO> input) {
		return input != null ? input.size() : 0;
	}

}