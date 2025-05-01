package br.com.order.webui.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Descriptions {

    // Class Error Response
    public static final String HTTP_CODE = "HTTP status code of the error";
    public static final String HTTP_DESCRIPTION = "HTTP error description";
    public static final String MESSAGE = "Error message";
    public static final String PATH = "Request path";
    public static final String TIMESTAMP = "Timestamp of the error";
    public static final String FIELDS = "List of fields with validation errors";
    public static final String FIELD = "Name of the field with error";
    public static final String FIELD_MESSAGE = "Error message related to the field";

    public static final String ID = "Unique identifier";
    public static final String PAGE = "Page number";
    public static final String LIMIT = "Number of records to fetch per page";
    public static final String ITEM_ID = "Unique identifier of the item";
    public static final String ITEM_DESCRIPTION = "Item description";
    public static final String ADDITIONAL_INFO = "Optional field to add extra information";
    public static final String ITEM_VALUE = "Item price";
    public static final String ITEMS_LIST = "List of items";
    public static final String QUANTITY_ITEMS = "Quantity of items";
    public static final String ORDER_DATE = "Date the order was placed. Format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String CLIENT_ID = "Unique identifier of the client";
    public static final String VLR_TOTAL = "Total value of the order";
    public static final String ORDER_STATUS = "Order status";

    // Pagination
    public static final String HAS_NEXT = "Indicates if there is a next page with elements";
    public static final String HAS_PREVIOUS = "Indicates if there is a previous page";
    public static final String PAGE_NUMBER = "Current page number";
    public static final String PAGE_SIZE = "Number of elements on the current page";
    public static final String ITEMS = "List of items on the page";
}