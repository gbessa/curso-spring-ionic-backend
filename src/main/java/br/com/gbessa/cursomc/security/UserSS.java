package br.com.gbessa.cursomc.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.gbessa.cursomc.enums.Perfil;

public class UserSS implements UserDetails{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;
	 
    public UserSS() {
	
    }
        
    
    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
	super();
	this.id = id;
	this.email = email;
	this.senha = senha;
	this.authorities = perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getNome())).collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {	
	return authorities;
    }

    @Override
    public String getPassword() {
	// TODO Auto-generated method stub
	return senha;
    }

    @Override
    public String getUsername() {
	// TODO Auto-generated method stub
	return email;
    }

    @Override
    public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }
    
    public Integer getId() {
	return id;
    }

}
