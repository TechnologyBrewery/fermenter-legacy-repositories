@levelOneRestCache
Feature: REST Client -> Level One Cache

  Scenario: Test that entities are CREATED upon transaction completion via an INDIVIDUAL create call
    Given a new entity created via a rest client within a transaction
    And the entity is validated to not exist outside the current transaction
    When the transaction completes
    Then the entity can be retrieved outside the original transaction

  Scenario: Test that entities are UPDATED upon transaction completion via an INDIVIDUAL update call
    Given a existing entity that is updated within a transaction
    And the update is validated to not exist outside the current transaction
    When the transaction completes
    Then the update can be retrieved outside the original transaction

  Scenario: Test that entities are DELETED upon transaction completion via an INDIVIDUAL delete call
    Given a existing entity that is deleted within a transaction
    And the delete is validated to not exist outside the current transaction
    When the transaction completes
    Then the entity no longer exists outside the original transaction

  Scenario: Get transactional version of a entity when performing a lookup against the rest service
    Given a existing entity that is updated within a transaction
    When a findByPrimary key is performed on that entity
    Then the updated instance is returned

  Scenario: validate that a deleted item is not available via individual lookup within a transaction
    Given a existing entity that is deleted within a transaction
    When a findByPrimary key is performed on that entity
    Then no entity is returned

  Scenario: Test that entities are CREATED upon transaction completion via a BULK save or update call
    Given multiple new entities created via a bulk rest client within a transaction
    And the entities are validated to not exist outside the current transaction
    When the transaction completes
    Then the entities can be retrieved outside the original transaction

  Scenario: Test that entities are UPDATED upon transaction completion via a BULK save or update call
    Given multiple entities updated via a bulk rest client within a transaction
    And the updates are validated to not exist outside the current transaction
    When the transaction completes
    Then the updates can be retrieved outside the original transaction

  Scenario: Test that entities are DELETED upon transaction completion via a BULK save or update call
    Given multiple entities deleted via a bulk rest client within a transaction
    And the deletes are validated to exist outside the current transaction
    When the transaction completes
    Then the entities no longer exists outside the original transaction
  #Scenario: find by example
