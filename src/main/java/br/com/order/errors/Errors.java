package br.com.order.errors;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Errors {

    public static final String FIELD_IS_REQUIRED = "Field is required";

    public static final String ITEMS_LIST_SHOULD_HAVE_AT_LEAST_ONE_ITEM = "The items list should have at least one item";
    public static final String INVALID_STATUS = "Order status invalid";
    public static final String ORDER_NOT_FOUND = "Order not found";

    public static final String MINIMAL_PAGE = "Minimal page is 1";

}
