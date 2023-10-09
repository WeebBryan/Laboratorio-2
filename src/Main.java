import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Cliente> clientes = new ArrayList<>();
		List<Venda> vendas = new ArrayList<>();
		Map<String, Integer> tipoCachorroMaisVendido = new HashMap<>();
		Map<String, Integer> tipoBebidaMaisVendida = new HashMap<>();
		double valorTotalArrecadado = 0.0;
		double valorTotalDescontos = 0.0;

		while (true) {
			System.out.println("1 - Cadastrar Aluno");
			System.out.println("2 - Cadastrar Professor ou Servidor");
			System.out.println("3 - Realizar Venda");
			System.out.println("4 - Visualizar Vendas");
			System.out.println("5 - Quantos cachorros-quentes foram vendidos no total?");
			System.out.println(
					"6 - Informe o número de cachorros-quentes vendidos para professores, alunos e servidores");
			System.out.println("7 - Qual é o tipo de cachorro quente mais vendido?");
			System.out.println("8 - Qual é o tipo de bebida mais vendida?");
			System.out.println("9 - Qual é o valor total arrecadado com as vendas?");
			System.out.println("10 - Qual é o valor total dado em descontos?");
			System.out.println("11 - Sair");
			System.out.print("Escolha uma opção: ");
			int opcao = scanner.nextInt();

			if (opcao == 1) {
				System.out.print("Digite o nome do aluno: ");
				String nomeAluno = scanner.next();
				System.out.print("Digite o número de matrícula do aluno: ");
				String matriculaAluno = scanner.next();
				Cliente aluno = new Cliente(nomeAluno, matriculaAluno);
				clientes.add(aluno);
				System.out.println("Aluno cadastrado com sucesso!");
			} else if (opcao == 2) {
				System.out.print("Digite o nome do professor ou servidor: ");
				String nomeCliente = scanner.next();
				System.out.print("Digite o SIAPE do professor ou servidor: ");
				String siapeCliente = scanner.next();
				Cliente professorServidor = new Cliente(nomeCliente, siapeCliente);
				clientes.add(professorServidor);
				System.out.println("Professor ou servidor cadastrado com sucesso!");
			} else if (opcao == 3) {
				System.out.print("Digite a identificação do cliente (Matrícula ou SIAPE): ");
				String identificacao = scanner.next();
				Cliente cliente = null;

				for (Cliente c : clientes) {
					if (c.getIdentificacao().equalsIgnoreCase(identificacao)) {
						cliente = c;
						break;
					}
				}

				if (cliente == null) {
					System.out.println("Cliente não encontrado.");
				} else {
					List<CachorroQuente> cachorrosVendidos = new ArrayList<>();
					List<String> ingredientesAdicionais = new ArrayList<>();
					String bebida = "";

					while (true) {
						System.out.println(
								"Escolha a proteína do cachorro-quente (salsicha, linguiça, frango ou bacon): ");
						String proteina = scanner.next();
						System.out.println(
								"Escolha o queijo do cachorro-quente (mussarela, prato, parmesão ou coalho): ");
						String queijo = scanner.next();

						ingredientesAdicionais.clear();
						System.out.println("Deseja adicionar maionese? (S/N): ");
						if (scanner.next().equalsIgnoreCase("S")) {
							ingredientesAdicionais.add("maionese");
						}
						System.out.println("Deseja adicionar ketchup? (S/N): ");
						if (scanner.next().equalsIgnoreCase("S")) {
							ingredientesAdicionais.add("ketchup");
						}
						System.out.println("Deseja adicionar ovo? (S/N): ");
						if (scanner.next().equalsIgnoreCase("S")) {
							ingredientesAdicionais.add("ovo");
						}
						System.out.println("Deseja adicionar batata palha? (S/N): ");
						if (scanner.next().equalsIgnoreCase("S")) {
							ingredientesAdicionais.add("batata palha");
						}

						System.out.println("Escolha a bebida (Coca-cola, Del Rio ou Suco do Chaves): ");
						bebida = scanner.next();

						CachorroQuente cachorroQuente = new CachorroQuente(proteina, queijo, ingredientesAdicionais,
								bebida);
						cachorrosVendidos.add(cachorroQuente);

						System.out.println("Deseja adicionar outro cachorro-quente? (S/N): ");
						if (scanner.next().equalsIgnoreCase("N")) {
							break;
						}
					}

					Venda venda = new Venda(cliente);
					for (CachorroQuente cq : cachorrosVendidos) {
						venda.adicionarCachorroQuente(cq);
						valorTotalArrecadado += cq.getPreco();
						valorTotalDescontos += venda.aplicarDesconto();
						atualizarTipoMaisVendido(tipoCachorroMaisVendido, cq.getProteina());
						atualizarTipoMaisVendido(tipoBebidaMaisVendida, bebida);
					}
					vendas.add(venda);

					System.out.println("Venda realizada com sucesso!");
				}
			} else if (opcao == 4) {
				System.out.println("***** Vendas Realizadas *****");
				for (Venda venda : vendas) {
					Cliente clienteVenda = venda.getCliente();
					System.out.println(
							"Aluno: " + clienteVenda.getNome() + " - Matrícula: " + clienteVenda.getIdentificacao());
					System.out.println("Cachorro quente:");
					for (CachorroQuente cq : venda.getCachorrosQuentes()) {
						System.out.println("- Tipo: " + cq.getProteina());
						System.out.println("- Ingredientes: " + String.join(", ", cq.getIngredientesAdicionais()));
						System.out.println("- Bebida: " + cq.getBebida());
					}
					System.out.println("*****************************");
				}
			} else if (opcao == 5) {
				int totalCachorrosVendidos = vendas.stream().mapToInt(venda -> venda.getCachorrosQuentes().size())
						.sum();
				System.out.println("Total de cachorros-quentes vendidos no total: " + totalCachorrosVendidos);
			} else if (opcao == 6) {
				int totalCachorrosAlunos = 0;
				int totalCachorrosProfessores = 0;
				int totalCachorrosServidores = 0;

				for (Venda venda : vendas) {
					Cliente clienteVenda = venda.getCliente();
					int quantidadeCachorros = venda.getCachorrosQuentes().size();
					if (clienteVenda.getIdentificacao().startsWith("S")
							|| clienteVenda.getIdentificacao().startsWith("s")) {
						totalCachorrosProfessores += quantidadeCachorros;
					} else if (clienteVenda.getIdentificacao().startsWith("P")
							|| clienteVenda.getIdentificacao().startsWith("p")) {
						totalCachorrosServidores += quantidadeCachorros;
					} else {
						totalCachorrosAlunos += quantidadeCachorros;
					}
				}

				System.out.println("Total de cachorros-quentes vendidos para alunos: " + totalCachorrosAlunos);
				System.out
						.println("Total de cachorros-quentes vendidos para professores: " + totalCachorrosProfessores);
				System.out.println("Total de cachorros-quentes vendidos para servidores: " + totalCachorrosServidores);
			} else if (opcao == 7) {
				String tipoMaisVendido = encontrarTipoMaisVendido(tipoCachorroMaisVendido);
				System.out.println("O tipo de cachorro quente mais vendido é: " + tipoMaisVendido);
			} else if (opcao == 8) {
				String bebidaMaisVendida = encontrarTipoMaisVendido(tipoBebidaMaisVendida);
				System.out.println("A bebida mais vendida é: " + bebidaMaisVendida);
			} else if (opcao == 9) {
				System.out.println("Valor total arrecadado com as vendas: R$ " + valorTotalArrecadado);
			} else if (opcao == 10) {
				System.out.println("Valor total dado em descontos: R$ " + valorTotalDescontos);
			} else if (opcao == 11) {
				System.out.println("Encerrando o programa.");
				scanner.close();
				System.exit(0);
			} else {
				System.out.println("Opção inválida. Tente novamente.");
			}
		}
	}

	private static void atualizarTipoMaisVendido(Map<String, Integer> tipoMaisVendido, String tipo) {
		tipoMaisVendido.put(tipo, tipoMaisVendido.getOrDefault(tipo, 0) + 1);
	}

	private static String encontrarTipoMaisVendido(Map<String, Integer> tipoMaisVendido) {
		String maisVendido = "";
		int quantidade = 0;

		for (Map.Entry<String, Integer> entry : tipoMaisVendido.entrySet()) {
			if (entry.getValue() > quantidade) {
				quantidade = entry.getValue();
				maisVendido = entry.getKey();
			}
		}

		return maisVendido;
	}
}