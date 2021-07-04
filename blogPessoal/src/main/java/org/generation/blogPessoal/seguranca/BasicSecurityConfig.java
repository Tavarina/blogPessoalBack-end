package org.generation.blogPessoal.seguranca;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // --> Habilitar a configuração de web Security
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	
	@Override // --> Sobrescrita de método
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{ //--> throws Exception, trativa de erros
		
		auth.userDetailsService(userDetailsService);
		
		auth.inMemoryAuthentication().withUser("root12").password(passwordEncoder().encode("root12"))
		.authorities("ROLE_ADMIN");
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //--> throws Exception, trativa de erros
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/usuarios/logar").permitAll() // --> Essa config. serve para liberar EndPoints, ou seja, para liberar alguns caminhos dentro do meu controller para que o client tenha acesso a eles sem precisar passar uma chave em token
		.antMatchers(HttpMethod.POST ,"/usuarios/cadastrar").permitAll()
		.anyRequest().authenticated() // --> Todas as outras requisições deverão ser autenticadas
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //--> Indicar qual o tipo de sessão que vamos utilizar // --> STATELESS NÃO VAI GUARDAR SESSÃO
		.and().cors()
		.and().csrf().disable();
	}
	
}
