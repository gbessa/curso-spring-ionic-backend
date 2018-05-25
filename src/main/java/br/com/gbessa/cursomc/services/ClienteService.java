package br.com.gbessa.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.gbessa.cursomc.domain.Cidade;
import br.com.gbessa.cursomc.domain.Cliente;
import br.com.gbessa.cursomc.domain.Endereco;
import br.com.gbessa.cursomc.dto.ClienteDTO;
import br.com.gbessa.cursomc.dto.ClienteNewDTO;
import br.com.gbessa.cursomc.enums.Perfil;
import br.com.gbessa.cursomc.enums.TipoCliente;
import br.com.gbessa.cursomc.repositories.CidadeRepository;
import br.com.gbessa.cursomc.repositories.ClienteRepository;
import br.com.gbessa.cursomc.repositories.EnderecoRepository;
import br.com.gbessa.cursomc.security.UserSS;
import br.com.gbessa.cursomc.services.exceptions.AuthorizationException;
import br.com.gbessa.cursomc.services.exceptions.DataIntegrityException;
import br.com.gbessa.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;
    
    @Autowired
    private CidadeRepository cidadeRepository;    

    @Autowired
    private EnderecoRepository enderecoRepository;   
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private S3Service s3Service;
    
    @Autowired
    private ImageService imageService;
    
    @Value("${img.prefix.client.profile}")
    private String imgPrefix;
    
    @Value("${img.profile.size}")
    private int imgSize;
        
    public Cliente find(Integer id) {
	
	UserSS user = UserService.authenticated();
	if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
	    throw new AuthorizationException("Acesso negado");
	}
	
	Optional<Cliente> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    
    public Cliente findByEmail(String email) {
	UserSS user = UserService.authenticated();
	if (user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
	    throw new AuthorizationException("Acesso negado");
	}
	
	Cliente obj = repo.findByEmail(email);
	if (obj == null) {
	    throw new ObjectNotFoundException(
		    	"Objeto não encontrado. Email: " + email + ", Tipo: " + Cliente.class.getName());
	}
	return obj;
    }
    
    public List<Cliente> findAll() {
	return repo.findAll();
    }

    @Transactional
    public Cliente insert(Cliente obj) {
	obj.setId(null); // Para garantir que o save vai inserir e não atualizar, caso algum id seja mandado
	obj = repo.save(obj);
	enderecoRepository.saveAll(obj.getEnderecos());
	return obj;
    }

    public Cliente update(Cliente obj) {
	Cliente newObj = find(obj.getId());
	updateData(newObj, obj);
	return repo.save(newObj);
    }

    private void updateData(Cliente newObj, Cliente obj) {
	newObj.setNome(obj.getNome());
	newObj.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
	find(id);
	try {
	    repo.deleteById(id);	    
	}
	catch (DataIntegrityViolationException e) {
	    throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
	}
    }
    
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
	
	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
	return repo.findAll(pageRequest);
	
    }
    
    public Cliente fromDTO(ClienteDTO objDto) {
	return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }
    
    public Cliente fromDTO(ClienteNewDTO objDto) {
	Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha()));
	Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());
	Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid.get());
	cli.getEnderecos().add(end);
	cli.getTelefones().add(objDto.getTelefone1());
	if (objDto.getTelefone2() != null) {
	    cli.getTelefones().add(objDto.getTelefone2());
	}
	if (objDto.getTelefone3() != null) {
	    cli.getTelefones().add(objDto.getTelefone3());
	}
	return cli;
    }
    
    public URI uploadProfilePicture(MultipartFile multipartFile) {
	
	UserSS user = UserService.authenticated();
	if (user == null) {
		throw new AuthorizationException("Acesso negado");
    	}
	
	BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
	jpgImage = imageService.cropSquare(jpgImage);
	jpgImage = imageService.resize(jpgImage, imgSize);
	
	String fileName = imgPrefix + user.getId() + ".jpg";
	
	return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "iamge");
	
    }
}
