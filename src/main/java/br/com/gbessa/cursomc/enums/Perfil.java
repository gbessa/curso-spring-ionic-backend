package br.com.gbessa.cursomc.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");

	private Integer cod;
	private String nome;
	
	private Perfil(Integer cod, String nome) {
		this.cod = cod;
		this.nome = nome;
	}

	public Integer getCod() {
		return cod;
	}

	public String getNome() {
		return nome;
	}
		
	public static Perfil toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
				
		for (Perfil tipo : Perfil.values()) {
			if (cod.equals(tipo.getCod())) {
				return tipo;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
