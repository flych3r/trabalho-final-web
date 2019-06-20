package br.ufc.web._final.config;

import br.ufc.web._final.security.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected UserDetailsServiceImplementation userDetailsServiceImplementation;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                .antMatchers("/").permitAll()

                .antMatchers("/cliente/cadastrar").permitAll()
                .antMatchers("/cliente/salvar").permitAll()
                .antMatchers("/prato/listar_cliente").permitAll()
                .antMatchers("/pedido/adicionar").permitAll()
                .antMatchers("/pedido/remover").permitAll()
                .antMatchers("/pedido/selecionados").permitAll()
                .antMatchers("/pedido/adicionar").permitAll()
                .antMatchers("/pedido/remover").permitAll()
                .antMatchers("/item/listar").permitAll()

                .antMatchers("/pedido/listar").hasRole("USER")
                .antMatchers("/cliente/atualizar").hasRole("USER")
                .antMatchers("/pedido/salvar").hasRole("USER")
                .antMatchers("/cliente/excluir").hasRole("USER")
                .antMatchers("/prato/listar").hasRole("USER")

                .antMatchers("/cliente/listar").hasRole("ADMIN")
                .antMatchers("/pedido/listar_pendente").hasRole("ADMIN")
                .antMatchers("/pedido/enviar").hasRole("ADMIN")
                .antMatchers("/prato/cadastrar").hasRole("ADMIN")
                .antMatchers("/prato/salvar").hasRole("ADMIN")
                .antMatchers("/prato/listar_gerente").hasRole("ADMIN")
                .antMatchers("/prato/excluir").hasRole("ADMIN")
                .antMatchers("/prato/atualizar").hasRole("ADMIN")

                .anyRequest().authenticated()

                .and()
                    .formLogin()
                    .loginPage("/logar")
                    .permitAll()
                    .defaultSuccessUrl("/user")

                //.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

                .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/logar?logout")
                    .permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImplementation).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/images/**");
    }
}
