// -------------------------------------------------------------
//
// This is the main Block Structure used by the application.
// Product data: Code, Title, Price, Category and Description.
//
// Author: Aggelos Stamatiou, November 2019
//
// --------------------------------------------------------------

package com.blockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class ProductBlock {

    private String hash;
    private String previousHash;
    private Integer blockId;
    private String productCode;
    private String productTitle;
    private Double productPrice;
    private String productCategory;
    private String productDescription;
    private Integer productPreviousRecordId;
    private long timestamp;
    private int nonce;

    public ProductBlock(String previousHash, Integer blockId, String productCode, String productTitle,
                        Double productPrice, String productCategory, String productDescription, Integer productPreviousRecordId) throws Exception {
        this.previousHash = previousHash;
        this.blockId = blockId;
        this.productCode = productCode;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productPreviousRecordId = productPreviousRecordId;
        this.timestamp = new Date().getTime();
        this.hash = calculateBlockHash();
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() { return this.previousHash; }

    public Integer getBlockId() {
        return this.blockId;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public String getProductTitle() {
        return this.productTitle;
    }

    public String getProductCategory() {
        return this.productCategory;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public Double getProductPrice() { return productPrice; }

    public long getTimestamp() { return timestamp; }

    public Integer getProductPreviousRecordId() { return productPreviousRecordId; }

    // This method tries to find(mine) the appropriate hash for a given prefix.
    public String mineBlock(int prefix) throws Exception {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash();
        }
        return hash;
    }

    // Calculating Block hash by its current state.
    public String calculateBlockHash() throws Exception {
        String dataToHash = previousHash + timestamp + nonce + blockId + productCode + productTitle + productPrice + productCategory + productDescription + productPreviousRecordId;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new Exception("There was an exception on Block hash calculation.");
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }
}
