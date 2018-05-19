package br.com.gbessa.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gbessa.cursomc.domain.ItemPedido;
import br.com.gbessa.cursomc.domain.PagamentoComBoleto;
import br.com.gbessa.cursomc.domain.Pedido;
import br.com.gbessa.cursomc.enums.EstadoPagamento;
import br.com.gbessa.cursomc.repositories.ItemPedidoRepository;
import br.com.gbessa.cursomc.repositories.PagamentoRepository;
import br.com.gbessa.cursomc.repositories.PedidoRepository;
import br.com.gbessa.cursomc.repositories.ProdutoRepository;
import br.com.gbessa.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;    
    
    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    public Pedido find(Integer id) {
	Optional<Pedido> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
    
    public Pedido insert(Pedido obj) {
	obj.setId(null);
	obj.setInstante(new Date());
	obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
	obj.getPagamento().setPedido(obj);
	if (obj.getPagamento() instanceof PagamentoComBoleto) {
	    PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
	    boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
	}
	obj = repo.save(obj);
	
	pagamentoRepository.save(obj.getPagamento());
	
	for (ItemPedido ip : obj.getItens()) {
	    ip.setDesconto(0.0);
	    ip.setPreco(produtoRepository.findById(ip.getProduto().getId()).orElse(null).getPreco());
	    ip.setPedido(obj);
	    itemPedidoRepository.save(ip);
	}
	
	return obj;
	
    }
}
