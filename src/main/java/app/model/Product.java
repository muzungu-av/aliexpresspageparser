package app.model;

/**
 * Products pojo class
 */
public class Product {
    private String productId;
    private String sellerId;
    private String oriMinPrice;
    private String oriMaxPrice;
    private String promotionId;
    private String startTime;
    private String endTime;
    private String phase;
    private String productTitle;
    private String minPrice;
    private String maxPrice;
    private String discount;
    private String orders;
    private String productImage;
    private String productDetailUrl;
    private String shopUrl;
    private String totalTranpro3;
    private String productPositiveRate;
    private String productAverageStar;
    private String itemEvalTotalNum;

    public Product() {}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getOriMinPrice() {
        return oriMinPrice;
    }

    public void setOriMinPrice(String oriMinPrice) {
        this.oriMinPrice = oriMinPrice;
    }

    public String getOriMaxPrice() {
        return oriMaxPrice;
    }

    public void setOriMaxPrice(String oriMaxPrice) {
        this.oriMaxPrice = oriMaxPrice;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDetailUrl() {
        return productDetailUrl;
    }

    public void setProductDetailUrl(String productDetailUrl) {
        this.productDetailUrl = productDetailUrl;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getTotalTranpro3() {
        return totalTranpro3;
    }

    public void setTotalTranpro3(String totalTranpro3) {
        this.totalTranpro3 = totalTranpro3;
    }

    public String getProductPositiveRate() {
        return productPositiveRate;
    }

    public void setProductPositiveRate(String productPositiveRate) {
        this.productPositiveRate = productPositiveRate;
    }

    public String getProductAverageStar() {
        return productAverageStar;
    }

    public void setProductAverageStar(String productAverageStar) {
        this.productAverageStar = productAverageStar;
    }

    public String getItemEvalTotalNum() {
        return itemEvalTotalNum;
    }

    public void setItemEvalTotalNum(String itemEvalTotalNum) {
        this.itemEvalTotalNum = itemEvalTotalNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return getProductId().equals(product.getProductId());
    }

    @Override
    public int hashCode() {
        return getProductId().hashCode();
    }
}
