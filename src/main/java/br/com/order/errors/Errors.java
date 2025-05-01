package br.com.order.errors;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Errors {

    // GENERICS
    public static final String FIELD_IS_REQUIRED = "Field is required";
    public static final String QUERY_PARAMS_REQUERIDO = "Parâmetro é requerido";
    public static final String VALOR_MAIOR_QUE_0 = "O valor deve ser maior que zero";

    // Pedido
    public static final String PEDIDO_VALOR_TOTAL_ZERO = "Pedido possuí valor para pagamento zerado";
    public static final String FORMA_PAGAMENTO_NAO_DISPONIVEL = "Forma de pagamento não está disponível";
    public static final String PEDIDO_STATUS_DIFERENTE_RECEBIDO = "Status do pedido é diferente de recebido";
    public static final String VALOR_TOTAL_PEDIDO_DIFERENTE_SOMATORIO_ITENS = "Valor total do pedido é diferente do somatório dos itens";
    public static final String ITEMS_LIST_SHOULD_HAVE_AT_LEAST_ONE_ITEM = "The items list should have at least one item";
    public static final String STATUS_INVALIDO = "O pedido deve estar em preparação para ir para pronto ou pronto para ir para finalizado.";
    public static final String PEDIDO_PAGAMENTO_INVALIDO = "Pagamento não encontrado.";
    public static final String ORDER_NOT_FOUND = "Order not found";

    // Paginação
    public static final String PAGE_MINIMA = "Página miníma é 1";

    // Cliente
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";

    //ENUMS
    public static final String ENUM_ATIVO_INVALIDO = "O valor informado está invalido. Valores permitidos [S, N]";

}
