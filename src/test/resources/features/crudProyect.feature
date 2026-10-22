Feature: Gestion de proyectos en Todo.ly

  @Regression
  Scenario: Crear, actualizar y eliminar un proyecto
    Given tengo credenciales validas para Todo.ly
    When creo un proyecto nuevo
    Then la respuesta debe confirmar que el proyecto fue creado
    And la respuesta debe cumplir el schema createProjectSchema1.json

    When actualizo el nombre del proyecto creado
    Then la respuesta debe confirmar que el proyecto fue actualizado

    When elimino el usuario creado
    Then la respuesta debe confirmar que el usuario fue eliminado