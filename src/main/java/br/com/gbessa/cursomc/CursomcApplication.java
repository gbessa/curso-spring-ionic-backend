package br.com.gbessa.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.gbessa.cursomc.domain.Categoria;
import br.com.gbessa.cursomc.domain.Cidade;
import br.com.gbessa.cursomc.domain.Cliente;
import br.com.gbessa.cursomc.domain.Endereco;
import br.com.gbessa.cursomc.domain.Estado;
import br.com.gbessa.cursomc.domain.ItemPedido;
import br.com.gbessa.cursomc.domain.Pagamento;
import br.com.gbessa.cursomc.domain.PagamentoComBoleto;
import br.com.gbessa.cursomc.domain.PagamentoComCartao;
import br.com.gbessa.cursomc.domain.Pedido;
import br.com.gbessa.cursomc.domain.Produto;
import br.com.gbessa.cursomc.enums.EstadoPagamento;
import br.com.gbessa.cursomc.enums.TipoCliente;
import br.com.gbessa.cursomc.repositories.CategoriaRepository;
import br.com.gbessa.cursomc.repositories.CidadeRepository;
import br.com.gbessa.cursomc.repositories.ClienteRepository;
import br.com.gbessa.cursomc.repositories.EnderecoRepository;
import br.com.gbessa.cursomc.repositories.EstadoRepository;
import br.com.gbessa.cursomc.repositories.ItemPedidoRepository;
import br.com.gbessa.cursomc.repositories.PagamentoRepository;
import br.com.gbessa.cursomc.repositories.PedidoRepository;
import br.com.gbessa.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Santa Catarina");

		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "Florianópolis", est2);
		Cidade cid3 = new Cidade(null, "Blumenau", est2);

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));


		Cliente cli1 = new Cliente(null, "Maria Silva", "msilva@gmail.com", "11111111111", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("21-98766666", "21-453462162"));
		Endereco end1 = new Endereco(null, "Rua Flores", "12", "apto 101", "Icarai", "24333-000", cli1, cid1);
		Endereco end2 = new Endereco(null, "Rua Moreira Cesar", "877", "fundos", "Icarai", "24333-000", cli1, cid2);
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
