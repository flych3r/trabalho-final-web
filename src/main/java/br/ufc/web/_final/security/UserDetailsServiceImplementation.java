package br.ufc.web._final.security;

import javax.transaction.Transactional;

import br.ufc.web._final.model.Cliente;
import br.ufc.web._final.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente == null) {
            throw new UsernameNotFoundException("Cliente não encontrado");
        }

        return new User(cliente.getUsername(), cliente.getPassword(),true,true,
                true,true, cliente.getAuthorities());
    }

}