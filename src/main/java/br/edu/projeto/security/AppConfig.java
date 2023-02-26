package br.edu.projeto.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

//Configurações de autenticação/autorização para a API de segurança
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "login.xhtml",
                errorPage = "login-error.xhtml"
        )
)

//Configura local e forma onde usuários e papéis/permissões/roles serão consultados/autenticados
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:/PostgresDS",
        callerQuery = "SELECT senha FROM usuario WHERE usuario = ?",
        groupsQuery = "SELECT permissao FROM tipo_permissao JOIN permissao USING (id_tipo_permissao) JOIN usuario USING (id_usuario) WHERE usuario = ?"
//        groupsQuery = "SELECT permissao FROM tipo_permissao JOIN permissao USING (id_tipo_permissao) JOIN product USING (id_product) WHERE product = ?"
)

@ApplicationScoped
public class AppConfig {
	
}
