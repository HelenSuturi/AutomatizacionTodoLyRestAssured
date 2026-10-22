
Feature: Gestión de usuarios en Todo.ly

  @Regression
  Scenario: Crear, actualizar y eliminar un usuario
    When envio los datos para crear un usuario
    Then la respuesta debe confirmar que el usuario fue creado
    And la respuesta debe contener el identificador del usuario

    When actualizo los datos del usuario creado
    Then la respuesta debe confirmar que el usuario fue actualizado

    When elimino el usuario creado
    Then la respuesta debe confirmar que el usuario fue eliminado
