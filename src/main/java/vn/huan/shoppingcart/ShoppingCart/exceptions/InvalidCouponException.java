package vn.huan.shoppingcart.ShoppingCart.exceptions;

public class InvalidCouponException extends  RuntimeException{
    public InvalidCouponException (String message){
        super(message);
    }

}
