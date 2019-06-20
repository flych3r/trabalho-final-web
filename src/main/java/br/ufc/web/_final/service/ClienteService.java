package br.ufc.web._final.service;

import br.ufc.web._final.model.Cliente;
import br.ufc.web._final.model.Role;
import br.ufc.web._final.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public void save(Cliente cliente) {

        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));

        Role role = new Role();
        role.setPapel("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        cliente.setRoles(roles);

        clienteRepository.save(cliente);
    }

    public List<Cliente> listClientes() {
        return clienteRepository.findAll();
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente serchById(Long id) {
        return clienteRepository.getOne(id);
    }

    public Cliente serchByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

}
